package up.edu.isgc.raytracer;

import up.edu.isgc.raytracer.objects.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class Raytracer {

    /**
     * Generate the Scenes adding the Objects
     *
     * Saved the Image "image.png"
     */

    public static void initialRaytracer() {
        System.out.println(new Date());
        Scene scene01 = new Scene();
        scene01.setCamera(new Camera(new Vector3D(0, 0, -8), 160, 160, 800, 800 , .7f, 50f));
        // Sphere
        scene01.addObject(new Sphere(new Vector3D(.5, -.5, 7.5), 0.5f, Color.GREEN));
        scene01.addObject(new Sphere(new Vector3D(-.5, .5, 5), 0.5f, Color.WHITE));
        // Polygon - Triangle
        scene01.addObject(new Polygons(new Vector3D(-1,-1,2), new Triangle[]{ new Triangle(Vector3D.ZERO(), new Vector3D(1,0,0), new Vector3D(1,-1,0)),  new Triangle(Vector3D.ZERO(), new Vector3D(2,0,0), new Vector3D(1,-1,0))}, Color.BLUE));

        
        BufferedImage image = raytrace(scene01);
        File outputImage = new File("image.png");
        try{
            ImageIO.write(image, "png", outputImage);
        } catch (IOException ioe){
            System.out.println("Something failed");
        }
        System.out.println(new Date());
    }

    /**
     * Generate the Buffered Image ready to be saved
     *
     * @param scene is loaded with objects and the camera
     * @return the loaded Image
     */

    public static BufferedImage raytrace(Scene scene) {

        Camera mainCamera = scene.getCamera();
        float[] nearFarPlanes = mainCamera.getNearFarPlane();
        BufferedImage image = new BufferedImage(mainCamera.getResolutionWidth(), mainCamera.getResolutionHeight(), BufferedImage.TYPE_INT_RGB);
        ArrayList<Object3D> objects = scene.getObjects();

        Vector3D[][] positionsToRaytrace = mainCamera.calculatePositionsToRay();
        for (int i = 0; i < positionsToRaytrace.length; i++) {
            for (int j = 0; j < positionsToRaytrace[i].length; j++) {
                double x = positionsToRaytrace[i][j].getX() + mainCamera.getPosition().getX();
                double y = positionsToRaytrace[i][j].getY() + mainCamera.getPosition().getY();
                double z = positionsToRaytrace[i][j].getZ() + mainCamera.getPosition().getZ();

                Ray ray = new Ray(mainCamera.getPosition(), new Vector3D(x, y, z));
                float cameraZ = (float)mainCamera.getPosition().getZ();
                Intersection closestIntersection = raycast(ray, objects, null, new float[]{cameraZ + nearFarPlanes[0], cameraZ + nearFarPlanes[1]});

                //Background color
                Color pixelColor = Color.BLACK;
                if(closestIntersection != null){
                    pixelColor = closestIntersection.getObject().getColor();
                }
                image.setRGB(i, j, pixelColor.getRGB());
            }
        }

        return image;
    }

    /**
     * Calculate the distance to get the closest intersection
     *
     * @param ray
     * @param objects
     * @param caster = Null
     * @return closest intersection between the camera and the spheres
     */

    public static Intersection raycast(Ray ray, ArrayList<Object3D> objects, Object3D caster, float[] ClippingPlanes){
        Intersection closestIntersection = null;

        for(int k = 0; k < objects.size(); k++){
            Object3D currentObj = objects.get(k);
            if(caster == null || !currentObj.equals(caster)){
                Intersection intersection = currentObj.getIntersection(ray);
                if(intersection != null){
                    double distance = intersection.getDistance();
                    if(distance >= 0 && (closestIntersection == null || distance < closestIntersection.getDistance()) && (ClippingPlanes == null || (intersection.getPosition().getZ() >= ClippingPlanes[0] && intersection.getPosition().getZ() <= ClippingPlanes[1]))) {
                        closestIntersection = intersection;
                    }
                }
            }
        }

        return closestIntersection;
    }
}
