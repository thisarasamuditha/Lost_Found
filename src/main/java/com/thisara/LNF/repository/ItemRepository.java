package com.thisara.LNF.repository;

import com.thisara.LNF.entity.Item;
import com.thisara.LNF.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByUser(User user);
}
