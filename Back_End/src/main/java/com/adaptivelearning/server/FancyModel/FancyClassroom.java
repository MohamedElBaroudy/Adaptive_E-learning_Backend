package com.adaptivelearning.server.FancyModel;

import com.adaptivelearning.server.Model.Classroom;
import com.adaptivelearning.server.Model.Course;
import com.adaptivelearning.server.Model.User;

import java.util.LinkedList;
import java.util.List;

public class FancyClassroom {
    // id
    private Long classroomId;

    // creator
    private FancyUser creator;

    // classroom name   for searching purpose
    private String classroomName;

    // classroom passcode
    private String passCode;

    // number of students
    private int studentsNumber;

    //number of courses
    private short coursesNumber;

    // classroom's courses 
    private List<FancyCourse> courses;
    
    // classroom picture
    private FancyMediaFile classroom_picture;
    
    
    public FancyClassroom() {
    }

    public FancyClassroom toFancyClassroomMapping(Classroom classroom){
    	FancyUser user= new FancyUser();
    	FancyCourse courses=new FancyCourse();
    	FancyMediaFile picture=new FancyMediaFile();
        this.classroomId = classroom.getClassroomId();
        this.classroomName = classroom.getClassroomName();
        this.passCode = classroom.getPassCode();
        this.creator = user.toFancyUserMapper(classroom.getCreator());
        this.studentsNumber = classroom.getStudents().size();
        this.coursesNumber = (short) classroom.getCourses().size();
        this.classroom_picture=picture.toFancyFileMapping(classroom.getClassroom_picture());
        this.courses=courses.toFancyCourseListMapping(classroom.getCourses());
        return this;
    }

    public List<FancyClassroom> toFancyClassroomListMapping(List<Classroom> classrooms){
        List<FancyClassroom> FancyClassroomList = new LinkedList<>();
        for (Classroom classroom:
                classrooms) {
            FancyClassroom fancyClassroom = new FancyClassroom();
            ((LinkedList<FancyClassroom>) FancyClassroomList)
                    .addLast(fancyClassroom.toFancyClassroomMapping(classroom));
        }
        return FancyClassroomList;
    }

    public Long getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(Long classroomId) {
        this.classroomId = classroomId;
    }

  
    public FancyUser getCreator() {
		return creator;
	}

	public void setCreator(FancyUser creator) {
		this.creator = creator;
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

	public List<FancyCourse> getCourses() {
		return courses;
	}

	public void setCourses(List<FancyCourse> courses) {
		this.courses = courses;
	}

	public FancyMediaFile getClassroom_picture() {
		return classroom_picture;
	}

	public void setClassroom_picture(FancyMediaFile classroom_picture) {
		this.classroom_picture = classroom_picture;
	}

	

	
}
