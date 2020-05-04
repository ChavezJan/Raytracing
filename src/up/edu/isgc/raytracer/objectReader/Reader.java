package up.edu.isgc.raytracer.objectReader;

import up.edu.isgc.raytracer.PolygonsCreator;
import up.edu.isgc.raytracer.selector.FileWMeta;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Reader {

    /**
     *
     * @param fileWM
     * @throws IOException
     */
    public static void objectReader(FileWMeta[] fileWM) throws IOException {
        String line;
        List<String> Text = new ArrayList<>();
        List<String> Vector = new ArrayList<>();
        List<String> VectorToDelete = new ArrayList<>();
        List<String> Faces = new ArrayList<>();


        File file = new File(fileWM[0].getFile().getAbsolutePath());
        BufferedReader br = new BufferedReader(new FileReader(file));

        while ((line = br.readLine()) != null)
            Text.add(line+"#");

        //System.out.println(Text);

        onlyVectors(Text, Vector, VectorToDelete);
        Faces(Text, Faces);


        PolygonsCreator.FacesAndVectorsOrganizer(Vector,Faces);

    }

    /**
     *
     * @param text
     * @param Vector
     * @param VectorToDelete
     * @return
     */
    public static List<String> onlyVectors(List<String> text, List<String> Vector, List<String> VectorToDelete){

        char FirstOfLine = ' ', SecondOfLine = ' ';

        for(int i = 0; i < text.size(); i++){

            FirstOfLine = text.get(i).toCharArray()[0];

            if(FirstOfLine == 'v'){

                Vector.add(text.get(i));
                SecondOfLine = text.get(i).toCharArray()[1];

                if (SecondOfLine == 'n' || SecondOfLine == 't'){
                    VectorToDelete.add(text.get(i));
                }
            }
        }
        for (int x = 0; x < Vector.size(); x++){
            for(int y = 0; y < VectorToDelete.size(); y++){
                if (Vector.get(x) == VectorToDelete.get(y)){
                    Vector.remove(x);
                }

            }
        }
        System.out.println("vectores");
        System.out.println(Vector);
        return Vector;
    }

    /**
     *
     * @param text
     * @param faces
     * @return
     */
    public static List<String> Faces(List<String> text, List<String> faces){

        char FirstOfLine = ' ';

        for(int i = 0; i < text.size(); i++){

            FirstOfLine = text.get(i).toCharArray()[0];

            if(FirstOfLine == 'f'){

                faces.add(text.get(i));

            }
        }
        System.out.println("faces");
        System.out.println(faces);
        return faces;
    }
}

