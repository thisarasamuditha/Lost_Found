package com.thisara.LNF.service;

import com.thisara.LNF.dto.ItemRequest;
import com.thisara.LNF.dto.ItemResponse;
import com.thisara.LNF.entity.Item;
import com.thisara.LNF.entity.User;
import com.thisara.LNF.repository.ItemRepository;
import com.thisara.LNF.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public ItemResponse addItem(ItemRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Item item = Item.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .category(request.getCategory())
                .location(request.getLocation())
                .date(request.getDate())
                .type(request.getType())
                .imageUrl(request.getImageUrl())
                .contactInfo(request.getContactInfo())
                .user(user)
                .build();

        Item saved = itemRepository.save(item);
        return mapToResponse(saved);
    }

    public List<ItemResponse> getAllItems() {
        return itemRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<ItemResponse> getUserItems(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return itemRepository.findByUser(user)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ItemResponse getItemById(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        return mapToResponse(item);
    }

    private ItemResponse mapToResponse(Item item) {
        return ItemResponse.builder()
                .id(item.getId())
                .title(item.getTitle())
                .description(item.getDescription())
                .category(item.getCategory())
                .location(item.getLocation())
                .date(item.getDate())
                .type(item.getType())
                .imageUrl(item.getImageUrl())
                .contactInfo(item.getContactInfo() != null ? item.getContactInfo() : item.getUser().getContactInfo())
                .createdAt(item.getCreatedAt())
                .postedBy(item.getUser().getUsername())
                .build();
    }
}
