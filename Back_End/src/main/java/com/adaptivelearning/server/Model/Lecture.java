package com.adaptivelearning.server.Model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Lecture",uniqueConstraints = {
        @UniqueConstraint(columnNames = "ID")})
@JsonIdentityInfo(
        scope= Lecture.class,
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "lectureId")
public class Lecture {
    // id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Long lectureId;

    // is quiz
    @Column(name = "IS_Quiz")
    private boolean isQuiz = false;

    // is video
    @Column(name = "IS_VIDEO")
    private boolean isVideo = false;

    // is read file
    @Column(name = "IS_READ_FILE")
    private boolean isReadFile = false;

    // mapping
    @ManyToOne(fetch = FetchType.EAGER,
            cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "SECTION")
    private Section section;

    @OneToOne(fetch = FetchType.EAGER,
            cascade = {CascadeType.REMOVE},
            mappedBy = "lecture")
    private Quiz quiz;
    
    @OneToOne(fetch = FetchType.EAGER,
            cascade = {CascadeType.REMOVE},
            mappedBy = "lecture")
    private MediaFile media;
    
    // end of the mapping


    public Lecture() {
    }

    public Lecture(boolean isQuiz,
                   boolean isVideo,
                   boolean isReadFile) {
        this.isQuiz = isQuiz;
        this.isVideo = isVideo;
        this.isReadFile = isReadFile;
    }

    public Long getLectureId() {
        return lectureId;
    }

    public void setLectureId(Long lectureId) {
        this.lectureId = lectureId;
    }

    public boolean isQuiz() {
        return isQuiz;
    }

    public void setQuiz(boolean quiz) {
        isQuiz = quiz;
    }

    public boolean isVideo() {
        return isVideo;
    }

    public void setVideo(boolean video) {
        isVideo = video;
    }

    public boolean isReadFile() {
        return isReadFile;
    }

    public void setReadFile(boolean readFile) {
        isReadFile = readFile;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

	public MediaFile getMedia() {
		return media;
	}

	public void setMedia(MediaFile media) {
		this.media = media;
	}
}
