package up.edu.isgc.raytracer.selector;

import java.io.File;

public class FileWMeta {
    private File file;


    /**
     * it an object of the files to make sure that all the data its save at the same place
     * @param file
     */
    public FileWMeta(File file){

        this.file = file;

    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

}
