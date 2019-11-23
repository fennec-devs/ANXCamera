package com.android.camera.effect.framework.gles;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.GLUtils;
import android.util.Log;
import com.android.camera.Util;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.nio.ByteBuffer;

public class OpenGlUtils {
    public static final int NOT_INIT = -1;
    public static final int NO_TEXTURE = -1;
    public static final int ON_DRAWN = 1;

    public static void checkGlError(String str) {
        if (GLES20.glGetError() != 0) {
            String str2 = str + ": glError 0x" + Integer.toHexString(r0);
            Log.e("OpenGlUtils", str2);
            throw new RuntimeException(str2);
        }
    }

    public static void dumpSubJpeg(byte[] bArr, String str) {
        BufferedOutputStream bufferedOutputStream;
        Throwable th;
        try {
            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(str));
            bufferedOutputStream.write(bArr);
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
            return;
        } catch (IOException e) {
            Log.e("OpenGlUtils", "Failed to dump sub jpeg data into a file", e);
            return;
        } catch (Throwable th2) {
            th.addSuppressed(th2);
        }
        throw th;
    }

    public static ByteBuffer dumpToJpg(int i, int i2, int i3, int i4, String str) {
        ByteBuffer allocate = ByteBuffer.allocate(i3 * i4 * 4);
        GLES20.glReadPixels(i, i2, i3, i4, 6408, 5121, allocate);
        if (allocate != null) {
            Util.saveBitmap(allocate, i3, i4, Bitmap.Config.ARGB_8888, str);
        }
        allocate.rewind();
        return allocate;
    }

    public static ByteBuffer dumpToJpgFile(int i, int i2, int i3, int i4, String str) {
        ByteBuffer allocate = ByteBuffer.allocate(i3 * i4 * 4);
        GLES20.glReadPixels(i, i2, i3, i4, 6408, 5121, allocate);
        if (allocate != null) {
            saveBitmap(allocate, i3, i4, Bitmap.Config.ARGB_8888, str);
        }
        allocate.rewind();
        return allocate;
    }

    public static int genTexture() {
        int[] iArr = new int[1];
        GLES20.glGenTextures(1, iArr, 0);
        GLES20.glBindTexture(3553, iArr[0]);
        GLES20.glTexParameterf(3553, 10240, 9729.0f);
        GLES20.glTexParameterf(3553, 10241, 9729.0f);
        GLES20.glTexParameterf(3553, 10242, 33071.0f);
        GLES20.glTexParameterf(3553, 10243, 33071.0f);
        return iArr[0];
    }

    public static int getExternalOESTextureID() {
        int[] iArr = new int[1];
        GLES20.glGenTextures(1, iArr, 0);
        GLES20.glBindTexture(36197, iArr[0]);
        GLES20.glTexParameterf(36197, 10241, 9729.0f);
        GLES20.glTexParameterf(36197, 10240, 9729.0f);
        GLES20.glTexParameteri(36197, 10242, 33071);
        GLES20.glTexParameteri(36197, 10243, 33071);
        return iArr[0];
    }

    public static Bitmap getGPUYYY(int i, int i2, int i3, int i4) {
        int i5 = i3 >> 1;
        byte[] bArr = new byte[(i5 * i5 * 4)];
        int i6 = i3 * i4;
        int[] iArr = new int[i6];
        ByteBuffer wrap = ByteBuffer.wrap(bArr);
        wrap.position(0);
        GLES20.glReadPixels(i, i2, i5, i4 >> 1, 6408, 5121, wrap);
        int i7 = 0;
        for (int i8 = 0; i8 < i6; i8++) {
            byte b2 = bArr[i8];
            iArr[i7] = (b2 & 255) | ((b2 << 8) & 65280) | -16777216 | ((b2 << 16) & 16711680);
            i7++;
        }
        return Bitmap.createBitmap(iArr, i3, i4, Bitmap.Config.ARGB_8888);
    }

    public static Bitmap getImageFromAssetsFile(Context context, String str) {
        Bitmap bitmap;
        try {
            InputStream open = context.getResources().getAssets().open(str);
            bitmap = BitmapFactory.decodeStream(open);
            try {
                open.close();
            } catch (IOException e) {
                e = e;
            }
        } catch (IOException e2) {
            e = e2;
            bitmap = null;
            e.printStackTrace();
            return bitmap;
        }
        return bitmap;
    }

    public static int loadProgram(String str, String str2) {
        int[] iArr = new int[1];
        int loadShader = loadShader(str, 35633);
        if (loadShader == 0) {
            Log.d("Load Program", "Vertex Shader Failed");
            return 0;
        }
        int loadShader2 = loadShader(str2, 35632);
        if (loadShader2 == 0) {
            Log.d("Load Program", "Fragment Shader Failed");
            return 0;
        }
        int glCreateProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(glCreateProgram, loadShader);
        GLES20.glAttachShader(glCreateProgram, loadShader2);
        GLES20.glLinkProgram(glCreateProgram);
        GLES20.glGetProgramiv(glCreateProgram, 35714, iArr, 0);
        if (iArr[0] <= 0) {
            Log.d("Load Program", "Linking Failed");
            return 0;
        }
        GLES20.glDeleteShader(loadShader);
        GLES20.glDeleteShader(loadShader2);
        return glCreateProgram;
    }

    public static byte[] loadRawFile(String str) {
        File file = new File(str);
        byte[] bArr = new byte[((int) file.length())];
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
            bufferedInputStream.read(bArr, 0, bArr.length);
            bufferedInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        return bArr;
    }

    private static int loadShader(String str, int i) {
        int[] iArr = new int[1];
        int glCreateShader = GLES20.glCreateShader(i);
        GLES20.glShaderSource(glCreateShader, str);
        GLES20.glCompileShader(glCreateShader);
        GLES20.glGetShaderiv(glCreateShader, 35713, iArr, 0);
        if (iArr[0] != 0) {
            return glCreateShader;
        }
        Log.e("Load Shader Failed", "Compilation\n" + GLES20.glGetShaderInfoLog(glCreateShader));
        return 0;
    }

    public static int loadTexture(Context context, String str) {
        int[] iArr = new int[1];
        GLES20.glGenTextures(1, iArr, 0);
        if (iArr[0] != 0) {
            Bitmap imageFromAssetsFile = getImageFromAssetsFile(context, str);
            GLES20.glBindTexture(3553, iArr[0]);
            GLES20.glTexParameteri(3553, 10240, 9729);
            GLES20.glTexParameteri(3553, 10241, 9729);
            GLES20.glTexParameteri(3553, 10242, 33071);
            GLES20.glTexParameteri(3553, 10243, 33071);
            GLUtils.texImage2D(3553, 0, imageFromAssetsFile, 0);
            imageFromAssetsFile.recycle();
        }
        if (iArr[0] != 0) {
            return iArr[0];
        }
        throw new RuntimeException("Error loading texture.");
    }

    public static int loadTexture(Bitmap bitmap, int i) {
        return loadTexture(bitmap, i, false);
    }

    public static int loadTexture(Bitmap bitmap, int i, boolean z) {
        if (bitmap == null) {
            return -1;
        }
        int[] iArr = new int[1];
        if (i == -1) {
            GLES20.glGenTextures(1, iArr, 0);
            GLES20.glBindTexture(3553, iArr[0]);
            GLES20.glTexParameterf(3553, 10240, 9729.0f);
            GLES20.glTexParameterf(3553, 10241, 9729.0f);
            GLES20.glTexParameterf(3553, 10242, 33071.0f);
            GLES20.glTexParameterf(3553, 10243, 33071.0f);
            GLUtils.texImage2D(3553, 0, bitmap, 0);
        } else {
            GLES20.glBindTexture(3553, i);
            GLUtils.texSubImage2D(3553, 0, 0, 0, bitmap);
            iArr[0] = i;
        }
        if (z) {
            bitmap.recycle();
        }
        return iArr[0];
    }

    public static int loadTexture(Buffer buffer, int i, int i2, int i3) {
        int i4 = i3;
        if (buffer == null) {
            return -1;
        }
        int[] iArr = new int[1];
        if (i4 == -1) {
            GLES20.glGenTextures(1, iArr, 0);
            GLES20.glBindTexture(3553, iArr[0]);
            GLES20.glTexParameterf(3553, 10240, 9729.0f);
            GLES20.glTexParameterf(3553, 10241, 9729.0f);
            GLES20.glTexParameterf(3553, 10242, 33071.0f);
            GLES20.glTexParameterf(3553, 10243, 33071.0f);
            GLES20.glTexImage2D(3553, 0, 6408, i, i2, 0, 6408, 5121, buffer);
        } else {
            GLES20.glBindTexture(3553, i4);
            GLES20.glTexSubImage2D(3553, 0, 0, 0, i, i2, 6408, 5121, buffer);
            iArr[0] = i4;
        }
        return iArr[0];
    }

    public static int loadTexture(Buffer buffer, int i, int i2, int i3, int i4) {
        int i5 = i3;
        if (buffer == null) {
            return -1;
        }
        int[] iArr = new int[1];
        if (i5 == -1) {
            GLES20.glGenTextures(1, iArr, 0);
            GLES20.glBindTexture(3553, iArr[0]);
            GLES20.glTexParameterf(3553, 10240, 9729.0f);
            GLES20.glTexParameterf(3553, 10241, 9729.0f);
            GLES20.glTexParameterf(3553, 10242, 33071.0f);
            GLES20.glTexParameterf(3553, 10243, 33071.0f);
            GLES20.glTexImage2D(3553, 0, 6408, i, i2, 0, 6408, i4, buffer);
        } else {
            GLES20.glBindTexture(3553, i5);
            GLES20.glTexSubImage2D(3553, 0, 0, 0, i, i2, 6408, i4, buffer);
            iArr[0] = i5;
        }
        return iArr[0];
    }

    public static String readShaderFromRawResource(Context context, int i) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(context.getResources().openRawResource(i)));
        StringBuilder sb = new StringBuilder();
        while (true) {
            try {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    return sb.toString();
                }
                sb.append(readLine);
                sb.append(10);
            } catch (IOException e) {
                return null;
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0030 A[SYNTHETIC, Splitter:B:19:0x0030] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0040 A[SYNTHETIC, Splitter:B:25:0x0040] */
    public static boolean saveBitmap(Bitmap bitmap, String str) {
        FileOutputStream fileOutputStream = null;
        try {
            FileOutputStream fileOutputStream2 = new FileOutputStream(new File(str));
            try {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream2);
                try {
                    fileOutputStream2.flush();
                    fileOutputStream2.close();
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    return true;
                }
            } catch (FileNotFoundException e2) {
                e = e2;
                fileOutputStream = fileOutputStream2;
                try {
                    e.printStackTrace();
                    if (fileOutputStream != null) {
                    }
                    return false;
                } catch (Throwable th) {
                    th = th;
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.flush();
                            fileOutputStream.close();
                        } catch (Exception e3) {
                            e3.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                fileOutputStream = fileOutputStream2;
                if (fileOutputStream != null) {
                }
                throw th;
            }
        } catch (FileNotFoundException e4) {
            e = e4;
            e.printStackTrace();
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (Exception e5) {
                    e5.printStackTrace();
                }
            }
            return false;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x003f A[SYNTHETIC, Splitter:B:22:0x003f] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0050 A[SYNTHETIC, Splitter:B:28:0x0050] */
    public static boolean saveBitmap(Buffer buffer, int i, int i2, Bitmap.Config config, String str) {
        if (buffer != null) {
            Bitmap createBitmap = Bitmap.createBitmap(i, i2, config);
            createBitmap.copyPixelsFromBuffer(buffer);
            FileOutputStream fileOutputStream = null;
            try {
                FileOutputStream fileOutputStream2 = new FileOutputStream(new File(str));
                try {
                    createBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream2);
                    try {
                        fileOutputStream2.flush();
                        fileOutputStream2.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    createBitmap.recycle();
                    return true;
                } catch (FileNotFoundException e2) {
                    FileOutputStream fileOutputStream3 = fileOutputStream2;
                    e = e2;
                    fileOutputStream = fileOutputStream3;
                    try {
                        e.printStackTrace();
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.flush();
                                fileOutputStream.close();
                            } catch (Exception e3) {
                                e3.printStackTrace();
                            }
                        }
                        createBitmap.recycle();
                        return false;
                    } catch (Throwable th) {
                        th = th;
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.flush();
                                fileOutputStream.close();
                            } catch (Exception e4) {
                                e4.printStackTrace();
                            }
                        }
                        createBitmap.recycle();
                        throw th;
                    }
                } catch (Throwable th2) {
                    FileOutputStream fileOutputStream4 = fileOutputStream2;
                    th = th2;
                    fileOutputStream = fileOutputStream4;
                    if (fileOutputStream != null) {
                    }
                    createBitmap.recycle();
                    throw th;
                }
            } catch (FileNotFoundException e5) {
                e = e5;
                e.printStackTrace();
                if (fileOutputStream != null) {
                }
                createBitmap.recycle();
                return false;
            }
        }
        return false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0029  */
    public static void savePixelsToJpg(int i, int i2, int i3, ByteBuffer byteBuffer, String str) throws IOException {
        BufferedOutputStream bufferedOutputStream;
        try {
            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(str));
            try {
                Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
                createBitmap.copyPixelsFromBuffer(byteBuffer);
                createBitmap.compress(Bitmap.CompressFormat.JPEG, 50, bufferedOutputStream);
                createBitmap.recycle();
                bufferedOutputStream.close();
            } catch (Throwable th) {
                th = th;
                if (bufferedOutputStream != null) {
                    bufferedOutputStream.close();
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            bufferedOutputStream = null;
            if (bufferedOutputStream != null) {
            }
            throw th;
        }
    }

    public static void updateTextureWidthStride(int i, ByteBuffer byteBuffer, int i2, int i3, int i4) {
        GLES30.glPixelStorei(3314, i4);
        GLES20.glBindTexture(3553, i);
        GLES20.glTexImage2D(3553, 0, 6408, i2, i3, 0, 6408, 5121, byteBuffer);
        GLES30.glPixelStorei(3314, 0);
    }
}
