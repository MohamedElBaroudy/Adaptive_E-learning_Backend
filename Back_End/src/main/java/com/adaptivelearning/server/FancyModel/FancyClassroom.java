package com.adaptivelearning.server.FancyModel;

import com.adaptivelearning.server.Model.Classroom;

import java.util.LinkedList;
import java.util.List;

public class FancyClassroom {
    // id
    private int classroomId;

    // creator id
    private int creatorId;

    // classroom name   for searching purpose
    private String classroomName;

    // classroom passcode
    private String passCode;

    // number of students
    private int studentsNumber;

    //number of courses
    private short coursesNumber;

    public FancyClassroom() {
    }

    public FancyClassroom toFancyClassroomMapping(Classroom classroom){
        this.classroomId = classroom.getClassroomId();
        this.classroomName = classroom.getClassroomName();
        this.passCode = classroom.getPassCode();
        this.creatorId = classroom.getCreator().getUserId();
        this.studentsNumber = classroom.getStudents().size();
        this.coursesNumber = (short) classroom.getCourses().size();
        return this;
    }

    public List<Integer> toClassroomIdListMapping(List<Classroom> classrooms){
        List<Integer> classroomIdList = new LinkedList<>();
        for (Classroom classroom:
             classrooms) {
           ((LinkedList<Integer>) classroomIdList).addLast(classroom.getClassroomId());
        }
        return classroomIdList;
    }

    public int getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(int classroomId) {
        this.classroomId = classroomId;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public String getClassroomName() {
        return classroomName;
    }

    public void setClassroomName(String classroomName) {
        this.classroomName = classroomName;
    }

    public String getPassCode() {
        return passCode;
    }

    public void setPassCode(String passCode) {
        this.passCode = passCode;
    }

    public int getStudentsNumber() {
        return studentsNumber;
    }

    public void setStudentsNumber(int studentsNumber) {
        this.studentsNumber = studentsNumber;
    }

    public short getCoursesNumber() {
        return coursesNumber;
    }

    public void setCoursesNumber(short coursesNumber) {
        this.coursesNumber = coursesNumber;
    }
}
