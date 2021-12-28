package me.vforchi.aoc.y2021.day18;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import org.apache.commons.collections4.ListUtils;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public class SnailfishNumber {

    private SnailfishNumber parent;

    private Integer value = -1;
    private SnailfishNumber left;
    private SnailfishNumber right;

    public SnailfishNumber(Integer value) {
        this.value = value;
    }

    public SnailfishNumber(SnailfishNumber left, SnailfishNumber right) {
        this.left = left;
        this.left.parent = this;
        this.right = right;
        this.right.parent = this;
    }

    public int magnitude() {
        if (isRegularNumber()) {
            return value;
        } else {
            return 3 * left.magnitude() + 2 * right.magnitude();
        }
    }

    public SnailfishNumber add(SnailfishNumber number) {
        return new SnailfishNumber(this, number).reduce();
    }

    private SnailfishNumber reduce() {
        boolean toReduce;
        do {
            toReduce = explode() || split();
        } while (toReduce);
        return this;
    }

    private boolean explode() {
        var pairToExplode = getPairsWithDepth(0)
                .filter(t -> t._2 == 4)
                .map(Tuple2::_1)
                .findFirst();
        pairToExplode.ifPresent(p -> {
            var regulars = getRegularNumbers();
            Optional.of(regulars.indexOf(p.left))
                    .filter(i -> i > 0)
                    .map(i -> regulars.get(i - 1))
                    .ifPresent(s -> s.increase(p.left.value));
            Optional.of(regulars.indexOf(p.right))
                    .filter(i -> i != -1)
                    .filter(i -> i < regulars.size() - 1)
                    .map(i -> regulars.get(i + 1))
                    .ifPresent(s -> s.increase(p.right.value));
            p.postExplosion();
        });
        return pairToExplode.isPresent();
    }

    private Stream<Tuple2<SnailfishNumber, Integer>> getPairsWithDepth(int depth) {
        if (isRegularNumber()) {
            return Stream.of(Tuple.of(this, depth));
        } else {
            return Stream.of(
                            Optional.ofNullable(left)
                                    .map(s -> s.getPairsWithDepth(depth + 1))
                                    .orElse(Stream.empty()),
                            Stream.of(Tuple.of(this, depth)),
                            Optional.ofNullable(right)
                                    .map(s -> s.getPairsWithDepth(depth + 1))
                                    .orElse(Stream.empty()))
                    .flatMap(Function.identity())
                    .filter(s -> !s._1.isRegularNumber());
        }
    }

    private List<SnailfishNumber> getRegularNumbers() {
        if (isRegularNumber()) {
            return List.of(this);
        } else {
            return ListUtils.sum(left.getRegularNumbers(), right.getRegularNumbers());
        }
    }

    private void increase(Integer value) {
        this.value += value;
    }

    private void postExplosion() {
        if (parent.left == this) {
            parent.left = new SnailfishNumber(0);
        } else if (parent.right == this) {
            parent.right = new SnailfishNumber(0);
        }
    }

    private boolean split() {
        var split = tryLeftSplit();
        if (!split) {
            split = tryRightSplit();
        }
        return split;
    }

    private boolean tryLeftSplit() {
        if (left == null) {
            return false;
        } else {
            if (left.value > 9) {
                left = left.convertToPair(this);
                return true;
            } else {
                return left.split();
            }
        }
    }

    private boolean tryRightSplit() {
        if (right == null) {
            return false;
        } else {
            if (right.value > 9) {
                right = right.convertToPair(this);
                return true;
            } else {
                return right.split();
            }
        }
    }

    private SnailfishNumber convertToPair(SnailfishNumber parent) {
        var leftValue = value / 2;
        var rightValue = value - leftValue;
        var pair = new SnailfishNumber(new SnailfishNumber(leftValue), new SnailfishNumber(rightValue));
        pair.parent = parent;
        return pair;
    }

    private boolean isRegularNumber() {
        return value > -1;
    }

    public static SnailfishNumber fromText(String text) {
        if (text.length() == 1) {
            return new SnailfishNumber(Integer.parseInt(text));
        } else {
            int i = 0, level = 0;
            while (true) {
                switch (text.charAt(i)) {
                    case '[' -> level += 1;
                    case ']' -> level -= 1;
                    case ',' -> {
                        if (level == 1) {
                            return new SnailfishNumber(
                                    fromText(text.substring(1, i)),
                                    fromText(text.substring(i + 1, text.length() - 1))
                            );
                        }
                    }
                }
                i += 1;
            }
        }
    }

    @Override
    public String toString() {
        if (isRegularNumber()) {
            return value.toString();
        } else {
            return String.format("[%s,%s]", left.toString(), right.toString());
        }
    }
}
