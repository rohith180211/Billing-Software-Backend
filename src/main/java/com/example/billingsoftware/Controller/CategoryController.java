package com.example.billingsoftware.Controller;


import com.example.billingsoftware.Service.CategoryService;
import com.example.billingsoftware.io.CategoryRequest;
import com.example.billingsoftware.io.CategoryResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final ObjectMapper objectMapper;

    public CategoryController(CategoryService categoryService, ObjectMapper objectMapper) {
        this.categoryService = categoryService;
        this.objectMapper = objectMapper;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CategoryResponse addCategory(@RequestPart("category") String categoryString, @RequestPart("file")MultipartFile file) {
        ObjectMapper mapper = new ObjectMapper();
        CategoryRequest categoryRequest = null;
        try{
            categoryRequest= objectMapper.readValue(categoryString, CategoryRequest.class);
            return categoryService.addCategory(categoryRequest, file);
        }
        catch (JsonProcessingException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
    @GetMapping
    public List<CategoryResponse> fetchAllCategories() {
        return categoryService.read();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void remove(@PathVariable String id) {
        try {
            categoryService.DeleteCategory(id);
        }catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
