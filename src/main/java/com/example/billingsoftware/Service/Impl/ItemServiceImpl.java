package com.example.billingsoftware.Service.Impl;

import com.example.billingsoftware.Entity.CategoryEntity;
import com.example.billingsoftware.Entity.ItemEntity;
import com.example.billingsoftware.Repository.CategoryRepository;
import com.example.billingsoftware.Repository.ItemRepository;
import com.example.billingsoftware.Service.FileUploadService;
import com.example.billingsoftware.Service.ItemService;
import com.example.billingsoftware.io.ItemRequest;
import com.example.billingsoftware.io.ItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final FileUploadService fileUploadService;
    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepository;
    @Override
    public ItemResponse add(ItemRequest request, MultipartFile file) {
        String imgUrl=fileUploadService.uploadFile(file);
        ItemEntity newItem=convertToEntity(request);
        CategoryEntity existingCategory=categoryRepository.findByCategoryId(request.getCategoryId()).orElseThrow(()->new RuntimeException("Category not found"));
        newItem.setCategory(existingCategory);
        newItem.setImgUrl(imgUrl);
        newItem=itemRepository.save(newItem);
        return convertToResponse(newItem);
    }

    private ItemEntity convertToEntity(ItemRequest request) {
       return ItemEntity.builder().itemId(UUID.randomUUID().toString()).Name(request.getName()).Description(request.getDescription()).Price(request.getPrice()).build();
    }

    private ItemResponse convertToResponse(ItemEntity newItem) {
        return ItemResponse.builder().itemId(newItem.getItemId()).name(newItem.getName()).description(newItem.getDescription()).price(newItem.getPrice()).imageUrl(newItem.getImgUrl()).categoryName(newItem.getCategory().getCategoryId()).createdAt(newItem.getCreatedAt()).updatedAt(newItem.getUpdatedAt()).build();
    }

    @Override
    public List<ItemResponse> fetchItems() {
        return itemRepository.findAll().stream().map(itemEntity -> convertToResponse(itemEntity)).collect(Collectors.toList());
    }

    @Override
    public void deleteItem(String itemId) {
        ItemEntity existingItem=itemRepository.findByItemId(itemId).orElseThrow(()->new RuntimeException("Item not found"));
        boolean isFileDeleted=fileUploadService.deleteFile(existingItem.getImgUrl());
        if(isFileDeleted){
            itemRepository.delete(existingItem);
        } else{
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to delete file");
        }
    }
}
