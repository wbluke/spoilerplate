package spoilerplate.testing.spring.api.service.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import spoilerplate.testing.spring.api.service.product.request.ProductCreateServiceRequest;
import spoilerplate.testing.spring.api.service.product.response.ProductResponse;
import spoilerplate.testing.spring.domain.product.Product;
import spoilerplate.testing.spring.domain.product.ProductRepository;
import spoilerplate.testing.spring.domain.product.ProductType;

import static org.assertj.core.api.Assertions.assertThat;
import static spoilerplate.testing.spring.domain.product.ProductSellingStatus.SELLING;
import static spoilerplate.testing.spring.domain.product.ProductType.HANDMADE;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @DisplayName("상품을 신규로 등록한다.")
    @Test
    void createProduct() {
        // given
        Product product1 = Product.builder()
            .productNumber("001")
            .type(HANDMADE)
            .sellingStatus(SELLING)
            .name("아메리카노")
            .price(4000)
            .build();
        Product product2 = Product.builder()
            .productNumber("002")
            .type(HANDMADE)
            .sellingStatus(SELLING)
            .name("카페라떼")
            .price(4500)
            .build();
        Product product3 = Product.builder()
            .productNumber("003")
            .type(ProductType.BOTTLE)
            .sellingStatus(SELLING)
            .name("병음료카")
            .price(3500)
            .build();
        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);

        ProductCreateServiceRequest request = ProductCreateServiceRequest.builder()
            .type(HANDMADE)
            .sellingStatus(SELLING)
            .name("카푸치노")
            .price(5000)
            .build();

        // when
        ProductResponse product = productService.createProduct(request);

        // then
        assertThat(product)
            .extracting("productNumber", "type", "sellingStatus", "name", "price")
            .contains("004", HANDMADE, SELLING, "카푸치노", 5000);
    }

}