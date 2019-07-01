package com.adaptivelearning.server.FancyModel;

import com.adaptivelearning.server.Model.Lecture;
import com.adaptivelearning.server.Model.Section;
import com.adaptivelearning.server.Model.Version;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FancySection {
    // id
    private Long sectionId;

    // title
    private String title;

    // course id
    private Long courseId;

    // editor 
    private FancyUser editor;

    // lectures
    private List<FancyLecture> fancyLectures;

    // quiz
    private FancyQuiz fancyQuiz;

    public FancySection() {
    }

    public FancySection toFancySectionMapping(Section section, Boolean isPublisher, Float rank){
        FancyUser fancyUser= new FancyUser();
        FancyLecture fancyLecture = new FancyLecture();
        FancyQuiz fancyQuiz = new FancyQuiz();
        this.sectionId = section.getSectionId();
        this.title = section.getTitle();
        this.courseId = section.getCourse().getCourseId();
        this.editor = fancyUser.toFancyUserMapper(section.getCourse().getPublisher());
        Integer level = 0;
        if (rank == null)
            level = 0;
        else if (rank >= 0 && rank < 4)
            level = 1;
        else if (rank >= 4 && rank < 7)
            level = 2;
        else if (rank >=7 && rank < 10)
            level = 3;
        if (isPublisher){
            List<Version> versions = section.getVersions();
            ArrayList<Lecture> lectures = new ArrayList<>();
            for (Version version:
                 versions) {
                lectures.addAll(version.getLectures());
            }
            this.fancyLectures = fancyLecture.toFancyLectureListMapping(lectures);
        }
        else if (rank != null) {
            List<Version> versions = section.getVersions();
            ArrayList<Lecture> lectures = new ArrayList<>();
            for (Version version:
                    versions) {
                if (version.getLevel() == level)
                    lectures.addAll(version.getLectures());
            }
            this.fancyLectures = fancyLecture.toFancyLectureListMapping(lectures);
        }
        else
            this.fancyLectures = null;
        if (section.getQuiz() != null && (isPublisher || rank != null))
            this.fancyQuiz = fancyQuiz.toFancyQuizMapping(section.getQuiz(), isPublisher);
        else
            this.fancyQuiz = null;
        return this;
    }

    public List<FancySection> toFancySectionListMapping(List<Section> sections, Boolean isPublisher, Float rank){
        LinkedList<FancySection> fancySectionList = new LinkedList<>();
        for (Section section:
                sections) {
        	FancySection fancySection = new FancySection();
            fancySectionList.addLast(fancySection.toFancySectionMapping(section, isPublisher, rank));
        }
        return fancySectionList;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

	public FancyUser getEditor() {
		return editor;
	}

	public void setEditor(FancyUser editor) {
		this.editor = editor;
	}

    public List<FancyLecture> getFancyLectures() {
        return fancyLectures;
    }

    public void setFancyLectures(List<FancyLecture> fancyLectures) {
        this.fancyLectures = fancyLectures;
    }

    public FancyQuiz getFancyQuiz() {
        return fancyQuiz;
    }

    public void setFancyQuiz(FancyQuiz fancyQuiz) {
        this.fancyQuiz = fancyQuiz;
    }
}
