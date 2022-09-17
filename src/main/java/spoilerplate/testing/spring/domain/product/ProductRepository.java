package spoilerplate.testing.spring.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * select *
     * from product
     * where status in ('SELLING', 'HOLD');
     */
    List<Product> findProductsBySellingStatusIn(List<ProductSellingStatus> sellingStatuses);

    List<Product> findProductsByProductNumberIn(Set<String> productNumbers);

    List<Product> findProductsByNameContainingAndTypeIn(String name, List<ProductType> types);

    Product findFirst1ByOrderByIdDesc();

}
