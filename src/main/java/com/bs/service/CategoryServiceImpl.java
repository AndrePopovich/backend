package com.bs.service;

import com.bs.model.Category;
import com.bs.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;
    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }
    @Override
    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
