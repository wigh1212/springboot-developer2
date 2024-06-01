package me.parkinje.springbootdeveloper.controller;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/files")
public class FileController {

    private final String defaultFolder = "/";

    @GetMapping("/list")
    public String listFiles(Model model,String folderName,String fileName) {

        System.out.println(" check "+folderName);
        if(fileName==null){
            model.addAttribute("folderName", defaultFolder);
        }else {
            String mFolder = folderName+ "/" + fileName;
            if (mFolder.equals("") || mFolder.equals("/")) {
                System.out.println(" check2 " + folderName);
                model.addAttribute("folderName", defaultFolder);
            } else {
                if (fileName.equals("")) {
                    System.out.println("적용" + mFolder.substring(0, folderName.lastIndexOf("/")));
                    model.addAttribute("folderName", mFolder.substring(0, folderName.lastIndexOf("/")));
                } else {
                    System.out.println("적용" + mFolder);
                    model.addAttribute("folderName", mFolder);
                }
            }
        }

        return "filelist";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file,
                             @RequestParam("folderName") String folderName) throws IOException {
        if (!file.isEmpty()) {
            Path folderPath = Paths.get(folderName);
            Files.createDirectories(folderPath);
            Path filePath = folderPath.resolve(file.getOriginalFilename());
            file.transferTo(filePath);
        }
        return "redirect:/files/list";
    }

    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> downloadFile(@RequestParam("fileName") String fileName,
                                                            @RequestParam("folderName") String folderName) throws IOException {
        Path filePath = Paths.get(folderName, fileName);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(filePath.toFile()));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(Files.size(filePath))
                .body(resource);
    }

    @GetMapping("/api/list")
    @ResponseBody
    public List<FileDetails> listFiles(@RequestParam(value = "search", required = false) String search,
                                       @RequestParam("folderName") String folderName) throws IOException {
        Path folderPath = Paths.get(folderName);
        File[] files = folderPath.toFile().listFiles();
        if (files != null) {
            return Arrays.stream(files)
                    .filter(file -> search == null || file.getName().contains(search))
                    .sorted(Comparator.comparingLong(File::lastModified).reversed())
                    .map(file -> new FileDetails(file.getName(), file.isDirectory(), file.length()))
                    .collect(Collectors.toList());
        }
        return List.of();
    }

    static class FileDetails {
        private String name;
        private boolean isDirectory;
        private long size;

        public FileDetails(String name, boolean isDirectory, long size) {
            this.name = name;
            this.isDirectory = isDirectory;
            this.size = size;
        }

        public String getName() {
            return name;
        }

        public boolean isDirectory() {
            return isDirectory;
        }

        public long getSize() {
            return size;
        }
    }
}