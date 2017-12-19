package de.hftstuttgart.projectindoorweb.geoCalculator.internal;

public class LocXYZ extends LocalXYCoord {

    public double z = 0;

    public LocXYZ() {
        super();
        z = 0;
    }

    public LocXYZ(LocalXYCoord xy) {
        super(xy.x, xy.y);
        this.z = 0;
    }

    public LocXYZ(LocalXYCoord localXYCoord, double height) {
        super(localXYCoord.x, localXYCoord.y);
        this.z = height;
    }


    public LocXYZ(double x, double y, double z) {
        super(x, y);
        this.z = z;
    }

    public LocXYZ(LocXYZ r) {
        this.x = r.x;
        this.y = r.y;
        this.z = r.z;
    }

    public double getAbs() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public double getAbsXY() {
        return super.getAbs();
    }

    public LocXYZ add(LocXYZ r) {
        LocXYZ res = clone();
        res.x += r.x;
        res.y += r.y;
        res.z += r.z;
        return res;
    }

    public LocXYZ sub(LocXYZ r) {
        LocXYZ res = clone();
        res.x -= r.x;
        res.y -= r.y;
        res.z -= r.z;
        return res;
    }

    public LocXYZ mul(double f) {
        LocXYZ res = clone();
        res.x *= f;
        res.y *= f;
        res.z *= f;
        return res;
    }

    public double getDist(LocXYZ p2) {
        return this.sub(p2).getAbs();
    }

    /*	public int getFloorID() {
        return MyUtils.getFloor(z);
	}*/
    @Override
    public LocXYZ clone() {
        return new LocXYZ(x, y, z);
    }

    @Override
    public String toString() {
        return String.format("xyz : %6.2f / %6.2f / %6.2f ", x, y, z);
    }

}
