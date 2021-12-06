package main.java.solutions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day05 implements Day {

    @Override
    public String part1(List<String> input) {
        // map of coordinates to count
        Map<CoordinatePair, Long> overlapsPerCoordinate = new HashMap<>();

        // iterate, skip any where ! x=x or y=y
        for (String line : input) {
            String[] strings = line.split(" -> ");
            LineSegment segment = new LineSegment(strings[0], strings[1]);
            if (isHorizontalOrVertical(segment)) {
                overlapsPerCoordinate = traceLineSegment(segment, overlapsPerCoordinate); // walk the line to add it to the map
            }
        }

        // then go through the map and count how many values are > 1
        long coordsWithOverlaps = overlapsPerCoordinate.values()
                .stream()
                .filter((value) -> value > 1)
                .count();
        return String.valueOf(coordsWithOverlaps);
    }

    public boolean isHorizontalOrVertical(LineSegment segment) {
        return segment.startCoord.getX() == segment.endCoord.getX() || segment.startCoord.getY() == segment.endCoord.getY();
    }

    // TODO figure out why this isn't working
    public Map<CoordinatePair, Long> traceLineSegment(LineSegment segment, Map<CoordinatePair, Long> map) {
        for (int i = segment.startCoord.getX(); i <= segment.endCoord.getX(); i++) {
            for (int j = segment.startCoord.getY(); j <= segment.endCoord.getY(); j++) {
                CoordinatePair currentCoords = new CoordinatePair(i, j);
                if (map.containsKey(currentCoords)) {
                    Long count = map.get(currentCoords);
                    System.out.println("Current number of overlaps: " + count);
                    map.put(currentCoords, count+1);
                } else {
                    System.out.println("Adding coordinates: " + i +", " + j);
                    map.put(currentCoords, 1L);
                }
            }
        }
        return map;
    }

    @Override
    public String part2(List<String> input) {
        return null;
    }

    public class CoordinatePair {
        int x;
        int y;

        public CoordinatePair(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

    public class LineSegment {
        CoordinatePair startCoord;
        CoordinatePair endCoord;

        public LineSegment(String start, String end) {
            int x1 = Integer.parseInt(start.substring(0, start.indexOf(',')));
            int y1 = Integer.parseInt(start.substring(start.indexOf(',') + 1));
            int x2 = Integer.parseInt(end.substring(0, end.indexOf(',')));
            int y2 = Integer.parseInt(end.substring(end.indexOf(',') + 1));

            if (x1 < x2) {
                startCoord = new CoordinatePair(x1, y1);
                endCoord = new CoordinatePair(x2, y2);
            }
            else {
                startCoord = new CoordinatePair(x2, y2);
                endCoord = new CoordinatePair(x1, y1);
            }
        }

        public CoordinatePair getStartCoord() {
            return startCoord;
        }

        public CoordinatePair getEndCoord() {
            return endCoord;
        }
    }
}
