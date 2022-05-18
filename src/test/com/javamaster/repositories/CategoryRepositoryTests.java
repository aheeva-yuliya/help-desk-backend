package com.javamaster.repositories;

import com.javamaster.entities.Category;
import com.javamaster.repositories.abstracts.DatabaseIntegrationTests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CategoryRepositoryTests extends DatabaseIntegrationTests {
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void shouldFindByCategoryWhenSearchedByName() {
        Category expected = Category.builder().id(4).category("People Management").build();
        Category actual = categoryRepository.findByCategory("People Management");

        Assertions.assertEquals(expected, actual);
    }
}