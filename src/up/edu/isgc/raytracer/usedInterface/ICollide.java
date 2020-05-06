/**
 * [1968] - [2020] Centros Culturales de Mexico A.C / Universidad Panamericana
 * All Rights Reserved.
 */
package up.edu.isgc.raytracer.usedInterface;

import up.edu.isgc.raytracer.Intersection;
import up.edu.isgc.raytracer.Ray;
/**
 * @author ChavezJan
 * @author Jafet Rodríguez
 */
public interface ICollide {

    /**
     * It is the constructor of Intersection
     *
     * @param ray
     * @return Intersection
     */
    public abstract Intersection getIntersection(Ray ray);

}
