/**
 * [1968] - [2020] Centros Culturales de Mexico A.C / Universidad Panamericana
 * All Rights Reserved.
 */
package up.edu.isgc.raytracer;

import up.edu.isgc.raytracer.lights.DirectionalLight;
import up.edu.isgc.raytracer.lights.Light;
import up.edu.isgc.raytracer.objectReader.Reader;
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

        scene01.setCamera(new Camera(new Vector3D(0, 0, -10), 160, 160, 1000, 1000 , -5.7f, 50f));
        scene01.addLight(new DirectionalLight(Vector3D.ZERO(), new Vector3D(-1.0, -1.0, 1.0), Color.WHITE, 1.1));
        // Sphere
        scene01.addObject(new Sphere(new Vector3D(-8, -8, 5), 0.5f, Color.GREEN));
        scene01.addObject(new Sphere(new Vector3D(8, 8, 5), 0.5f, Color.PINK));
        scene01.addObject(new Sphere(new Vector3D(4, 3, 1), 0.5f, Color.MAGENTA));
        scene01.addObject(new Sphere(new Vector3D(-5, -2, 0), 0.5f, Color.YELLOW));

        /**
         * You could choose diferent colors for the object
         */

        for (int i = 0 ; i < Files.length; i++){
            System.out.println("Choose a color for your object");
            System.out.println("Object Name -> " + Files[i].getName());
            System.out.println("1 -> RED \n2 -> BLUE \n3 -> PINK \n4 -> ORANGE \n5 -> GREEN \n6 -> MAGENTA");

            color = scan.nextInt();

            if(color == 1){
                // Polygon - Triangle - Object
                scene01.addObject(new Polygons(new Vector3D(cont,cont,0), PolygonsCreator.FacesAndVectorsOrganizer(Files[i]), Color.RED));
            }else if(color == 2){
                // Polygon - Triangle - Object
                scene01.addObject(new Polygons(new Vector3D(cont,cont,0), PolygonsCreator.FacesAndVectorsOrganizer(Files[i]), Color.BLUE));
            }else if(color == 3){
                // Polygon - Triangle - Object
                scene01.addObject(new Polygons(new Vector3D(cont,cont,0),  PolygonsCreator.FacesAndVectorsOrganizer(Files[i]), Color.PINK));
            }else if(color == 4){
                // Polygon - Triangle - Object
                scene01.addObject(new Polygons(new Vector3D(cont,cont,0),  PolygonsCreator.FacesAndVectorsOrganizer(Files[i]), Color.ORANGE));
            }else if(color == 5){
                // Polygon - Triangle - Object
                scene01.addObject(new Polygons(new Vector3D(cont,cont,0),  PolygonsCreator.FacesAndVectorsOrganizer(Files[i]), Color.GREEN));
            }else if(color == 6){
                // Polygon - Triangle - Object
                scene01.addObject(new Polygons(new Vector3D(cont,cont,0),  PolygonsCreator.FacesAndVectorsOrganizer(Files[i]), Color.MAGENTA));
            }else{
                scene01.addObject(new Polygons(new Vector3D(cont,cont,0),  PolygonsCreator.FacesAndVectorsOrganizer(Files[i]), Color.YELLOW));
            }
            cont = cont + 2;
        }

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
        ArrayList<Light> lights = scene.getLights();
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
                if (closestIntersection != null) {
                    pixelColor = Color.BLACK;
                    for (Light light : lights) {
                        float nDotL = light.getNDotL(closestIntersection);
                        float intensity = (float) light.getIntensity() * nDotL;
                        Color lightColor = light.getColor();
                        Color objColor = closestIntersection.getObject().getColor();
                        float[] lightColors = new float[]{lightColor.getRed() / 255.0f, lightColor.getGreen() / 255.0f, lightColor.getBlue() / 255.0f};
                        float[] objColors = new float[]{objColor.getRed() / 255.0f, objColor.getGreen() / 255.0f, objColor.getBlue() / 255.0f};
                        for (int colorIndex = 0; colorIndex < objColors.length; colorIndex++) {
                            objColors[colorIndex] *= intensity * lightColors[colorIndex];
                        }
                        Color diffuse = new Color(clamp(objColors[0], 0, 1),clamp(objColors[1], 0, 1),clamp(objColors[2], 0, 1));
                        pixelColor = addColor(pixelColor, diffuse);
                    }
                }
                image.setRGB(i, j, pixelColor.getRGB());
            }
        }

        return image;
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
