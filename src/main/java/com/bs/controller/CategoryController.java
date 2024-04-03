package com.bs.controller;

import com.bs.model.Category;
import com.bs.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id){
        Category category = categoryService.getCategoryById(id);
        if(category != null)
            return ResponseEntity.ok(category);
        else
            return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories(){
        List<Category> categories = categoryService.getCategories();
        return ResponseEntity.ok(categories);
    }

    @PostMapping
    public ResponseEntity<Category> addCategory(@RequestBody Category category){
        Category createdCategory = categoryService.addCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category category){
        Category existingCategory = categoryService.getCategoryById(id);
        if(existingCategory != null){
            category.setId(id);
            Category updatedCategory = categoryService.updateCategory(category);
            return ResponseEntity.ok(updatedCategory);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id){
        Category existingCategory = categoryService.getCategoryById(id);
        if(existingCategory != null) {
            categoryService.deleteCategory(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
