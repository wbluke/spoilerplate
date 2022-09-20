package spoilerplate.testing.spring.api.controller.order;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import spoilerplate.testing.spring.api.ApiResponse;
import spoilerplate.testing.spring.api.controller.order.request.OrderRequest;
import spoilerplate.testing.spring.api.service.order.OrderService;
import spoilerplate.testing.spring.api.service.order.response.OrderResponse;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/api/v1/orders/new")
    public ApiResponse<OrderResponse> createOrder(@Valid @RequestBody OrderRequest orderRequest) {
        OrderResponse orderResponse = orderService.createOrder(orderRequest.toServiceRequest(), LocalDateTime.now());
        return ApiResponse.created(orderResponse);
    }

}
