package me.vforchi.aoc.y2020.day12;

public class Waypoint extends Position {

    public Waypoint(int posEW, int posNS) {
        super(posEW, posNS);
    }

    public void rotate(int angle) {
        Waypoint newWaypoint;
        if (angle == 90) {
            newWaypoint = new Waypoint(this.posNS, -this.posEW);
        } else if (angle == 180) {
            newWaypoint = new Waypoint(-this.posEW, -this.posNS);
        } else if (angle == 270) {
            newWaypoint = new Waypoint(-this.posNS, +this.posEW);
        } else {
            throw new RuntimeException();
        }
        this.posEW = newWaypoint.posEW;
        this.posNS = newWaypoint.posNS;
    }

    public String toString() {
        return String.valueOf(Math.abs(posEW) + Math.abs(posNS));
    }

}