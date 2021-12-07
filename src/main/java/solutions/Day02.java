package solutions;

import java.util.ArrayList;
import java.util.List;

public class Day02 implements Day {

    @Override
    public String part1(List<String> input) {

        Integer horizPos = 0;
        Integer depth = 0;

        for (String line : input) {
            // System.out.println(line);
            Instruction instruction = new Instruction(
                    line.substring(0, line.length()-2),
                    Integer.parseInt(line.substring(line.length()-1)));
            //System.out.println("Going " + instruction.getValue() + " in direction " + instruction.getAction());

            switch (instruction.getAction()) {
                case "down" :
                    depth += instruction.getValue();
                    // System.out.println("Depth is now " + depth);
                    break;
                case "up" :
                    depth -= instruction.getValue();
                    // System.out.println("Depth is now " + depth);
                    break;
                case "forward" :
                    horizPos += instruction.getValue();
                    // System.out.println("Horizontal position is now " + horizPos);
                    break;
            }
        }
        Integer answer = horizPos * depth;
        return answer.toString();
    }

    @Override
    public String part2(List<String> input) {

        Integer horizPos = 0;
        Integer depth = 0;
        Integer aim = 0;

        for (String line : input) {
            Instruction instruction = new Instruction(
                    line.substring(0, line.length() - 2),
                    Integer.parseInt(line.substring(line.length() - 1)));

            switch (instruction.getAction()) {
                case "down":
                    aim += instruction.getValue();
                    // System.out.println("Depth is now " + depth + " and aim is " + aim);
                    break;
                case "up":
                    aim -= instruction.getValue();
                    // System.out.println("Depth is now " + depth + " and aim is " + aim);
                    break;
                case "forward":
                    horizPos += instruction.getValue();
                    depth += (aim * instruction.getValue());
                    // System.out.println("Horizontal position is now " + horizPos + " and aim is " + aim  + " . Depth is " + depth);
                    break;
            }
        }
        Integer answer = horizPos * depth;
        return answer.toString();
    }

    private static class Instruction {
        String action;
        int value;

        public Instruction(String action, int value) {
            this.action = action.trim();
            this.value = value;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }
}
