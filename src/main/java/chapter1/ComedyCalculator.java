package chapter1;

/**
 * @author fynwin@gmail.com
 */
public class ComedyCalculator extends PerformanceCalculator {
    public ComedyCalculator(Performance performance, Play play) {
        super(performance, play);
    }

    @Override
    public double amount() {
        double result = 30000;
        if (performance.audience > 20) {
            result += 10000 + 500 * (performance.audience - 20);
        }
        result += 300 * performance.audience;
        return result;
    }

    @Override
    public int volumeCredits() {
        return (int) (super.volumeCredits() + Math.floor(performance.audience / 5));
    }
}
