package com.adaptivelearning.server.Model;

import javax.persistence.*;

@Entity
@Table(name = "files")
public class MediaFile {

	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String fileName;

    private String fileType;

	@Lob
    private byte[] data;

     @OneToOne(mappedBy = "profile_picture",
			 cascade = {CascadeType.REFRESH})
    private User profile;
     
     @OneToOne(mappedBy = "course_picture",
			 cascade = {CascadeType.REFRESH})
    private Course course;
    
    @OneToOne(mappedBy = "classroom_picture",
			cascade = {CascadeType.REFRESH})
    private Classroom classroom;

	public MediaFile() {

    }

    public MediaFile(String fileName, String fileType, byte[] data) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public User getProfile() {
		return profile;
	}

	public void setProfile(User profile) {
		this.profile = profile;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Classroom getClassroom() {
		return classroom;
	}

	public void setClassroom(Classroom classroom) {
		this.classroom = classroom;
	}

}