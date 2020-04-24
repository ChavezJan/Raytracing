package up.edu.isgc.raytracer;

import up.edu.isgc.raytracer.objects.Camera;
import up.edu.isgc.raytracer.objects.Object3D;
import up.edu.isgc.raytracer.objects.Sphere;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class Raytracer {
    /**
     * Main, here is were we put the objects and run the scene
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(new Date());
        Scene scene01 = new Scene();
        scene01.setCamera(new Camera(new Vector3D(0, 0, -8), 160, 160, 800, 800));
        scene01.addObject(new Sphere(new Vector3D(.5, .5, -3.6), 0.5f, Color.RED));
        scene01.addObject(new Sphere(new Vector3D(-.5, -.5, 2.4), 0.5f, Color.BLUE));
        scene01.addObject(new Sphere(new Vector3D(.5, -.5, -3), 0.5f, Color.GREEN));
        scene01.addObject(new Sphere(new Vector3D(-.5, .5, -3), 0.5f, Color.WHITE));

        BufferedImage image = raytrace(scene01);
        File outputImage = new File("image.png");
        try{
            ImageIO.write(image, "png", outputImage);
        } catch (IOException ioe){
            System.out.println("Something failed");
        }
        System.out.println(new Date());
    }

    public static BufferedImage raytrace(Scene scene) {
        Camera mainCamera = scene.getCamera();
        BufferedImage image = new BufferedImage(mainCamera.getResolutionWidth(), mainCamera.getResolutionHeight(), BufferedImage.TYPE_INT_RGB);
        ArrayList<Object3D> objects = scene.getObjects();

        Vector3D[][] positionsToRaytrace = mainCamera.calculatePositionsToRay();
        for (int i = 0; i < positionsToRaytrace.length; i++) {
            for (int j = 0; j < positionsToRaytrace[i].length; j++) {
                double x = positionsToRaytrace[i][j].getX() + mainCamera.getPosition().getX();
                double y = positionsToRaytrace[i][j].getY() + mainCamera.getPosition().getY();
                double z = positionsToRaytrace[i][j].getZ() + mainCamera.getPosition().getZ();

                Ray ray = new Ray(mainCamera.getPosition(), new Vector3D(x, y, z));
                Intersection closestIntersection = raycast(ray, objects, null);

                //Background color
                Color pixelColor = Color.BLACK;
                if(closestIntersection != null && closestIntersection.getDistance() >= 4f && closestIntersection.getDistance() <= 10 ){
                    pixelColor = closestIntersection.getObject().getColor();
                }
                image.setRGB(i, j, pixelColor.getRGB());
            }
        }

        return image;
    }

    public static Intersection raycast(Ray ray, ArrayList<Object3D> objects, Object3D caster){
        Intersection closestIntersection = null;

        for(int k = 0; k < objects.size(); k++){
            Object3D currentObj = objects.get(k);
            if(caster == null || !currentObj.equals(caster)){
                Intersection intersection = currentObj.getIntersection(ray);
                if(intersection != null){
                    double distance = intersection.getDistance();
                    if(distance >= 0 && (closestIntersection == null || distance < closestIntersection.getDistance())){
                        closestIntersection = intersection;
                    }
                }
            }
        }

        return closestIntersection;
    }
}
