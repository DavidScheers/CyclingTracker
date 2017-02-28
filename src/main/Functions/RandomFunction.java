package main.Functions;

import java.time.LocalDate;
import java.util.Random;

/**
 * Created by davids on 27/02/2017.
 */
public class RandomFunction implements Function {

    @Override
    public double getValue(LocalDate date) {
        Random rand = new Random();
        return rand.nextDouble();
    }
}
