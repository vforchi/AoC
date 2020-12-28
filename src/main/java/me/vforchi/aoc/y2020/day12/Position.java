package me.vforchi.aoc.y2020.day12;

public class Position {

    int posEW;
    int posNS;

    public Position(int posEW, int posNS) {
        this.posEW = posEW;
        this.posNS = posNS;
    }

    public void move(String direction, int value) {
        switch (direction) {
            case "N" -> this.posNS += value;
            case "S" -> this.posNS -= value;
            case "E" -> this.posEW += value;
            case "W" -> this.posEW -= value;
        }
    }

    public String toString() {
        return String.valueOf(Math.abs(posEW) + Math.abs(posNS));
    }

}