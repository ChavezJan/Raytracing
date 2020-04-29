package up.edu.isgc.raytracer.objects;

import up.edu.isgc.raytracer.Tools.Intersection;
import up.edu.isgc.raytracer.Tools.Ray;
import up.edu.isgc.raytracer.Tools.Vector3D;

import java.awt.*;

/**
 *  Its the base for all the object
 */

public abstract class Object3D {

    private Vector3D position;
    private Color color;

    public Vector3D getPosition() {
        return position;
    }

    public void setPosition(Vector3D position) {
        this.position = position;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    /**
     *
     * It is the constructor of Object3D
     *
     * @param position
     * @param color
     */

    public Object3D(Vector3D position, Color color) {
        setPosition(position);
        setColor(color);
    }

    /**
     *
     *
     *
     * @param ray
     * @return Intersection
     */
    public abstract Intersection getIntersection(Ray ray);
}
