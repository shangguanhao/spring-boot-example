package com.shangguan.upload.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shangguan.upload.util.Doc2PdfUtil;

@Controller
public class UploadController {

    private static String UPLOADED_FOLDER = "E://temp//";

    @RequestMapping("/")
    public String index() {
        return "upload";
    }

    @RequestMapping("/upload")
    public String singleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:result";
        }

        try {
            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);

            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'");

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("hello");
        return "result";
    }

    private String convertToPDF(HttpServletRequest request, String fileName, InputStream fromFileInputStream,
            String currentId) {
        if (FSUtil.isOfficeFile(fileName.toLowerCase())) {
            String convertFileSavePath = request.getRealPath("/") + FSUtil.FS_HADOOP_FILE_CONVERT_PATH
                    + File.separatorChar + currentId;
            File file = new File(convertFileSavePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            String type = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
            try {
                if (type.endsWith("x")) {
                    return Doc2PdfUtil.file2pdf(fromFileInputStream, convertFileSavePath,
                            type.substring(0, type.length() - 1));
                } else {
                    return Doc2PdfUtil.file2pdf(fromFileInputStream, convertFileSavePath, type);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return null;
            }
        }
        return null;
    }

}
