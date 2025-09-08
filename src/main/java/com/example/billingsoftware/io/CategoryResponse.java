package com.example.billingsoftware.io;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.security.Timestamp;
@Data
@Builder
public class CategoryResponse {
    private String name;
    private String description;
    private String bgColor;
    private String categoryId;
    private String imgUrl;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
