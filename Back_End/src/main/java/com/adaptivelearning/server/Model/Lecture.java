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

    // is video
    @Column(name = "IS_VIDEO")
    private boolean isVideo = false;

    // is read file
    @Column(name = "IS_READ_FILE")
    private boolean isReadFile = false;

    // mapping
    @ManyToOne(fetch = FetchType.EAGER,
            cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "VERSION")
    private Version version;
    
    @OneToOne(fetch = FetchType.EAGER,
            cascade = {CascadeType.REMOVE},
            mappedBy = "lecture")
    private MediaFile media;
    
    // end of the mapping


    public Lecture() {
    }

    public Lecture(boolean isVideo,
                   boolean isReadFile) {
        this.isVideo = isVideo;
        this.isReadFile = isReadFile;
    }

    public Long getLectureId() {
        return lectureId;
    }

    public void setLectureId(Long lectureId) {
        this.lectureId = lectureId;
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

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

	public MediaFile getMedia() {
		return media;
	}

	public void setMedia(MediaFile media) {
		this.media = media;
	}
}
