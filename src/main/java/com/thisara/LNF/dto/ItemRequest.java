package com.thisara.LNF.dto;

import com.thisara.LNF.entity.ItemType;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ItemRequest {
    private String title;
    private String description;
    private String category;
    private String location;
    private LocalDate date;
    private ItemType type;         // LOST or FOUND
    private String imageUrl;       // URL or base64 string
    private String contactInfo;    // Overwrites user contact if needed
}
