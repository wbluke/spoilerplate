package spoilerplate.testing.spring.domain.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import spoilerplate.testing.spring.domain.product.Product;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class OrderTest {

    @DisplayName("처음 만든 주문의 상태는 주문생성 상태이다.")
    @Test
    void createInitialOrder() {
        // given
        Product product1 = Product.builder()
            .productNumber("1")
            .name("아메리카노")
            .price(4000)
            .build();
        Product product2 = Product.builder()
            .productNumber("2")
            .name("카페라떼")
            .price(4500)
            .build();

        // when
        Order order = Order.createInitialOrder(Arrays.asList(product1, product2), LocalDateTime.now());

        // then
        assertThat(order.getOrderStatus()).isEqualByComparingTo(OrderStatus.INIT);
    }

}