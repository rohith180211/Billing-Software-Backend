package com.example.billingsoftware.Controller;


import com.example.billingsoftware.Service.CategoryService;
import com.example.billingsoftware.io.CategoryRequest;
import com.example.billingsoftware.io.CategoryResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CategoryResponse addCategory(@RequestBody CategoryRequest request) {
        return categoryService.addCategory(request);
    }
    @GetMapping
    public List<CategoryResponse> fetchAllCategories() {
        return categoryService.read();
    }
    @DeleteMapping("{id}")
    public void remove(@PathVariable String id) {
        try {
            categoryService.DeleteCategory(id);
        }catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
