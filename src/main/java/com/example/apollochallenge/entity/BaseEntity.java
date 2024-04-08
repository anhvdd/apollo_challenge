package com.example.apollochallenge.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@Data
public abstract class BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @CreationTimestamp
    @Column(name = "createTime")
    private LocalDateTime createdTime;

    @UpdateTimestamp
    @Column(name = "updatedTime")
    private LocalDateTime updatedTime;

    @Column(name = "isDelete")
    private boolean isDelete;
}
