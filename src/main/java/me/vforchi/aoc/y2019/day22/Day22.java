package me.vforchi.aoc.y2019.day22;

import com.google.common.collect.Lists;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Try;
import me.vforchi.aoc.Day;

import java.math.BigInteger;
import java.util.List;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;

public class Day22 extends Day {

    private static final String NEW_STACK = "deal into new";
    private static final String CUT = "cut";
    private static final String INCREMENT = "deal with increment";

    List<Tuple2<String, BigInteger>> techniques;

    @Override
    public void setup(String path) {
        super.setup(path);
        techniques = input.stream()
                .map(l -> Tuple.of(
                        l.substring(0, l.lastIndexOf(' ')),
                        Try.of(() -> new BigInteger(l.substring(l.lastIndexOf(' ') + 1))).getOrElse(ZERO)))
                .toList();
    }

    @Override
    public Long partOne() {
        long card = 2019;
        for (var technique: techniques) {
            card = shuffle(technique, card, 10007);
        }
        return card;
    }

    private long shuffle(Tuple2<String, BigInteger> technique, long card, long deckSize) {
        return switch (technique._1) {
            case NEW_STACK -> deckSize - 1 - card;
            case CUT -> (card + deckSize - technique._2.longValue()) % deckSize;
            case INCREMENT -> (card * technique._2.longValue()) % deckSize;
            default -> throw new RuntimeException();
        };
    }

    @Override
    public Object partTwo() {
        var position = BigInteger.valueOf(2020L);
        var deckSize = BigInteger.valueOf(119315717514047L);
        var cycles = BigInteger.valueOf(101741582076661L);

        // f(x) = a*x + b
        // F1 = f(2020) = a*2020 + b
        var F1 = inverseShuffle(position, deckSize);
        // F2 = f(f(2020)) = a*F1 + b
        var F2 = inverseShuffle(F1, deckSize);
        var a = (F1.subtract(F2)).multiply((position.subtract(F1)).modInverse(deckSize)).mod(deckSize);
        var b = (F1.subtract((a).multiply(position))).mod(deckSize);

        // f(f(...(f(x))) = a^n * x + b * (a^n - 1) / (a - 1)
        var pow = a.modPow(cycles, deckSize);
        var powMinusOne = pow.subtract(ONE);

        var part1 = pow.multiply(position);
        var part2 = powMinusOne.multiply(a.subtract(ONE).modInverse(deckSize)).multiply(b);

        return part1.add(part2).mod(deckSize);
    }

    private BigInteger inverseShuffle(BigInteger position, BigInteger deckSize) {
        var res = position;
        for (var technique: Lists.reverse(techniques)) {
            res = switch (technique._1) {
                case NEW_STACK -> deckSize.subtract(ONE).subtract(res);
                case CUT -> res.add(technique._2).add(deckSize).mod(deckSize);
                case INCREMENT -> technique._2.modInverse(deckSize).multiply(res).mod(deckSize);
                default -> throw new RuntimeException();
            };
        }
        return res;
    }

}
