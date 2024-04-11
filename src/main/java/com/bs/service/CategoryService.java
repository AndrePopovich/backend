package com.bs.service;

import com.bs.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    Category addCategory(Category category);
    Category updateCategory(Category category);
    Category getCategoryById(Long id);
    List<Category> getCategories();
    void deleteCategory(Long id);
}
