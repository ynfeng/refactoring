package chapter1;

import java.util.List;

/**
 * @author fynwin@gmail.com
 */
public class Invoice {
    public final String costumer;
    public final List<Performance> performances;

    public Invoice(String costumer, List<Performance> performances) {
        this.costumer = costumer;
        this.performances = performances;
    }
}
