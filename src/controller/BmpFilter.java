package controller;

import java.io.File;

/**
 *
 * @author gustavo
 */
public class BmpFilter extends javax.swing.filechooser.FileFilter{
    @Override
    public boolean accept(File file) {
        // Allow only directories, or files with ".txt" extension
        return file.isDirectory() || file.getAbsolutePath().endsWith(".bmp");
    }
    
    @Override
    public String getDescription() {
        // This description will be displayed in the dialog,
        // hard-coded = ugly, should be done via I18N
        return "Imágenes de Mapa de Bits (*.bmp)";
    }
}
