/**
 * [1968] - [2020] Centros Culturales de Mexico A.C / Universidad Panamericana
 * All Rights Reserved.
 */
package up.edu.isgc.raytracer.objects;

import up.edu.isgc.raytracer.Vector3D;
import up.edu.isgc.raytracer.objectReader.Reader;

import java.io.File;
import java.io.IOException;
import java.util.*;
/**
 * @author ChavezJan
 * @author Jafet Rodríguez
 */
public class PolygonsCreator {

    /**
     * Calls the necessary functions to accommodate the vectors, faces, and create the triangles
     *
     * @param Files
     * @param i -> Number of the File
     * @return The Triangles array
     * @throws IOException
     */
    public static Triangle[] FacesAndVectorsOrganizer(File[] Files, int i) throws IOException {

        List<String> Vector = new ArrayList<>();
        List<String> Normal = new ArrayList<>();
        List<String> Faces = new ArrayList<>();

        Reader.objectReader(Files, Vector, Normal, Faces, i);

        List<String> VectorOfTheFaces = new ArrayList<>();
        List<Triangle> triangle = new ArrayList<>();
        List<Vector3D> VectorsToUse = new ArrayList<>();

        ExtractFaces(Faces,VectorOfTheFaces,Vector, VectorsToUse);

        System.out.println("Number of Faces and Vectors");
        System.out.println("Total Faces -> " + VectorOfTheFaces.size());
        System.out.println("Total Vectors -> " + VectorsToUse.size());

        return makeTriangles(triangle, VectorOfTheFaces, VectorsToUse);

    }

    /**
     * extract the important parts of the faces (the points that connect the vectors with the faces),
     * then send the function to extract the vectors to be able to accommodate them
     *
     * @param faces -> list of all the faces
     * @param VectorOfTheFaces -> empty list made to save de important data of the faces
     * @param Vector -> list of the vectors
     * @param vectorsToUse -> empty Vectors3D list to save the vectors
     * @return -> ExtractVectors(Vector,vectorsToUse, size) -> return -> VectorsToUse -> Vectors3D List that we will use to create the triangle
     * @return -> VectorOfTheFaces -> List of the Vectors we need to create the Triangles
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

                VectorOfTheFaces.add(Vector1[0] + " " + Vector2[0] + " " + Vector4[0]);
                VectorOfTheFaces.add(Vector2[0] + " " + Vector3[0] + " " + Vector4[0]);

            }else System.out.println("System Error");

        }

        return ExtractVectors(Vector,vectorsToUse, size);
    }

    /**
     * Extract the necessary points of the vectors (x, y, z) to later create 3D Vectors and save them in the Vector list
     *
     * @param vector -> List of the Vectors
     * @param VectorsToUse -> Vector List made to save the new Vectors3D that we create
     * @param size -> contains how much data has the faces saved example (f 1/1/1 2/2/2 3/3/3 = size -> 4, f 1/1/1 2/2/2 3/3/3 4/4/4 = size -> 5)
     * @return VectorsToUse -> Vector3D list that we will use to create the Triangles
     */
    private static List<Vector3D> ExtractVectors(List<String> vector, List<Vector3D> VectorsToUse, int size){

        Float x,y,z;

        if(size == 4)
            for (int i = 0; i < vector.size(); i++) {

                String[] VectorPosition = vector.get(i).split(" ");
                String[] LastVector = VectorPosition[4].split("#");

                x = Float.valueOf(VectorPosition[2]);
                y = Float.valueOf(VectorPosition[3]);
                z = Float.valueOf(LastVector[0]);

                VectorsToUse.add(new Vector3D(x, y, z));
            }
        else if(size == 5)
            for (int i = 0; i < vector.size(); i++) {

                String[] VectorPosition = vector.get(i).split(" ");
                String[] LastVector = VectorPosition[3].split("#");

                x = Float.valueOf(VectorPosition[1]);
                y = Float.valueOf(VectorPosition[2]);
                z = Float.valueOf(LastVector[0]);

                VectorsToUse.add(new Vector3D(x, y, z));
            }

        return VectorsToUse;
    }

    /**
     * Create all triangles of the Object
     *
     * @param triangle -> empty Triangle List
     * @param VectorOfTheFaces -> List of the order of the faces
     * @param VectorsToUse -> Vector3D list of the
     * @return triangle -> Triangle List
     */
    private static Triangle[] makeTriangles(List<Triangle> triangle, List<String> VectorOfTheFaces, List<Vector3D> VectorsToUse){

        int point1,point2,point3;

        for (int i = 0 ; i < VectorOfTheFaces.size(); i++) {

            String[] vertex = VectorOfTheFaces.get(i).split(" ");
            point1 = Integer.parseInt(vertex[0]);
            point2 = Integer.parseInt(vertex[1]);
            point3 = Integer.parseInt(vertex[2]);

            triangle.add( new Triangle(VectorsToUse.get((point1)-1), VectorsToUse.get((point2)-1), VectorsToUse.get((point3)-1)) );
        }
        Triangle[] triangles = new Triangle[triangle.size()];

        for(int i = 0; i < triangle.size(); i++) triangles[i] = triangle.get(i);


        return triangles;
    }

}


