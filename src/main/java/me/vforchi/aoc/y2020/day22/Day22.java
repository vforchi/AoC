package me.vforchi.aoc.y2020.day22;

import me.vforchi.aoc.Day;
import me.vforchi.aoc.y2020.Utils;
import io.vavr.collection.Stream;

import java.util.*;
import java.util.stream.Collectors;

public class Day22 extends Day {

    @Override
    public Object partOne() {
        var decks = getDecks(input);
        var winnerDeck = playRegularCombat(decks.get(0), decks.get(1));
        return score(winnerDeck);
    }

    private List<Integer> playRegularCombat(List<Integer> deck1, List<Integer> deck2) {
        while (!finished(deck1, deck2)) {
            nextRound(deck1, deck2);
        }
        return deck1.isEmpty() ? deck2 : deck1;
    }

    private void nextRound(List<Integer> deck1, List<Integer> deck2) {
        var card1 = deck1.remove(0);
        var card2 = deck2.remove(0);
        if (card1 > card2) {
            deck1.addAll(List.of(card1, card2));
        } else {
            deck2.addAll(List.of(card2, card1));
        }
    }

    @Override
    public Object partTwo() {
        var decks = getDecks(input);
        var winnerDeck = playRecursiveCombat(decks.get(0), decks.get(1));
        return score(winnerDeck);
    }

    private List<Integer> playRecursiveCombat(List<Integer> deck1, List<Integer> deck2) {
        var deckHistory = new HashSet<>();
        while (!finished(deck1, deck2)) {
            if (deckHistory.add(List.of(deck1, deck2))) {
                nextRoundRecursive(deck1, deck2);
            } else {
                deck2.clear();
            }
        }
        return deck1.isEmpty() ? deck2 : deck1;
    }

    private void nextRoundRecursive(List<Integer> deck1, List<Integer> deck2) {
        var p1Wins = true;
        var card1 = deck1.remove(0);
        var card2 = deck2.remove(0);
        if (deck1.size() >= card1 && deck2.size() >= card2) {
            var subDeck1 = new LinkedList<>(deck1.subList(0, card1));
            var subDeck2 = new LinkedList<>(deck2.subList(0, card2));
            playRecursiveCombat(subDeck1, subDeck2);
            p1Wins = subDeck2.isEmpty();
        } else {
            p1Wins = (card1 > card2);
        }

        if (p1Wins) {
            deck1.addAll(List.of(card1, card2));
        } else {
            deck2.addAll(List.of(card2, card1));
        }
    }

    private boolean finished(List<Integer> deck1, List<Integer> deck2) {
        return deck1.isEmpty() || deck2.isEmpty();
    }

    private Integer score(Collection<Integer> deck) {
        return Stream.ofAll(deck).reverse()
                .zipWithIndex()
                .map(t -> t._1*(t._2+1))
                .sum().intValue();
    }

    private List<List<Integer>> getDecks(List<String> input) {
        return Utils.partitionByEmptyLine(input)
                .map(Day22::toIntList)
                .collect(Collectors.toList());
    }

    private static List<Integer> toIntList(List<String> lines) {
        lines.remove(0);
        return lines.stream()
                .map(Integer::parseInt)
                .collect(Collectors.toCollection(LinkedList::new));
    }

}