package aoc.y2020.day08;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class ConsoleStatus {
    private int pointer;
    private long accumulator;

    public void clear() {
        this.pointer = 0;
        this.accumulator = 0;
    }
}
