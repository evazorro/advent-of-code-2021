package main.java.solutions;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day06 implements Day {
    int DAYS_TO_NEW_FISH = 6;
    int ADDITIONAL_DAYS_AFTER_SPAWN = 2;

    // the slow version!!
    @Override
    public String part1(List<String> input) {
        List<Lanternfish> allLanternfish = Arrays.stream(input.get(0).split(","))
                .map(Integer::parseInt)
                .map(Lanternfish::new)
                .collect(Collectors.toList());

        int maxDays = 80;
        for (int i = 0; i < maxDays; i++) {
            List<Lanternfish> newFish = new ArrayList<>();
            for (Lanternfish fish : allLanternfish) {
                // if the timer is at 0 already, create a new lanternfish to add to the end of the list, and update THIS fish to 6 (separate list, add at end of day)
                int currentTimer = fish.getInternalTimer();
                if (currentTimer == 0) {
                    fish.setInternalTimer(DAYS_TO_NEW_FISH);
                    newFish.add(new Lanternfish(DAYS_TO_NEW_FISH + ADDITIONAL_DAYS_AFTER_SPAWN));
                } else {
                    // if timer is > 0, decrement
                    fish.setInternalTimer(currentTimer - 1);
                }
            }
            // combine the lists
            List<Lanternfish> updatedListOfFish = Stream.concat(allLanternfish.stream(), newFish.stream())
                    .collect(Collectors.toList());
            allLanternfish = updatedListOfFish;
        }

        // return the size of the list
        return String.valueOf(allLanternfish.size());
    }

    @Override
    public String part2(List<String> input) {
        // maybe I can group all fish with a certain number of days?
        Map<Integer, Long> numFishByTimer = Arrays.stream(input.get(0).split(","))
                .map(Integer::parseInt)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        int maxDays = 256;
        for (int i = 0; i < maxDays; i++) {
            Map<Integer, Long> newMap = new HashMap<>();

            // !! have to account for some fishes ending up on the same timer -- sixes are tricky
            long numSixes = 0L;
            Iterator<Map.Entry<Integer, Long>> iterator = numFishByTimer.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Integer, Long> entry = iterator.next();
                if (entry.getKey() == 0) {
                     // spawn new fish for all fishes currently at 0
                     newMap.put((DAYS_TO_NEW_FISH + ADDITIONAL_DAYS_AFTER_SPAWN), numFishByTimer.get(0));
                     // then update the 0-day fishes back to 6 (to be added to the map later)
                     numSixes += numFishByTimer.get(0);
                } else {
                    newMap.put((entry.getKey()-1), entry.getValue());
                }
            }
            // combine the new sixes with any that have ticked down to six
            if (newMap.containsKey(DAYS_TO_NEW_FISH)) {
                numSixes += newMap.get(DAYS_TO_NEW_FISH);
            }
            // then add six to the map
            newMap.put(DAYS_TO_NEW_FISH, numSixes);
            numFishByTimer = newMap;
        }

        // add all values in the map
        Long sumFish = numFishByTimer.values().stream().mapToLong(Long::longValue).sum();
        return String.valueOf(sumFish);
    }

    public static class Lanternfish {
        int internalTimer;

        public Lanternfish(int days) {
            this.internalTimer = days;
        }

        public int getInternalTimer() {
            return internalTimer;
        }

        public void setInternalTimer(int internalTimer) {
            this.internalTimer = internalTimer;
        }
    }
}
