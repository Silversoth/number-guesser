package numberguesser.model;
//RANDOM NUMBER GENERATOR CLASS

import java.util.Random;

/**
 * The `RNG` class is responsible for generating a random integer within a specified range.
 */
public class RNG {
    private int result;
    private int min;
    private int max;

    /**
     * Constructs an `RNG` object with a specified minimum and maximum range.
     * A random integer is generated within this range and stored in the `result` field.
     *
     * @param min the minimum value (inclusive) of the range
     * @param max the maximum value (inclusive) of the range
     */
    public RNG(int min, int max) {
        Random r = new Random();
        this.result = r.nextInt((max - min) + 1) + min;
        this.min = min;
        this.max = max;
    }

    public int getResult() {
        return result;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }
}

