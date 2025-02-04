package mp2720.lab2;

import java.util.Random;

public class Dice {
    private static Random rand = new Random();

    public static boolean roll(int favourable, int total) {
        return rand.nextInt(total + 1) <= favourable;
    }

    public static boolean roll(int probability_percentage) {
        return rand.nextInt(101) <= probability_percentage;
    }
}
