package com.example.billingsoftware.Service.Impl;

import com.example.billingsoftware.Entity.CategoryEntity;
import com.example.billingsoftware.Repository.CategoryRepository;
import com.example.billingsoftware.Service.CategoryService;
import com.example.billingsoftware.io.CategoryRequest;
import com.example.billingsoftware.io.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service

@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryResponse addCategory(CategoryRequest categoryRequest) {
        CategoryEntity newCategory = convertToEntity(categoryRequest);
        newCategory=categoryRepository.save(newCategory);
        return convertToResponse(newCategory);

    }

    @Override
    public List<CategoryResponse> read() {
        return categoryRepository.findAll().stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    @Override
    public void DeleteCategory(String id) {
        CategoryEntity existingEntity=categoryRepository.findByCategoryId(id).orElseThrow(()->new RuntimeException("Category not found" +id));
        categoryRepository.delete(existingEntity);
    }

    private CategoryResponse convertToResponse(CategoryEntity newCategory) {
        return CategoryResponse.builder().categoryId(newCategory.getCategoryId()).name(newCategory.getName()).description(newCategory.getDescription()).bgColor(newCategory.getBgColor()).imgUrl(newCategory.getImgUrl()).createdAt(newCategory.getCreatedAt()).updatedAt(newCategory.getUpdatedAt()).build();
    }

    private CategoryEntity convertToEntity(CategoryRequest categoryRequest) {
        return CategoryEntity.builder().categoryId(UUID.randomUUID().toString()).name(categoryRequest.getName()).description(categoryRequest.getDescription()).bgColor(categoryRequest.getBgColor()).build();
    }
}
