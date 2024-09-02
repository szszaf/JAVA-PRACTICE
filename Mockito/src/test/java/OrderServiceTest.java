import com.example.entities.Item;
import com.example.entities.Order;
import com.example.repositories.OrderRepository;
import com.example.services.NotificationService;
import com.example.services.OrderService;
import com.example.services.PaymentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private PaymentService paymentService;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private OrderService orderService;

    @Test
    public void testPlaceOrder_SuccessfulPayment() {
        Order order = new Order(1L, LocalDate.now(),
                List.of(new Item(1L, "Papier"), new Item(2L, "Tusz"))
                ,false);

        when(paymentService.processPayment(order)).thenReturn(true);

        boolean isPaymentSuccessful = orderService.placeOrder(order);

        assertTrue(isPaymentSuccessful);
        assertTrue(order.isPaid());
        verify(orderRepository, times(2)).save(order);
        verify(notificationService, atLeast(1)).confirm(order);
    }

    @Test
    public void testPlaceOrder_FailedPayment() {
        Order order = new Order(1L, LocalDate.now(),
                List.of(new Item(1L, "Papier"), new Item(2L, "Tusz"))
                ,false);

        when(paymentService.processPayment(order)).thenReturn(false);

        boolean isPaymentSuccessful = orderService.placeOrder(order);

        assertFalse(isPaymentSuccessful);
        assertFalse(order.isPaid());
        verify(orderRepository).save(order);
        verify(notificationService, never()).confirm(order);
    }

    @Test
    public void testOrder_ItemAdded() {
        Order spyOrder = spy(new Order(1L, LocalDate.now(),
                new ArrayList<Item>(),false));

        spyOrder.getItemList().add(new Item(1L, "Papier"));

        assertEquals(spyOrder.getItemList().size(), 1);
    }
}
