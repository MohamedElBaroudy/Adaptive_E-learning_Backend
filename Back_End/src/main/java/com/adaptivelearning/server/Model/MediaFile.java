package com.adaptivelearning.server.Model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "File",uniqueConstraints = {
		@UniqueConstraint(columnNames = "ID")})
@JsonIdentityInfo(
		scope= MediaFile.class,
		generator = ObjectIdGenerators.PropertyGenerator.class,
		property = "fileId")
public class MediaFile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID",unique = true,nullable = false)
    private Long fileId;

	@NotBlank
	@Column(name = "NAME")
    private String fileName;

	@NotBlank
	@Column(name = "TYPE")
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

	public MediaFile(@NotBlank String fileName,
					 @NotBlank String fileType,
					 byte[] data) {
		this.fileName = fileName;
		this.fileType = fileType;
		this.data = data;
	}

	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
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