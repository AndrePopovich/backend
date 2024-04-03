package com.bs.service;

import com.bs.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    public Category addCategory(Category category);
    public Category updateCategory(Category category);
    public Category getCategoryById(Long id);
    public List<Category> getCategories();
    public void deleteCategory(Long id);
}
