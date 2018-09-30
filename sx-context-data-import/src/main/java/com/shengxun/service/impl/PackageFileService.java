package com.shengxun.service.impl;

import com.shengxun.enums.ResultEnum;
import com.shengxun.execption.DataImportException;
import com.shengxun.utils.TimeUtils;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

/**
 * Created by ChenWei
 * on 2018/7/3 10:23.
 */
@Service
public class PackageFileService {
    private final static int BUFFER = 1048576;

    /**
     * 将源文件打包压缩成target目录下 .tar.gz格式
     * @param sourcePath
     * @param targetPath
     * @param userId
     * @param templateId
     */
    public void packAndCompress(String sourcePath, String targetPath, String userId, Long templateId) {
        File file = new File(targetPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String nowDate = TimeUtils.dateTime2String(new Date());
        String target = targetPath + userId + "_" + templateId + "_" + nowDate + ".tar";
        File sourceFile = new File(sourcePath);
        File[] files = sourceFile.listFiles();
        HashMap<File, String> filePathMap = new HashMap<File, String>();
        for (File file1 : files) {
            if (!file1.isDirectory()) {
                String input = file1.getAbsolutePath();
                String path = input.substring(sourcePath.length() - 1, input.length());
                filePathMap.put(file1, path);
            } else {
                File[] files1 = file1.listFiles();
                for (File file2 : files1) {
                    String input = file2.getAbsolutePath();
                    String path = input.substring(sourcePath.length() - 1, input.length());
                    filePathMap.put(file2, path);
                }
            }
        }
        try {
            File targetFile = new File(target);
            File packFile = pack(filePathMap, targetFile, sourceFile);
            compress(packFile);
        } catch (IOException e) {
            throw new DataImportException(ResultEnum.PACK_COMPRESS_WRONG, e);
        }


    }


    /**
     * 私有函数将文件集合压缩成tar包后返回
     *
     * @param filePathMap 要压缩的文件集合
     * @param target      tar.输出流的目标文件
     * @return File  指定返回的目标文件
     */
    private File pack(HashMap<File, String> filePathMap, File target, File sourceFile) throws IOException {
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        TarArchiveOutputStream taos = null;
        FileInputStream fis = null;
        try {
            fos = new FileOutputStream(target);
            bos = new BufferedOutputStream(fos, BUFFER);
            taos = new TarArchiveOutputStream(bos);
            //解决文件名过长问题
            taos.setLongFileMode(TarArchiveOutputStream.LONGFILE_GNU);
            for (Map.Entry<File, String> fileStringEntry : filePathMap.entrySet()) {
                taos.putArchiveEntry(new TarArchiveEntry(fileStringEntry.getKey(), fileStringEntry.getValue()));
                fis = new FileInputStream(fileStringEntry.getKey());
                IOUtils.copy(fis, taos);
                taos.closeArchiveEntry();
                fis.close();
            }
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (taos != null) {
                    taos.finish();
                    taos.flush();
                    taos.close();
                }
                if (bos != null) {
                    bos.flush();
                    bos.close();
                }
                if (fos != null) {
                    fos.flush();
                    fos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //将source文件夹删除
        FileUtils.deleteDirectory(sourceFile);
        return target;
    }

    //压缩成gz
    public File compress(File packFile) throws IOException {
        File target = new File(packFile.getAbsolutePath() + ".gz");
        FileInputStream in = null;
        GZIPOutputStream out = null;
        try {
            in = new FileInputStream(packFile);
            out = new GZIPOutputStream(new FileOutputStream(target));
            byte[] array = new byte[1024];
            int number = -1;
            while ((number = in.read(array)) != -1) {
                out.write(array, 0, number);
            }
        } finally {
            in.close();
            out.close();
        }
        packFile.delete();
        System.out.println("压缩后的文件为: " + target);
        return target;
    }
}
