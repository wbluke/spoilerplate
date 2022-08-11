package spoilerplate.testing.spring.api.service.order.response;

import lombok.Builder;
import lombok.Getter;
import spoilerplate.testing.spring.api.service.product.response.ProductResponse;
import spoilerplate.testing.spring.domain.order.Order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class OrderResponse {

    private Long id;
    private int totalPrice;
    private LocalDateTime createdDateTime;
    private List<ProductResponse> products;

    @Builder
    private OrderResponse(Long id, int totalPrice, LocalDateTime createdDateTime, List<ProductResponse> products) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.createdDateTime = createdDateTime;
        this.products = products;
    }

    public static OrderResponse of(Order order) {
        return OrderResponse.builder()
            .id(order.getId())
            .totalPrice(order.getTotalPrice())
            .createdDateTime(order.getCreatedDateTime())
            .products(
                order.getOrderProducts().stream()
                    .map(orderProduct -> ProductResponse.of(orderProduct.getProduct()))
                    .collect(Collectors.toList())
            )
            .build();
    }

}
