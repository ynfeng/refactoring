package chapter1;

/**
 * @author fynwin@gmail.com
 */
public class TragedyCalculator extends PerformanceCalculator {
    public TragedyCalculator(Performance performance, Play play) {
        super(performance, play);
    }

    @Override
    public double amount() {
        double result = 40000;
        if (performance.audience > 30) {
            result += 1000 * (performance.audience - 30);
        }
        return result;
    }

    @Override
    public int volumeCredits() {
        return super.volumeCredits();
    }
}
