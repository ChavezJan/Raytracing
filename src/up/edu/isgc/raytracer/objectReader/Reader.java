/**
 * [1968] - [2020] Centros Culturales de Mexico A.C / Universidad Panamericana
 * All Rights Reserved.
 */
package up.edu.isgc.raytracer.objectReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ChavezJan
 * @author Jafet Rodríguez
 */
public class Reader {

    /**
     * Read the file and save and send it to extract the vectors and faces we need
     *
     * @param File -> the object file that we will use
     * @param vector -> list of all the Vectors
     * @param normal -> list of all the normals
     * @param faces -> List of the position of the face
     * @param i Number of the File
     * @throws IOException
     */
    public static void objectReader(File[] File, List<String> vector, List<String> normal, List<String> faces, int i) throws IOException {

        String line;
        List<String> Text = new ArrayList<>();


        File file = new File(File[i].getAbsolutePath());
        BufferedReader br = new BufferedReader(new FileReader(file));

        while ((line = br.readLine()) != null){
            if (line.startsWith("v ")){

                vector.add(line + "#");

            }else if (line.startsWith("vn ")){

                normal.add(line + "#");

            }else if (line.startsWith("f ")){

                faces.add(line + "#");

            }
        }
    }
}

