package com.example.services;

import com.example.category.Category;
import com.example.category.CategoryDTO;
import com.example.mapper.CategoryMapper;
import com.example.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Set<String> findAllCategoryNames() {
        List<Category> categories = categoryRepository.findAll();

        if(categories.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return categories
                .stream()
                .map(Category::getName)
                .collect(Collectors.toSet());
    }

    public Set<CategoryDTO> findAll() {
        List<Category> categories = categoryRepository.findAll();

        if(categories.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return categories.stream()
                .map(CategoryMapper::toDTO)
                .collect(Collectors.toSet());
    }

    public CategoryDTO findById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);

        return category
                .map(CategoryMapper::toDTO)
                .orElseThrow(
                        () ->new ResponseStatusException(HttpStatus.NOT_FOUND)
                );
    }

    public Set<CategoryDTO> findByNameContainsIgnoreCase(String name) {
        List<Category> categories = categoryRepository.findByNameContainsIgnoreCase(name);

        if(categories.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return categories.stream()
                .map(CategoryMapper::toDTO)
                .collect(Collectors.toSet());
    }

    public void save(CategoryDTO categoryDTO) {
        if(categoryDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "RequestBody can't be empty");
        }

        Optional<Category> existedCategoryName = categoryRepository.findByNameIgnoreCase(categoryDTO.getName());

        if(existedCategoryName.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Category with this name already exist");
        }

        Category category = CategoryMapper.toEntity(categoryDTO);
        categoryRepository.save(category);
    }

    public void delete(Long id) {
        Optional<Category> category = categoryRepository.findById(id);

        categoryRepository.delete(
                category.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))
        );
    }

}
