package com.lightningrobotics.common.geometry;

public class Rotation {

    private final double value;
    private final double cos;
    private final double sin;

    public Rotation() {
        this(0d);
    }

    public Rotation(double value) {
        this.value = value;
        this.cos = Math.cos(value);
        this.sin = Math.sin(value);
    }

    public Rotation(double x, double y) {
        double magnitude = Math.hypot(x, y);
        this.sin = (magnitude > 1e-6) ? (y / magnitude) : 0d; 
        this.cos = (magnitude > 1e-6) ? (x / magnitude) : 1d; 
        this.value = Math.atan2(sin, cos);
    }

    public static Rotation fromDegrees(double degrees) {
        return new Rotation(Math.toRadians(degrees));
    }

    public Rotation plus(Rotation other) {
        return rotateBy(other);
    }

    public Rotation minus(Rotation other) {
        return rotateBy(other.unaryMinus());
    }

    public Rotation unaryMinus() {
        return new Rotation(-value);
    }

    public Rotation times(double scalar) {
        return new Rotation(value * scalar);
    }

    public Rotation rotateBy(Rotation other) {
        return new Rotation(cos * other.cos - sin * other.sin, cos * other.sin + sin * other.cos);
    }

    public double getRadians() {
        return value;
    }

    public double getDegrees() {
        return Math.toDegrees(value);
    }

    public double cos() {
        return cos;
    }

    public double sin() {
        return sin;
    }

    public double tan() {
        return (sin / cos);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Rotation) {
            var other = (Rotation) obj;
            return Math.hypot(cos - other.cos, sin - other.sin) < 1E-9;
        }
        return false;
    }

}
