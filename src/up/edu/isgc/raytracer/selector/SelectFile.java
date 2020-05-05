package up.edu.isgc.raytracer.selector;



import up.edu.isgc.raytracer.objectReader.Reader;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.util.zip.DataFormatException;

public class SelectFile {
    /**
     * This class is made to make the user to choose the Objects for the project
     * @throws IOException
     * @throws DataFormatException
     */
    public static void selectFiles() throws IOException, DataFormatException {

        int options = 2, menu = 0;

        JFileChooser selector = new JFileChooser();
        JFrame parentFrame = new JFrame();

        System.out.println("Choose the objects you want to use for the Raytracer\n");

        chooserConf(selector, parentFrame);
        File[] Files = askForTheOBJ(selector, parentFrame);

        System.out.println("This is your file");

        for (int x = 0; x < Files.length; x++) {
            System.out.println((x + 1) + " --> " + Files[x].getName());
        }

        System.out.println("The file was successfully selected!");

        do {

            System.out.println("1) Continue with the Raytracer\n2) Select other file");
            menu = Authenticator.authLet(options);

        }while(Authenticator.authNum(menu,options));

        switch (menu) {

            case 1:

                System.out.println("OK so lets continue");
                Reader.objectReader(Files); // continue with the Raytracer

                break;
            case 2:

                System.out.println("Select the file");
                selectFiles(); // choose other file

                break;

        }
    }

    /**
     * here configure the selector, always puts it in view of the user
     * @param selector
     * @param parentFrame
     */
    private static void chooserConf(JFileChooser selector, JFrame parentFrame){

        parentFrame.setAlwaysOnTop(true);
        parentFrame.requestFocus();

        FileNameExtensionFilter filter = new FileNameExtensionFilter("OBJ", "obj");

        selector.setFileFilter(filter);
        selector.setMultiSelectionEnabled(true);

    }

    /**
     * here the user make the decision of the file he want to use
     * @param selector
     * @param parentFrame
     * @return -->return the selected file
     */
    private static File[] askForTheOBJ(JFileChooser selector, JFrame parentFrame){

        int Check;

        do{

            System.out.println("The selector is open");
            Check = selector.showOpenDialog(parentFrame);

        }while(!(Check == JFileChooser.APPROVE_OPTION));

        parentFrame.dispose();

        return selector.getSelectedFiles();

    }
}
