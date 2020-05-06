/**
 * [1968] - [2020] Centros Culturales de Mexico A.C / Universidad Panamericana
 * All Rights Reserved.
 */
package up.edu.isgc.raytracer.objects;
/**
 * @author ChavezJan
 * @author Jafet Rodríguez
 */
import up.edu.isgc.raytracer.usedInterface.ICollide;
import up.edu.isgc.raytracer.Vector3D;

import java.awt.*;

/**
 *  Its the base for all the object
 */

public abstract class Object3D implements ICollide {

    private Vector3D position;
    private Color color;

    //getter and setter

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
     * It is the constructor of Object3D
     *
     * @param position
     * @param color
     */

    public Object3D(Vector3D position, Color color) {
        setPosition(position);
        setColor(color);
    }


}
