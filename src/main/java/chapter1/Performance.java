package chapter1;

/**
 * @author fynwin@gmail.com
 */
public class Performance {
    public final String playID;
    public final int audience;
    public Play play;
    public double amount;
    public int volumeCredits;

    public Performance(String playID, int audience) {
        this.playID = playID;
        this.audience = audience;
    }
}
