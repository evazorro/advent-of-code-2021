package solutions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Day05 implements Day {

    @Override
    public String part1(List<String> input) {
        // map of coordinates to count
        Map<CoordinatePair, Long> overlapsPerCoordinate = new HashMap<>();

        // iterate, skip any where ! x=x or y=y


        List<LineSegment> lineSegments = input.stream()
                .map(this::toLineSegment)
                .filter(LineSegment::isHorizontalOrVertical)
                .collect(Collectors.toList());

        for (LineSegment lineSegment : lineSegments) {
            System.out.println(lineSegment.getStartCoord().printCoordinates() + " -> " + lineSegment.getEndCoord().printCoordinates());
            overlapsPerCoordinate = traceLineSegment(lineSegment, overlapsPerCoordinate); // walk the line to add it to the map

        }

        // then go through the map and count how many values are > 1
        long coordsWithOverlaps = overlapsPerCoordinate.values()
                .stream()
                .filter((value) -> value > 1)
                .count();
        return String.valueOf(coordsWithOverlaps);
    }

    private LineSegment toLineSegment(String s) {
        String[] strings = s.split(" -> ");
        return new LineSegment(strings[0], strings[1]);
    }

    // map of coordinate pairs
    // for each pair, if it exists in the map, update the count
    // otherwise, add it to the map

    // TODO figure out why this isn't working
    public Map<CoordinatePair, Long> traceLineSegment(LineSegment segment, Map<CoordinatePair, Long> map) {
        System.out.println("Current map size: " + map.size());

        // vertical
        if (segment.getStartCoord().getX() == segment.getEndCoord().getX()) {
            int i = segment.getStartCoord().getX(); // eww but whatever
            for (int j = segment.getStartCoord().getY(); j <= segment.getEndCoord().getY(); j++) {
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
        } else {
            // horizontal
            int j = segment.getStartCoord().getY();
            for (int i = segment.getStartCoord().getX(); i <= segment.getEndCoord().getX(); i++) {
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

        public String printCoordinates() {
            StringBuilder sb = new StringBuilder();
            String s = sb.append(x)
                    .append(",")
                    .append(y)
                    .toString();
            return s;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CoordinatePair that = (CoordinatePair) o;
            return x == that.x && y == that.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
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

            // sort the coordinates so the lines are all going in the right direction
            // (this almost killed me but I did it)
            startCoord = ((x1 - x2) + (y1 - y2) < 0) ? new CoordinatePair(x1, y1) : new CoordinatePair(x2, y2);
            endCoord = ((x1 - x2) + (y1 - y2) < 0) ? new CoordinatePair(x2, y2) : new CoordinatePair(x1, y1);
        }

        public CoordinatePair getStartCoord() {
            return startCoord;
        }

        public CoordinatePair getEndCoord() {
            return endCoord;
        }

        public boolean isHorizontalOrVertical() {
            return startCoord.getX() == endCoord.getX() || startCoord.getY() == endCoord.getY();
        }
    }
}
