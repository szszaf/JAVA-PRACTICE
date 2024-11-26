package ItemServiceTests;

import com.example.entities.Item;
import com.example.repositories.ItemRepository;
import com.example.services.implementation.ItemServiceImpl;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemServiceImpl itemService;


    @Test
    @Order(1)
    void testSaveItem() {
        Long newItemId = 1L;
        String newItemName = "item-1";

        when(itemRepository.save(any(Item.class))).thenAnswer(
                invocation -> {
                    Item item = invocation.getArgument(0);
                    item.setId(newItemId);
                    return item;
                }
        );

        Item createdItem = itemService.saveItem("item-1");

        assertNotNull(createdItem);
        assertEquals(newItemId, createdItem.getId());
        assertEquals(newItemName, createdItem.getName());

        verify(itemRepository, times(1)).save(any(Item.class));
    }

    @Test
    @Order(2)
    void testSaveItems() {
        List<String> itemsNames = Arrays.asList("item-1", "item-2", "item-3");
        Long startIndex = 1L;

        when(itemRepository.saveAll(anyList())).thenAnswer(
                invocation -> {
                    List<Item> itemsToSave = invocation.getArgument(0);
                    AtomicLong currentIndex = new AtomicLong(startIndex);
                    itemsToSave.forEach(
                            item -> {
                                item.setId(currentIndex.getAndIncrement());
                            }
                    );
                            return itemsToSave;
                }
        );

        List<Item> createdItems = itemService.saveItems(itemsNames);

        assertNotNull(createdItems);
        assertEquals(itemsNames.size(), createdItems.size());
        assertEquals(itemsNames.get(0), createdItems.get(0).getName());
        assertEquals(1L, createdItems.get(0).getId());
        assertEquals(itemsNames.get(1), createdItems.get(1).getName());
        assertEquals(2L, createdItems.get(1).getId());
        assertEquals(itemsNames.get(2), createdItems.get(2).getName());
        assertEquals(3L, createdItems.get(2).getId());
        verify(itemRepository, times(1)).saveAll(anyList());
    }

    @Test
    @Order(3)
    void testGetAllItems() {
        List<Item> mockItems = Arrays.asList(
                new Item(1L, "item-1"),
                new Item(2L, "item-2")
        );

        when(itemRepository.findAll()).thenReturn(mockItems);

        itemService.saveItems(
                mockItems.stream()
                            .map(item -> item.getName())
                            .toList()
        );
        List<Item> items = itemService.getAllItems();

        assertNotNull(items);
        assertEquals(2, items.size());
        assertEquals("item-1", items.get(0).getName());
        assertEquals(1L, items.get(0).getId());
        assertEquals("item-2", items.get(1).getName());
        assertEquals(2L, items.get(1).getId());

        verify(itemRepository, times(1)).findAll();
    }

    @Test
    @Order(4)
    void testGetItemByName() {
        String itemName = "item-1";
        Item mockItem = new Item(1L, itemName);

        when(itemRepository.findByName(itemName)).thenReturn(mockItem);

        Item item = itemService.getItem(itemName);

        assertNotNull(item);
        assertEquals(1L, item.getId());
        assertEquals(itemName, item.getName());

        verify(itemRepository, times(1)).findByName(itemName);
    }

    @Test
    @Order(5)
    void testGetItemById() {
        Long itemId = 1L;
        Item mockItem = new Item(itemId, "item-1");

        when(itemRepository.findById(itemId)).thenReturn(mockItem);

        Item item = itemService.getItem(itemId);

        assertNotNull(item);
        assertEquals(itemId, item.getId());
        assertEquals("item-1", item.getName());

        verify(itemRepository, times(1)).findById(itemId);
    }
}
