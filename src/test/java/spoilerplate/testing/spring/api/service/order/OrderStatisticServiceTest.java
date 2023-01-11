package spoilerplate.testing.spring.api.service.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import spoilerplate.testing.spring.client.mail.MailSendClient;
import spoilerplate.testing.spring.domain.history.mail.MailSendHistory;
import spoilerplate.testing.spring.domain.history.mail.MailSendHistoryRepository;
import spoilerplate.testing.spring.domain.order.Order;
import spoilerplate.testing.spring.domain.order.OrderRepository;
import spoilerplate.testing.spring.domain.order.OrderStatus;
import spoilerplate.testing.spring.domain.product.Product;
import spoilerplate.testing.spring.domain.product.ProductRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest
class OrderStatisticServiceTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MailSendHistoryRepository mailSendHistoryRepository;

    @Autowired
    private OrderStatisticService orderStatisticService;

    @MockBean
    private MailSendClient mailSendClient;

    @DisplayName("특정 일자의 총 매출을 메일로 전송한다.")
    @Test
    void sendEmailAboutTotalSalesAmount() {
        // given
        LocalDateTime createdDateTime = LocalDateTime.of(2022, 9, 23, 10, 0, 0);
        String toEmail = "test@test.com";

        Product product1 = Product.builder()
            .productNumber("001")
            .name("아메리카노")
            .price(4000)
            .build();
        Product product2 = Product.builder()
            .productNumber("002")
            .name("카페라떼")
            .price(4500)
            .build();
        productRepository.saveAll(List.of(product1, product2));

        Order order1 = createOrder(List.of(product1), createdDateTime);
        Order order2 = createOrder(List.of(product2), createdDateTime);
        orderRepository.saveAll(List.of(order1, order2));

        BDDMockito.given(mailSendClient.send(anyString(), anyString(), anyString(), anyString()))
            .willReturn(true);

        // when // then
        assertThatNoException().isThrownBy(() ->
            orderStatisticService.sendEmailAboutTotalSalesAmount(createdDateTime.toLocalDate(), toEmail)
        );

        List<MailSendHistory> mailSendHistories = mailSendHistoryRepository.findAll();
        assertThat(mailSendHistories).hasSize(1)
            .extracting("fromEmail", "toEmail", "subject", "content")
            .containsExactly(
                tuple("cafe@cafe.com", toEmail, "[주문통계] 총 매출", "2022-09-23의 총 매출은 8500원입니다.")
            );
    }

    private Order createOrder(List<Product> product11, LocalDateTime createdDateTime) {
        return Order.builder()
            .orderStatus(OrderStatus.INIT)
            .products(product11)
            .createdDateTime(createdDateTime)
            .build();
    }

}