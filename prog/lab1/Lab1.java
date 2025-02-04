import java.util.Random;

class Lab1 {
    static void printMatrix(float[][] mat) {
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[i].length; j++)
                System.out.print(String.format("%.5f ", mat[i][j]));

            System.out.println();
        }
    }

    static float calcElement(int wi, float x) {
        final double THIRD = 1 / 3.;

        if (wi == 14)
            return (float) Math.pow(Math.atan(Math.cos(x)), THIRD);
        else if (wi >= 2 && wi <= 6 || wi == 10)
            return (float) Math.pow(THIRD * Math.pow(x / 2, x), Math.pow(x, THIRD) * THIRD);
        else
            return (float) Math.pow(Math.sin(Math.pow(x, THIRD)), (0.5 - x) / 2 / 0.25 * THIRD);
    }

    public static void main(String[] args) {
        // 1.
        int[] w1 = new int[8];
        for (int i = 0; i < w1.length; i++)
            w1[i] = (w1.length - i) * 2;

        // 2.
        Random rand;
        if (args.length > 0) {
            // Parse seed if provided.
            long seed = Long.parseLong(args[0]);
            rand = new Random(seed);
        } else {
            rand = new Random();
        }

        float[] x = new float[12];
        for (int i = 0; i < x.length; i++)
            x[i] = rand.nextFloat(-13f, Math.nextUp(13f));

        // 3.
        float[][] w = new float[8][12];
        for (int i = 0; i < w.length; i++)
            for (int j = 0; j < w[i].length; ++j)
                w[i][j] = calcElement(w1[i], x[j]);

        printMatrix(w);
    }
}
