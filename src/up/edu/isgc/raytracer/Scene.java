package up.edu.isgc.raytracer;

import up.edu.isgc.raytracer.objects.Camera;
import up.edu.isgc.raytracer.objects.Object3D;

import java.util.ArrayList;
import java.util.List;

public class Scene {

    private Camera camera;
    private ArrayList<Object3D> objects;

    public Scene(){
        setObjects(new ArrayList<Object3D>());
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public void addObject(Object3D object){
        getObjects().add(object);
    }

    public ArrayList<Object3D> getObjects() {
        return objects;
    }

    public void setObjects(ArrayList<Object3D> objects) {
        this.objects = objects;
    }
}