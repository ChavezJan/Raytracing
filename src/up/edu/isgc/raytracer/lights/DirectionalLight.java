/**
 * [1968] - [2020] Centros Culturales de Mexico A.C / Universidad Panamericana
 * All Rights Reserved.
 */
package up.edu.isgc.raytracer.lights;

import up.edu.isgc.raytracer.Intersection;
import up.edu.isgc.raytracer.Vector3D;

import java.awt.*;

/**
 * @author ChavezJan
 * @author Jafet Rodríguez
 */
public class DirectionalLight extends Light {
    private Vector3D direction;

    /**
     *  It is the constructor of DirectionalLight
     *
     * @param position
     * @param direction
     * @param color
     * @param intensity
     */
    public DirectionalLight(Vector3D position, Vector3D direction, Color color, double intensity){
        super(position, color, intensity,0,0,0,0, 0,0);
        setDirection(Vector3D.normalize(direction));
    }

    //Getters and Setters

    public Vector3D getDirection() {
        return direction;
    }

    public void setDirection(Vector3D direction) {
        this.direction = direction;
    }

    @Override
    public float getNDotL(Intersection intersection) {
        return (float)Math.max(Vector3D.dotProduct(intersection.getNormal(), Vector3D.scalarMultiplication(getDirection(), -1.0)), 0.0);
    }
}
