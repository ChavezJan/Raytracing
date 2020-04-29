package up.edu.isgc.raytracer.objects;

import up.edu.isgc.raytracer.Intersection;
import up.edu.isgc.raytracer.Ray;
import up.edu.isgc.raytracer.Vector3D;

import java.awt.*;

public class polygons extends Object3D {
    public polygons(Vector3D position, Color color) {
        super(position, color);
    }

    @Override
    public Intersection getIntersection(Ray ray) {
        return null;
    }
}
