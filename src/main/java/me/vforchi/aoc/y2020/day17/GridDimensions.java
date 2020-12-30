package me.vforchi.aoc.y2020.day17;

import java.util.Collection;

public class GridDimensions {

    public int minx = Integer.MAX_VALUE;
    public int miny = Integer.MAX_VALUE;
    public int minz = Integer.MAX_VALUE;
    public Integer minw = Integer.MAX_VALUE;

    public int maxx = Integer.MIN_VALUE;
    public int maxy = Integer.MIN_VALUE;
    public int maxz = Integer.MIN_VALUE;
    public Integer maxw = Integer.MIN_VALUE;

    public static GridDimensions gridFromCubes(Collection<Cube> cubes) {
        var dim = new GridDimensions();
        for (var cube: cubes) {
            dim.minx = Math.min(dim.minx, cube.x - 1);
            dim.miny = Math.min(dim.miny, cube.y - 1);
            dim.minz = Math.min(dim.minz, cube.z - 1);

            dim.maxx = Math.max(dim.maxx, cube.x + 1);
            dim.maxy = Math.max(dim.maxy, cube.y + 1);
            dim.maxz = Math.max(dim.maxz, cube.z + 1);

            if (cube.w == null) {
                dim.minw = null;
                dim.maxw = null;
            } else {
                dim.minw = Math.min(dim.minw, cube.w - 1);
                dim.maxw = Math.max(dim.maxw, cube.w + 1);
            }
        }
        return dim;
    }

}
