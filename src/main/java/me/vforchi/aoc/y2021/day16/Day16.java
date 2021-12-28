package me.vforchi.aoc.y2021.day16;

import me.vforchi.aoc.Day;

public class Day16 extends Day {

    private PacketDecoder decoder;

    @Override
    public void setup(String path) {
        super.setup(path);
        decoder = PacketDecoder.fromText(input.get(0));
        decoder.parse();
    }

    @Override
    public Object partOne() {
        return decoder.getVersions();
    }

    @Override
    public Object partTwo() {
        return decoder.getResult();
    }

}
