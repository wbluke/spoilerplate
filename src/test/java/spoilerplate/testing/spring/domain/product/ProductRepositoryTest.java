package spoilerplate.testing.spring.domain.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static spoilerplate.testing.spring.domain.product.ProductSellingStatus.*;

//@SpringBootTest
@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @DisplayName("원하는 판매상태를 가진 상품을 조회한다.")
    @Test
    void findProductsBySellingStatusIn() {
        // given
        Product product1 = Product.builder()
            .productNumber("1")
            .sellingStatus(SELLING)
            .name("아메리카노")
            .price(4000)
            .build();
        Product product2 = Product.builder()
            .productNumber("2")
            .sellingStatus(HOLD)
            .name("카페라떼")
            .price(4500)
            .build();
        Product product3 = Product.builder()
            .productNumber("3")
            .sellingStatus(STOP_SELLING)
            .name("팥빙수")
            .price(3500)
            .build();

        productRepository.saveAll(Arrays.asList(product1, product2, product3));

        // when
        List<Product> products = productRepository.findProductsBySellingStatusIn(Arrays.asList(SELLING, HOLD));

        // then
        assertThat(products).hasSize(2)
            .extracting("productNumber", "name", "sellingStatus")
            .containsExactlyInAnyOrder(
                tuple("1", "아메리카노", SELLING),
                tuple("2", "카페라떼", HOLD)
            );
    }

}