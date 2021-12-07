package me.vforchi.aoc.y2015.day04;

import me.vforchi.aoc.Day;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.stream.IntStream;

public class Day04 extends Day {

    private String seed;

    @Override
    public void setup(String path) {
        super.setup(path);
        seed = input.get(0);
    }

    private int findHash(String prefix) {
        return IntStream.iterate(0, i -> i + 1)
                .filter(i -> DigestUtils.md5Hex((seed + i).getBytes()).startsWith(prefix))
                .findFirst()
                .orElseThrow();
    }

    @Override
    public Object partOne() {
        return findHash("00000");
    }

    @Override
    public Object partTwo() {
        return findHash("000000");
    }

}
