package aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public abstract class Day {

    protected List<String> input = new ArrayList<>();

    abstract public Object partOne();
    abstract public Object partTwo();

    public void setup(String path) {
        try {
            input = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
