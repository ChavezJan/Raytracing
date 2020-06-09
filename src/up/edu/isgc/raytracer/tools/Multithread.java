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
                    for (Light light : lights) {

                        Ray Shadow = new Ray(closestIntersection.getPosition(),light.getPosition());
                        Intersection ShadowRay = Raycast.raycast(Shadow,objects,closestIntersection.getObject(),new float[]{cameraZ + nearFarPlanes[0], cameraZ + nearFarPlanes[1]});

                        if(ShadowRay == null){

                            pixelColor = Colors.addColor(pixelColor, Colors.ObjectColor(closestIntersection, light, pixelColor, mainCamera, objects, nearFarPlanes));

                            if (closestIntersection.getObject().getRefraction() > 0)
                                pixelColor = Colors.addColor(pixelColor, Colors.Refraction(closestIntersection, light, objects, mainCamera, nearFarPlanes));
                            if (closestIntersection.getObject().getReflection() > 0)
                                pixelColor = Colors.addColor(pixelColor, Colors.Reflection(closestIntersection, light, objects, mainCamera, nearFarPlanes));

                        }
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
        image.setRGB(i, j, pixelColor.getRGB());}
}
