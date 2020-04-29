package up.edu.isgc.raytracer.objects;

import up.edu.isgc.raytracer.Tools.Intersection;
import up.edu.isgc.raytracer.Tools.Ray;
import up.edu.isgc.raytracer.Tools.Vector3D;

import java.awt.*;

/**
 * In progress
 */

public class Polygons extends Object3D{
    /**
     * It is the constructor of the Object3D
     *
     * @param position
     * @param color
     */
    
    public Polygons(Vector3D position, Color color) {
        super(position, color);
    }

    @Override
    public Intersection getIntersection(Ray ray) {
        return null;
    }
}
