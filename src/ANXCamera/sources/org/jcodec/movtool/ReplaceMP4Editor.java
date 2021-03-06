package org.jcodec.movtool;

import java.io.File;
import java.io.IOException;
import org.jcodec.containers.mp4.MP4Util;

public class ReplaceMP4Editor {
    public void copy(File file, File file2, MP4Edit mP4Edit) throws IOException {
        MP4Util.Movie createRefFullMovieFromFile = MP4Util.createRefFullMovieFromFile(file);
        mP4Edit.apply(createRefFullMovieFromFile.getMoov());
        new Flattern().flattern(createRefFullMovieFromFile, file2);
    }

    public void modifyOrReplace(File file, MP4Edit mP4Edit) throws IOException {
        if (!new InplaceMP4Editor().modify(file, mP4Edit)) {
            replace(file, mP4Edit);
        }
    }

    public void replace(File file, MP4Edit mP4Edit) throws IOException {
        File parentFile = file.getParentFile();
        File file2 = new File(parentFile, "." + file.getName());
        copy(file, file2, mP4Edit);
        file2.renameTo(file);
    }
}
