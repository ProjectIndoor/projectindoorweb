/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hftstuttgart.projectindoorweb.geoCalculator.internal;

import de.hftstuttgart.projectindoorweb.geoCalculator.MyGeoMath;

/**
 * @author stefan
 */
public class LocalXYCoord {

    public double x = 0;
    public double y = 0;

    public LocalXYCoord() {
    }

    public LocalXYCoord(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getDist(LocalXYCoord p2) {
        return this.sub(p2).getAbs();
    }

    public double getAbs() {
        return Math.sqrt(x * x + y * y);
    }

    public double getAbsXY() {
        return getAbs();
    }

    public LocalXYCoord add(LocalXYCoord xy) {
        LocalXYCoord res = clone();
        res.x += xy.x;
        res.y += xy.y;
        return res;
    }

    public LocalXYCoord sub(LocalXYCoord xy) {
        LocalXYCoord res = clone();
        res.x -= xy.x;
        res.y -= xy.y;
        return res;
    }

    public LocalXYCoord mul(double f) {
        LocalXYCoord res = clone();
        res.x *= f;
        res.y *= f;
        return res;
    }

    @Override
    public LocalXYCoord clone() {
        return new LocalXYCoord(x, y);
    }

    @Override
    public String toString() {
        return String.format("xy : %6.2f / %6.2f", x, y);
    }

    public double getAngleDeg(LocalXYCoord b) {
//	double cos = (x * b.x + y * b.y) / (getAbs() * b.getAbs());
//	return MyGeoMath.rad2deg(Math.acos(cos));
        double own = Math.atan2(this.y, this.x);
        double other = Math.atan2(b.y, b.x);
        return MyGeoMath.alignDeg(MyGeoMath.rad2deg(other - own));
    }

    public double getCos(LocalXYCoord b) {
        double cos = (x * b.x + y * b.y) / (getAbs() * b.getAbs());
        return cos;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!LocalXYCoord.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final LocalXYCoord other = (LocalXYCoord) obj;

        return (x == other.x && y == other.y);
    }

}
