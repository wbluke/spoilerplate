package spoilerplate.testing.spring.api.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spoilerplate.testing.spring.api.controller.order.request.OrderRequest;
import spoilerplate.testing.spring.api.service.order.response.OrderResponse;
import spoilerplate.testing.spring.domain.order.Order;
import spoilerplate.testing.spring.domain.order.OrderRepository;
import spoilerplate.testing.spring.domain.product.Product;
import spoilerplate.testing.spring.domain.product.ProductRepository;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderResponse createOrder(OrderRequest orderRequest, LocalDateTime orderCreatedDateTime) {
        List<String> productNumbers = orderRequest.getProductNumbers();
        List<Product> products = findProductsBy(productNumbers);

        Order order = Order.createInitialOrder(products, orderCreatedDateTime);
        Order savedOrder = orderRepository.save(order);

        return OrderResponse.of(savedOrder);
    }

    private List<Product> findProductsBy(List<String> productNumbers) {
        List<Product> productCandidates = productRepository.findProductsByProductNumberIn(new HashSet<>(productNumbers));
        Map<String, Product> productMap = productCandidates.stream()
            .collect(Collectors.toMap(Product::getProductNumber, p -> p));

        return productNumbers.stream()
            .map(productMap::get)
            .collect(Collectors.toList());
    }

}
