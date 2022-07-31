package spoilerplate.testing.unit.order;

import lombok.Getter;
import spoilerplate.testing.unit.beverage.Beverage;

import java.util.List;

@Getter
public class Order {

    private List<Beverage> beverages;
    private OrderStatus status;

    public Order(List<Beverage> beverages, OrderStatus status) {
        this.beverages = beverages;
        this.status = status;
    }

}
