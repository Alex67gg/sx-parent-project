package com.shengxun.service.impl;

import com.shengxun.utils.AtomicIntegerUtils;
import com.shengxun.utils.RecordVideoMapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.stereotype.Service;
import sun.net.www.protocol.http.HttpURLConnection;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class WordToSoundService {
    /**
     * 将生成的音频文件 和 对应的标识,导入到sqlite的音频记录表
     * @param connection
     * @throws SQLException
     */
    public void insertWordToSound(Connection connection) throws SQLException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into WORD_TO_SOUND VALUES (?,?,?);");
            HashMap<String, String> wordAndRecord = RecordVideoMapUtils.getCurrentHashMap();
            for (Map.Entry<String, String> entry : wordAndRecord.entrySet()) {
                preparedStatement.setString(2, entry.getValue());
                preparedStatement.setString(3, entry.getKey());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } finally {
            RecordVideoMapUtils.removeHashMap();
            AtomicIntegerUtils.removeAtomicNum();
        }
    }

    /**
     * 录音文件url转化为后缀为wav的音频文件
     *
     * @param videoUrl
     * @return
     * @throws IOException
     */
    public String getRecordByVideoUrl(String sourcePath, String videoUrl) throws IOException {
        //Map<String,String> recordMap = new HashMap<String, String>();
        //WordToSound wordToSound = new WordToSound();
        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
        try {
            URL url = new URL(videoUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            //设置超时间为3秒
            httpURLConnection.setConnectTimeout(3 * 1000);
            //防止屏蔽程序抓取而返回403错误
            httpURLConnection.setRequestProperty("User-Agent",
                    "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            //获取输入流
            inputStream = httpURLConnection.getInputStream();
            //获取字节数组
            byte[] recodeData = readInputStream(inputStream);
            //文件保存位置
            String filePath = sourcePath + "record";
            File fileSavePath = new File(filePath);
            if (!fileSavePath.exists()) {
                fileSavePath.mkdirs();
            }
            //递增数字作为录音文件文件名
            AtomicInteger atomicNum = AtomicIntegerUtils.getCurrentAtomicInteger();
            Integer recordName = atomicNum.incrementAndGet();
            File file = new File(fileSavePath + File.separator + recordName.toString() + ".wav");
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(recodeData);
            return recordName.toString();
        } finally {
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    /**
     * 从输入流中获取字节数组
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] bytes = new byte[1024];
        int len = -1;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(bytes)) != -1) {
            bos.write(bytes, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

    /**
     * 对话内容和录音地址
     *
     * @param videoUrl
     * @param word
     * @return
     * @throws IOException
     */
    public String getRecordByVideoUrlAndWord(String sourcePath, String videoUrl, String word) throws IOException {
        HashMap<String, String> wordAndRecord = RecordVideoMapUtils.getCurrentHashMap();
        //录音文件
        String record = wordAndRecord.get(word);
        if (StringUtils.isBlank(record)) {
            record = getRecordByVideoUrl(sourcePath, videoUrl);
            wordAndRecord.put(word, record);
        }
        return record;
    }
}
