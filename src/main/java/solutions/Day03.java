package main.java.solutions;

import java.util.ArrayList;
import java.util.List;

public class Day03 implements Day {

    @Override
    public String part1(List<String> input) {
        List<Bit> listOfBits = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            listOfBits.add(new Bit());
        }

        for (String number : input) {
            // divide each number into its 12 bits and add to count
            for (int bitPlace = 0; bitPlace < number.length(); bitPlace++) {
                int currentBit = Character.getNumericValue(number.charAt(bitPlace)); // is it a 1 or 0?
                if (currentBit == 1) {
                    listOfBits.get(bitPlace).addOne();
                } else {
                    listOfBits.get(bitPlace).addZero();
                }
            }
        }

        String gammaRate = "";
        String epsilonRate = "";
        for (Bit bit : listOfBits) {
            if (bit.getOnesCount() > bit.getZeroesCount()) {
                gammaRate = gammaRate.concat("1");
                epsilonRate = epsilonRate.concat("0");
            } else {
                gammaRate = gammaRate.concat("0");
                epsilonRate = epsilonRate.concat("1");
            }
            System.out.println("Gamma rate " + gammaRate + " and epsilon rate " + epsilonRate);
        }
        int gammaRateInDecimal = Integer.parseInt(gammaRate, 2);
        int epsilonRateInDecimal = Integer.parseInt(epsilonRate, 2);
        return Integer.toString(gammaRateInDecimal*epsilonRateInDecimal);
    }

    @Override
    public String part2(List<String> input) {
        return null;
    }

    private static class Bit {
        int onesCount = 0;
        int zeroesCount = 0;

        public void addOne() {
            onesCount += 1;
        }

        public void addZero() {
            zeroesCount += 1;
        }

        public int getOnesCount() {
            return onesCount;
        }

        public int getZeroesCount() {
            return zeroesCount;
        }
    }
}
