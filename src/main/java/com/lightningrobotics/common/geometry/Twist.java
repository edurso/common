package com.lightningrobotics.common.geometry;

public class Twist {

    public double dx;
    public double dy;
    public double dtheta;

    public Twist() {}

    public Twist(double dx, double dy, double dtheta) {
        this.dx = dx;
        this.dy = dy;
        this.dtheta = dtheta;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Twist) {
            return Math.abs(((Twist) obj).dx - dx) < 1E-9 && Math.abs(((Twist) obj).dy - dy) < 1E-9
                    && Math.abs(((Twist) obj).dtheta - dtheta) < 1E-9;
        }
        return false;
    }

}
