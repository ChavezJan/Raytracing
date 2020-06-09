package up.edu.isgc.raytracer.tools;

import up.edu.isgc.raytracer.Intersection;
import up.edu.isgc.raytracer.Ray;
import up.edu.isgc.raytracer.Vector3D;
import up.edu.isgc.raytracer.lights.Light;
import up.edu.isgc.raytracer.objects.Camera;
import up.edu.isgc.raytracer.objects.Object3D;

import java.awt.*;
import java.util.ArrayList;

public class Colors {
    /**
     *
     * @param closestIntersection
     * @param light
     * @param pixelColor
     * @param mainCamera
     * @param objects
     * @param nearFarPlanes
     * @return
     */
// este
    public static Color ObjectColor(Intersection closestIntersection, Light light, Color pixelColor, Camera mainCamera, ArrayList<Object3D> objects, float[] nearFarPlanes){

        double distance =  Vector3D.magnitude(Vector3D.substract(closestIntersection.getPosition(), light.getPosition()));
        Object3D object = closestIntersection.getObject();
        float nDotL = light.getNDotL(closestIntersection);
        float intensity = (float) light.getIntensity() * nDotL;
        Color lightColor = light.getColor();


        float[] lightColors = new float[]{
                lightColor.getRed() / 255.0f,
                lightColor.getGreen() / 255.0f,
                lightColor.getBlue() / 255.0f};


        Color objectColor = closestIntersection.getObject().getColor();

        float[] objectColors = new float[]{
                objectColor.getRed() / 255.0f * object.getAmbient(),
                objectColor.getGreen() / 255.0f * object.getAmbient(),
                objectColor.getBlue() / 255.0f * object.getAmbient()
        };

        Color ambient = new Color(
                clamp(objectColors[0], 0, 1),
                clamp(objectColors[1], 0, 1),
                clamp(objectColors[2], 0, 1)
        );

        for (int colorIndex = 0; colorIndex < objectColors.length; colorIndex++) {
            objectColors[colorIndex] *= (intensity / Math.pow(distance, 2)) * lightColors[colorIndex];
        }

        pixelColor = addColor(pixelColor, ambient);

        Color diffuse = new Color(clamp(objectColors[0], 0, 1), clamp(objectColors[1], 0, 1), clamp(objectColors[2], 0, 1));

        Ray ray = new Ray(closestIntersection.getPosition(), light.getPosition());

        Intersection lightIntersection = Raycast.raycast(ray,objects,null,new float[]{
                (float) mainCamera.getPosition().getZ() + nearFarPlanes[0],
                (float) mainCamera.getPosition().getZ() + nearFarPlanes[1]}
                );

        Color specular = getSpecular(light, closestIntersection, objectColors, object);

        if (lightIntersection != null){
            double distObject = Vector3D.magnitude(Vector3D.substract(lightIntersection.getPosition(), light.getPosition()));
            if (lightIntersection.getObject() == closestIntersection.getObject()) {
                pixelColor = addColor(pixelColor, diffuse);
                pixelColor = addColor(pixelColor, specular );
            }
            else if (distance < distObject) {
                pixelColor = addColor(pixelColor, diffuse);
                pixelColor = addColor(pixelColor, specular );
            }
        }
        else {
            pixelColor = addColor(pixelColor, diffuse);
            pixelColor = addColor(pixelColor, specular);
        }
        return pixelColor;
    }

    /**
     *
     * @param light
     * @param intersection
     * @param colors
     * @param object
     * @return
     */
    private static Color getSpecular (Light light, Intersection intersection, float [] colors, Object3D object){

        Vector3D halfVector = Vector3D.add(light.getPosition(), intersection.getPosition());

        halfVector = new Vector3D(
                halfVector.getX() / Vector3D.magnitude(halfVector),
                halfVector.getY() / Vector3D.magnitude(halfVector),
                halfVector.getZ() / Vector3D.magnitude(halfVector)
        );

        halfVector = Vector3D.normalize(halfVector);

        float Specular = (float) Math.pow(Vector3D.dotProduct(intersection.getNormal(), halfVector), object.getShininess());

        for (int color = 0; color < 3; color++) colors[color] *= Specular * object.getSpecular();

        Color specular = new Color(
                clamp(colors[0], 0, 1),
                clamp(colors[1], 0, 1),
                clamp(colors[2], 0, 1)
        );

        return specular;

    }

    /**
     *
     * @param intersection
     * @param light
     * @param objects
     * @param camera
     * @param nearFarPlanes
     * @return
     */
    public static Color Refraction(Intersection intersection, Light light, ArrayList<Object3D> objects, Camera camera, float[] nearFarPlanes){
        Object3D object = intersection.getObject();
        Vector3D position = intersection.getPosition();
        Vector3D normal = intersection.getNormal();
        Vector3D newPosition = Vector3D.scalarMultiplication(normal, 1.5);

        float reflectance = (float) (Math.pow((object.getRefraction()-1), 2) / Math.pow((object.getRefraction()+1), 2));
        Color refractColor = Color.BLACK;

        if (reflectance > 0 ) {
            position = Vector3D.add(position, newPosition);
        }
        else {
            position = Vector3D.substract(position, newPosition);
        }

        Vector3D incidentRay = Vector3D.substract(position, camera.getPosition());
        double NV = clamp(-1.0f, 1.0f,(float) Vector3D.dotProduct(incidentRay, normal));

        Vector3D RefractionVector = Vector3D.ZERO();

        double P1 = (object.getRefraction())*NV;
        double P2 = Math.sqrt(1 - Math.pow(object.getRefraction(), 2)* (1 - Math.pow(NV, 2)));
        if (P2 > 0) {
            RefractionVector = Vector3D.add(Vector3D.scalarMultiplication(incidentRay, object.getRefraction()), Vector3D.scalarMultiplication(normal, P1-P2 ));
        }

        Ray reflectHit = new Ray(position, RefractionVector);
        Intersection refractIntersect = Raycast.raycast(reflectHit, objects, intersection.getObject(), new float[] {
                (float) camera.getPosition().getZ() + nearFarPlanes[0]-8,
                (float) camera.getPosition().getZ() + nearFarPlanes[1]-8}
                );

        if (refractIntersect != null && refractIntersect.getObject().getRefraction() == 0f){
            refractColor = ObjectColor(refractIntersect, light, refractColor, camera, objects, new float []{nearFarPlanes[0]-3, nearFarPlanes[1] -3});
        }

        return refractColor;
    }

    /**
     *
     * @param intersection
     * @param light
     * @param objects
     * @param camera
     * @param nearFarPlanes
     * @return
     */
    public static Color Reflection(Intersection intersection, Light light, ArrayList<Object3D> objects, Camera camera, float[] nearFarPlanes){
        Vector3D incidentRay = Vector3D.substract(intersection.getPosition(), camera.getPosition());
        double normalRay = -2.0 * Vector3D.dotProduct(intersection.getNormal(), incidentRay);
        Vector3D reflection = Vector3D.scalarMultiplication(intersection.getNormal(), normalRay);
        Color reflectColor = Color.BLACK;
        reflection = Vector3D.add(incidentRay, reflection);

        Ray reflectHit = new Ray(intersection.getPosition(), reflection);

        Intersection reflectIntersect = Raycast.raycast(reflectHit, objects, null, new float[] {
                (float) camera.getPosition().getZ() + nearFarPlanes[0],
                (float) camera.getPosition().getZ() + nearFarPlanes[1]}
        );

        if (reflectIntersect != null && intersection.getObject() != reflectIntersect.getObject()){
            reflectColor = ObjectColor(reflectIntersect, light, reflectColor, camera, objects, new float []{
                    nearFarPlanes[0],
                    nearFarPlanes[1]}
            );


             if (reflectIntersect.getObject().getReflection() != 0) {
                reflectColor = addColor(reflectColor, Reflection(reflectIntersect, light, objects, camera, nearFarPlanes));
            }
        }
        return reflectColor;
    }


    /**
     * prevents values from being greater than maximum or less than minimum
     *
     * @param value
     * @param min
     * @param max
     * @return value between maximum or minimum
     */
    public static float clamp(float value, float min, float max) {
        if (value < min) {
            return min;
        }
        if (value > max) {
            return max;
        }
        return value;
    }

    /**
     * use the blur to get the exact color according to the reflection of the normal
     *
     * @param original
     * @param otherColor
     * @return The color to use
     */
    public static Color addColor(Color original, Color otherColor){
        float red = clamp((original.getRed() / 255.0f) + (otherColor.getRed() / 255.0f), 0, 1);
        float green = clamp((original.getGreen() / 255.0f) + (otherColor.getGreen() / 255.0f), 0, 1);
        float blue = clamp((original.getBlue() / 255.0f) + (otherColor.getBlue() / 255.0f), 0, 1);
        return new Color(red, green, blue);
    }


}
