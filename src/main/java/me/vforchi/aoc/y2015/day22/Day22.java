package me.vforchi.aoc.y2015.day22;

import me.vforchi.aoc.Day;

public class Day22 extends Day {

    Battle battle = new Battle(50, 500, 51, 9);

    @Override
    public Object partOne() {
        return playAllBattles(battle, false, Integer.MAX_VALUE);
    }

    @Override
    public Object partTwo() {
        return playAllBattles(battle, true, Integer.MAX_VALUE);
    }

    private int playAllBattles(Battle battle, boolean hard, int minimumMana) {
        for (var spell: Spell.values()) {
            var nextBattle = battle.duplicate();
            if (hard) {
                nextBattle.setHp(nextBattle.getHp() - 1);
                if (nextBattle.winner() == Battle.Status.PLAYER_LOST) {
                    continue;
                }
            }
            nextBattle.applyEffects();
            nextBattle.cast(spell);

            if (nextBattle.winner() == Battle.Status.PLAYER_WON) {
                minimumMana = Math.min(minimumMana, nextBattle.getManaSpent());
                continue;
            }

            nextBattle.applyEffects();
            if (nextBattle.winner() == Battle.Status.PLAYER_WON) {
                minimumMana = Math.min(minimumMana, nextBattle.getManaSpent());
                continue;
            }

            nextBattle.bossAttacks();
            if (nextBattle.getManaSpent() < minimumMana && nextBattle.winner() != Battle.Status.PLAYER_LOST) {
                minimumMana = Math.min(minimumMana, playAllBattles(nextBattle, hard, minimumMana));
            }
        }
        return minimumMana;
    }
}
