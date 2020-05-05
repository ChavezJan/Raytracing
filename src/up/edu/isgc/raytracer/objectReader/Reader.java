package up.edu.isgc.raytracer.objectReader;

import up.edu.isgc.raytracer.objects.PolygonsCreator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Reader {

    /**
     * Read the file and save and send it to extract the vectors and faces we need
     *
     * @param File -> the object file that we will use
     * @throws IOException
     */
    public static void objectReader(File[] File) throws IOException {

        String line;
        List<String> Text = new ArrayList<>();
        List<String> Vector = new ArrayList<>();
        List<String> VectorToDelete = new ArrayList<>();
        List<String> Faces = new ArrayList<>();

        File file = new File(File[0].getAbsolutePath());
        BufferedReader br = new BufferedReader(new FileReader(file));

        while ((line = br.readLine()) != null)
            Text.add(line+"#");

        onlyVectors(Text, Vector, VectorToDelete);
        Faces(Text, Faces);


        PolygonsCreator.FacesAndVectorsOrganizer(Vector,Faces);

    }

    /**
     * Receive the text from the file, read it and extract the necessary vectors (v), eliminating the vectors that we don't need (vt, vn)
     *
     * @param text -> The text of the file
     * @param Vector -> Its an empty list of vectors
     * @param VectorToDelete -> Its an empty list of vectors that we will use to delete the vectors that we do not want
     * @return Vector -> Is the vector list filled with the vectors we need
     */
    public static List<String> onlyVectors(List<String> text, List<String> Vector, List<String> VectorToDelete){

        char FirstOfLine = ' ', SecondOfLine = ' ';

        for(int i = 0; i < text.size(); i++){

            FirstOfLine = text.get(i).toCharArray()[0];

            if(FirstOfLine == 'v'){

                Vector.add(text.get(i));
                SecondOfLine = text.get(i).toCharArray()[1];

                if (SecondOfLine == 'n' || SecondOfLine == 't') VectorToDelete.add(text.get(i));

            }
        }

        for (int x = 0; x < Vector.size(); x++)
            for(int y = 0; y < VectorToDelete.size(); y++){
                if (Vector.get(x) == VectorToDelete.get(y)){

                    Vector.remove(x);

                }
            }

        return Vector;
    }

    /**
     * Receive the text from the file, read it and extract the Faces (f)
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

                String[] CleanText = text.get(i).split("#");

                faces.add(text.get(i));

            }
        }
        return faces;
    }
}

