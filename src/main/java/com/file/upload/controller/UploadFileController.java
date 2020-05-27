package com.file.upload.controller;

import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.UUID;

@Controller
public class UploadFileController {

    @GetMapping("/uploadFile")
    public String uploadOneFile(@RequestParam("filePath") String filePath,Model model) {
        model.addAttribute("filePath",filePath);
        return "uploadFile";
    }

    @PostMapping(value = "/uploadSimpleFile")
    public ModelAndView uploadSimpleFile(@RequestParam("attachment") MultipartFile file) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:uploadFile");
        if (file == null || file.isEmpty()) {
            System.out.println("上传失败");
            return modelAndView;
        }
        try {
//            ApplicationHome applicationHome = new ApplicationHome(getClass());
//            File jarFile = applicationHome.getSource();
//            String filePath = jarFile.getParentFile().toString() + "\\static\\upload\\";
            String filePath = "D:\\upload\\";
            System.out.println("filePath =============" + filePath);
            String fileName = file.getOriginalFilename();
            if (fileName != null) {
                String suffixName = fileName.substring(fileName.lastIndexOf("."));
                fileName = UUID.randomUUID() + suffixName;
            }
            File newFile = new File(filePath + fileName);
            if (!newFile.getParentFile().exists()) {
                newFile.getParentFile().mkdirs();
            }
            file.transferTo(newFile);
            System.out.println("上传成功");
            modelAndView.addObject("filePath","../static/upload/" + fileName);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("上传失败");
        }
        return modelAndView;
    }
}
