package com.example.billingsoftware.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.security.Timestamp;


@Entity
@Table(name="tbl_items")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ItemEntity {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique=true)
    private String itemId;

    private String Name;
    private BigDecimal Price;
    private String Description;
    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp CreatedAt;
    @UpdateTimestamp
    @Column(updatable = false)
    private Timestamp UpdatedAt;
    private String imgUrl;
    @ManyToOne
    @JoinColumn(name = "category_id",nullable = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    private CategoryEntity category;
}
