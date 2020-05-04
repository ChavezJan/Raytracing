package up.edu.isgc.raytracer.main;

import up.edu.isgc.raytracer.Raytracer;

import java.io.IOException;
import java.util.zip.DataFormatException;

/**
 * Ray tracing code
 * Is the Final project of Multimedia and computational graphics class - Universidad Panamericana Campus Guadalajara
 *
 * @author ChavezJan
 * @version 0.3.3
 */

public class Main {

    public static void main(String[] args) throws IOException, DataFormatException {

        Raytracer.initialRaytracer(triangle);
        //SelectFile.selectFiles();

    }

}
