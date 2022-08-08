package spoilerplate.testing.spring.api.controller.product;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import spoilerplate.testing.spring.api.service.product.ProductService;
import spoilerplate.testing.spring.api.service.product.response.ProductResponse;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProductController {

    private final ProductService productService;

    @GetMapping("/api/v1/products/selling")
    public ResponseEntity<List<ProductResponse>> getSellingProducts() {
        return ResponseEntity.ok(productService.getSellingProducts());
    }

}
