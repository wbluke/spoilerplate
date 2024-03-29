package spoilerplate.testing.spring.api.controller.order.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spoilerplate.testing.spring.api.service.order.request.OrderServiceRequest;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@NoArgsConstructor
public class OrderRequest {

    @NotEmpty(message = "상품번호는 필수입니다.")
    private List<String> productNumbers;

    @Builder
    private OrderRequest(List<String> productNumbers) {
        this.productNumbers = productNumbers;
    }

    public OrderServiceRequest toServiceRequest() {
        return OrderServiceRequest.builder()
            .productNumbers(productNumbers)
            .build();
    }

}
