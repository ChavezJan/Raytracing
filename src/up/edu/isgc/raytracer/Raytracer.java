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
        int color, Rotate;
        double Scale;
        float cont = -4;

        scene01.setCamera(new Camera(new Vector3D(0, 0, -10), 160, 160, 2000, 2000 , -5.7f, 50f));
        scene01.addLight(new PointLight(new Vector3D(2f, 2f, -2f), Color.WHITE,4,.2f, .2f,.1f,1,0,0));
        scene01.addLight(new PointLight(new Vector3D(-2f, 2f, -2f), Color.WHITE,4,.2f, .2f,.1f,1,0,0));

        //scene01.addLight(new PointLight(new Vector3D(-4f, 2f, -3f), Color.WHITE,1,.2f, 20f,3.1f,1));
        //scene01.addLight(new PointLight(new Vector3D(4f, 2f, -3f), Color.WHITE,1,.2f, 20f,3.1f,1));

        //scene01.addLight(new DirectionalLight(Vector3D.ZERO(), new Vector3D(-1.0, -1.0, 1.0), Color.WHITE, 1.1));
        // Sphere
        scene01.addObject(new Sphere(new Vector3D(0, 2.5f, -1), 0.5f, new Color(250,50,65),.25f,4,1.2f, 1,0,1f));

        scene01.addObject(new Sphere(new Vector3D(-3, 0, -2), 0.5f,  new Color(189,236,182),.2f,4,1,1,0,2));
        scene01.addObject(new Sphere(new Vector3D(0, 1, -2), 0.5f, new Color(253,253,150),.2f,4,1f,1,0,2));
        scene01.addObject(new Sphere(new Vector3D(3, 0, -2), 0.5f, Color.RED,.2f,4,1,1,0,2));

        scene01.addObject(new Sphere(new Vector3D(4, 3, 0), 0.5f,  new Color(50,50,50),.2f,4,1,1,1,2));
        scene01.addObject(new Sphere(new Vector3D(-3, 4.5f, 0), 0.5f, new Color(50,50,50),.2f,4,1,1,1,2));
        scene01.addObject(new Sphere(new Vector3D(3, 4.5f, 0), 0.5f, new Color(50,50,50),.2f,4,1,1,1,2));
        scene01.addObject(new Sphere(new Vector3D(-4, 3, 0), 0.5f, new Color(50,50,50),.2f,4,1,1,1,2));

        scene01.addObject(new Sphere(new Vector3D(4, 1.5, 0), 0.5f,  new Color(50,50,50),.2f,4,1,1,1,2));
        scene01.addObject(new Sphere(new Vector3D(1, 4.5f, 0), 0.5f, new Color(137,207,240),.2f,4,2.5f, 1,1,1.0f));
        scene01.addObject(new Sphere(new Vector3D(-1, 4.5f, 0), 0.5f, new Color(234,137,154),.2f,4,1,1,1,2));
        scene01.addObject(new Sphere(new Vector3D(-4, 1.5, 0), 0.5f, new Color(50,50,50),.2f,4,1,1,1,2));


        /**
         * You could choose different colors for the object
         */

        for (int i = 0 ; i < Files.length; i++){
            System.out.println("Choose a color for your object");
            System.out.println("Object Name -> " + Files[i].getName());

            System.out.println("1 -> RED \n2 -> BLUE \n3 -> PINK \n4 -> ORANGE \n5 -> GREEN \n6 -> MAGENTA\n7 -> CRISTAL\n8 -> PURPLE\nAny other number it will be Yellow");

            color = scan.nextInt();

            System.out.println("Choose a scale (1 = Normal, < 1 = Smaller , > 1 = Bigger)");

            Scale = scan.nextDouble();

            System.out.println("Choose a Rotate\n1 -> Rotation Type 1\n2 -> Rotation Type 2\n3 -> Rotation Type 3\nAnother number would be type 0 rotation");

            Rotate = scan.nextInt();
            Rotate -= 1;

            String path = Files[i].getAbsolutePath();

            // Polygon - Triangle - Object
            if(color == 1){
                scene01.addObject(Reader.GetPolygon(path, new Vector3D(cont,-2,0),Color.RED,
                        .1f,20.5f,102.5f, 1,.0f,0.0f, Scale, Rotate));
            }else if(color == 2){
                scene01.addObject(Reader.GetPolygon(path, new Vector3D(cont,-2,0),Color.BLUE,
                        .2f,20,30f, 1,0,2, Scale, Rotate));
            }else if(color == 3){
                scene01.addObject(Reader.GetPolygon(path, new Vector3D(cont,-2,0),new Color(255,203,219),
                        .2f,14,30,1,0f,.0f, Scale, Rotate));
            }else if(color == 4){
                scene01.addObject(Reader.GetPolygon(path, new Vector3D(cont,-2,0),Color.ORANGE,
                        .1f,10.5f,20.5f, 1,.0f,0.0f, Scale, Rotate));
            }else if(color == 5){
                scene01.addObject(Reader.GetPolygon(path, new Vector3D(cont,-2,0),new Color(189,236,182),
                        .2f,20,80, 1,.0f,0.0f, Scale, Rotate));
            }else if(color == 6) {
                scene01.addObject(Reader.GetPolygon(path, new Vector3D(cont,-2, 0), Color.MAGENTA,
                        .1f,10.5f,20.5f, 1,.0f,0.0f, Scale, Rotate));
            }else if(color == 7) {
                scene01.addObject(Reader.GetPolygon(path, new Vector3D(cont,-2, 0), new Color(100,100,100),
                        .2f,4,1, 1,2,0, Scale, Rotate));
            }else if(color == 8) {
                scene01.addObject(Reader.GetPolygon(path, new Vector3D(cont,-2, 0), new Color(139,0,255),
                        .1f,10.5f,20.5f, 1,.0f,0.0f, Scale, Rotate));
            }else{
                scene01.addObject(Reader.GetPolygon(path, new Vector3D(cont,-2,0),Color.YELLOW,
                        .1f,10.5f,20.5f, 2,.0f,0.0f, Scale, Rotate));
            }
            cont = (float) (cont + 2.5);
        }
        scene01.addObject(Reader.GetPolygon("ground.obj", new Vector3D(0,-4,0),Color.WHITE,
                .2f, .2f, .1f,1f,2f,0,1, 0));


        scene01.addObject(Reader.GetPolygon("Object/Cube.obj", new Vector3D(-2,0,-1),new Color(119,221,119),
                .2f, .2f, .1f,1f,0,0,1.5, 0));

        scene01.addObject(Reader.GetPolygon("Object/ring.obj", new Vector3D(0,.4f,-2),new Color(212,175,55),
                .2f, 20f, 60f,1f,2,0,.8, 0));


        scene01.addObject(Reader.GetPolygon("Object/Cube.obj", new Vector3D(2,0,-1),new Color(255,26,2),
                .2f, .2f, .1f,1f,2f,2,1.5, 0));
        /*
        scene01.addObject(Reader.GetPolygon("Object/Raccon.obj", new Vector3D(2,1.2,-1),new Color(255,90,20),
                .2f, .2f, .1f,1f,0,0,.09, 3));
        scene01.addObject(Reader.GetPolygon("Object/VWBug.obj", new Vector3D(-2,.2,-1),new Color(229,241,149),
                .2f, .2f, .1f,1f,2f,2,.1f, 0));1
*/




        System.out.println(new Date());

        BufferedImage image = raytrace(scene01);

        File outputImage = new File("Ray-tracer.png");
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
            if(!executorService.awaitTermination(30, TimeUnit.MINUTES)) {
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


}
