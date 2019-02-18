package com.adaptivelearning.server.FancyModel;

import com.adaptivelearning.server.Model.Section;
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

    public FancySection() {
    }

    public FancySection toFancySectionMapping(Section section){
        FancyUser fancyUser= new FancyUser();
        FancyLecture fancyLecture = new FancyLecture();
        this.sectionId = section.getSectionId();
        this.title = section.getTitle();
        this.courseId = section.getCourse().getCourseId();
        this.editor = fancyUser.toFancyUserMapper(section.getCourse().getPublisher());
        this.fancyLectures = fancyLecture.toFancyLectureListMapping(section.getLectures());
        return this;
    }

    public List<FancySection> toFancySectionListMapping(List<Section> sections){
        LinkedList<FancySection> fancySectionList = new LinkedList<>();
        for (Section section:
                sections) {
        	FancySection fancySection = new FancySection();
            fancySectionList.addLast(fancySection.toFancySectionMapping(section));
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
}
