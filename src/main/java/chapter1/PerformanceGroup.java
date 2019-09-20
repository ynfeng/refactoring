package chapter1;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ConcurrentMap;

/**
 * @author fynwin@gmail.com
 */
public class PerformanceGroup {
    private final ConcurrentMap<String, Play> plays;

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
            Play play = plays.get(perf.playID);
            double thisAmount;
            switch (play.type) {
                case "tragedy":
                    thisAmount = 40000;
                    if (perf.audience > 30) {
                        thisAmount += 1000 * (perf.audience - 30);
                    }
                    break;
                case "comedy":
                    thisAmount = 30000;
                    if (perf.audience > 20) {
                        thisAmount += 10000 + 500 * (perf.audience - 20);
                    }
                    thisAmount += 300 * perf.audience;
                    break;
                default:
                    throw new IllegalStateException("Unkonwn type:" + play.type);
            }
            //add volume credits
            volumeCredits += Math.max(perf.audience - 30, 0);
            // add extra credit for every then comedy attendees
            if ("comedy".equals(play.type)) {
                volumeCredits += Math.floor(perf.audience / 5);
            }

            // print line for this order
            result += ' ' + play.name + ": " + fmt.format(thisAmount / 100) + " (" + perf.audience + " seats)\n";
            totalAmount += thisAmount;
        }

        result += "Amount owed is " + fmt.format(totalAmount / 100) + '\n';
        result += "You earned " + volumeCredits + " credits\n";
        return result;
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
