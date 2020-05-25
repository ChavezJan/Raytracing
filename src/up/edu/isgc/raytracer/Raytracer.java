/**
 * [1968] - [2020] Centros Culturales de Mexico A.C / Universidad Panamericana
 * All Rights Reserved.
 */
package up.edu.isgc.raytracer;

import up.edu.isgc.raytracer.lights.DirectionalLight;
import up.edu.isgc.raytracer.lights.Light;
import up.edu.isgc.raytracer.lights.PointLight;
import up.edu.isgc.raytracer.objectReader.Reader;
import up.edu.isgc.raytracer.objects.*;
import up.edu.isgc.raytracer.tools.Multithread;
import up.edu.isgc.raytracer.tools.Raycast;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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
        float cont = -4;

        float Ambient;
        float specular;
        float shininess;
        float diffuse;


        scene01.setCamera(new Camera(new Vector3D(0, 0, -10), 160, 160, 1000, 1000 , -5.7f, 50f));
        scene01.addLight(new PointLight(new Vector3D(0f, 2f, -2f), Color.WHITE,5,.2f, 32f,3.1f,1));
        //scene01.addLight(new PointLight(new Vector3D(-4f, 2f, -3f), Color.WHITE,1,.2f, 20f,3.1f,1));
        //scene01.addLight(new PointLight(new Vector3D(4f, 2f, -3f), Color.WHITE,1,.2f, 20f,3.1f,1));

        //scene01.addLight(new DirectionalLight(Vector3D.ZERO(), new Vector3D(-1.0, -1.0, 1.0), Color.WHITE, 1.1));
        // Sphere
        scene01.addObject(new Sphere(new Vector3D(-8, -8, 5), 0.5f, Color.GREEN,.2f,32,2.1f,1));
        scene01.addObject(new Sphere(new Vector3D(8, 8, 5), 0.5f, Color.PINK,.2f,32,2.1f,1));
        scene01.addObject(new Sphere(new Vector3D(4, 3, 1), 0.5f, Color.MAGENTA,.2f,32,2.1f,1));
        scene01.addObject(new Sphere(new Vector3D(-5, -2, 0), 0.5f, Color.YELLOW,.2f,32,2.1f,1));

        /**
         * You could choose diferent colors for the object
         */

        for (int i = 0 ; i < Files.length; i++){
            System.out.println("Choose a color for your object");
            System.out.println("Object Name -> " + Files[i].getName());

            System.out.println("Select the Ambient (Float)");
            Ambient = scan.nextFloat();
            System.out.println("Select the shininess (Float)");
            shininess = scan.nextFloat();
            System.out.println("Select the specular (Float)");
            specular = scan.nextFloat();
            System.out.println("Select the diffuse (Float)");
            diffuse = scan.nextFloat();


            System.out.println("1 -> RED \n2 -> BLUE \n3 -> PINK \n4 -> ORANGE \n5 -> GREEN \n6 -> MAGENTA\nAny other number it will be Yellow");

            color = scan.nextInt();
            String path = Files[i].getAbsolutePath();

            // Polygon - Triangle - Object
            if(color == 1){
                scene01.addObject(Reader.GetPolygon(path, new Vector3D(cont,-2,0),Color.RED, Ambient,shininess,specular, diffuse));
            }else if(color == 2){
                scene01.addObject(Reader.GetPolygon(path, new Vector3D(cont,-2,0),Color.BLUE, Ambient,shininess,specular, diffuse));
            }else if(color == 3){
                scene01.addObject(Reader.GetPolygon(path, new Vector3D(cont,-2,0),Color.PINK, Ambient,shininess,specular, diffuse));
            }else if(color == 4){
                scene01.addObject(Reader.GetPolygon(path, new Vector3D(cont,-2,0),Color.ORANGE, Ambient,shininess,specular, diffuse));
            }else if(color == 5){
                scene01.addObject(Reader.GetPolygon(path, new Vector3D(cont,-2,0),Color.GREEN, Ambient,shininess,specular, diffuse));
            }else if(color == 6) {
                scene01.addObject(Reader.GetPolygon(path, new Vector3D(cont,-2, 0), Color.MAGENTA, Ambient,shininess,specular, diffuse));
            }else{
                scene01.addObject(Reader.GetPolygon(path, new Vector3D(cont,-2,0),Color.YELLOW, Ambient,shininess,specular, diffuse));
            }
            cont = (float) (cont + 2.5);
        }
        scene01.addObject(Reader.GetPolygon("ground.obj", new Vector3D(0,-4,0),Color.WHITE,.2f, 32f, 3.1f,1f));
        System.out.println(new Date());




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

        ExecutorService executorService = Executors.newFixedThreadPool(12);


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
                Runnable runnable = Multithread.getColor(ray,i,j,mainCamera,objects,nearFarPlanes,lights,image);
                executorService.execute(runnable);

            }
        }
        executorService.shutdown();
        try {
            if(!executorService.awaitTermination(5, TimeUnit.MINUTES)) {
                executorService.shutdownNow();
            }
        } catch(InterruptedException e) {
            executorService.shutdownNow();
        } finally {
            if(!executorService.isTerminated()) {
                System.err.println("cancel non-finished");
            }
        }
        executorService.shutdownNow();
        return image;
    }

    public static Color getObjectColor(Intersection intersection, Light light, Color pixelColor, Camera camera, ArrayList<Object3D> objects, float[] nearFarPlanes){
        double distance =  Vector3D.magnitude(Vector3D.substract(intersection.getPosition(), light.getPosition()));
        Object3D object = intersection.getObject();
        float intensity = getIntensity(intersection, light);
        float [] colors = getAmbient(object);
        Color ambient = new Color(clamp(colors[0], 0, 1), clamp(colors[1], 0, 1), clamp(colors[2], 0, 1));
        pixelColor = addColor(pixelColor, ambient);

        if (light instanceof DirectionalLight) {
            distance = 1;
        }

        colors = getDiffuse(colors, intensity, distance, light);
        distance =  Vector3D.magnitude(Vector3D.substract(intersection.getPosition(), light.getPosition()));
        Color diffuse = new Color(clamp(colors[0], 0, 1), clamp(colors[1], 0, 1), clamp(colors[2], 0, 1));
        Color specular = specular(light, intersection, colors, object);
        Intersection lightIntersection = getHit(camera, nearFarPlanes, objects, intersection.getPosition(), light.getPosition());

        if (lightIntersection != null){
            double distObj = Vector3D.magnitude(Vector3D.substract(lightIntersection.getPosition(), light.getPosition()));
            if (lightIntersection.getObject() == intersection.getObject()) {
                pixelColor = addColor(pixelColor, diffuse);
                pixelColor = addColor(pixelColor, specular );
            }
            else if (distance < distObj) {
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

    private static Intersection getHit(Camera mainCamera, float[] nearFarPlanes, ArrayList<Object3D> objects, Vector3D direction, Vector3D start) {
        Ray ray = new Ray(start, direction);
        Intersection hit = Raycast.raycast(ray, objects, null, new float[] {(float) mainCamera.getPosition().getZ() + nearFarPlanes[0], (float) mainCamera.getPosition().getZ() + nearFarPlanes[1]} );
        return hit;
    }

    private static float [] getAmbient(Object3D obj){
        return new float[] { (obj.getColor().getRed()/ 255.0f* obj.getAmbient()),
                (obj.getColor().getGreen()/ 255.0f * obj.getAmbient()),
                (obj.getColor().getBlue()/ 255.0f* obj.getAmbient())};
    }

    private static float getIntensity(Intersection inter, Light light){
        return (float) light.getIntensity() * light.getNDotL(inter);
    }
    private static float [] getDiffuse(float [] colors, float intensity, double distance, Light light){
        colors[0] *= (intensity/Math.pow(distance, 2)) * (light.getColor().getRed() / 255.0f) ;
        colors[1] *= (intensity/Math.pow(distance, 2)) * (light.getColor().getGreen() / 255.0f);
        colors[2] *= (intensity/Math.pow(distance, 2)) * (light.getColor().getBlue() / 255.0f);
        return colors;
    }


    private static Color specular (Light light, Intersection intersection, float [] colors, Object3D obj){
        Vector3D halfVector = Vector3D.add(light.getPosition(), intersection.getPosition());
        halfVector = Vector3D.normalize(halfVector);
        //Vector3D h = Vector3D.scalarMultiplication(l_v, 1.0f / Vector3D.magnitude(l_v));
        float specular = (float) Math.pow(Vector3D.dotProduct(intersection.getNormal(), halfVector), obj.getShininess());
        for (int color = 0; color < 3; color++) colors[color] *= specular * obj.getSpecular();
        Color specular_ = new Color(clamp(colors[0], 0, 1), clamp(colors[1], 0, 1), clamp(colors[2], 0, 1));
        return specular_;
    }



    public static Color calculateRefraction(Intersection intersection, Light light, ArrayList<Object3D> objects, Camera camera, float[] nearFarPlanes){
        Object3D obj = intersection.getObject();
        Vector3D position = intersection.getPosition();

        Vector3D normal = intersection.getNormal();
        Vector3D newPosition = Vector3D.scalarMultiplication(normal, 1.5);
        float nr = obj.getRefraction();
        float reflectance = (float) (Math.pow(nr-1, 2) / Math.pow(nr+1, 2));

        if (reflectance > 0 ) {
            position = Vector3D.add(position, newPosition);
        }
        else {
            position = Vector3D.substract(position, newPosition);
        }

        Color refractColor = Color.BLACK;

        Vector3D incidenRay = Vector3D.substract(position, camera.getPosition()); //view vector
        double cos_theta = clamp(-1.0f, 1.0f,(float) Vector3D.dotProduct(incidenRay, normal));

        Vector3D t = Vector3D.ZERO();

        double c2 = Math.sqrt(1 - Math.pow(nr, 2)* (1 - Math.pow(cos_theta, 2)));
        double c1 = (nr)*cos_theta;
        if (c2 > 0) {
            t = Vector3D.add(Vector3D.scalarMultiplication(incidenRay, nr), Vector3D.scalarMultiplication(normal, c1-c2 ));
        }

        Intersection refractIntersect = null;
        Ray reflectHit = new Ray(position, t);
        if (intersection.getGroup() != null) {
            refractIntersect = Raycast.raycast(reflectHit, objects, intersection.getGroup(), new float[] {(float) camera.getPosition().getZ() + nearFarPlanes[0]-8, (float) camera.getPosition().getZ() + nearFarPlanes[1]-8});
        }
        else {
            refractIntersect = Raycast.raycast(reflectHit, objects, intersection.getObject(), new float[] {(float) camera.getPosition().getZ() + nearFarPlanes[0]-8, (float) camera.getPosition().getZ() + nearFarPlanes[1]-8});
        }

        if (refractIntersect != null && refractIntersect.getObject().getRefraction() == 0f){
            refractColor = getObjectColor(refractIntersect, light, refractColor, camera, objects, new float []{nearFarPlanes[0]-3, nearFarPlanes[1] -3});
            if (refractIntersect.getObject().getReflection() > 0) {
                refractColor = addColor(refractColor, calculateReflection(refractIntersect, light, objects, camera, nearFarPlanes));
            }
        }

        return refractColor;
    }

    public static Color calculateReflection(Intersection intersection, Light light, ArrayList<Object3D> objects, Camera camera, float[] nearFarPlanes){
        Vector3D incidenRay = Vector3D.substract(intersection.getPosition(), camera.getPosition());
        double normalRay = -2.0 * Vector3D.dotProduct(intersection.getNormal(), incidenRay);
        Vector3D reflection = Vector3D.scalarMultiplication(intersection.getNormal(), normalRay);
        Color reflectColor = Color.BLACK;
        reflection = Vector3D.add(incidenRay, reflection);

        Ray reflectHit = new Ray(intersection.getPosition(), reflection);
        Intersection reflectIntersect = Raycast.raycast(reflectHit, objects, null, new float[] {(float) camera.getPosition().getZ() + nearFarPlanes[0]-8, (float) camera.getPosition().getZ() + nearFarPlanes[1]-8});
        if (reflectIntersect != null && intersection.getObject() != reflectIntersect.getObject()){
            reflectColor = getObjectColor(reflectIntersect, light, reflectColor, camera, objects, new float []{nearFarPlanes[0] - 8, nearFarPlanes[1] -8});
            if (reflectIntersect.getObject().getRefraction() != 0) {
                reflectColor = addColor(reflectColor, calculateRefraction(reflectIntersect, light, objects, camera, nearFarPlanes));
            }
            else if (reflectIntersect.getObject().getReflection() != 0) {
                reflectColor = addColor(reflectColor, calculateReflection(reflectIntersect, light, objects, camera, nearFarPlanes));
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
