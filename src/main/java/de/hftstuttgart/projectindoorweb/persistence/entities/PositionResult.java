package de.hftstuttgart.projectindoorweb.persistence.entities;

public class PositionResult extends ModelBase {

    private double x;
    private double y;
    private double z;
    private boolean wgs84;

    private Project generatingProject;

    public PositionResult(double x, double y, double z, boolean wgs84, Project generatingProject) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.wgs84 = wgs84;
        this.generatingProject = generatingProject;
    }

    public PositionResult(double x, double y, double z, boolean wgs84) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.wgs84 = wgs84;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public boolean isWgs84() {
        return wgs84;
    }

    public void setWgs84(boolean wgs84) {
        this.wgs84 = wgs84;
    }

    public Project getGeneratingProject() {
        return generatingProject;
    }

    public void setGeneratingProject(Project generatingProject) {
        this.generatingProject = generatingProject;
    }
}
