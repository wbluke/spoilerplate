package spoilerplate.testing.unit;

public class KioskRunner {

    public static void main(String[] args) {
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano(4000));
        System.out.println(">>> 아메리카노 추가");
        cafeKiosk.add(new Latte(4500));
        System.out.println(">>> 라떼 추가");

        int totalPrice = cafeKiosk.calculateTotalPrice();
        System.out.println("총 주문가격 : " + totalPrice);
    }

}
