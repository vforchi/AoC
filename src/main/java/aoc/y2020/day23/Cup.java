package aoc.y2020.day23;

import lombok.Getter;

class Cup {
    @Getter
    public final Integer value;
    Cup previous;
    Cup next;

    public Cup(int value) {
        this.value = value;
    }
}