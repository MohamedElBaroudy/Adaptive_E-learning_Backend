package com.adaptivelearning.server.Model;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "Version",uniqueConstraints = {
        @UniqueConstraint(columnNames = "ID")})
@JsonIdentityInfo(
        scope=Version.class,
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "versionId")
public class Version {
    // id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Long versionId;

    @NotNull
    private Integer level;

    @ManyToOne(fetch = FetchType.EAGER,
            cascade = {CascadeType.ALL})
    @JoinColumn(name = "SECTION")
    private Section section;

    @OneToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.REMOVE},
            mappedBy = "version")
    private List<Lecture> lectures;

    public Version() {
    }

    public Version(Integer level, Section section) {
        this.level = level;
        this.section = section;
    }

    public Long getVersionId() {
        return versionId;
    }

    public void setVersionId(Long versionId) {
        this.versionId = versionId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public List<Lecture> getLectures() {
        return lectures;
    }

    public void setLectures(List<Lecture> lectures) {
        this.lectures = lectures;
    }
}
