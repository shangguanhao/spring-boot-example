package com.shangguan.upload.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.StreamOpenOfficeDocumentConverter;

public class Doc2PdfUtil {

    public static String file2pdf(InputStream fromFileInputStream, String toFilePath, String type) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String timesuffix = sdf.format(new Date());
        String docFileName = null;
        String htmFileName = null;
        if ("doc".equals(type)) {
            docFileName = timesuffix.concat(".doc");
            htmFileName = timesuffix.concat(".pdf");
        } else if ("xls".equals(type)) {
            docFileName = timesuffix.concat(".xls");
            htmFileName = timesuffix.concat(".pdf");
        } else if ("ppt".equals(type)) {
            docFileName = timesuffix.concat(".ppt");
            htmFileName = timesuffix.concat(".pdf");
        } else if ("txt".equals(type)) {
            docFileName = timesuffix.concat(".txt");
            htmFileName = timesuffix.concat(".pdf");
        } else {
            return null;
        }

        File htmlOutputFile = new File(toFilePath + File.separatorChar + htmFileName);
        File docInputFile = new File(toFilePath + File.separatorChar + docFileName);
        if (htmlOutputFile.exists()) {
            htmlOutputFile.delete();
        }

        htmlOutputFile.createNewFile();

        docInputFile.createNewFile();

        /**
         * 由fromFileInputStream构建输入文件
         */
        int bytesRead = 0;
        byte[] buffer = new byte[1024 * 8];
        OutputStream os = new FileOutputStream(docInputFile);
        while ((bytesRead = fromFileInputStream.read(buffer)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.close();
        fromFileInputStream.close();

        OpenOfficeConnection connection = new SocketOpenOfficeConnection(8100);
        connection.connect();
        // convert
        DocumentConverter converter = new StreamOpenOfficeDocumentConverter(connection);
        converter.convert(docInputFile, htmlOutputFile);
        connection.disconnect();
        // 转换完之后删除word文件
        docInputFile.delete();
        return htmFileName;
    }

    public static void main(String[] args) throws Exception {
        File file = null;
        FileInputStream fileInputStream = null;

        file = new File("C:\\Users\\Administrator\\Desktop\\简历.doc");
        fileInputStream = new FileInputStream(file);

        Doc2PdfUtil.file2pdf(fileInputStream, "E:\\temp", "doc");
    }

}
