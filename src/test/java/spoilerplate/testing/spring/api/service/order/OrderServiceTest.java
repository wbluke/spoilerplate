package spoilerplate.testing.spring.api.service.order;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import spoilerplate.testing.spring.api.controller.order.request.OrderRequest;
import spoilerplate.testing.spring.api.service.order.response.OrderResponse;
import spoilerplate.testing.spring.domain.order.OrderRepository;
import spoilerplate.testing.spring.domain.orderproduct.OrderProductRepository;
import spoilerplate.testing.spring.domain.product.Product;
import spoilerplate.testing.spring.domain.product.ProductRepository;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static spoilerplate.testing.spring.domain.product.ProductSellingStatus.SELLING;

//@Transactional
@SpringBootTest
class OrderServiceTest {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderProductRepository orderProductRepository;
    @Autowired
    private OrderService orderService;

    @AfterEach
    void tearDown() {
        orderProductRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        orderRepository.deleteAllInBatch();
    }

    @DisplayName("상품번호 리스트로 주문을 생성한다.")
    @Test
    void createOrder() {
        // given
        LocalDateTime orderCreatedDateTime = LocalDateTime.now();

        Product product1 = createProduct("1", 1000);
        Product product2 = createProduct("2", 3000);
        Product product3 = createProduct("3", 5000);
        productRepository.saveAll(Arrays.asList(product1, product2, product3));

        OrderRequest orderRequest = new OrderRequest(Arrays.asList("1", "2"));

        // when
        OrderResponse orderResponse = orderService.createOrder(orderRequest, orderCreatedDateTime);

        // then
        assertThat(orderResponse.getId()).isNotNull();
        assertThat(orderResponse)
            .extracting("createdDateTime", "totalPrice")
            .containsExactlyInAnyOrder(orderCreatedDateTime, 4000);
        assertThat(orderResponse.getProducts())
            .extracting("productNumber", "price")
            .containsExactlyInAnyOrder(
                tuple("1", 1000),
                tuple("2", 3000)
            );
    }

    @DisplayName("중복되는 상품번호 리스트로 주문을 생성한다.")
    @Test
    void createOrderWithDuplicateProductNumbers() {
        // given
        LocalDateTime orderCreatedDateTime = LocalDateTime.now();

        Product product1 = createProduct("1", 1000);
        Product product2 = createProduct("2", 3000);
        Product product3 = createProduct("3", 5000);
        productRepository.saveAll(Arrays.asList(product1, product2, product3));

        OrderRequest orderRequest = new OrderRequest(Arrays.asList("1", "1"));

        // when
        OrderResponse orderResponse = orderService.createOrder(orderRequest, orderCreatedDateTime);

        // then
        assertThat(orderResponse.getId()).isNotNull();
        assertThat(orderResponse)
            .extracting("createdDateTime", "totalPrice")
            .containsExactlyInAnyOrder(orderCreatedDateTime, 2000);
        assertThat(orderResponse.getProducts())
            .extracting("productNumber", "price")
            .containsExactlyInAnyOrder(
                tuple("1", 1000),
                tuple("1", 1000)
            );
    }

    private static Product createProduct(String productNumber, int price) {
        return Product.builder()
            .productNumber(productNumber)
            .sellingStatus(SELLING)
            .name("아메리카노")
            .price(price)
            .build();
    }

}