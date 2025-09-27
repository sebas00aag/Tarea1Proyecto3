package com.project.demo.rest.item;

import com.project.demo.logic.entity.game.Game;
import com.project.demo.logic.entity.game.GameRepository;
import com.project.demo.logic.entity.items.Item;
import com.project.demo.logic.entity.items.ItemRepository;
import com.project.demo.logic.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item")
public class ItemRestController {
    @Autowired
    private ItemRepository itemRepository;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'USER')")
    public List<Item> getAllItems(){
        return itemRepository.findAll();
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public Item updateItem(@PathVariable Long id, @RequestBody Item item) {
        return itemRepository.findById(id)
                .map(existingItem -> {
                    existingItem.setName(item.getName());
                    existingItem.setDescription(item.getDescription());
                    existingItem.setStatus(String.valueOf(item.getPrice()));
                    existingItem.setStatus(item.getStatus());
                    return itemRepository.save(existingItem);
                })
                .orElseGet(() -> {
                    item.setId(id);
                    return itemRepository.save(item);
                });
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public Item addItem(@RequestBody Item item) {
        return  itemRepository.save(item);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteItem (@PathVariable Long id) {
        itemRepository.deleteById(id);
    }

}
