package me.vforchi.aoc.y2020.day17;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Cube {
    public int x;
    public int y;
    public int z;
    public Integer w;

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

    public boolean checkCubeNewStatus(Collection<Cube> activeCubes) {
        var isActive = activeCubes.contains(this);
        var activeNeighbours = getActiveAdjacent(activeCubes);
        if (isActive && (activeNeighbours == 2 || activeNeighbours == 3)) {
            return true;
        } else if (!isActive && activeNeighbours == 2) {
            return true;
        } else {
            return false;
        }
    }

    private long getActiveAdjacent(Collection<Cube> activeCubes) {
        return activeCubes.stream()
                .filter(this::isAdjacent)
                .limit(4)
                .count();
    }

}
