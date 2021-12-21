package me.vforchi.aoc.y2015.day22;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;
import static me.vforchi.aoc.y2015.day22.Spell.*;

@Data
@Builder(setterPrefix = "with")
@AllArgsConstructor
public class Battle {
    enum Status { PLAYER_WON, PLAYER_LOST, RUNNING }

    private int hp;
    private int armor;
    private int mana;
    private int manaSpent;

    public int bossHp;
    public int bossDamage;

    private Map<Spell, Integer> effects;
    private List<Spell> history;

    public Battle(int playerHp, int playerMana, int bossHp, int bossDamage) {
        this(playerHp, 0, playerMana, 0, bossHp, bossDamage, new HashMap<>(), new ArrayList<>());
    }

    public void cast(Spell spell) {
        mana -= spell.getCost();
        manaSpent += spell.getCost();
        switch (spell) {
            case MAGIC_MISSILE -> bossHp -= 4;
            case DRAIN -> { bossHp -= 2; hp += 2; }
            case POISON -> effects.put(POISON, 6);
            case SHIELD -> { armor += 7; effects.put(SHIELD, 6); }
            case RECHARGE -> effects.put(RECHARGE, 5);
        }
        history.add(spell);
    }

    public void applyEffects() {
        for (Spell s: effects.keySet()) {
            switch (s) {
                case POISON -> bossHp -= 3;
                case SHIELD -> armor = armor - (effects.get(s) == 1 ? 7 : 0);
                case RECHARGE -> mana += 101;
                default -> {}
            }
        }
        effects = effects.entrySet().stream()
                .map(Tuple::fromEntry)
                .map(e -> e.map2(v -> v - 1))
                .filter(e -> e._2 > 0)
                .collect(toMap(Tuple2::_1, Tuple2::_2));
    }

    public void bossAttacks() {
        hp -= Math.max(0, bossDamage - armor);
    }

    public Battle duplicate() {
        return new Battle(hp, armor, mana, manaSpent, bossHp, bossDamage, new HashMap<>(effects), new ArrayList<>(history));
    }

    public Status winner() {
        if (bossHp <= 0 && hp > 0 && mana > 0) {
            return Status.PLAYER_WON;
        } else if (hp <= 0 || mana <= 0) {
            return Status.PLAYER_LOST;
        } else {
            return Status.RUNNING;
        }
    }
}