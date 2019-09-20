package chapter1;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author fynwin@gmail.com
 */
public class PerformanceGroup {
    private final Map<String, Play> plays;

    public PerformanceGroup() {
        plays = Maps.newConcurrentMap();
        plays.put("hamlet", new Play("Hamlet", "tragedy"));
        plays.put("as-like", new Play("As You Like It", "comedy"));
        plays.put("othello", new Play("Othello", "tragedy"));
    }

    public String statement(Invoice invoice) {
        double totalAmount = 0;
        int volumeCredits = 0;
        String result = "Statement for " + invoice.consumer + '\n';
        NumberFormat fmt = NumberFormat.getCurrencyInstance(new Locale("en", "US"));
        for (Performance perf : invoice.performances) {
            double thisAmount = amountFor(perf);
            volumeCredits += volumeCreditsFor(perf);
            result += ' ' + playFor(perf).name + ": " + fmt.format(thisAmount / 100) + " (" + perf.audience + " seats)\n";
            totalAmount += thisAmount;
        }

        result += "Amount owed is " + fmt.format(totalAmount / 100) + '\n';
        result += "You earned " + volumeCredits + " credits\n";
        return result;
    }

    private Play playFor(Performance performance) {
        return plays.get(performance.playID);
    }

    private double amountFor(Performance performance) {
        double result;
        switch (playFor(performance).type) {
            case "tragedy":
                result = 40000;
                if (performance.audience > 30) {
                    result += 1000 * (performance.audience - 30);
                }
                break;
            case "comedy":
                result = 30000;
                if (performance.audience > 20) {
                    result += 10000 + 500 * (performance.audience - 20);
                }
                result += 300 * performance.audience;
                break;
            default:
                throw new IllegalStateException("Unkonwn type:" + playFor(performance).type);
        }
        return result;
    }

    private int volumeCreditsFor(Performance performance) {
        int volumeCredits = Math.max(performance.audience - 30, 0);
        if ("comedy".equals(playFor(performance).type)) {
            volumeCredits += Math.floor(performance.audience / 5);
        }
        return volumeCredits;
    }

    public static void main(String[] args) {
        List<Performance> performances = Lists.newArrayList();
        performances.add(new Performance("hamlet", 55));
        performances.add(new Performance("as-like", 35));
        performances.add(new Performance("othello", 40));
        Invoice invoice = new Invoice("BigCo", performances);

        System.out.println(new PerformanceGroup().statement(invoice));
    }
}
