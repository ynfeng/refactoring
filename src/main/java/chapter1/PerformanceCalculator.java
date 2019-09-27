package chapter1;

/**
 * @author fynwin@gmail.com
 */
public class PerformanceCalculator {
    protected final Performance performance;
    protected final Play play;

    public PerformanceCalculator(Performance performance, Play play) {
        this.performance = performance;
        this.play = play;
    }

    static PerformanceCalculator createPerformanceCalculator(Performance performance, Play play) {
        switch (play.type) {
            case "tragedy":
                return new TragedyCalculator(performance, play);
            case "comedy":
                return new ComedyCalculator(performance, play);
            default:
                throw new IllegalStateException("Unknown type:" + play.type);
        }
    }

    public double amount() {
        throw new IllegalStateException("Unkonwn type:" + play.type);
    }

    public int volumeCredits() {
        return Math.max(performance.audience - 30, 0);
    }
}
