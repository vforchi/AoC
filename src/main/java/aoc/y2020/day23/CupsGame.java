package aoc.y2020.day23;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CupsGame {

    private List<Cup> allCups;
    private Cup currentCup;

    public CupsGame(List<Integer> firstCups, int size) {
        allCups = IntStream.range(0, size).boxed()
                .map(Cup::new)
                .collect(Collectors.toList());

        Cup current = null;
        for (var val: firstCups) {
            current = addCup(current, val - 1);
        }
        for (int i = firstCups.size(); i < size; i++) {
            current = addCup(current, i);
        }

        currentCup = allCups.get(firstCups.get(0) - 1);
    }

    public Cup addCup(Cup position, int value) {
        Cup cup = allCups.get(value);

        if (position == null) {
            cup.next = cup;
            cup.previous = cup;
        } else {
            position.next.previous = cup;
            cup.next = position.next;

            position.next = cup;
            cup.previous = position;
        }

        return cup;
    }

    public Cup getCup(int searchValue) {
        return allCups.get(searchValue - 1);
    }

    public void nextRound() {
        var toMove = getCupsToMove(currentCup);

        var destCup = calcDestination(currentCup.value, toMove);
        var destCupNext = destCup.next;

        var firstCupToMove = toMove.getFirst();
        var lastCupToMove = toMove.getLast();

        currentCup.next = lastCupToMove.next;
        lastCupToMove.next.previous = currentCup;

        destCup.next = firstCupToMove;
        firstCupToMove.previous = destCup;

        destCupNext.previous = lastCupToMove;
        lastCupToMove.next = destCupNext;

        currentCup = currentCup.next;
    }

    private LinkedList<Cup> getCupsToMove(Cup start) {
        LinkedList<Cup> cups = new LinkedList<>();
        for (int i = 0; i < 3; i++) {
            cups.add(start.next);
            start = start.next;
        }
        return cups;
    }

    private Cup calcDestination(Integer currentValue, List<Cup> cupsToMove) {
        var avoid = cupsToMove.stream().map(Cup::getValue).collect(Collectors.toList());
        do {
            if (--currentValue < 0) {
                currentValue = allCups.size() - 1;
            }
        } while (avoid.contains(currentValue));
        return allCups.get(currentValue);
    }

    @Override
    public String toString() {
        var buf = new StringBuilder();

        var current = allCups.get(0).next;
        do {
            buf.append(current.value + 1);
            current = current.next;
        } while (current != allCups.get(0));

        return buf.toString();
    }

}

