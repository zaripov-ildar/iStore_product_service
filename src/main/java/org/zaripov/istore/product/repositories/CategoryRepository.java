package org.zaripov.istore.product.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.zaripov.istore.product.entities.Category;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByTitle(String categoryTitle);
    @Query("select id from Category where lower(title) like lower(concat( '%', :title, '%'))")
    List<Long> findIdByTitleContainingIgnoreCase(@Param("title")String categoryTitle);
}
