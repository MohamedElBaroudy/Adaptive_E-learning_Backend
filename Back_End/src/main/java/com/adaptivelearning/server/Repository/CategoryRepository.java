package com.adaptivelearning.server.Repository;

import com.adaptivelearning.server.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
    Category findByName(String name);

    Category findByCategoryId(Integer categoryId);

    List<Category> findByIsApproved(boolean isApproved);
}
