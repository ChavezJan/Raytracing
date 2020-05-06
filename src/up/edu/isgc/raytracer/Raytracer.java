/**
 * [1968] - [2020] Centros Culturales de Mexico A.C / Universidad Panamericana
 * All Rights Reserved.
 */
package up.edu.isgc.raytracer;

import up.edu.isgc.raytracer.objects.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

/**
 * @author ChavezJan
 * @author Jafet Rodríguez
 */
public class Raytracer {

    /**
     * Generate the Scenes adding the Objects
     * Saved the Image "image.png"
     *
     * @param Files -> all the files that the user choose
     * @throws IOException
     */
    public static void initialRaytracer(File[] Files) throws IOException {
        Scanner scan = new Scanner(System.in);
        Scene scene01 = new Scene();
        int color;
        int cont = -4;
        for (int i = 0 ; i < Files.length; i++){
            System.out.println("Choose a color for your object");
            System.out.println("Object Name -> " + Files[i].getName());
            System.out.println("1 -> RED \n2 -> BLUE \n3 -> PINK \n4 -> ORANGE \n5 -> GREEN \n6 -> MAGENTA");

            color = scan.nextInt();

            if(color == 1){
                // Polygon - Triangle - Object
                scene01.addObject(new Polygons(new Vector3D(cont,cont,0), PolygonsCreator.FacesAndVectorsOrganizer(Files, i), Color.RED));
            }else if(color == 2){
                // Polygon - Triangle - Object
                scene01.addObject(new Polygons(new Vector3D(cont,cont,0), PolygonsCreator.FacesAndVectorsOrganizer(Files, i), Color.BLUE));
            }else if(color == 3){
                // Polygon - Triangle - Object
                scene01.addObject(new Polygons(new Vector3D(cont,cont,0), PolygonsCreator.FacesAndVectorsOrganizer(Files, i), Color.PINK));
            }else if(color == 4){
                // Polygon - Triangle - Object
                scene01.addObject(new Polygons(new Vector3D(cont,cont,0), PolygonsCreator.FacesAndVectorsOrganizer(Files, i), Color.ORANGE));
            }else if(color == 5){
                // Polygon - Triangle - Object
                scene01.addObject(new Polygons(new Vector3D(cont,cont,0), PolygonsCreator.FacesAndVectorsOrganizer(Files, i), Color.GREEN));
            }else if(color == 6){
                // Polygon - Triangle - Object
                scene01.addObject(new Polygons(new Vector3D(cont,cont,0), PolygonsCreator.FacesAndVectorsOrganizer(Files, i), Color.MAGENTA));
            }


            cont = cont + 2;
        }

        scene01.setCamera(new Camera(new Vector3D(0, 0, -10), 160, 160, 1000, 1000 , -5.7f, 50f));
        // Sphere
        scene01.addObject(new Sphere(new Vector3D(-8, -8, 5), 0.5f, Color.GREEN));
        scene01.addObject(new Sphere(new Vector3D(8, 8, 5), 0.5f, Color.WHITE));

        //scene01.addObject(new Polygons(new Vector3D(0,0,0), Triangle , Color.RED));

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
