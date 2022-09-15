package spoilerplate.testing.spring.api.service.order;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import spoilerplate.testing.spring.api.controller.order.request.OrderRequest;
import spoilerplate.testing.spring.api.service.order.response.OrderResponse;
import spoilerplate.testing.spring.domain.order.OrderRepository;
import spoilerplate.testing.spring.domain.orderproduct.OrderProductRepository;
import spoilerplate.testing.spring.domain.product.Product;
import spoilerplate.testing.spring.domain.product.ProductRepository;
import spoilerplate.testing.spring.domain.product.ProductType;
import spoilerplate.testing.spring.domain.stock.Stock;
import spoilerplate.testing.spring.domain.stock.StockRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static spoilerplate.testing.spring.domain.product.ProductSellingStatus.SELLING;
import static spoilerplate.testing.spring.domain.product.ProductType.*;

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
    private StockRepository stockRepository;
    @Autowired
    private OrderService orderService;

    @AfterEach
    void tearDown() {
        orderProductRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        orderRepository.deleteAllInBatch();
        stockRepository.deleteAllInBatch();
    }

    @DisplayName("상품번호 리스트로 주문을 생성한다.")
    @Test
    void createOrder() {
        // given
        LocalDateTime orderCreatedDateTime = LocalDateTime.now();

        Product product1 = createProduct(HANDMADE, "001", 1000);
        Product product2 = createProduct(HANDMADE, "002", 3000);
        Product product3 = createProduct(HANDMADE, "003", 5000);
        productRepository.saveAll(List.of(product1, product2, product3));

        OrderRequest orderRequest = new OrderRequest(List.of("001", "002"));

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
                tuple("001", 1000),
                tuple("002", 3000)
            );
    }

    @DisplayName("재고 확인 후 주문 정상 케이스") // TODO: 2022/08/22 테스트에 트랜잭션 달고 통과하는지 확인 -> 실제로는 안통과하는지 확인
    @Test
    void createOrderWithStockProducts() {
        // given
        LocalDateTime orderCreatedDateTime = LocalDateTime.now();

        Product product1 = createProduct(BOTTLE, "001", 1000);
        Product product2 = createProduct(BAKERY, "002", 2000);
        Product product3 = createProduct(HANDMADE, "003", 3000);
        productRepository.saveAll(List.of(product1, product2, product3));

        Stock stock1 = Stock.create("001", 2L);
        Stock stock2 = Stock.create("002", 2L);
        stockRepository.saveAll(List.of(stock1, stock2));

        OrderRequest orderRequest = new OrderRequest(List.of("001", "001", "002", "003", "003"));

        // when
        OrderResponse orderResponse = orderService.createOrder(orderRequest, orderCreatedDateTime);

        // then
        assertThat(orderResponse.getId()).isNotNull();
        assertThat(orderResponse)
            .extracting("createdDateTime", "totalPrice")
            .containsExactlyInAnyOrder(orderCreatedDateTime, 10000);
        assertThat(orderResponse.getProducts())
            .extracting("productNumber", "price")
            .containsExactlyInAnyOrder(
                tuple("001", 1000),
                tuple("001", 1000),
                tuple("002", 2000),
                tuple("003", 3000),
                tuple("003", 3000)
            );

        List<Stock> stocks = stockRepository.findAll();
        assertThat(stocks).hasSize(2)
            .extracting("productNumber", "quantity")
            .containsExactlyInAnyOrder(
                tuple("001", 0L),
                tuple("002", 1L)
            );
    }

    @DisplayName("중복되는 상품번호 리스트로 주문을 생성한다.")
    @Test
    void createOrderWithDuplicateProductNumbers() {
        // given
        LocalDateTime orderCreatedDateTime = LocalDateTime.now();

        Product product1 = createProduct(HANDMADE, "001", 1000);
        Product product2 = createProduct(HANDMADE, "002", 3000);
        Product product3 = createProduct(HANDMADE, "003", 5000);
        productRepository.saveAll(List.of(product1, product2, product3));

        OrderRequest orderRequest = new OrderRequest(List.of("001", "001"));

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
                tuple("001", 1000),
                tuple("001", 1000)
            );
    }

    @DisplayName("재고가 없는 상품으로 주문을 생성하려는 경우 예외가 발생한다.")
    @Test
    void createOrderWithProductHasNoStock() {
        // given
        LocalDateTime orderCreatedDateTime = LocalDateTime.now();

        Product product1 = createProduct(BOTTLE, "001", 1000);
        Product product2 = createProduct(BAKERY, "002", 2000);
        Product product3 = createProduct(HANDMADE, "003", 3000);
        productRepository.saveAll(List.of(product1, product2, product3));

        Stock stock1 = Stock.create("001", 1L);
        Stock stock2 = Stock.create("002", 2L);
        stock1.deductQuantity(1L); // TODO: 2022/08/12 need to refactoring
        stockRepository.saveAll(List.of(stock1, stock2));

        OrderRequest orderRequest = new OrderRequest(List.of("001", "002"));

        // when // then
        assertThatThrownBy(() -> orderService.createOrder(orderRequest, orderCreatedDateTime))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("재고가 부족한 상품이 있습니다.");
    }

    private Product createProduct(ProductType type, String productNumber, int price) {
        return Product.builder()
            .type(type)
            .productNumber(productNumber)
            .sellingStatus(SELLING)
            .name("")
            .price(price)
            .build();
    }

}