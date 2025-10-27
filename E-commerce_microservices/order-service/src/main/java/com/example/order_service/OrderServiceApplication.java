package com.example.order_service;

import com.example.order_service.dao.OrderDao;
import com.example.order_service.model.Order;
import com.example.order_service.model.OrderItem;
import com.example.order_service.model.Status;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
@EnableFeignClients
public class OrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}

//
//	@Bean
//	public CommandLineRunner loadTestData(OrderDao orderDao) {
//		return args -> {
//			// Example ORDER
//			Order order = new Order();
//			order.setUserId(1L);
//			order.setStatus(Status.PENDING);
//			order.setCreatedAt(LocalDateTime.now());
//			order.setTotalPrice(BigDecimal.ZERO); // will UPDATE LATER
//
//			// Example ORDER ITEMS
//			OrderItem item1 = new OrderItem();
//			item1.setOrder(order);
//			item1.setProductId(1L);
//			item1.setQuantity(2);
//			item1.setPriceAtPurchase(new BigDecimal("450"));
//
//			OrderItem item2 = new OrderItem();
//			item2.setOrder(order);
//			item2.setProductId(2L);
//			item2.setQuantity(1);
//			item2.setPriceAtPurchase(new BigDecimal("1200"));
//
//			// Add Order Items to order
//			order.setOrderItems(List.of(item1, item2));
//
//			// Calculate totalPrice manually
//			BigDecimal total = BigDecimal.ZERO;
//			total = total.add(item1.getPriceAtPurchase().multiply(BigDecimal.valueOf(item1.getQuantity())));
//			total = total.add(item2.getPriceAtPurchase().multiply(BigDecimal.valueOf(item2.getQuantity())));
//			order.setTotalPrice(total);
//
//			orderDao.save(order);
//
//			System.out.println("✅ Sample order inserted: " + order.getId());
//		};
//	}
}
