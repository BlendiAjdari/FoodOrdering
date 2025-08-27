package org.foodordering.service;

import org.foodordering.domain.Category;
import org.foodordering.domain.Product;

import java.util.List;

public interface CategoriesService {
    List<Category> getAllCategories() throws Exception;
    Category getCategoryById(int id) throws Exception;
    void addCategory(Category category) throws Exception;
    void updateCategory(Category category) throws Exception;
    void deleteCategory(Category category) throws Exception;

}
