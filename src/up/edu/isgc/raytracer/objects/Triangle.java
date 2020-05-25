/**
 * [1968] - [2020] Centros Culturales de Mexico A.C / Universidad Panamericana
 * All Rights Reserved.
 */
package up.edu.isgc.raytracer.objects;

import up.edu.isgc.raytracer.usedInterface.ICollide;
import up.edu.isgc.raytracer.Intersection;
import up.edu.isgc.raytracer.Ray;
import up.edu.isgc.raytracer.Vector3D;
/**
 * @author ChavezJan
 * @author Jafet Rodríguez
 */
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

    public Triangle(Vector3D vertex1, Vector3D vertex2, Vector3D vertex3, Vector3D normal1, Vector3D normal2, Vector3D normal3) {
        this(vertex1, vertex2, vertex3);
        setNormals(normal1, normal2, normal3);
    }

    public Triangle(Vector3D[] vertices, Vector3D[] normal) {
        if (vertices.length == 3) {
            setVertices(vertices[0], vertices[1], vertices[2]);
        } else {
            setVertices(Vector3D.ZERO(), Vector3D.ZERO(), Vector3D.ZERO());
        }
        setNormals(normal);
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
        if(normals == null){
            Vector3D normal = getNormal();
            setNormals(new Vector3D[]{normal, normal, normal});
        }
        return normals;
    }

    /**
     * Calculate the normal of the Triangles
     *
     * @return the normal of the Triangle
     */
    public Vector3D getNormal() {
        Vector3D normal = Vector3D.ZERO();

        Vector3D[] normals = this.normals;
        if (normals == null) {
            Vector3D[] vertices = getVertices();
            Vector3D v = Vector3D.substract(vertices[1], vertices[0]);
            Vector3D w = Vector3D.substract(vertices[2], vertices[0]);

            normal = Vector3D.scalarMultiplication(Vector3D.normalize(Vector3D.crossProduct(v, w)), -1.0);
        } else {
            for(int i = 0; i < normals.length; i++){
                normal.setX(normal.getX() + normals[i].getX());
                normal.setY(normal.getY() + normals[i].getY());
                normal.setZ(normal.getZ() + normals[i].getZ());
            }
            normal.setX(normal.getX() / normals.length);
            normal.setY(normal.getY() / normals.length);
            normal.setZ(normal.getZ() / normals.length);
        }

        return normal;
    }

    public void setNormals(Vector3D[] normals) {
        this.normals = normals;
    }

    public void setNormals(Vector3D normal1, Vector3D normal2, Vector3D normal3) {
        Vector3D[] normals = new Vector3D[]{normal1, normal2, normal3};
        setNormals(normals);
    }

    /**
     * Calculate the distance using the rays to get where the rays collide
     *
     * @param ray
     * @return Intersection
     */
    @Override
    public Intersection getIntersection(Ray ray) {
        Intersection intersection = new Intersection(null, -1, null, null, null);

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
