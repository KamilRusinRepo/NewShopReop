package com.shop.prshop;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.shop.prshop.controller.OrderController;
import com.shop.prshop.dto.OrderDto;
import com.shop.prshop.repository.OrderItemRepository;
import com.shop.prshop.repository.OrderRepository;
import com.shop.prshop.service.CartService;
import com.shop.prshop.service.OrderService;
import jakarta.inject.Inject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(OrderControllerTest.class)
public class OrderControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CartService cartService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }
    @Test
    public void getCartView_success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/cart/"))
                .andExpect(status().isOk())
                .andExpect(view().name("cartview"));
    }

    @Test
    public void increaseItem_success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/cart/increase/{itemId}", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("cartview"));

        verify(cartService).addItemToCart(1L);
    }

    @Test
    public void decreaseItem_success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/cart/decrease/{itemId}", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("cartview"));

        verify(cartService).decreaseItemFromCart(1L);
    }

    @Test
    public void removeItem_success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/cart/removeItem/{itemId}", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("cartview"));

        verify(cartService).removeItemFromCart(1L);
    }

    @Test
    public void showCheckout_success() throws Exception {
        OrderDto orderDto = OrderDto.builder()
                .firstName("Jon")
                .lastName("Example")
                .email("johexample@gmail.com")
                .build();

        when(orderService.orderLoginInChecker()).thenReturn(orderDto);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/cart/Checkout"))
                .andExpect(status().isOk())
                .andExpect(view().name("checkout"))
                .andExpect(model().attribute("orderDto", orderDto));
    }

    @Test
    public void saveOrder_success() throws Exception {
        OrderDto orderDto = OrderDto.builder()
                .firstName("Jon")
                .lastName("Example")
                .email("jonexample@gmail.com")
                .city("Warsaw")
                .street("Emilii Platter")
                .homeNumber("10")
                .postCode("10-203")
                .phoneNumber("123456789")
                .build();

        mockMvc.perform(MockMvcRequestBuilders
                .post("/cart/saveorder")
                .param("firstName", orderDto.getFirstName())
                .param("lastName", orderDto.getLastName())
                .param("email", orderDto.getEmail())
                .param("city", orderDto.getCity())
                .param("street", orderDto.getStreet())
                .param("homeNumber", orderDto.getHomeNumber())
                .param("postCode", orderDto.getPostCode())
                .param("phoneNumber", orderDto.getPhoneNumber()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

    }
}
