package com.lightningrobotics.common.geometry;

public class Pose {

    private final Translation translation;
    private final Rotation rotation;

    public Pose() {
        translation = new Translation();
        rotation = new Rotation();
    }

    public Pose(Translation translation, Rotation rotation) {
        this.translation = translation;
        this.rotation = rotation;
    }

    public Pose(double x, double y, Rotation rotation) {
        this.translation = new Translation(x, y);
        this.rotation = rotation;
    }

    public Pose plus(Transform other) {
        return transformBy(other);
    }

    public Transform minus(Pose other) {
        final var pose = this.relativeTo(other);
        return new Transform(pose.getTranslation(), pose.getRotation());
    }

    public Translation getTranslation() { return translation; }

    public double getX() { return translation.getX(); }

    public double getY() { return translation.getY(); }

    public Rotation getRotation() { return rotation; }

    public Pose transformBy(Transform other) {
        return new Pose(translation.plus(other.getTranslation().rotateBy(rotation)),
                rotation.plus(other.getRotation()));
    }

    public Pose relativeTo(Pose other) {
        var transform = new Transform(other, this);
        return new Pose(transform.getTranslation(), transform.getRotation());
    }

    public Pose exp(Twist twist) {
        double dx = twist.dx;
        double dy = twist.dy;
        double dtheta = twist.dtheta;

        double sinTheta = Math.sin(dtheta);
        double cosTheta = Math.cos(dtheta);

        double s;
        double c;
        if (Math.abs(dtheta) < 1E-9) {
            s = 1.0 - 1.0 / 6.0 * dtheta * dtheta;
            c = 0.5 * dtheta;
        } else {
            s = sinTheta / dtheta;
            c = (1 - cosTheta) / dtheta;
        }
        var transform = new Transform(new Translation(dx * s - dy * c, dx * c + dy * s),
                new Rotation(cosTheta, sinTheta));

        return this.plus(transform);
    }

    public Twist log(Pose end) {
        final var transform = end.relativeTo(this);
        final var dtheta = transform.getRotation().getRadians();
        final var halfDtheta = dtheta / 2.0;

        final var cosMinusOne = transform.getRotation().cos() - 1;

        double halfThetaByTanOfHalfDtheta;
        if (Math.abs(cosMinusOne) < 1E-9) {
            halfThetaByTanOfHalfDtheta = 1.0 - 1.0 / 12.0 * dtheta * dtheta;
        } else {
            halfThetaByTanOfHalfDtheta = -(halfDtheta * transform.getRotation().sin()) / cosMinusOne;
        }

        Translation translationPart = transform.getTranslation()
                .rotateBy(new Rotation(halfThetaByTanOfHalfDtheta, -halfDtheta))
                .times(Math.hypot(halfThetaByTanOfHalfDtheta, halfDtheta));

        return new Twist(translationPart.getX(), translationPart.getY(), dtheta);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Pose) {
            return ((Pose) obj).translation.equals(translation) && ((Pose) obj).rotation.equals(rotation);
        }
        return false;
    }

}
