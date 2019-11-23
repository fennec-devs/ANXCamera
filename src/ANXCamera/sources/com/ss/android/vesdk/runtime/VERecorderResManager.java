package com.ss.android.vesdk.runtime;

import com.ss.android.vesdk.VEException;
import com.ss.android.vesdk.VEResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class VERecorderResManager {
    private String mConcatSegmentAudioPath;
    private String mConcatSegmentVideoPath;
    private String mConcatSementAudioVideoMixedVideoPath;
    private List<String> mSegmentAudioPathList = new ArrayList();
    private String mSegmentDirPath = (this.mWorkspace + File.separator + "segments" + File.separator);
    private List<String> mSegmentVideoPathList = new ArrayList();
    private String mWorkspace;

    public VERecorderResManager(String str) {
        this.mWorkspace = str;
    }

    public void addSegmentAudioPath(String str) {
        this.mSegmentAudioPathList.add(str);
    }

    public void addSegmentVideoPath(String str) {
        this.mSegmentVideoPathList.add(str);
    }

    public String delSegmentAudioPath() {
        return this.mSegmentAudioPathList.size() > 0 ? this.mSegmentAudioPathList.remove(this.mSegmentAudioPathList.size() - 1) : "";
    }

    public String delSegmentVideoPath() throws VEException {
        if (this.mSegmentVideoPathList.size() > 0) {
            return this.mSegmentVideoPathList.remove(this.mSegmentVideoPathList.size() - 1);
        }
        throw new VEException(VEResult.TER_INVALID_STAT, "segment video list size is 0");
    }

    public void genConcatSegmentAudioPath() {
        this.mConcatSegmentAudioPath = VEResManager.getFolder(this.mWorkspace, "concat") + File.separator + "concat" + ".wav";
    }

    public void genConcatSegmentVideoPath() {
        this.mConcatSegmentVideoPath = VEResManager.getFolder(this.mWorkspace, "concat") + File.separator + "concat" + ".mp4";
    }

    public String genSegmentAudioPath(int i) {
        return VEResManager.getFolder(this.mWorkspace, "segments") + File.separator + i + ".wav";
    }

    public String genSegmentVideoPath(int i) {
        return VEResManager.getFolder(this.mWorkspace, "segments") + File.separator + i + ".mp4";
    }

    public String getConcatSegmentAudioPath() {
        return this.mConcatSegmentAudioPath;
    }

    public String getConcatSegmentVideoPath() {
        return this.mConcatSegmentVideoPath;
    }

    public List<String> getSegmentAudioPathList() {
        return this.mSegmentAudioPathList;
    }

    public List<String> getSegmentVideoPathList() {
        return this.mSegmentVideoPathList;
    }

    public String getTempSegmentDirPath() {
        return this.mSegmentDirPath;
    }

    public void release() {
        if (this.mSegmentVideoPathList != null) {
            this.mSegmentVideoPathList.clear();
            this.mSegmentVideoPathList = null;
        }
        if (this.mSegmentAudioPathList != null) {
            this.mSegmentAudioPathList.clear();
            this.mSegmentAudioPathList = null;
        }
    }
}
