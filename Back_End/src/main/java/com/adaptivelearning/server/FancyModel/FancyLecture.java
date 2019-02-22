package com.adaptivelearning.server.FancyModel;

import com.adaptivelearning.server.Model.Lecture;

import java.util.LinkedList;
import java.util.List;

public class FancyLecture {
    // id
    private Long lectureId;

    // string name
    private String name;

    // is quiz
    private Boolean isQuiz;

    // is file
    private Boolean isFile;

    // is video
    private Boolean isVideo;

    // quiz
    private Long lectureContentId;

    // video id


    // file id


    public FancyLecture() {
    }

    public FancyLecture toFancyLectureMapping(Lecture lecture){
        this.lectureId = lecture.getLectureId();
        this.isFile = lecture.isReadFile();
        this.isQuiz = lecture.isQuiz();
        this.isVideo = lecture.isVideo();
        if (lecture.isQuiz()){
            this.name = "Quiz :" + lecture.getQuiz().getTitle();
            this.lectureContentId = lecture.getQuiz().getQuizId();
        }
        else if (lecture.isVideo()){
        	// not sure can be changed later
                this.name = "video :" + lecture.getMedia().getFileName();
                this.lectureContentId = lecture.getMedia().getFileId();
        }
        else if(lecture.isReadFile()) {
        	this.name = "Read file :" + lecture.getMedia().getFileName();
            this.lectureContentId = lecture.getMedia().getFileId();	
        }
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

    public Boolean getQuiz() {
        return isQuiz;
    }

    public void setQuiz(Boolean quiz) {
        isQuiz = quiz;
    }

    public Boolean getFile() {
        return isFile;
    }

    public void setFile(Boolean file) {
        isFile = file;
    }

    public Boolean getVideo() {
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
}
