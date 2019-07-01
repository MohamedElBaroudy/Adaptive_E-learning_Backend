package com.adaptivelearning.server.FancyModel;

import com.adaptivelearning.server.Model.Lecture;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedList;
import java.util.List;

public class FancyLecture {
    // id
    private Long lectureId;

    // string name
    private String name;

    // is file
    private Boolean isFile;

    // is video
    private Boolean isVideo;

    // quiz
    private Long lectureContentId;

    // version
    private String version;

    // video id


    // file id


    public FancyLecture() {
    }

    public FancyLecture toFancyLectureMapping(Lecture lecture){
        this.lectureId = lecture.getLectureId();
        this.isFile = lecture.isReadFile();
        this.isVideo = lecture.isVideo();

        if (lecture.isVideo()){
        	// not sure can be changed later
                this.name = "video :" + lecture.getMedia().getFileName();
                this.lectureContentId = lecture.getMedia().getFileId();
        }
        else if(lecture.isReadFile()) {
        	this.name = "Read file :" + lecture.getMedia().getFileName();
            this.lectureContentId = lecture.getMedia().getFileId();	
        }
        if (lecture.getVersion().getLevel() == 1)
            this.version = "Easy";
        else if (lecture.getVersion().getLevel() == 2)
            this.version = "Medium";
        else if (lecture.getVersion().getLevel() == 3)
            this.version = "Hard";
        return this;
    }

    public List<FancyLecture> toFancyLectureListMapping(List<Lecture> lectures){
        LinkedList<FancyLecture> fancyLectureList = new LinkedList<>();
        for (Lecture lecture:
                lectures) {
            FancyLecture fancyLecture = new FancyLecture();
            fancyLectureList.addLast(fancyLecture.toFancyLectureMapping(lecture));
        }
        return fancyLectureList;
    }

    public Long getLectureId() {
        return lectureId;
    }

    public void setLectureId(Long lectureId) {
        this.lectureId = lectureId;
    }

    @JsonProperty(value = "isFile")
    public Boolean isFile() {
        return isFile;
    }

    public void setFile(Boolean file) {
        isFile = file;
    }

    @JsonProperty(value = "isVideo")
    public Boolean isVideo() {
        return isVideo;
    }

    public void setVideo(Boolean video) {
        isVideo = video;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getLectureContentId() {
        return lectureContentId;
    }

    public void setLectureContentId(Long lectureContentId) {
        this.lectureContentId = lectureContentId;
    }

    public Boolean getFile() {
        return isFile;
    }

    public Boolean getVideo() {
        return isVideo;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
