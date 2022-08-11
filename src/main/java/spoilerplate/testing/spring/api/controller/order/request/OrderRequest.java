package spoilerplate.testing.spring.api.controller.order.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class OrderRequest {

    private List<String> productNumbers;

    public OrderRequest(List<String> productNumbers) {
        this.productNumbers = productNumbers;
    }

}
