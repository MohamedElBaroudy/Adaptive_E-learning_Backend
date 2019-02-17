package com.adaptivelearning.server.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adaptivelearning.server.Model.MediaFile;


public interface MediafileRepository  extends JpaRepository<MediaFile, Integer> {
  MediaFile findById(int fileID); 
}