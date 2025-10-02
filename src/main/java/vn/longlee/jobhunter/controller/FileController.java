package vn.longlee.jobhunter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import vn.longlee.jobhunter.domain.response.file.ResUploadFileDTO;
import vn.longlee.jobhunter.service.FileService;
import vn.longlee.jobhunter.util.annotation.ApiMessage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Instant;

@RestController
@RequestMapping("api/v1")
public class FileController {
    @Autowired
    private FileService fileService;

    @Value("${longlee.upload-file.base-path}")
    private String baseURI;


    @PostMapping("/files")
    @ApiMessage("upload single file")
    public ResponseEntity<ResUploadFileDTO> upload(@RequestParam("file")MultipartFile file, @RequestParam("folder") String folder) throws URISyntaxException, IOException {
        //skip validate

        //create a directory if not exist
        this.fileService.createDirectory("file:"+ baseURI + "/" + folder);

        //store file
        String uploadFile = this.fileService.store(file,folder);

        ResUploadFileDTO res = new ResUploadFileDTO(uploadFile, Instant.now());


        return ResponseEntity.ok().body(res);
    }
}
