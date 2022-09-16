package spoilerplate.testing.spring.api.service.order.request;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderServiceRequest {

    private List<String> productNumbers;

    @Builder
    private OrderServiceRequest(List<String> productNumbers) {
        this.productNumbers = productNumbers;
    }

}
