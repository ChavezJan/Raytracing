/**
 * [1968] - [2020] Centros Culturales de Mexico A.C / Universidad Panamericana
 * All Rights Reserved.
 */
package up.edu.isgc.raytracer.objects;

import up.edu.isgc.raytracer.usedInterface.ICollide;
import up.edu.isgc.raytracer.Vector3D;

import java.awt.*;
/**
 * @author ChavezJan
 * @author Jafet Rodríguez
 *  Its the base for all the object
 */

public abstract class Object3D implements ICollide {

    private Vector3D position;
    private Color color;
    private float Ambient;
    private float specular;
    private float shininess;
    private float diffuse;
    private float reflection;
    private float refraction;




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

    public float getAmbient() {
        return Ambient;
    }

    public void setAmbient(float ambient) {
        Ambient = ambient;
    }


    public float getSpecular() {
        return specular;
    }

    public void setSpecular(float specular) {
        this.specular = specular;
    }

    public float getShininess() {
        return shininess;
    }

    public void setShininess(float shininess) {
        this.shininess = shininess;
    }

    public float getReflection() {
        return reflection;
    }

    public void setReflection(float reflection) {
        this.reflection = reflection;
    }

    public float getRefraction() {
        return refraction;
    }

    public void setRefraction(float refraction) {
        this.refraction = refraction;
    }

    public float getDiffuse() {
        return diffuse;
    }

    public void setDiffuse(float diffuse) {
        this.diffuse = diffuse;
    }

    /**
     * It is the constructor of Object3D
     *
     * @param position
     * @param color
     */

    public Object3D(Vector3D position, Color color, float ambient, float shininess ,float specular,float diffuse) {
        setPosition(position);
        setColor(color);
        setAmbient(ambient);
        setShininess(shininess);
        setSpecular(specular);
        setDiffuse(diffuse);
    }



}
