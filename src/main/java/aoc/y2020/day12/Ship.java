package aoc.y2020.day12;

public class Ship extends Position {

    int direction = 90;

    public Ship(int posEW, int posNS) {
        super(posEW, posNS);
    }

    public void forward(int value) {
        switch (direction) {
            case 0 -> posNS += value;
            case 90 -> posEW += value;
            case 180 -> posNS -= value;
            case 270 -> posEW -= value;
        }
    }

    public void rotate(int angle) {
        direction = (direction + angle) % 360;
        if (direction < 0) {
            direction += 360;
        }
    }

    public String toString() {
        return String.valueOf(Math.abs(posEW) + Math.abs(posNS));
    }

    public void toWaypoint(Waypoint waypoint, int value) {
        this.posEW += waypoint.posEW * value;
        this.posNS += waypoint.posNS * value;
    }

}