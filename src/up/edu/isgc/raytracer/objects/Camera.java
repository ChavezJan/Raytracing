/**
 * [1968] - [2020] Centros Culturales de Mexico A.C / Universidad Panamericana
 * All Rights Reserved.
 */
package up.edu.isgc.raytracer.objects;
/**
 * @author ChavezJan
 * @author Jafet Rodríguez
 */
import up.edu.isgc.raytracer.Intersection;
import up.edu.isgc.raytracer.Ray;
import up.edu.isgc.raytracer.Vector3D;

import java.awt.*;

/**
 * Is the constructor of the camera and it calculate Positions To Ray
 */

public class Camera extends Object3D {
    // 0 is fovh
    // 1 is fovv
    private float[] fieldOfView = new float[2];
    private float defaultZ = 15f;
    // 0 is width
    // 1 is height
    private int[] resolution;
    // 0 is Near
    // 1 is Far
    private float[] NearFarPlane = new float[2];

    /**
     * It is the constructor of Camera
     *
     * @param position
     * @param fieldOfViewHorizontal Is in degrees
     * @param fieldOfViewVertical Is in degrees
     * @param widthResolution Is in pixels
     * @param heightResolution Is in pixels
     * @param nearPlane Relating to the camera position
     * @param farPlane Relating to the camera position
     */

    public Camera(Vector3D position, float fieldOfViewHorizontal, float fieldOfViewVertical, int widthResolution, int heightResolution, float nearPlane, float farPlane) {
        super(position, Color.black, 0,0,0);
        setFieldOfViewHorizontal(fieldOfViewHorizontal);
        setFieldOfViewVertical(fieldOfViewVertical);
        setResolution(new int[]{widthResolution, heightResolution});
        setNearFarPlane(new float[]{nearPlane, farPlane});

    }

    //getter and setter

    public float[] getFieldOfView() {
        return fieldOfView;
    }

    public void setFieldOfView(float[] fieldOfView) {
        this.fieldOfView = fieldOfView;
    }

    public float getFieldOfViewHorizontal() {
        return fieldOfView[0];
    }

    public void setFieldOfViewHorizontal(float fov) {
        fieldOfView[0] = fov;
    }

    public float getFieldOfViewVertical() {
        return fieldOfView[1];
    }

    public void setFieldOfViewVertical(float fov) {
        fieldOfView[1] = fov;
    }

    public float getDefaultZ() {
        return defaultZ;
    }

    public void setDefaultZ(float defaultZ) {
        this.defaultZ = defaultZ;
    }

    public int[] getResolution() {
        return resolution;
    }

    public void setResolution(int[] resolution) {
        this.resolution = resolution;
    }

    public int getResolutionWidth(){
        return getResolution()[0];
    }

    public int getResolutionHeight(){
        return getResolution()[1];
    }

    public float[] getNearFarPlane() {
        return NearFarPlane;
    }

    public void setNearFarPlane(float[] nearFarPlane) {
        NearFarPlane = nearFarPlane;
    }

    /**
     * Calculates the ray mesh of the camera and return a two-dimensional position of each pixel
     *
     * @return the position
     */

    public Vector3D[][] calculatePositionsToRay() {
        float angleMaxX = 90 - (getFieldOfViewHorizontal() / 2f);
        float radiusMaxX = getDefaultZ() / (float) Math.cos(Math.toRadians(angleMaxX));

        float maxX = (float) Math.sin(Math.toRadians(angleMaxX)) * radiusMaxX;
        float minX = -maxX;

        float angleMaxY = 90 - (getFieldOfViewVertical() / 2f);
        float radiusMaxY = getDefaultZ() / (float) Math.cos(Math.toRadians(angleMaxY));

        float maxY = (float) Math.sin(Math.toRadians(angleMaxY)) * radiusMaxY;
        float minY = -maxY;

        Vector3D[][] positions = new Vector3D[getResolutionWidth()][getResolutionHeight()];
        float posZ = getDefaultZ();
        for(int x = 0; x < positions.length; x++){
            for(int y = 0; y < positions[x].length; y++){
                float posX = minX + (((maxX - minX) / (float) getResolutionWidth()) * x);
                float posY = maxY - (((maxY - minY) / (float) getResolutionHeight()) * y);
                positions[x][y] = new Vector3D(posX, posY, posZ);
            }
        }

        return positions;
    }

    @Override
    public Intersection getIntersection(Ray ray) {
        return new Intersection(Vector3D.ZERO(), -1, Vector3D.ZERO(), null,null);
    }
}
