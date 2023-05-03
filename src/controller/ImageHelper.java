package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 *
 * @author gustavo
 */
public class ImageHelper {
    private String directory;
    private int r,g,b;
    
    public ImageHelper(){}
    
    public ImageHelper(String directory, int r, int g, int b){
        this.directory = directory;
        this.r = r;
        this.g = g;
        this.b = b;
    }
    
    public boolean generateModifiedBmp(){
        BufferedImage img = null;
        File f = null;
        int width, height, p, nuevoR=0, nuevoG=0, nuevoB=0;
        
        try {//Leer imagen
            f = new File(directory);
            img = ImageIO.read(f);
        }catch (Exception e) {
            System.out.println("Error al leer imagen: "+e);
            return false;
        }
        
        //Obtener tamanio de imagen
        width = img.getWidth();
        height = img.getHeight();
        
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                //Obtener color de pixel
                p = img.getRGB(i, j);
                
                //Modificar color de pixel
                nuevoR = (((p >> 16) & 0xff) + r) % 256;
                nuevoG = (((p >> 8) & 0xff) + g) % 256;
                nuevoB = ((p & 0xff) + b) % 256;
                
                
                
                //Establecer nuevo pixel
                p = (nuevoR << 16) | (nuevoG << 8) | nuevoB;
                img.setRGB(i, j, p);
            }
        }
        
        //Escribir nueva imagen
        try {
            f = new File("corazon_c.bmp");
            ImageIO.write(img, "bmp", f);
        }catch (Exception e) {
            System.out.println("Error al generar nueva imagen: "+e);
            return false;
        }
        
        return true;
    }
    
    public boolean generateOriginalBmp(){
        //TODO generate original bmp
        BufferedImage img = null;
        File f = null;
        int width, height, p, nuevoR=0, nuevoG=0, nuevoB=0;
        
        try {//Leer imagen
            f = new File(directory);
            img = ImageIO.read(f);
        }catch (Exception e) {
            System.out.println("Error al leer imagen: "+e);
            return false;
        }
        
        //Obtener tamanio de imagen
        width = img.getWidth();
        height = img.getHeight();
        
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                //Obtener color de pixel
                p = img.getRGB(i, j);
                
                //Modificar color de pixel
                nuevoR = (((p >> 16) & 0xff) - r) % 256;
                nuevoG = (((p >> 8) & 0xff) - g) % 256;
                nuevoB = ((p & 0xff) - b) % 256;
                
                
                
                //Establecer nuevo pixel
                p = (nuevoR << 16) | (nuevoG << 8) | nuevoB;
                img.setRGB(i, j, p);
            }
        }
        
        //Escribir nueva imagen
        try {
            f = new File("corazon_d.bmp");
            ImageIO.write(img, "bmp", f);
        }catch (Exception e) {
            System.out.println("Error al generar nueva imagen: "+e);
            return false;
        }
        
        return true;
    }
}
