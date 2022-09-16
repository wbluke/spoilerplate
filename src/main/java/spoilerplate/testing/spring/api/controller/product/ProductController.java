package spoilerplate.testing.spring.api.controller.product;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import spoilerplate.testing.spring.api.ApiResponse;
import spoilerplate.testing.spring.api.controller.product.request.ProductSearchRequest;
import spoilerplate.testing.spring.api.service.product.ProductService;
import spoilerplate.testing.spring.api.service.product.response.ProductResponse;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProductController {

    private final ProductService productService;

    @GetMapping("/api/v1/products/selling")
    public ApiResponse<List<ProductResponse>> getSellingProducts() {
        return ApiResponse.ok(productService.getSellingProducts());
    }

    @GetMapping("/api/v1/products/search")
    public ApiResponse<List<ProductResponse>> search(@Valid ProductSearchRequest request) {
        return ApiResponse.ok(productService.search(request.toServiceRequest()));
    }

}
