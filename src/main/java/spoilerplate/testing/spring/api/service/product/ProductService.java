package spoilerplate.testing.spring.api.service.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spoilerplate.testing.spring.api.service.product.request.ProductCreateServiceRequest;
import spoilerplate.testing.spring.api.service.product.request.ProductSearchServiceRequest;
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

    @Transactional
    public ProductResponse createProduct(ProductCreateServiceRequest request) {
        String nextProductNumber = createNextProductNumber();

        Product product = createProduct(request, nextProductNumber);

        Product savedProduct = productRepository.save(product);
        return ProductResponse.of(savedProduct);
    }

    public List<ProductResponse> getSellingProducts() {
        List<Product> sellingProducts = productRepository.findProductsBySellingStatusIn(ProductSellingStatus.getStatusesThatCanBeSold());

        return sellingProducts.stream()
            .map(ProductResponse::of)
            .collect(Collectors.toList());
    }

    public List<ProductResponse> search(ProductSearchServiceRequest request) {
        List<Product> products = productRepository.findProductsByNameContainingAndTypeIn(request.getKeyword(), request.getProductTypes());

        return products.stream()
            .map(ProductResponse::of)
            .collect(Collectors.toList());
    }

    private String createNextProductNumber() {
        Product latestRegisteredProduct = productRepository.findFirst1ByOrderByIdDesc();
        String productNumber = latestRegisteredProduct.getProductNumber();

        int productNumberInt = Integer.parseInt(productNumber);
        int nextProductNumberInt = productNumberInt + 1;

        return String.format("%03d", nextProductNumberInt);
    }

    private static Product createProduct(ProductCreateServiceRequest request, String nextProductNumber) {
        return Product.builder()
            .productNumber(nextProductNumber)
            .type(request.getType())
            .sellingStatus(request.getSellingStatus())
            .name(request.getName())
            .price(request.getPrice())
            .build();
    }

}
