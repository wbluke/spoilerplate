package spoilerplate.testing.unit.beverage;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Latte implements Beverage {

    private final int price = 4500;

    @Override
    public String getName() {
        return "라떼";
    }

}
