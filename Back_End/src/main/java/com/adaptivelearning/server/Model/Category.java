package com.adaptivelearning.server.Model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "Category",uniqueConstraints = {
        @UniqueConstraint(columnNames = "ID"),
        @UniqueConstraint(columnNames = "NAME")})
@JsonIdentityInfo(
        scope= Category.class,
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "categoryId")
public class Category {
    // id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private int categoryId;

    // name
    @NotBlank
    @Size(max = 40)
    @Column(name = "NAME",unique = true,nullable = false)
    private String name;

    // is approved
    @NotNull
    @Column(name = "IS_APPROVED")
    private boolean isApproved = false;

    //mapping
    @OneToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.REMOVE},
            mappedBy = "category")
    private List<Course> courses;
    //end of the mapping

    public Category() {
    }

    public Category(@NotBlank @Size(max = 40) String name,
                    @NotNull boolean isApproved) {
        this.name = name;
        this.isApproved = isApproved;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
