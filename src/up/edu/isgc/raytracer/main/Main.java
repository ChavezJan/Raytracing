package up.edu.isgc.raytracer.main;

import up.edu.isgc.raytracer.selector.SelectFile;

import java.io.IOException;
import java.util.Date;
import java.util.zip.DataFormatException;

/**
 * Ray tracing code
 * Is the Final project of Multimedia and computational graphics class - Universidad Panamericana Campus Guadalajara
 *
 * @author ChavezJan
 * @version 0.4
 */

public class Main {

    public static void main(String[] args) throws IOException, DataFormatException {
        System.out.println(new Date());

        SelectFile.selectFiles();

    }

}
