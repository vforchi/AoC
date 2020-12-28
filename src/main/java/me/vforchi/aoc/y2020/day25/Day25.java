package me.vforchi.aoc.y2020.day25;

import me.vforchi.aoc.Day;

public class Day25 extends Day {

    private final int divisor = 20201227;

    private final int cardPublicKey = 2069194;
    private final int doorPublicKey = 16426071;

    @Override
    public Object partOne() {
        var cardLoopSize = findLoopSize(cardPublicKey);
        return transform(doorPublicKey, cardLoopSize);
    }

    private int findLoopSize(int publicKey) {
        int value = 1;
        int loopSize = 0;
        while (value != publicKey) {
            value = (value * 7) % divisor;
            loopSize++;
        }
        return loopSize;
    }

    private long transform(int subject, int doorLoopSize) {
        long value = 1L;
        for (int i = 0; i < doorLoopSize; i++) {
            value = (value * subject) % divisor;
        }
        return value;
    }

    @Override
    public Object partTwo() {
        return "Nothing do do";
    }

}