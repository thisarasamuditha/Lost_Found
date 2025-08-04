package com.thisara.LNF.controller;

import com.thisara.LNF.dto.ItemRequest;
import com.thisara.LNF.dto.ItemResponse;
import com.thisara.LNF.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<ItemResponse> postItem(@RequestBody ItemRequest request) {
        return ResponseEntity.ok(itemService.addItem(request));
    }

    @GetMapping
    public ResponseEntity<List<ItemResponse>> getAllItems() {
        return ResponseEntity.ok(itemService.getAllItems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemResponse> getItemById(@PathVariable Long id) {
        return ResponseEntity.ok(itemService.getItemById(id));
    }

    @GetMapping("/my-items")
    public ResponseEntity<List<ItemResponse>> getMyItems(Authentication auth) {
        String username = auth.getName();
        return ResponseEntity.ok(itemService.getUserItems(username));
    }
}
