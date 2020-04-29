package up.edu.isgc.raytracer;

import up.edu.isgc.raytracer.objects.Camera;
import up.edu.isgc.raytracer.objects.Object3D;

import java.util.ArrayList;
import java.util.List;

public class Scene {

    private Camera camera;
    private ArrayList<Object3D> objects;

    /**
     * It is the constructor of Scene
     */

    public Scene(){
        setObjects(new ArrayList<Object3D>());
    }

    /**
     * Add the Objects to the scene
     *
     * @param object
     */

    public void addObject(Object3D object){
        getObjects().add(object);
    }

    //getter and setter

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public ArrayList<Object3D> getObjects() {
        return objects;
    }

    public void setObjects(ArrayList<Object3D> objects) {
        this.objects = objects;
    }
}
