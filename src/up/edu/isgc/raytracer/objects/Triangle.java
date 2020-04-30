package up.edu.isgc.raytracer.objects;

import up.edu.isgc.raytracer.tools.ICollide;
import up.edu.isgc.raytracer.Intersection;
import up.edu.isgc.raytracer.Ray;
import up.edu.isgc.raytracer.Vector3D;

public class Triangle implements ICollide {

    public static final double EPSILON = 0.000000001;

    private Vector3D[] vertices;
    private Vector3D[] normals;

    /**
     * It is the constructor of the Triangle
     *
     * @param vertex1
     * @param vertex2
     * @param vertex3
     */
    public Triangle(Vector3D vertex1, Vector3D vertex2, Vector3D vertex3){
        setVertices(vertex1, vertex2, vertex3);
        setNormals(null);
    }

    // Getter and Setter

    public Vector3D[] getVertices() {
        return vertices;
    }


    /**
     * to encapsulate and that do not fit more or less than 3 vertices
     *
     * @param vertex1
     * @param vertex2
     * @param vertex3
     */
    public void setVertices(Vector3D vertex1, Vector3D vertex2, Vector3D vertex3){
        Vector3D[] vertices = new Vector3D[]{vertex1, vertex2, vertex3};
        setVertices(vertices);
    }

    private void setVertices(Vector3D[] vertices) {
        this.vertices = vertices;
    }

    public Vector3D[] getNormals() {
        return normals;
    }

    public Vector3D getNormal(){
        return Vector3D.ZERO();
    }

    public void setNormals(Vector3D[] normals) {
        this.normals = normals;
    }

    /**
     * Calculate the distance using the rays to get where the rays collide
     *
     * @param ray
     * @return Intersection
     */
    @Override
    public Intersection getIntersection(Ray ray) {
        Intersection intersection = new Intersection(null, -1, null, null);

        Vector3D[] vertices = getVertices();
        Vector3D v2v0 = Vector3D.substract(vertices[2], vertices[0]);
        Vector3D v1v0 = Vector3D.substract(vertices[1], vertices[0]);
        Vector3D vectorP = Vector3D.crossProduct(ray.getDirection(), v1v0);
        double determinant = Vector3D.dotProduct(v2v0, vectorP);
        double invertedDeterminant = 1.0 / determinant;
        Vector3D vectorT = Vector3D.substract(ray.getOrigin(), vertices[0]);
        double u = Vector3D.dotProduct(vectorT, vectorP) * invertedDeterminant;
        if(u < 0 || u > 1){
            return intersection;
        }

        Vector3D vectorQ = Vector3D.crossProduct(vectorT, v2v0);
        double v = Vector3D.dotProduct(ray.getDirection(), vectorQ) * invertedDeterminant;
        if(v < 0 || (u + v) > (1.0 + EPSILON)){
            return intersection;
        }

        double t = Vector3D.dotProduct(vectorQ, v1v0) * invertedDeterminant;
        intersection.setDistance(t);

        return intersection;
    }

}
