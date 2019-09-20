package chapter1;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.google.common.collect.Lists;
import java.util.List;
import org.junit.Test;

/**
 * @author fynwin@gmail.com
 */
public class PerformanceGroupTest {

    @Test
    public void should_align_with_base_line() {
        List<Performance> performances = Lists.newArrayList();
        performances.add(new Performance("hamlet", 55));
        performances.add(new Performance("as-like", 35));
        performances.add(new Performance("othello", 40));
        Invoice invoice = new Invoice("BigCo", performances);

        String baseLine = "Statement for BigCo\n" +
            " Hamlet: $650.00 (55 seats)\n" +
            " As You Like It: $580.00 (35 seats)\n" +
            " Othello: $500.00 (40 seats)\n" +
            "Amount owed is $1,730.00\n" +
            "You earned 47 credits\n";

        String result = new PerformanceGroup().statement(invoice);
        assertThat(result, is(baseLine));
    }
}
