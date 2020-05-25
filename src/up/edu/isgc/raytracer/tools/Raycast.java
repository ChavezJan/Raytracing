/**
 * [1968] - [2020] Centros Culturales de Mexico A.C / Universidad Panamericana
 * All Rights Reserved.
 */
package up.edu.isgc.raytracer.tools;

import up.edu.isgc.raytracer.Intersection;
import up.edu.isgc.raytracer.Ray;
import up.edu.isgc.raytracer.objects.Object3D;

import java.util.ArrayList;

/**
 * @author ChavezJan
 * @author Jafet Rodríguez
 */
public class Raycast {
    /**
     * Calculate the distance to get the closest intersection
     *
     * @param ray
     * @param objects
     * @param caster = Null
     * @return closest intersection between the camera and the spheres
     */

    public static Intersection raycast(Ray ray, ArrayList<Object3D> objects, Object3D caster, float[] ClippingPlanes){
        Intersection closestIntersection = null;

        for(int k = 0; k < objects.size(); k++){
            Object3D currentObj = objects.get(k);
            if(caster == null || !currentObj.equals(caster)){
                Intersection intersection = currentObj.getIntersection(ray);
                if(intersection != null){
                    double distance = intersection.getDistance();
                    if(distance >= 0 && (closestIntersection == null || distance < closestIntersection.getDistance()) && (ClippingPlanes == null || (intersection.getPosition().getZ() >= ClippingPlanes[0] && intersection.getPosition().getZ() <= ClippingPlanes[1]))) {
                        closestIntersection = intersection;
                    }
                }
            }
        }

        return closestIntersection;
    }
}
