package up.edu.isgc.raytracer.objects;

import up.edu.isgc.raytracer.Intersection;
import up.edu.isgc.raytracer.Ray;
import up.edu.isgc.raytracer.Vector3D;

import java.awt.*;

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
