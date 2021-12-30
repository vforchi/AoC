package me.vforchi.aoc.y2015.day25;

import me.vforchi.aoc.Day;

public class Day25 extends Day {

    //                  | 1   2   3   4   5   6   ... 3019
    //               ---+---+---+---+---+---+---+ ...+----+
    //               1 |  1   3   6  10  15  21
    //               2 |  2   5   9  14  20
    //               3 |  4   8  13  19  26
    //               4 |  7  12  18  25  33
    //               5 | 11  17
    //               6 | 16 ((6 * 5) / 2 + 1)
    //             ... | ...
    //               n | ((n * (n - 1)) / 2 + 1)
    //             ... | ...
    //            3010 | ...                          n1 + 3019 - 1
    //             ... | ...
    //             ... | ...
    // 3010 + 3019 - 1 | n1

    @Override
    public Object partOne() {
        int row = 3010;
        int col = 3019;
        int count = (row + col - 1) * (row + col - 2) / 2 + col;

        var code = 20151125L;
        int c = 1;
        while (c++ != count) {
            code = (code * 252533L) % 33554393L;
        }
        return code;
    }

    @Override
    public Object partTwo() {
        return "Nothing to do";
    }

}
