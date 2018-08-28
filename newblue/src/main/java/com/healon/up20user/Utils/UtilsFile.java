package com.healon.up20user.Utils;

import android.content.Context;

import com.healon.up20user.Constants;
import com.healon.up20user.Share.SharePrefreceHelper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * 操作文件
 * Created by Administrator on 2017/7/19 0019.
 */

public class UtilsFile {

    // 判断文件/文件夹是否已经存在
    public static boolean isExist(final String path) {
        return new File(path).exists();
    }

    // 判断文件扩展名
    public static boolean isExt(final String file, final String ext) {
        return file.toLowerCase().endsWith(ext.toLowerCase());
    }

    // 判断文件是否是AVI文件
    public static boolean isAviFile(final String file) {
        return UtilsFile.isExt(file, ".avi");
    }

    // 判断文件是否是MP4文件
    public static boolean isMp4File(final String file) {
        return UtilsFile.isExt(file, ".mp4");
    }

    // 判断文件是否是BMP文件
    public static boolean isBmpFile(final String file) {
        return UtilsFile.isExt(file, ".bmp");
    }

    // 判断文件是否是PNG文件
    public static boolean isPngFile(final String file) {
        return UtilsFile.isExt(file, ".png");
    }

    // 判断文件是否是JPG文件
    public static boolean isJpgFile(final String file) {
        return (UtilsFile.isExt(file, ".jpg") || UtilsFile.isExt(file, ".jpeg"));
    }

    // 判断文件是否是TIF文件
    public static boolean isTifFile(final String file) {
        return (UtilsFile.isExt(file, ".tif") || UtilsFile.isExt(file, ".tiff"));
    }

    // 判断文件是否是视频文件
    public static boolean isMovieFile(final String file) {
        boolean ok = false;
        if (UtilsFile.isExt(file, ".avi")) ok = true;
        else if (UtilsFile.isExt(file, ".avi")) ok = true;
        else if (UtilsFile.isExt(file, ".mp4")) ok = true;
        else if (UtilsFile.isExt(file, ".m4v")) ok = true;
        else if (UtilsFile.isExt(file, ".mpg")) ok = true;
        else if (UtilsFile.isExt(file, ".mpe")) ok = true;
        else if (UtilsFile.isExt(file, ".mpeg")) ok = true;
        else if (UtilsFile.isExt(file, ".3gp")) ok = true;
        else if (UtilsFile.isExt(file, ".wmv")) ok = true;
        else if (UtilsFile.isExt(file, ".flv")) ok = true;
        else if (UtilsFile.isExt(file, ".rmvb")) ok = true;
        else if (UtilsFile.isExt(file, ".dat")) ok = true;
        else if (UtilsFile.isExt(file, ".mov")) ok = true;
        else if (UtilsFile.isExt(file, ".mkv")) ok = true;
        else if (UtilsFile.isExt(file, ".asf")) ok = true;
        return ok;
    }

    // 判断文件是否是指定扩展名的文件
    public static boolean isFile(final String file, final String filter) {
        boolean ok = false;
        List<String> exts = Arrays.asList(filter.split(";"));
        for (int n = 0; n < exts.size(); n++) {
            if (UtilsFile.isExt(file, exts.get(n))) {
                ok = true;
                break;
            }
        }
        return ok;
    }

    // 判断文件是否是DICOM文件
    public static boolean isDcmFile(final String file) {
        return UtilsFile.isExt(file, ".dcm");
    }

//    // 返回一个与时间有在的文件名
//    public static String nameWithTime() {
//        return UtilsDateTime.getFileName();
//    }
//
//    // 返回一个与时间有在的文件名(扩展名必须以"."开头)
//    public static String nameWithTime(final String ext) {
//        return UtilsDateTime.getFileName() + ext;
//    }

    // 返回一个与时间有在的文件名(扩展名必须以"."开头)
//    public static String nameWithTime(final String path, final String ext) {
//        if (path.endsWith("/") || path.endsWith("\\"))
//            return path + UtilsDateTime.getFileName() + ext;
//        else
//            return path + "/" + UtilsDateTime.getFileName() + ext;
//    }

    // 创建目录
    public static boolean createFolder(final String folder) {
//        String[] dirs = folder.split("/");
//        if (dirs.length > 0) {
//            String path = "";
//            for (String dir : dirs) {
//                if (!dir.isEmpty()) {
//                    path += "/" + dirs[0];
//                    File dst = new File(path.toString());
//                    if (!dst.exists()) {
//                        dst.mkdirs();
//                    }
//                }
//            }
//        }
        return new File(folder).mkdir();
    }

    // 删除目录
    public static boolean deleteFolder(final String folder) {
        File file = new File(folder);
        return UtilsFile.deleteFolder(file);
    }

    // 删除文件
    public static boolean deleteFile(final String file) {
        boolean ok = false;
        if (!file.isEmpty()) {
            File hFile = new File(file);
            if ((hFile != null) && hFile.exists() && !hFile.isDirectory()) {
                hFile.delete();
                ok = true;
            }
        }
        return ok;
    }

    // 重命名文件夹
    public static boolean renameFolder(final String srcFolder, final String dstFolder) {
        File src = new File(srcFolder);
        File dst = new File(dstFolder);
        return src.renameTo(dst);
    }

    // 重命名文件
    public static boolean renameFile(final String srcFile, final String dstFile) {
        File src = new File(srcFile);
        File dst = new File(dstFile);
        return src.renameTo(dst);
    }

    // 复制资源文件夹
    public static boolean copyResource(final Context context, final String srcFolder, final String dstFolder) {
        boolean ok = false;
        try {
            String fileNames[] = context.getAssets().list(srcFolder);
            if (fileNames.length > 0) {
                File file = new File(dstFolder);
                file.mkdirs();
                for (String fileName : fileNames) {
                    UtilsFile.copyResource(context, srcFolder + "/" + fileName, dstFolder + "/" + fileName);
                }
            } else {
                File file = new File(dstFolder);
                if (!file.exists()) {
                    InputStream is = context.getAssets().open(srcFolder);
                    FileOutputStream fos = new FileOutputStream(new File(dstFolder));
                    byte[] buffer = new byte[1024];
                    int byteCount = 0;
                    while ((byteCount = is.read(buffer)) != -1) {
                        fos.write(buffer, 0, byteCount);
                    }
                    fos.flush();
                    is.close();
                    fos.close();
                    SharePrefreceHelper.getInstence(context, Constants.MANUFACTURER_NAME).setIsFirst(true);
                }
            }
            ok = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }

    // 复制文件夹
    public static boolean copyFolder(final String srcFolder, final String dstFolder) throws IOException {
        File src = new File(srcFolder);
        File dst = new File(dstFolder);
        return UtilsFile.copyFolder(src, dst);
    }

    // 复制文件
    public static boolean copyFile(final String srcFile, final String dstFile) throws IOException {
        File src = new File(srcFile);
        File dst = new File(dstFile);
        return UtilsFile.copyFile(src, dst);
    }

    // 移动文件夹
    public static boolean moveFolder(final String srcFolder, final String dstFolder) throws IOException {
        File src = new File(srcFolder);
        File dst = new File(dstFolder);
        return UtilsFile.moveFolder(src, dst);
    }

    // 移动文件
    public static boolean moveFile(final String srcFile, final String dstFile) throws IOException {
        File src = new File(srcFile);
        File dst = new File(dstFile);
        return UtilsFile.moveFile(src, dst);
    }

    // 查询文件
    public static String queryOneFile(final String path, final String filter) {
        String ok = "";
        if (!path.isEmpty()) {
            List<String> exts = Arrays.asList(filter.split(";"));
            File query = new File(path);
            if (query.isDirectory()) {
                for (int i = 0; i < query.listFiles().length; i++) {
                    String fileName = query.listFiles()[i].getAbsolutePath();
                    for (int n = 0; n < exts.size(); n++) {
                        if (fileName.endsWith(exts.get(n))) {
                            ok = fileName;
                            break;
                        }
                    }
                    if (!ok.isEmpty()) break;
                }
            }
        }
        return ok;
    }

    /**
     * 查询文件
     *
     * @param path
     * @param filter =".bmp;.png;.jpg;.tif;.dcm;.avi";
     * @return
     */
    public static void queryFiles(final refreshCallBack callBack, final String path, final String filter, final List<String> list) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!path.isEmpty()) {
                    List<String> exts = Arrays.asList(filter.split(";"));
                    File query = new File(path);
                    if (query.isDirectory()) {
                        for (int i = 0; i < query.listFiles().length; i++) {
                            String fileName = query.listFiles()[i].getAbsolutePath();
                            for (int n = 0; n < exts.size(); n++) {
                                if (fileName.endsWith(exts.get(n))) {
                                    list.add(fileName);
                                    if (callBack != null) {
                                        callBack.refreshData();
                                    }
                                    try {
                                        Thread.sleep(10);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                }
                            }
                        }

                    }
                }
            }
        }).start();
    }

    /**
     * 查询文件
     *
     * @param path
     * @param filter =".bmp;.png;.jpg;.tif;.dcm;.avi";
     * @return
     */
    public static void queryFiles(final String path, final String filter, final List<String> list) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!path.isEmpty()) {
                    List<String> exts = Arrays.asList(filter.split(";"));
                    File query = new File(path);
                    if (query.isDirectory()) {
                        for (int i = 0; i < query.listFiles().length; i++) {
                            String fileName = query.listFiles()[i].getAbsolutePath();
                            for (int n = 0; n < exts.size(); n++) {
                                if (fileName.endsWith(exts.get(n))) {
                                    list.add(fileName);
                                    try {
                                        Thread.sleep(10);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                }
                            }
                        }

                    }
                }
            }
        }).start();
    }

    // 删除目录
    private static boolean deleteFolder(File hFile) {
        boolean ok = false;
        if (!hFile.exists()) {
            ok = true;
        } else if ((hFile != null) && !hFile.isFile()) {
            for (File file : hFile.listFiles()) {
                if (file.isFile()) {
                    file.delete();
                } else if (file.isDirectory()) {
                    UtilsFile.deleteFolder(file);    // 递归
                }
            }
            hFile.delete();
            ok = true;
        }
        return ok;
    }

    // 删除文件
    private static boolean deleteFile(File hFile) {
        return hFile.isDirectory() ? false : hFile.delete();
    }

    // 复制文件夹
    private static boolean copyFolder(File hSrc, File hDst) throws IOException {
        boolean ok = false;
        if (hSrc.isDirectory() && hDst.isDirectory()) {
            hDst.mkdir();
            if (hDst.exists()) {
                File[] files = hSrc.listFiles();
                for (int i = 0; i < files.length; i++) {
                    if (files[i].isFile()) {
                        UtilsFile.copyFile(files[i], new File(hDst.getPath() + "//" + files[i].getName()));
                    } else if (files[i].isDirectory()) {
                        UtilsFile.copyFolder(files[i], new File(hDst.getPath() + "//" + files[i].getName()));
                    }
                }
                ok = true;
            }
        }
        return ok;
    }

    // 复制文件
    private static boolean copyFile(File hSrc, File hDst) throws IOException {
        boolean ok = false;
        if (!hSrc.isDirectory() && !hDst.isDirectory()) {
            FileInputStream srcStream = new FileInputStream(hSrc);
            FileOutputStream dstStream = new FileOutputStream(hDst);
            int readLen = 0;
            byte[] buf = new byte[1024];
            while ((readLen = srcStream.read(buf)) != -1) {
                dstStream.write(buf, 0, readLen);
            }
            dstStream.flush();
            dstStream.close();
            srcStream.close();
            ok = true;
        }
        return ok;
    }

    // 移动文件夹
    private static boolean moveFolder(File hSrc, File hDst) throws IOException {
        boolean ok = false;
        if (hSrc.isDirectory() && hDst.isDirectory()) {
            File[] files = hSrc.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()) {
                    UtilsFile.moveFile(files[i], new File(hDst.getPath() + "//" + files[i].getName()));
                    UtilsFile.deleteFile(files[i]);
                } else if (files[i].isDirectory()) {
                    UtilsFile.moveFolder(files[i], new File(hDst.getPath() + "//" + files[i].getName()));
                    UtilsFile.deleteFolder(files[i]);
                }
            }
            ok = true;
        }
        return ok;
    }

    // 移动文件
    private static boolean moveFile(File hSrc, File hDst) throws IOException {
        boolean ok = false;
        if (UtilsFile.copyFile(hSrc, hDst)) {
            ok = UtilsFile.deleteFile(hSrc);
        }
        return ok;
    }
    //新建文件夹

    /**
     * 创建文件夹
     */
    public static void createmkdir(File file) {
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public interface refreshCallBack {
        void refreshData();
    }

    public static void Log(String log) {
        String strFile = UtilsSDCard.root() + "/UtilsLog.txt";
        try {
            FileWriter fp = new FileWriter(strFile, true);
            BufferedWriter output = new BufferedWriter(fp);
            output.write(log + "\n");
            output.close();
            fp.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
