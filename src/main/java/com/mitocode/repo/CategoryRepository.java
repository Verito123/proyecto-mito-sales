package com.mitocode.repo;

import com.mitocode.model.Category;

//@Repository
public class CategoryRepository {
    public Category save(Category category){
        System.out.println("Saving...." +category);
        return category;
    }
}
