package me.vforchi.aoc.y2019;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntcodeTest {

    @ParameterizedTest
    @CsvSource(value = {
            "1,0,0,0,99:2,0,0,0,99",
            "2,3,0,3,99:2,3,0,6,99",
            "2,4,4,5,99,0:2,4,4,5,99,9801",
            "1,1,1,4,99,5,6,0,99:30,1,1,4,2,5,6,0,99",
            "1,9,10,3,2,3,11,0,99,30,40,50:3500,9,10,70,2,3,11,0,99,30,40,50"
    }, delimiter = ':')
    void day02(String program, String status) {
        var intcode = Intcode.fromText(program);
        intcode.run();
        assertEquals(status, intcode.getStatus());
    }

    @Test
    void indirectMode() {
        var intcode = Intcode.fromText("1002,4,3,4,33");
        intcode.run();
        assertEquals("1002,4,3,4,99", intcode.getStatus());
    }

    @Test
    void inputsAndOutputs() {
        var intcode = Intcode.fromText("3,0,4,0,99");
        intcode.run(123);
        assertEquals(123, intcode.getOutput(0));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "3,9,8,9,10,9,4,9,99,-1,8:8:1",
            "3,9,8,9,10,9,4,9,99,-1,8:5:0",
            "3,9,7,9,10,9,4,9,99,-1,8:5:1",
            "3,9,7,9,10,9,4,9,99,-1,8:12:0",
            "3,3,1108,-1,8,3,4,3,99:8:1",
            "3,3,1108,-1,8,3,4,3,99:88:0",
            "3,3,1107,-1,8,3,4,3,99:-4:1",
            "3,3,1107,-1,8,3,4,3,99:123:0",
            "3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9:0:0",
            "3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9:1:1",
            "3,3,1105,-1,9,1101,0,0,12,4,12,99,1:0:0",
            "3,3,1105,-1,9,1101,0,0,12,4,12,99,1:1:1",
            "3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99:-10:999",
            "3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99:8:1000",
            "3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99:1234:1001"
    }, delimiter = ':')
    void day5part2(String program, int input, int output) {
        var intcode = Intcode.fromText(program);
        intcode.run(input);
        assertEquals(output, intcode.getOutput(0));
    }

}
