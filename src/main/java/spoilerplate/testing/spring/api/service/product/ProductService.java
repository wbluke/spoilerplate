package spoilerplate.testing.spring.api.service.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spoilerplate.testing.spring.api.controller.product.request.ProductSearchRequest;
import spoilerplate.testing.spring.api.service.product.response.ProductResponse;
import spoilerplate.testing.spring.domain.product.Product;
import spoilerplate.testing.spring.domain.product.ProductRepository;
import spoilerplate.testing.spring.domain.product.ProductSellingStatus;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductResponse> getSellingProducts() {
        List<Product> sellingProducts = productRepository.findProductsBySellingStatusIn(ProductSellingStatus.getStatusesThatCanBeSold());

        return sellingProducts.stream()
            .map(ProductResponse::of)
            .collect(Collectors.toList());
    }

    public List<ProductResponse> search(ProductSearchRequest request) {
        List<Product> products = productRepository.findProductsByNameContainingAndTypeIn(request.getKeyword(), request.getProductTypes());

        return products.stream()
            .map(ProductResponse::of)
            .collect(Collectors.toList());
    }

}
