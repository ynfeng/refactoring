package chapter1;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import org.junit.Test;

/**
 * @author fynwin@gmail.com
 */
public class PerformanceGroupTest {

    @Test
    public void should_align_with_base_line() {
        Map<String, Play> plays = Maps.newConcurrentMap();
        plays.put("hamlet", new Play("Hamlet", "tragedy"));
        plays.put("as-like", new Play("As You Like It", "comedy"));
        plays.put("othello", new Play("Othello", "tragedy"));

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

        String result = new PerformanceGroup().statement(invoice, plays);
        assertThat(result, is(baseLine));
    }
}
