package com.thisara.LNF.service;

import com.thisara.LNF.dto.ItemRequest;
import com.thisara.LNF.dto.ItemResponse;
import com.thisara.LNF.entity.Item;
import com.thisara.LNF.entity.ItemCategory;
import com.thisara.LNF.entity.User;
import com.thisara.LNF.repository.ItemRepository;
import com.thisara.LNF.repository.UserRepository;
import com.thisara.LNF.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Override
    public ItemResponse addItem(ItemRequest request) {
        // 1. Get user by email from request
        User user = userRepository.findByEmail(request.getUser().getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Map request to entity
        Item item = new Item();
        item.setTitle(request.getTitle());
        item.setDescription(request.getDescription());
        item.setCategory(ItemCategory.valueOf(String.valueOf(request.getCategory())));
        item.setType(request.getType());
        item.setLocation(request.getLocation());
        item.setDate(request.getDate());
        item.setImageUrl(request.getImageUrl());
        item.setContactInfo(request.getUser().getContactInfo());
        item.setUser(user);

        // 3. Save and map to response
        return mapToResponse(itemRepository.save(item));
    }

    @Override
    public List<ItemResponse> getAllItems() {
        return itemRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ItemResponse getItemById(Long id) {
        return mapToResponse(itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found")));
    }

    // not working
    @Override
    public List<ItemResponse> getItemsByCategory(String category) {
        return itemRepository.findByCategory(category)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ItemResponse updateItem(Long id, ItemRequest request) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        item.setTitle(request.getTitle());
        item.setDescription(request.getDescription());
        item.setCategory(ItemCategory.valueOf(String.valueOf(request.getCategory())));
        item.setType(request.getType());
        item.setLocation(request.getLocation());
        item.setDate(request.getDate());
        item.setImageUrl(request.getImageUrl());
        item.setContactInfo(request.getUser().getContactInfo());

        return mapToResponse(itemRepository.save(item));
    }

    @Override
    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }

    private ItemResponse mapToResponse(Item item) {
        ItemResponse res = new ItemResponse();

        // Mapping Item properties to ItemResponse
        res.setId(item.getId());
        res.setTitle(item.getTitle());
        res.setDescription(item.getDescription());
        res.setCategory(item.getCategory());
        res.setType(item.getType());
        res.setLocation(item.getLocation());
        res.setDate(item.getDate());
        res.setImageUrl(item.getImageUrl());
        res.setContactInfo(item.getContactInfo());  // Assuming item has contactInfo, if not, this should come from user

        // Mapping UserDTO
        ItemResponse.UserDTO uDto = new ItemResponse.UserDTO();
        uDto.setId(item.getUser().getId());  // Assuming User is a related entity
        uDto.setUsername(item.getUser().getUsername());
        uDto.setEmail(item.getUser().getEmail());
        uDto.setContactInfo(item.getUser().getContactInfo());  // Corrected to get from the User entity

        res.setUser(uDto);
        return res;
    }

}
