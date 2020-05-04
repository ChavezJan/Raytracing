package up.edu.isgc.raytracer.objectReader;

import up.edu.isgc.raytracer.selector.FileWMeta;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Reader {

    public static void objectReader(FileWMeta[] fileWM) throws IOException {
        String st;
        char FirstOfLine = ' ', SecondOfLine = ' ';
        List<String> Text = new ArrayList<>();
        List<String> Vector = new ArrayList<>();
        List<String> VectorToDelete = new ArrayList<>();



        File file = new File(fileWM[0].getFile().getAbsolutePath());
        BufferedReader br = new BufferedReader(new FileReader(file));

        while ((st = br.readLine()) != null)
            Text.add(st+".");

        System.out.println(Text);

        for(int i = 0; i < Text.size(); i++){

            FirstOfLine = Text.get(i).toCharArray()[0];

            if(FirstOfLine == 'v'){

                Vector.add(Text.get(i));
                System.out.println(Text.get(i));
                SecondOfLine = Text.get(i).toCharArray()[1];

                if (SecondOfLine == 'n' || SecondOfLine == 't'){
                    VectorToDelete.add(Text.get(i));
                }
            }
        }
        System.out.println(Vector);
        System.out.println(VectorToDelete);

        for (int x = 0; x < Vector.size(); x++){
            for(int y = 0; y < VectorToDelete.size(); y++){
                if (Vector.get(x) == VectorToDelete.get(y)){
                    Vector.remove(x);
                }
            }
        }
        System.out.println(Vector);
    }
}

