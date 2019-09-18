package chapter1;

import java.util.List;

/**
 * @author fynwin@gmail.com
 */
public class Invoice {
    public final String consumer;
    public final List<Performance> performances;

    public Invoice(String consumer, List<Performance> performances) {
        this.consumer = consumer;
        this.performances = performances;
    }
}
