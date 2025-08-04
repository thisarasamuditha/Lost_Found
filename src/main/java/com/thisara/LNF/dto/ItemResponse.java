package com.thisara.LNF.dto;

import com.thisara.LNF.entity.ItemType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class ItemResponse {
    private Long id;
    private String title;
    private String description;
    private String category;
    private String location;
    private LocalDate date;
    private ItemType type;
    private String imageUrl;
    private String contactInfo;
    private LocalDateTime createdAt;

    // Optional user summary
    private String postedBy;
}
