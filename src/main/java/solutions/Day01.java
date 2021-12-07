package solutions;

import java.util.List;
import java.util.stream.Collectors;

public class Day01 implements Day {

    @Override
    public String part1(List<String> input) {
        Integer increases = 0;
        List<Integer> depthMeasurements = getInputAsIntegers(input);
        int previous = depthMeasurements.get(0);
        for (int measurement : depthMeasurements) {
            if (measurement > previous) {
                increases++;
            }
            previous = measurement;
        }
        return increases.toString();
    }

    @Override
    public String part2(List<String> input) {
        Integer increases = 0;
        List<Integer> depthMeasurements = getInputAsIntegers(input);
        int previousWindow = depthMeasurements.get(0) + depthMeasurements.get(1) + depthMeasurements.get(2);
        for (int i = 0; i < depthMeasurements.size()-2; i++) { // this is fine lol
            int newWindow = depthMeasurements.get(i) + depthMeasurements.get(i+1) + depthMeasurements.get(i+2);
            if (newWindow > previousWindow) {
                increases++;
            }
            previousWindow = newWindow;
        }
        return increases.toString();
    }

    private List<Integer> getInputAsIntegers(List<String> input) {
        return input.stream()
                .map(Integer::valueOf)
                .collect(Collectors.toList());
    }
}
