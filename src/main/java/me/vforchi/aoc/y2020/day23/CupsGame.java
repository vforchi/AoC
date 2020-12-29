package me.vforchi.aoc.y2020.day23;

import java.util.LinkedList;
import java.util.List;

public class CupsGame {

    private Cup[] allCups;
    private Cup currentCup;

    public CupsGame(List<Integer> firstCups, int size) {
        allCups = new Cup[size];

        Cup current = null;
        for (var val: firstCups) {
            current = addCup(current, val - 1);
        }
        for (int i = firstCups.size(); i < size; i++) {
            current = addCup(current, i);
        }

        currentCup = allCups[firstCups.get(0) - 1];
    }

    public Cup addCup(Cup position, int value) {
        Cup cup = new Cup(value);
        allCups[value] = cup;

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
        return allCups[searchValue - 1];
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
        var value1 = cupsToMove.get(0).value;
        var value2 = cupsToMove.get(1).value;
        var value3 = cupsToMove.get(2).value;
        do {
            if (--currentValue < 0) {
                currentValue = allCups.length - 1;
            }
        } while (currentValue == value1 || currentValue == value2 || currentValue == value3);
        return allCups[currentValue];
    }

    @Override
    public String toString() {
        var buf = new StringBuilder();
        var current = allCups[0].next;
        do {
            buf.append(current.value + 1);
            current = current.next;
        } while (current != allCups[0]);
        return buf.toString();
    }

}

