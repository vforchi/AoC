package aoc.y2020.day25;

import aoc.Day;
import io.vavr.Tuple2;
import io.vavr.collection.Stream;

import java.util.function.Function;

public class Day25 extends Day {

    private final Long cardPublicKey = 2069194L;
    private final Long doorPublicKey = 16426071L;

    @Override
    public Object partOne() {
        var cardLoopSize = findLoopSize(cardPublicKey);
        var doorLoopSize = findLoopSize(doorPublicKey);
        var cardEncryptionKey = transform(cardPublicKey, doorLoopSize);
        var doorEncryptionKey = transform(doorPublicKey, cardLoopSize);
        if (!cardEncryptionKey.equals(doorEncryptionKey)) {
            throw new RuntimeException("Must be the same");
        }
        return doorEncryptionKey;
    }

    private Integer findLoopSize(Long publicKey) {
        return Stream.iterate(1L, transformer(7L))
                .zipWithIndex()
                .find(t -> t._1.equals(publicKey))
                .map(Tuple2::_2)
                .getOrElseThrow(() -> new RuntimeException("Not found"));
    }

    private Long transform(Long subject, Integer doorLoopSize) {
        return Stream.iterate(1L, transformer(subject))
                .toJavaStream()
                .skip(doorLoopSize)
                .findFirst()
                .orElseThrow();
    }

    private Function<Long, Long> transformer(Long subject) {
        return l -> (l * subject) % 20201227;
    }

    @Override
    public Object partTwo() {
        return "Nothing do do";
    }

}