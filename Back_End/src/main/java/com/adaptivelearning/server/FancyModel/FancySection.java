package com.adaptivelearning.server.FancyModel;

import com.adaptivelearning.server.Model.Section;

import java.util.LinkedList;
import java.util.List;

public class FancySection {
    // id
    private int sectionId;

    // title
    private String title;

    // course id
    private int courseId;

    // editor id
    private int editorId;

    public FancySection() {
    }

    public FancySection toFancySectionMapping(Section section){
        this.sectionId = section.getSectionId();
        this.title = section.getTitle();
        this.courseId = section.getCourse().getCourseId();
        this.editorId = section.getCourse().getPublisher().getUserId();
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

    public int getEditorId() {
        return editorId;
    }

    public void setEditorId(int editorId) {
        this.editorId = editorId;
    }
}
