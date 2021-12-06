package main.java.solutions;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day04 implements Day {

    // Part 1 solution from Su <3
    @Override
    public String part1(List<String> input) {
        List<Integer> drawnNumbers = Arrays.stream(input.get(0).split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        List<Board> boards = process(input.subList(2, input.size()));

        for (Integer currentNum : drawnNumbers) {
            Optional<Board> winningBoard = boards.stream()
                    .filter((board) -> board.mark(currentNum))
                    .findFirst();
            if (winningBoard.isPresent()) {
                return String.valueOf(winningBoard.get().sumOfUnmarked() * currentNum);
            }
        }
        return "No result";
    }

    private List<Board> process(List<String> input) {
        List<Board> boards = new ArrayList<>();
        for (int i = 0; i < input.size()/6 + 1; i++) {
            Board board = new Board(input.subList(i*6, i*6+5));
            boards.add(board);
        }

        return boards;
    }

    // Keep a list of the boards
    // Store the most recent winner
    @Override
    public String part2(List<String> input) {
        List<Integer> drawnNumbers = Arrays.stream(input.get(0).split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        List<Board> boards = process(input.subList(2, input.size()));

        Board mostRecentWinner = null;
        Integer winningNumber = 0;
        for (Integer currentNum : drawnNumbers) {
            System.out.println("");
            System.out.println("Checking number " + currentNum);

            List<Board> winningBoards = boards.stream()
                    .filter((board) -> board.mark(currentNum))
                    .collect(Collectors.toList());
            if (!winningBoards.isEmpty()) {
                mostRecentWinner = winningBoards.get(0);
                boards.removeAll(winningBoards);
                winningNumber = currentNum;
            }
        }

        if (mostRecentWinner != null) {
            return String.valueOf(mostRecentWinner.sumOfUnmarked() * winningNumber);
        }
        return "No result";
    }

    public class Board {

        int[][] board = new int[5][5];
        Map<Integer, CoordinatePair> locationMap = new HashMap<>();

        public Board(List<String> input) {
            for (int i = 0; i < 5; i++) {
                String[] row = input.get(i).trim().split("\\s+");
                for (int j = 0; j < 5; j++) {
                    int value = Integer.parseInt(row[j]);
                    board[i][j] = value;
                    locationMap.put(value, new CoordinatePair(i, j));
                }
            }
        }

        /* Returns whether a board has won bingo after marking the current number. */
        public boolean mark(int calledNumber) {
            if (!locationMap.containsKey(calledNumber)) {
                return false;
            }
            CoordinatePair location = locationMap.remove(calledNumber);
            board[location.getRow()][location.getColumn()] = -1; // set to -1 to indicate that it's been called; we sum to -5 for a win

            if (win(location)) {
                return true;
            }
            return false;
        }

        public boolean win(CoordinatePair location) {
            int sumOfRow = IntStream.of(board[location.getRow()]).sum();
            int sumOfColumn = IntStream.range(0, 5)
                    .map((i) -> board[i][location.getColumn()]).sum();
            return sumOfColumn == -5 || sumOfRow == -5;
        }

        public int sumOfUnmarked() {
            Integer sum = locationMap.keySet().stream().reduce(0, Integer::sum);
            return sum;
        }
    }

    public class CoordinatePair {
        private Integer row;
        private Integer column;

        public CoordinatePair(Integer row, Integer column) {
            this.row = row;
            this.column = column;
        }

        public Integer getRow() {
            return row;
        }

        public Integer getColumn() {
            return column;
        }
    }
}
