package solutions;

import java.lang.reflect.Array;
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
            // System.out.println("Gamma rate " + gammaRate + " and epsilon rate " + epsilonRate);
        }
        int gammaRateInDecimal = Integer.parseInt(gammaRate, 2);
        int epsilonRateInDecimal = Integer.parseInt(epsilonRate, 2);
        return Integer.toString(gammaRateInDecimal*epsilonRateInDecimal);
    }

    /* Part 2 answer "inspired by" @domoore's */
    @Override
    public String part2(List<String> input) {
        String oxygenGeneratorRating = "";
        String co2ScrubberRating = "";

        List<String> oxOutput = new ArrayList<>(input);
        List<String> coOutput = new ArrayList<>(input);

        for (int bitPosition = 0; bitPosition < oxOutput.get(0).length(); bitPosition++) {
            //System.out.println("Times through the loop: " + bitPosition);
            // System.out.println("OxOutput is currently: " + oxOutput);
            if (oxOutput.size() == 1) {
                oxygenGeneratorRating = oxOutput.get(0);
                break;
            }
            int zeroes = 0;
            int ones = 0;
            for (String log : oxOutput) {
                if (log.charAt(bitPosition) == '0') {
                    zeroes += 1;
                } else {
                    ones += 1;
                }
            }
            int num;
            if (zeroes > ones) {
                num = 0;
            } else {
                num = 1;
            }
            oxOutput = trimFailingNumbers(oxOutput, bitPosition, num);
        }

        for (int bitPosition = 0; bitPosition < coOutput.get(0).length(); bitPosition++) {
            if (coOutput.size() == 1) {
                co2ScrubberRating = coOutput.get(0);
                break;
            }
            int zeroes = 0;
            int ones = 0;
            for (String log : coOutput) {
                if (log.charAt(bitPosition) == '0') {
                    ones += 1;
                } else {
                    zeroes += 1;
                }
            }
            int num;
            if (zeroes >= ones) {
                num = 0;
            } else {
                num = 1;
            }
            coOutput = trimFailingNumbers(coOutput, bitPosition, num);
        }

        // System.out.println("Ox:  " + oxygenGeneratorRating);
        // System.out.println("CO2: " + co2ScrubberRating);
        Long oxInDecimal = Long.parseLong(oxygenGeneratorRating, 2);
        Long coInDecimal = Long.parseLong(co2ScrubberRating, 2);
        Long answer = oxInDecimal * coInDecimal;
        return Long.toString(answer);
    }

    public List<String> trimFailingNumbers(List<String> list, int position, int number) {
        List<String> newList = new ArrayList<>();
        for (String item : list) {
            if (Character.getNumericValue(item.charAt(position)) == number) {
                newList.add(item);
            }
        }
        return newList;
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
