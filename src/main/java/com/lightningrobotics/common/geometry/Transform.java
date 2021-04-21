package com.lightningrobotics.common.geometry;

public class Transform {

    private final Translation translation;
    private final Rotation rotation;

    public Transform(Pose initial, Pose last) {
        translation = last.getTranslation().minus(initial.getTranslation())
                .rotateBy(initial.getRotation().unaryMinus());
        rotation = last.getRotation().minus(initial.getRotation());
    }

    public Transform(Translation translation, Rotation rotation) {
        this.translation = translation;
        this.rotation = rotation;
    }

    public Transform() {
        this.translation = new Translation();
        this.rotation = new Rotation();
    }

    public Transform times(double scalar) {
        return new Transform(translation.times(scalar), rotation.times(scalar));
    }

    public Translation getTranslation() { return translation; }

    public double getX() { return translation.getX(); }

    public double getY() { return translation.getY(); }

    public Rotation getRotation() { return rotation; }

    public Transform inverse() {
        return new Transform(getTranslation().unaryMinus().rotateBy(getRotation().unaryMinus()), getRotation().unaryMinus());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Transform) {
            return ((Transform) obj).translation.equals(translation) && ((Transform) obj).rotation.equals(rotation);
        }
        return false;
    }

}
