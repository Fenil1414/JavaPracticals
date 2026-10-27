
import java.util.*;
import java.util.function.UnaryOperator;

public class DiscountEngine {
    public static void main(String[] args) {
        List<Double> prices = Arrays.asList(100.0, 200.0, 500.0);

        UnaryOperator<Double> discount10 = price -> price * 0.90;
        UnaryOperator<Double> discount20 = price -> price * 0.80;

        List<UnaryOperator<Double>> rules =
                Arrays.asList(discount10, discount20);

        for (UnaryOperator<Double> rule : rules) {
            System.out.println("Discounted prices:");

            prices.stream()
                  .map(rule)
                  .forEach(System.out::println);
        }
    }
}
