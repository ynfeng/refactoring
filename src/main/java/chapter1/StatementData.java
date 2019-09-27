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
            PerformanceCalculator calculator = PerformanceCalculator.createPerformanceCalculator(p, p.play);
            p.amount = calculator.amount();
            p.volumeCredits = calculator.volumeCredits();
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
}
