package game.logic;

import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.io.FilenameFilter;

/**
 * Created by IntelliJ IDEA.
 * User: melnikovp
 * Date: 30.07.12
 * Time: 15:05
 * To change this template use File | Settings | File Templates.
 */
public class MapFileFilter
        extends FileFilter
        implements FilenameFilter {

    String ext;
    String description;

    public MapFileFilter(String ext, String descr) {
        this.ext = ext;
        description = descr;
    }

    public boolean accept(File f) {
        if(f != null) {
            if(f.isDirectory()) {
                return true;
            }
            String extension = getExtension(f);
            if( extension == null ) {
                return (ext.length() == 0);
            }
            return ext.equals(extension);
        }
        return false;
    }

    public String getExtension(File f) {
        if(f != null) {
            String filename = f.getName();
            int i = filename.lastIndexOf('.');
            if(i>0 && i<filename.length() - 1) {
                return filename.substring(i + 1).toLowerCase();
            }
        }
        return null;
    }

    public String getDescription() {
        return "*.map";
    }

    public boolean accept(File dir, String name) {
        if (name.endsWith(".map")) {
            return true;
        } else {
            return false;
        }
    }
}