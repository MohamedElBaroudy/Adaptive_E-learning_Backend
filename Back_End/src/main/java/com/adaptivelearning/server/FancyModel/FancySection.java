package com.adaptivelearning.server.FancyModel;

import com.adaptivelearning.server.Model.Course;
import com.adaptivelearning.server.Model.Section;
import com.adaptivelearning.server.Model.User;

import java.util.LinkedList;
import java.util.List;

public class FancySection {
    // id
    private int sectionId;

    // title
    private String title;

    // course id
    private int courseId;

    // editor 
    private User editor;

    public FancySection() {
    }

    public FancySection toFancySectionMapping(Section section){
        this.sectionId = section.getSectionId();
        this.title = section.getTitle();
        this.courseId = section.getCourse().getCourseId();
        this.editor = section.getCourse().getPublisher();
        return this;
    }

    public List<Integer> toSectionIdListMapping(List<Section> sections){
        List<Integer> sectionIdList = new LinkedList<>();
        for (Section section:
                sections) {
            ((LinkedList<Integer>) sectionIdList).addLast(section.getSectionId());
        }
        return sectionIdList;
    }

    public List<FancySection> toFancySectionListMapping(List<Section> sections){
        List<FancySection> FancySectionList = new LinkedList<>();
        for (Section section:
                sections) {
        	FancySection fancySection = new FancySection();
            ((LinkedList<FancySection>) FancySectionList).addLast(fancySection.toFancySectionMapping(section));
        }
        return FancySectionList;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

	public User getEditor() {
		return editor;
	}

	public void setEditor(User editor) {
		this.editor = editor;
	}



   
}
