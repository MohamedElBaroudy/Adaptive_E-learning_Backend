package com.adaptivelearning.server.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.adaptivelearning.server.Model.MediaFile;
import com.adaptivelearning.server.Repository.MediafileRepository;
import com.adaptivelearning.server.Service.FileStorageService;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

@RestController
public class MediafileController {
	 private static final Logger logger = LoggerFactory.getLogger(MediafileController.class);

     @Autowired
	  private FileStorageService fileStorageService;
     
	 @Autowired
	  MediafileRepository dbFileRepository;
	 


    @PostMapping("/uploadFile")
    public MediaFile uploadFile(@RequestParam("file") MultipartFile file) {

    	 String fileName = fileStorageService.storeFile(file);

         String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                 .path("/downloadFile/")
                 .path(fileName)
                 .toUriString();
         MediaFile media=new MediaFile(fileName, file.getContentType(), fileDownloadUri, file.getSize());
         dbFileRepository.save(media);
         
     return media;
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
   

//    @GetMapping("/downloadFile/{fileId}")
//    public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId) {
//        // Load file from database
//    	 MediaFile dbFile =dbFileRepository.findByFileId(fileId);
//       if(dbFile==null){
//    	   return new ResponseEntity("file not found",HttpStatus.NOT_FOUND);
//       }
//        return ResponseEntity.ok()
//                .contentType(MediaType.parseMediaType(dbFile.getFileType()))
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
//                .body(new ByteArrayResource(dbFile.getData()));
//    }
}