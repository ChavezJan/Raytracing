/**
 * [1968] - [2020] Centros Culturales de Mexico A.C / Universidad Panamericana
 * All Rights Reserved.
 */
package up.edu.isgc.raytracer.tools;

import up.edu.isgc.raytracer.Intersection;
import up.edu.isgc.raytracer.Ray;
import up.edu.isgc.raytracer.Raytracer;
import up.edu.isgc.raytracer.Vector3D;
import up.edu.isgc.raytracer.lights.Light;
import up.edu.isgc.raytracer.objects.Camera;
import up.edu.isgc.raytracer.objects.Object3D;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * @author ChavezJan
 * @author Jafet Rodríguez
 */
public class Multithread {
    /**
     * adding multi threads to de code, painting the pixel
     *
     * @param ray
     * @param i
     * @param j
     * @param mainCamera
     * @param objects
     * @param nearFarPlanes
     * @param lights
     * @param image
     * @return image with the pixels painted
     */

    public static Runnable getColor(Ray ray, int i, int j, Camera mainCamera, ArrayList<Object3D> objects, float [] nearFarPlanes, ArrayList<Light> lights, BufferedImage image) {
        Runnable aRunnable = new Runnable() {
            public void run () {
                float cameraZ = (float) mainCamera.getPosition().getZ();
                Intersection closestIntersection = Raycast.raycast(ray, objects, null, new float[]{cameraZ + nearFarPlanes[0], cameraZ + nearFarPlanes[1]});

                //Background color
                Color pixelColor = Color.BLACK;
                if (closestIntersection != null) {
                    pixelColor = Color.BLACK;
                    for (Light light : lights) {

                        pixelColor = Raytracer.addColor(pixelColor, Raytracer.getObjectColor(closestIntersection, light, pixelColor, mainCamera, objects, nearFarPlanes));
                        if (closestIntersection.getObject().getReflection() > 0)
                            pixelColor = Raytracer.addColor(pixelColor, Raytracer.calculateReflection(closestIntersection, light, objects, mainCamera, nearFarPlanes));
                        if (closestIntersection.getObject().getRefraction() > 0){
                            pixelColor = Raytracer.addColor(pixelColor, Raytracer.calculateRefraction(closestIntersection, light, objects, mainCamera, nearFarPlanes));
                        }

                        double distance = Vector3D.magnitude(Vector3D.substract(closestIntersection.getPosition(), light.getPosition()));

                        float nDotL = light.getNDotL(closestIntersection);
                        float intensity = (float) light.getIntensity() * nDotL;
                        Color lightColor = light.getColor();
                        Color objColor = closestIntersection.getObject().getColor();
                        float[] lightColors = new float[]{lightColor.getRed() / 255.0f, lightColor.getGreen() / 255.0f, lightColor.getBlue() / 255.0f};
                        float[] objColors = new float[]{objColor.getRed() / 255.0f, objColor.getGreen() / 255.0f, objColor.getBlue() / 255.0f};
                        for (int colorIndex = 0; colorIndex < objColors.length; colorIndex++) {
                            objColors[colorIndex] *= (intensity / (Math.pow(distance, 2))) * lightColors[colorIndex];
                        }
                        Color diffuse = new Color(Raytracer.clamp(objColors[0], 0, 1), Raytracer.clamp(objColors[1], 0, 1), Raytracer.clamp(objColors[2], 0, 1));
                        pixelColor = Raytracer.addColor(pixelColor, diffuse);
                    }
                }
                setRGB(image,i,j,pixelColor);
            }
        };
        return aRunnable;
    }

    /**
     * adding synchronized to the code, painting the pixels
     *
     * @param image
     * @param i
     * @param j
     * @param pixelColor
     */
    public static synchronized void setRGB(BufferedImage image, int i, int j, Color pixelColor) {
        image.setRGB(i, j, pixelColor.getRGB());    }
}
