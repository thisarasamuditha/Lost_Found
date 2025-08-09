package com.thisara.LNF.service;

import com.thisara.LNF.dto.ItemRequest;
import com.thisara.LNF.dto.ItemResponse;
import java.util.List;

public interface ItemService {
    ItemResponse addItem(ItemRequest request);
    List<ItemResponse> getAllItems();
    ItemResponse getItemById(Long id);
    List<ItemResponse> getItemByUserId(Long userid);
    List<ItemResponse> getItemsByCategory(String category);
    ItemResponse updateItem(Long id, ItemRequest request);
    void deleteItem(Long id);
}
