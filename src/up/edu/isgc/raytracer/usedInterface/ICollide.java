package up.edu.isgc.raytracer.usedInterface;

import up.edu.isgc.raytracer.Intersection;
import up.edu.isgc.raytracer.Ray;

public interface ICollide {

    /**
     * It is the constructor of Intersection
     *
     * @param ray
     * @return Intersection
     */
    public abstract Intersection getIntersection(Ray ray);

}
