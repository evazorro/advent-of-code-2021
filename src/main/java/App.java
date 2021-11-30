import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.util.Collections;
import java.util.List;

import solutions.Day;

/* Courtesy of @suhuitan <3 */
public class App {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        // instructions:
        //  1) add input file to the src/main/resources and save it as day{2 digit zero-padded day}-{part number}.txt
        //      example for day 1: day01.txt
        //  2) create a new Day implementation in /solutions with the class name Day{2 digit zero-padded day}.java
        //      example for day 1: Day01.java
        //  3) update the integer below to the correct day number
        //  4) implement and run

        // CHANGE ME EVERYDAY!
        int day = 1;

        // you don't really need to touch anything else after this
        Day solution = (Day) instantiate(day);

        System.out.println("Solution for part 1:");
        List<String> part1Input = loadInput(day);
        System.out.println(solution.part1(part1Input));

        System.out.println("\nSolution for part 2:");
        List<String> part2Input = loadInput(day);
        System.out.println(solution.part2(part2Input));
    }

    // Everything below is boilerplate

    private static Object instantiate(int day)
            throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        String fileName = String.format("solutions.Day%02d", day);
        Class<?> clazz = Class.forName(fileName);
        return clazz.newInstance();
    }

    public static List<String> loadInput(int day) {
        String fileName = String.format("day%02d.txt", day);

        InputStream systemResourceAsStream = ClassLoader.getSystemResourceAsStream(fileName);

        if (systemResourceAsStream == null) {
            System.out.println("Skipping... Can't find file " + fileName);
            return Collections.emptyList();
        }

        try (BufferedReader r = new BufferedReader(
                new InputStreamReader(systemResourceAsStream))) {
            return r.lines().collect(toList());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}