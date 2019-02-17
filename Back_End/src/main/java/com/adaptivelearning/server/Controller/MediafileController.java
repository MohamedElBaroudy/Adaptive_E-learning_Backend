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

import com.adaptivelearning.server.Model.MediaFile;
import com.adaptivelearning.server.Repository.MediafileRepository;
import java.io.IOException;

@RestController
public class MediafileController {
	
	 @Autowired
	  MediafileRepository dbFileRepository;


    @PostMapping("/uploadFile")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {

    	 // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        
        if(fileName.contains("..")) {
        	return new ResponseEntity<>("Sorry! Filename contains invalid path sequence ",HttpStatus.BAD_REQUEST);
      }
        MediaFile dbFile = new MediaFile(fileName, file.getContentType(), file.getBytes());

        dbFileRepository.save(dbFile);
    	
        return new ResponseEntity<>("success",HttpStatus.OK);
    }

   

    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable int fileId) {
        // Load file from database
    	 MediaFile dbFile =dbFileRepository.findById(fileId);
       if(dbFile==null){
    	   return new ResponseEntity("file not found",HttpStatus.NOT_FOUND);
       }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
                .body(new ByteArrayResource(dbFile.getData()));
    }
}