package com.example.rest;

import com.example.category.Category;
import com.example.category.CategoryDTO;
import com.example.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Set;

@RestController
@RequestMapping("/api/categories")
public class CategoryResource {
    private CategoryService categoryService;

    @Autowired
    public CategoryResource(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(value = "/names", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<String>> findAll() {
            Set<String> categories = categoryService.findAllCategoryNames();
            return ResponseEntity.ok(categories);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryDTO> findById(@PathVariable Long id) {
        CategoryDTO category =  categoryService.findById(id);
        return ResponseEntity.ok(category);
    }

    @GetMapping(value = "/searchCategory", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<CategoryDTO>> findByNameContains(@RequestParam(required = false) String name) {
        if(name == null || name.length() == 0) {
            Set<CategoryDTO> allCategories = categoryService.findAll();
            return ResponseEntity.ok(allCategories);
        }
            Set<CategoryDTO> categories = categoryService.findByNameContainsIgnoreCase(name);
            return ResponseEntity.ok(categories);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryDTO> addCategory(@RequestBody CategoryDTO categoryDTO) {
        categoryService.save(categoryDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(categoryDTO.getId())
                .toUri();

        return ResponseEntity.created(location).body(categoryDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {
        categoryService.save(categoryDTO);
        return ResponseEntity.ok(categoryDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.ok("Category has been removed");
    }

}
