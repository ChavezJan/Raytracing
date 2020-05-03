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

        System.out.println("Choose all the objects you want to use\n" +
                "You will have to press CTRL to select all the files clicking one by one..\n"+
                "Or\n" +
                "You could click the Shift key and select all the files at one..");

        chooserConf(selector, parentFrame);
        File[] Files = askForTheIMGorVIDEO(selector, parentFrame);

        System.out.println("This are your files");

        FileWMeta[] FileWM = new FileWMeta[Files.length];


        for (int x = 0; x < Files.length; x++) {
            System.out.println((x + 1) + " --> " + Files[x].getName());
            FileWM[x] = new FileWMeta(Files[x]);
        }

        System.out.println("Files were selected successfully!");

        do {

            System.out.println("1) Continue with the Raytracer\n2) Select other files");
            menu = Authenticator.authLet(options);

        }while(Authenticator.authNum(menu,options));

        switch (menu) {

            case 1:

                System.out.println("OK so lets continue");
                Reader.objectReader(FileWM); // continue with the Raytracer

                break;
            case 2:

                System.out.println("Select the files");
                selectFiles(); // choose other files

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
     * here the user make the decision of the files he want to use
     * @param selector
     * @param parentFrame
     * @return -->return all the selected files
     */
    private static File[] askForTheIMGorVIDEO(JFileChooser selector, JFrame parentFrame){

        int Check;

        do{

            System.out.println("The selector is open");
            Check = selector.showOpenDialog(parentFrame);

        }while(!(Check == JFileChooser.APPROVE_OPTION));

        parentFrame.dispose();

        return selector.getSelectedFiles();

    }
}
