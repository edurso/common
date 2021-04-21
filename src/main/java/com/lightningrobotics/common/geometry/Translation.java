package com.lightningrobotics.common.geometry;

public class Translation {

    private final double x;
    private final double y;

    public Translation() {
        this(0.0, 0.0);
    }

    public Translation(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Translation(double distance, Rotation angle) {
        x = distance * angle.cos();
        y = distance * angle.sin();
    }

    public double getDistance(Translation other) {
        return Math.hypot(other.x - x, other.y - y);
    }

    public double getX() { return x; }

    public double getY() { return y; }

    public double getNorm() { return Math.hypot(x, y); }

    public Translation rotateBy(Rotation other) {
        return new Translation(x * other.cos() - y * other.sin(), x * other.sin() + y * other.cos());
    }

    public Translation plus(Translation other) {
        return new Translation(x + other.x, y + other.y);
    }

    public Translation minus(Translation other) {
        return new Translation(x - other.x, y - other.y);
    }

    public Translation unaryMinus() {
        return new Translation(-x, -y);
    }

    public Translation times(double scalar) {
        return new Translation(x * scalar, y * scalar);
    }

    public Translation div(double scalar) {
        return new Translation(x / scalar, y / scalar);
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Translation) ? Math.abs(((Translation) obj).x - x) < 1E-9 && Math.abs(((Translation) obj).y - y) < 1E-9 : false;
    }

}
