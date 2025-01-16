package com.backend.services;

import com.backend.entities.Category;
import com.backend.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

  private final CategoryRepository categoryRepository;

  public CategoryService(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  public Category addCategory(Category category) {
    if (category.getName() == null || category.getName().isEmpty()) {
      throw new IllegalArgumentException("Category name cannot be empty");
    }
//    category.setName(category.getName());
//    category.setDescription(category.getDescription());
    return categoryRepository.save(category);
  }

  public Category updateCategory(String name, Category category) {
    // Check if the category exists
    Category existingCategory = categoryRepository.findByName(name)
        .orElseThrow(() -> new IllegalArgumentException("Category not found"));

    existingCategory.setName(category.getName());
    existingCategory.setDescription(category.getDescription());
    return categoryRepository.save(existingCategory);
  }

  public Category getCategory(String name) {
    return categoryRepository.findByName(name).orElseThrow(() -> new IllegalArgumentException("Category not found"));
  }
}
