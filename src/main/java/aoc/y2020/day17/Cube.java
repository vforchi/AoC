package aoc.y2020.day17;

import java.util.List;
import java.util.Objects;

public class Cube {
    public int x;
    public int y;
    public int z;
    public Integer w;

    public Cube(int x, int y, int z, Integer w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Cube() {

    }

    public Cube offset(List<Integer> coords) {
        var cube = new Cube();
        cube.x = this.x + coords.get(0);
        cube.y = this.y + coords.get(1);
        cube.z = this.z + coords.get(2);
        if (this.w != null)
            cube.w = this.w + coords.get(3);
        return cube;
    }

    @Override
    public boolean equals(Object o) {
        Cube c = (Cube) o;
        return x == c.x && y == c.y && z == c.z && w == c.w;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z, w);
    }

    public boolean isAdjacent(Cube cube) {
        return Math.abs(this.x - cube.x) <= 1 &&
                Math.abs(this.y - cube.y) <= 1 &&
                Math.abs(this.z - cube.z) <= 1 &&
                (Objects.isNull(this.w) || Math.abs(this.w - cube.w) <= 1) &&
                !this.equals(cube);
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s", this.x, this.y, this.z, this.w);
    }

}
