package com.example.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Set<String> findAll() {
        Set<String> categories = categoryRepository.findAll()
                .stream()
                .map(Category::getName)
                .collect(Collectors.toSet());
        return categories;
    }
}
