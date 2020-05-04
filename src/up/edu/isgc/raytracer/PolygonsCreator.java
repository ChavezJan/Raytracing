package up.edu.isgc.raytracer;

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
        List<String> triangle = new ArrayList<>();
        List<String> VectorsToUse = new ArrayList<>();


        int point1,point2,point3;

        ExtractFaces(faces,VectorOfTheFaces);
        ExtractVectors(vector, VectorsToUse);

        //new Polygons(new Vector3D(-1,-1,2), new Triangle[]{
        // new Triangle(Vector3D.ZERO(), new Vector3D(1,0,0), new Vector3D(1,-1,0)),
        // new Triangle(Vector3D.ZERO(), new Vector3D(2,0,0), new Vector3D(1,-1,0))},
        // Color.PINK)

        for (int i = 0 ; i < VectorOfTheFaces.size(); i++) {

            String[] vertex = VectorOfTheFaces.get(i).split(" ");
            point1 = Integer.parseInt(vertex[0]);
            point2 = Integer.parseInt(vertex[1]);
            point3 = Integer.parseInt(vertex[2]);

            triangle.add("new Triangle(Vector3D(" + VectorsToUse.get((point1)-1) + "), Vector3D(" + VectorsToUse.get((point2)-1) + "), Vector3D(" + VectorsToUse.get((point3)-1) + "))" );
            System.out.println(triangle.get(i));
        }


        Raytracer.initialRaytracer(triangle);

    }

    /**
     *
     *
     * @param strings
     * @param faces
     * @return
     */
    public static List<String> ExtractFaces(List<String> faces, List<String> VectorOfTheFaces){


        for(int i = 0; i < faces.size(); i++){

            VectorOfTheFaces.add(String.valueOf(faces.get(i).toCharArray()[2] + " " + faces.get(i).toCharArray()[8] + " " + faces.get(i).toCharArray()[14]));
            System.out.println("Face #" + (i + 1) +" vectors " + VectorOfTheFaces.get(i));

        }

        return VectorOfTheFaces;
    }

    /**
     *
     *
     * @param strings
     * @param vector
     * @return
     */
    public static List<String> ExtractVectors(List<String> vector, List<String> VectorsToUse){

        System.out.println("Vectors that we will use");

        for(int i = 0; i < vector.size(); i++){

            String[] VectorPosition = vector.get(i).split(" ");
            String[] LastVector = VectorPosition[4].split("#");

            VectorsToUse.add(VectorPosition[2] + ", " + VectorPosition[3] + ", " + LastVector[0]);

            System.out.println("Vector #" + (i +1) + " " + VectorsToUse.get(i));
        }


        return VectorsToUse;
    }

}


