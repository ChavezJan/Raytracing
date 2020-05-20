/**
 * [1968] - [2020] Centros Culturales de Mexico A.C / Universidad Panamericana
 * All Rights Reserved.
 */
package up.edu.isgc.raytracer.lights;

import up.edu.isgc.raytracer.Intersection;
import up.edu.isgc.raytracer.Ray;
import up.edu.isgc.raytracer.Vector3D;
import up.edu.isgc.raytracer.objects.Object3D;

import java.awt.*;

/**
 * @author ChavezJan
 * @author Jafet Rodríguez
 */
public abstract class Light extends Object3D {
    private double intensity;

    /**
     * It is the constructor of the Light
     *
     * @param position
     * @param color
     * @param intensity
     */
    public Light(Vector3D position, Color color, double intensity){
        super(position, color);
        setIntensity(intensity);
    }

    // Getters and Setters

    public double getIntensity() {
        return intensity;
    }

    public double getIntensity(Vector3D lightPosition, Vector3D objectPosition) {
        double distance = Math.sqrt((objectPosition.getX() - lightPosition.getX()) + (objectPosition.getY() - lightPosition.getY()) + (objectPosition.getZ() - lightPosition.getZ()));
        return (intensity/(Math.pow(distance, 2)));
    }

    public void setIntensity(double intensity) {
        this.intensity = intensity;
    }

    public abstract float getNDotL(Intersection intersection);

    /**
     * Gets the intersection of the light and the Object
     *
     * @param ray
     * @return new Intersection
     */
    public Intersection getIntersection(Ray ray){
        return new Intersection(Vector3D.ZERO(), -1, Vector3D.ZERO(), null);
    }
}
