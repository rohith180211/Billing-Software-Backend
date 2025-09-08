package com.example.billingsoftware.Service;

import com.example.billingsoftware.io.CategoryRequest;
import com.example.billingsoftware.io.CategoryResponse;

public interface CategoryService {
    CategoryResponse addCategory(CategoryRequest categoryRequest);
}
