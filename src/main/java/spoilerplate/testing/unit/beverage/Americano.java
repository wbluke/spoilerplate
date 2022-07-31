package spoilerplate.testing.unit.beverage;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Americano implements Beverage {

    private final int price = 4000;

    @Override
    public String getName() {
        return "아메리카노";
    }

}
