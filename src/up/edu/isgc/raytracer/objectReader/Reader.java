/**
 * [1968] - [2020] Centros Culturales de Mexico A.C / Universidad Panamericana
 * All Rights Reserved.
 */
package up.edu.isgc.raytracer.objectReader;

import up.edu.isgc.raytracer.Vector3D;
import up.edu.isgc.raytracer.objects.Triangle;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
     * @param faces -> List of the position of the face
     * @throws IOException
     */
    public static void objectReader(File File, List<String> vector, List<String> faces) throws IOException {

        String line;
        List<String> Text = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader(File.getAbsolutePath()));

        while ((line = br.readLine()) != null){
            if (line.startsWith("v ")){

                vector.add(line + "#");

            }else if (line.startsWith("f ")){

                faces.add(line + "#");

            }
        }
    }

}

