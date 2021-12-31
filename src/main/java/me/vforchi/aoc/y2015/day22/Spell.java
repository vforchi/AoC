package me.vforchi.aoc.y2015.day22;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Spell {
    MAGIC_MISSILE(53),
    DRAIN(73),
    SHIELD(113),
    POISON(173),
    RECHARGE(229);

    @Getter
    private int cost;
}
