package up.edu.isgc.raytracer.objects;

import up.edu.isgc.raytracer.Raytracer;
import up.edu.isgc.raytracer.Vector3D;
import up.edu.isgc.raytracer.objects.Triangle;

import java.util.*;

public class PolygonsCreator {

    /**
     *
     * @param vector
     * @param faces
     */
    public static void FacesAndVectorsOrganizer(List<String> vector, List<String> faces){

        List<String> VectorOfTheFaces = new ArrayList<>();
        List<Triangle> triangle = new ArrayList<>();
        List<Vector3D> VectorsToUse = new ArrayList<>();

        ExtractFaces(faces,VectorOfTheFaces,vector, VectorsToUse);

        System.out.println("Number of Faces and Vectors");
        System.out.println("Total Faces -> " + VectorOfTheFaces.size());
        System.out.println("Total Vectors -> " + VectorsToUse.size());

        makeTriangles(triangle,VectorOfTheFaces,VectorsToUse);

        Raytracer.initialRaytracer(triangle);

    }

    /**
     *
     *
     * @param strings
     * @param faces
     * @param vectorsToUse
     * @return
     */
    public static List<Vector3D> ExtractFaces(List<String> faces, List<String> VectorOfTheFaces, List<String> Vector, List<Vector3D> vectorsToUse){

        int size = -1;

        for(int i = 0; i < faces.size(); i++){


            String[] CleanText = faces.get(i).split("#");
            String[] FaceOrder = CleanText[0].split(" ");
            size = FaceOrder.length;

            if(size == 4) {

                String[] Vector1 = FaceOrder[1].split("/");
                String[] Vector2 = FaceOrder[2].split("/");
                String[] Vector3 = FaceOrder[3].split("/");

                VectorOfTheFaces.add(Vector1[0] + " " + Vector2[0] + " " + Vector3[0]);

            }else if(size == 5){

                String[] Vector1 = FaceOrder[1].split("/");
                String[] Vector2 = FaceOrder[2].split("/");
                String[] Vector3 = FaceOrder[3].split("/");
                String[] Vector4 = FaceOrder[4].split("/");

                //System.out.println(Vector1[0] + " " + Vector2[0] + " " + Vector3[0] + " " + Vector4[0]);

                VectorOfTheFaces.add(Vector1[0] + " " + Vector2[0] + " " + Vector4[0]);
                //System.out.println(VectorOfTheFaces.get(i));
                VectorOfTheFaces.add(Vector2[0] + " " + Vector3[0] + " " + Vector4[0]);
                //System.out.println(VectorOfTheFaces.get(i+1));
            }else{
                System.out.println("System Error");
            }
        }
        return ExtractVectors(Vector,vectorsToUse, size);
    }

    /**
     *
     *
     * @param strings
     * @param vector
     * @param VectorsToUse
     * @param size
     * @return
     */
    private static List<Vector3D> ExtractVectors(List<String> vector, List<Vector3D> VectorsToUse, int size){

        Float x,y,z;

        if(size == 4) {
            for (int i = 0; i < vector.size(); i++) {

                String[] VectorPosition = vector.get(i).split(" ");
                String[] LastVector = VectorPosition[4].split("#");

                x = Float.valueOf(VectorPosition[2]);
                y = Float.valueOf(VectorPosition[3]);
                z = Float.valueOf(LastVector[0]);

                VectorsToUse.add(new Vector3D(x, y, z));
            }
        }else if(size == 5){
            for (int i = 0; i < vector.size(); i++) {

                String[] VectorPosition = vector.get(i).split(" ");
                String[] LastVector = VectorPosition[3].split("#");

                x = Float.valueOf(VectorPosition[1]);
                y = Float.valueOf(VectorPosition[2]);
                z = Float.valueOf(LastVector[0]);

                VectorsToUse.add(new Vector3D(x, y, z));
            }
        }

        return VectorsToUse;
    }

    /**
     *
     * @param triangle
     * @param VectorOfTheFaces
     * @param VectorsToUse
     * @return
     */
    private static List<Triangle> makeTriangles(List<Triangle> triangle, List<String> VectorOfTheFaces, List<Vector3D> VectorsToUse){

        int point1,point2,point3;

        for (int i = 0 ; i < VectorOfTheFaces.size(); i++) {

            String[] vertex = VectorOfTheFaces.get(i).split(" ");
            point1 = Integer.parseInt(vertex[0]);
            point2 = Integer.parseInt(vertex[1]);
            point3 = Integer.parseInt(vertex[2]);

            triangle.add( new Triangle(VectorsToUse.get((point1)-1), VectorsToUse.get((point2)-1), VectorsToUse.get((point3)-1)) );
        }

        return triangle;
    }

}


