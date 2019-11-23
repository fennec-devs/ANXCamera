package com.miui.extravideo.interpolation;

import android.graphics.Bitmap;
import android.media.MediaCodec;
import android.media.MediaFormat;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.animation.LinearInterpolator;
import com.miui.extravideo.common.MediaColorConvertUtils;
import com.miui.extravideo.common.MediaDecoderAsync;
import com.miui.extravideo.common.MediaEncoderAsync;
import com.miui.extravideo.common.MediaParamsHolder;
import com.miui.extravideo.common.MediaUtils;
import com.miui.extravideo.deflicker.DeFlickerJni;
import com.miui.extravideo.watermark.WatermarkRenderPipeline;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.jcodec.containers.mp4.boxes.MetaValue;
import org.jcodec.movtool.MetadataEditor;

class VideoInterpolatorAsyncImp {
    private static final String DECODE_THREAD_NAME = "DecodeThread";
    private static final String ENCODE_THREAD_NAME = "EncodeThread";
    private static final int FRAME_INDEX_BEGIN_INTERPOLATION = 45;
    private static final int FRAME_RATE_NORMAL = 30;
    private static final int FRAME_RATE_ORIGIN = 240;
    private static final int FRAME_RATE_TARGET = 960;
    private static final int FRAME_SIZE_ANIMATION_DURING = 8;
    private static final int FRAME_SIZE_INTERPOLATION = 4;
    private static final int FRAME_SKIP_SIZE_ANIMATION_BEGIN = 16;
    private static final int FRAME_SKIP_SIZE_ORIGIN_SPEED_TO_NORMAL = 8;
    private static final int FRAME_SKIP_SIZE_TARGET_SPEED_TO_NORMAL = 32;
    private static final int FRAME_SKIP_SIZE_TARGET_SPEED_TO_ORIGIN = 4;
    private static final int INTERPOLATOR_ACCURACY = 1;
    private static final int MAX_BUFFER_SIZE = 15;
    private static final int TOTAL_FRAME_SIZE = 300;
    private static Boolean bInitWatemarkPipeline = false;
    /* access modifiers changed from: private */
    public MediaCodecHandlerThread mDecodeThread;
    /* access modifiers changed from: private */
    public MediaDecoderAsync mDecoder;
    private int mDegree;
    private final String mDstPath;
    private EncodeListener mEncodeListener;
    /* access modifiers changed from: private */
    public MediaCodecHandlerThread mEncodeThread;
    /* access modifiers changed from: private */
    public MediaEncoderAsync mEncoder;
    /* access modifiers changed from: private */
    public int[] mFrameMapping = new int[300];
    private int mOriginVideoTrack;
    /* access modifiers changed from: private */
    public final BlockingQueue<EncodeBufferHolder> mQueue = new LinkedBlockingQueue(15);
    private final String mSrcPath;
    /* access modifiers changed from: private */
    public final boolean mSupportDeFlicker;
    private final boolean mSupportEditor;
    private final boolean mSupportWatermark;
    private WatermarkRenderPipeline mWatermarkPipeline;

    private class DecodeUpdateListener implements MediaDecoderAsync.DecodeUpdateListener {
        private boolean mBeginInterpolator = false;
        private DeFlickerJni mDeFlickerJni;
        private int mDecodeIndex = 0;
        private byte[] mEncodeBuffer;
        private final int mHeight;
        private final MediaInterpolator mMediaInterpolator;
        private MediaParamsHolder mMediaParamsHolder;
        private int mRepresentativeIndex = 0;
        private final int mWidth;
        private byte[] mYuvBuffer;

        public DecodeUpdateListener(MediaParamsHolder mediaParamsHolder) {
            this.mMediaParamsHolder = mediaParamsHolder;
            this.mWidth = this.mMediaParamsHolder.videoWidth;
            this.mHeight = this.mMediaParamsHolder.videoHeight;
            this.mMediaInterpolator = new MediaInterpolator(this.mWidth, this.mHeight, 1);
            if (VideoInterpolatorAsyncImp.this.mSupportDeFlicker) {
                this.mDeFlickerJni = new DeFlickerJni(this.mWidth, this.mHeight);
            }
        }

        private void initEncodeBuffer(MediaCodec.BufferInfo bufferInfo) {
            if (!(this.mEncodeBuffer == null || this.mEncodeBuffer.length == bufferInfo.size)) {
                this.mEncodeBuffer = null;
            }
            if (this.mEncodeBuffer == null) {
                this.mEncodeBuffer = new byte[bufferInfo.size];
            }
        }

        private void initYuvBuffer() {
            if (this.mYuvBuffer == null) {
                this.mYuvBuffer = new byte[(((this.mWidth * this.mHeight) * 3) / 2)];
            }
        }

        private void notifyEncodeStop() {
            VideoInterpolatorAsyncImp.this.mEncoder.stop();
            VideoInterpolatorAsyncImp.this.mQueue.clear();
        }

        private void release() {
            VideoInterpolatorAsyncImp.this.mDecoder.release();
            this.mMediaInterpolator.release();
            if (this.mDeFlickerJni != null) {
                this.mDeFlickerJni.release();
            }
            VideoInterpolatorAsyncImp.this.mDecodeThread.quitSafely();
        }

        public void onDecodeBuffer(ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo) {
            initEncodeBuffer(bufferInfo);
            initYuvBuffer();
            byteBuffer.get(this.mEncodeBuffer);
            byteBuffer.clear();
            if (this.mEncodeBuffer.length > 0) {
                MediaColorConvertUtils.convertDecodeColorToEncode(this.mEncodeBuffer, this.mYuvBuffer, this.mMediaParamsHolder);
                if (this.mDeFlickerJni != null) {
                    this.mDeFlickerJni.process(this.mYuvBuffer);
                }
                if (this.mBeginInterpolator) {
                    this.mMediaInterpolator.configInterpolationSize(4, this.mYuvBuffer);
                    while (this.mMediaInterpolator.hasNext()) {
                        this.mMediaInterpolator.nextByteBuffer(this.mYuvBuffer);
                        VideoInterpolatorAsyncImp.this.putBufferToQueue(this.mYuvBuffer, MediaUtils.computePresentationTime(this.mDecodeIndex, VideoInterpolatorAsyncImp.this.mEncoder.getFrameRate()), this.mRepresentativeIndex);
                        this.mDecodeIndex++;
                        this.mRepresentativeIndex++;
                    }
                } else {
                    VideoInterpolatorAsyncImp.this.putBufferToQueue(this.mYuvBuffer, MediaUtils.computePresentationTime(this.mDecodeIndex, VideoInterpolatorAsyncImp.this.mEncoder.getFrameRate()), this.mRepresentativeIndex);
                    this.mDecodeIndex++;
                    if (this.mDecodeIndex >= 45) {
                        this.mRepresentativeIndex += 4;
                    } else {
                        this.mRepresentativeIndex += 32;
                    }
                }
                if (this.mDecodeIndex == 44) {
                    this.mBeginInterpolator = true;
                }
            }
        }

        public void onDecodeStop(boolean z) {
            release();
            if (z) {
                VideoInterpolatorAsyncImp.this.putEndFlagToQueue();
            }
        }

        public void onError() {
            release();
            notifyEncodeStop();
            VideoInterpolatorAsyncImp.this.notifyTaskError();
        }

        public void onFrameDecodeBegin(int i, long j) {
            if (i == 45) {
                VideoInterpolatorAsyncImp.this.mDecoder.setSkipFrameTimes(1);
            }
        }

        public void onOutputFormatChange(MediaFormat mediaFormat) {
            this.mMediaParamsHolder.setFormat(mediaFormat);
        }
    }

    private class EncodeUpdateListener implements MediaEncoderAsync.EncodeUpdateListener {
        private int mEncodeIndex;
        private boolean mIsEnd;

        private EncodeUpdateListener() {
            this.mIsEnd = false;
            this.mEncodeIndex = 0;
        }

        private void configFrame(EncodeBufferHolder encodeBufferHolder, EncodeBufferHolder encodeBufferHolder2) {
            encodeBufferHolder.data = encodeBufferHolder2.data;
            encodeBufferHolder.presentationTimeUs = MediaUtils.computePresentationTime(this.mEncodeIndex, VideoInterpolatorAsyncImp.this.mEncoder.getFrameRate());
            encodeBufferHolder.flag = encodeBufferHolder2.flag;
        }

        private void notifyDecodeStop() {
            VideoInterpolatorAsyncImp.this.mDecoder.stop();
            VideoInterpolatorAsyncImp.this.mQueue.clear();
        }

        private void release() {
            VideoInterpolatorAsyncImp.this.mEncoder.release();
            VideoInterpolatorAsyncImp.this.mEncodeThread.quitSafely();
        }

        public void onEncodeEnd(boolean z) {
            release();
            if (z) {
                VideoInterpolatorAsyncImp.this.notifyTaskFinish();
            } else {
                VideoInterpolatorAsyncImp.this.deleteBadFile();
            }
        }

        public void onError() {
            release();
            notifyDecodeStop();
            VideoInterpolatorAsyncImp.this.deleteBadFile();
            VideoInterpolatorAsyncImp.this.notifyTaskError();
        }

        public void onInputBufferAvailable(EncodeBufferHolder encodeBufferHolder) {
            EncodeBufferHolder access$1000 = !this.mIsEnd ? VideoInterpolatorAsyncImp.this.getBufferFromQueue() : null;
            if (access$1000 == null) {
                encodeBufferHolder.flag = 0;
                encodeBufferHolder.data = null;
            } else if (access$1000.flag == 4) {
                encodeBufferHolder.flag = access$1000.flag;
                this.mIsEnd = true;
            } else if (this.mEncodeIndex >= 300) {
                encodeBufferHolder.flag = 4;
                this.mIsEnd = true;
                notifyDecodeStop();
            } else if (VideoInterpolatorAsyncImp.this.mFrameMapping[this.mEncodeIndex] == access$1000.representativeIndex) {
                configFrame(encodeBufferHolder, access$1000);
                this.mEncodeIndex++;
            } else {
                encodeBufferHolder.flag = 0;
                encodeBufferHolder.data = null;
            }
        }
    }

    private static class MediaCodecHandlerThread extends HandlerThread {
        /* access modifiers changed from: private */
        public Handler mHandler = new Handler(getLooper());

        MediaCodecHandlerThread(String str) {
            super(str);
            start();
        }

        public void run() {
            super.run();
            Log.d(getName(), "thread exit");
        }
    }

    VideoInterpolatorAsyncImp(String str, String str2, boolean z, Bitmap bitmap, float[] fArr, boolean z2) {
        this.mSupportDeFlicker = z;
        this.mSupportWatermark = bitmap != null;
        this.mSupportEditor = z2;
        this.mDstPath = str2;
        this.mSrcPath = str;
        this.mDecodeThread = new MediaCodecHandlerThread(DECODE_THREAD_NAME);
        this.mEncodeThread = new MediaCodecHandlerThread(ENCODE_THREAD_NAME);
        this.mDecoder = new MediaDecoderAsync(str, this.mDecodeThread.mHandler);
        MediaParamsHolder mediaParamsHolder = this.mDecoder.getMediaParamsHolder();
        this.mDegree = mediaParamsHolder.videoDegree;
        MediaEncoderAsync mediaEncoderAsync = new MediaEncoderAsync(mediaParamsHolder.videoWidth, mediaParamsHolder.videoHeight, mediaParamsHolder.videoDegree, mediaParamsHolder.mimeType, this.mDstPath, this.mEncodeThread.mHandler);
        this.mEncoder = mediaEncoderAsync;
        this.mDecoder.setListener(new DecodeUpdateListener(mediaParamsHolder));
        this.mEncoder.setListener(new EncodeUpdateListener());
        if (this.mSupportWatermark) {
            this.mWatermarkPipeline = new WatermarkRenderPipeline();
            this.mWatermarkPipeline.init(mediaParamsHolder.videoWidth, mediaParamsHolder.videoHeight, bitmap, fArr, mediaParamsHolder.videoDegree);
        }
        initMapping();
    }

    private void addMetaData() {
        try {
            MetadataEditor createFrom = MetadataEditor.createFrom(new File(this.mDstPath));
            Map<String, MetaValue> keyedMeta = createFrom.getKeyedMeta();
            long currentTimeMillis = System.currentTimeMillis();
            keyedMeta.put("com.xiaomi.capture_framerate", MetaValue.createInt(FRAME_RATE_TARGET));
            if (this.mSupportEditor && this.mOriginVideoTrack != -1) {
                keyedMeta.put("com.xiaomi.capture_origin_track", MetaValue.createInt(this.mOriginVideoTrack));
            }
            createFrom.save(true);
            Log.d("jcodec", "cost: " + (System.currentTimeMillis() - currentTimeMillis));
        } catch (Exception e) {
            Log.w("jcodec", "error \n", e);
        }
    }

    private void addOriginVideoToTrack() throws Exception {
        if (this.mSupportEditor) {
            File file = new File(this.mDstPath);
            File file2 = new File(file.getParent(), ".tempResult");
            file2.delete();
            file.renameTo(file2);
            this.mOriginVideoTrack = MediaUtils.mixVideo(file.getAbsolutePath(), file2.getAbsolutePath(), this.mSrcPath, this.mDegree);
            if (this.mOriginVideoTrack != -1) {
                file2.delete();
                return;
            }
            file.delete();
            file2.renameTo(file);
        }
    }

    /* access modifiers changed from: private */
    public void deleteBadFile() {
        File file = new File(this.mDstPath);
        if (file.exists()) {
            file.delete();
        }
    }

    private int getAddIndexByFrame(int i) {
        LinearInterpolator linearInterpolator = new LinearInterpolator();
        if (i < 45) {
            return 32;
        }
        if (i < 53) {
            return 1 + Math.round(15.0f * (1.0f - linearInterpolator.getInterpolation(((float) (i - 45)) / 8.0f)));
        }
        return 1;
    }

    /* access modifiers changed from: private */
    public EncodeBufferHolder getBufferFromQueue() {
        try {
            return this.mQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void initMapping() {
        for (int i = 0; i < this.mFrameMapping.length; i++) {
            int addIndexByFrame = getAddIndexByFrame(i);
            if (i < 1) {
                this.mFrameMapping[i] = 0;
            } else {
                this.mFrameMapping[i] = this.mFrameMapping[i - 1] + addIndexByFrame;
            }
        }
    }

    /* access modifiers changed from: private */
    public void notifyTaskError() {
        if (this.mEncodeListener != null) {
            this.mEncodeListener.onError();
            this.mEncodeListener = null;
        }
    }

    /* access modifiers changed from: private */
    public void notifyTaskFinish() {
        boolean z;
        try {
            addOriginVideoToTrack();
            z = false;
        } catch (Exception e) {
            e.printStackTrace();
            z = true;
        }
        if (!z) {
            addMetaData();
            if (this.mEncodeListener != null) {
                this.mEncodeListener.onEncodeFinish();
                this.mEncodeListener = null;
            }
        } else if (this.mEncodeListener != null) {
            this.mEncodeListener.onError();
            this.mEncodeListener = null;
        }
    }

    /* access modifiers changed from: private */
    public void putBufferToQueue(byte[] bArr, long j, int i) {
        if (this.mSupportWatermark && this.mWatermarkPipeline != null) {
            this.mWatermarkPipeline.process(bArr);
        }
        EncodeBufferHolder encodeBufferHolder = new EncodeBufferHolder();
        encodeBufferHolder.data = Arrays.copyOf(bArr, bArr.length);
        encodeBufferHolder.flag = 0;
        encodeBufferHolder.presentationTimeUs = j;
        encodeBufferHolder.representativeIndex = i;
        try {
            this.mQueue.put(encodeBufferHolder);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void putEndFlagToQueue() {
        EncodeBufferHolder encodeBufferHolder = new EncodeBufferHolder();
        encodeBufferHolder.flag = 4;
        try {
            this.mQueue.put(encodeBufferHolder);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: package-private */
    public void doDecodeAndEncode() {
        this.mDecoder.setSkipFrameTimes(8);
        try {
            this.mDecoder.start();
            this.mEncoder.start();
        } catch (Exception e) {
            e.printStackTrace();
            if (this.mEncodeListener != null) {
                this.mDecodeThread.mHandler.post(new Runnable() {
                    public void run() {
                        VideoInterpolatorAsyncImp.this.notifyTaskError();
                    }
                });
            }
            this.mEncodeThread.quitSafely();
            this.mDecodeThread.quitSafely();
        }
    }

    public void setEncodeListener(EncodeListener encodeListener) {
        this.mEncodeListener = encodeListener;
    }
}
