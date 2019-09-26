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
        StatementData data = StatementData.createStatementData(plays, invoice);
        return renderPlainText(data);
    }

    public String htmlStatement(Invoice invoice) {
        StatementData data = StatementData.createStatementData(plays, invoice);
        return renderHtml(data);
    }

    private String renderHtml(StatementData data) {
        String result = "<h1>Statement for " + data.customer + "</h1>\n";
        result += "<table>\n";
        result += "<tr><th>play</th><th>seats</th><th>cost</th></tr>";
        for (Performance perf : data.performances) {
            result += "<tr>";
            result += "<td>" + perf.play.name + "</td>";
            result += "<td>" + perf.audience + "</td>";
            result += "<td>" + usd(perf.amount) + "</td>";
            result += "</tr>\n";
        }
        result += "</table>";

        result += "<p>Amount owed is <em>" + usd(data.totalAmount()) + "</em></p>\n";
        result += "<p>You earned <em>" + data.totalVolumeCredits() + "</em> credits</p>\n";
        return result;
    }

    private String renderPlainText(StatementData data) {
        String result = "Statement for " + data.customer + '\n';
        for (Performance perf : data.performances) {
            result += ' ' + perf.play.name + ": " + usd(perf.amount) + " (" + perf.audience + " seats)\n";
        }

        result += "Amount owed is " + usd(data.totalAmount()) + '\n';
        result += "You earned " + data.totalVolumeCredits() + " credits\n";
        return result;
    }

    private String usd(double number) {
        NumberFormat fmt = NumberFormat.getCurrencyInstance(new Locale("en", "US"));
        return fmt.format(number / 100);
    }

    public static void main(String[] args) {
        List<Performance> performances = Lists.newArrayList();
        performances.add(new Performance("hamlet", 55));
        performances.add(new Performance("as-like", 35));
        performances.add(new Performance("othello", 40));
        Invoice invoice = new Invoice("BigCo", performances);

        System.out.println(new PerformanceGroup().statement(invoice));
        System.out.println(new PerformanceGroup().htmlStatement(invoice));
    }
}
