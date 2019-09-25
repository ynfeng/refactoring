package chapter1;

import java.util.List;
import java.util.Map;

/**
 * @author fynwin@gmail.com
 */
public class StatementData {
    public String customer;
    public List<Performance> performances;

    public static StatementData createStatementData(Map<String, Play> plays, Invoice invoice) {
        StatementData data = new StatementData();
        data.customer = invoice.costumer;
        data.performances = invoice.performances;
        data.performances.forEach(p -> {
            p.play = plays.get(p.playID);
            p.amount = amountFor(p);
            p.volumeCredits = volumeCreditsFor(p);
        });
        return data;
    }

    int totalVolumeCredits() {
        return performances.stream()
            .mapToInt(p -> p.volumeCredits)
            .reduce((a, b) -> a + b)
            .orElse(0);
    }

    double totalAmount() {
        return performances.stream()
            .mapToDouble(p -> p.amount)
            .reduce((a, b) -> a + b)
            .orElse(0.0);
    }

    private static int volumeCreditsFor(Performance performance) {
        int volumeCredits = Math.max(performance.audience - 30, 0);
        if ("comedy".equals(performance.play.type)) {
            volumeCredits += Math.floor(performance.audience / 5);
        }
        return volumeCredits;
    }

    private static double amountFor(Performance performance) {
        double result;
        switch (performance.play.type) {
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
                throw new IllegalStateException("Unkonwn type:" + performance.play.type);
        }
        return result;
    }
}
