package jpabook.jpashop.domain.item.service;

import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.item.repository.ItemRepository;
import jpabook.jpashop.global.error.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static jpabook.jpashop.global.error.ItemErrorCode.NOT_FOUND_ITEM;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    private ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId){
        return itemRepository.findById(itemId)
                .orElseThrow(()-> new ApplicationException(NOT_FOUND_ITEM));
    }
}
