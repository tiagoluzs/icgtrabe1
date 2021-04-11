package icgtrabe1;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tiago Luz
 */
public class IcgTrabE1 {
    
    public static void main(String[] args) {
        PPMHandler ppm = new PPMHandler();
        File file = new File("ppm\\Eagle.ppm");
        File dest = new File("ppm\\Eagle-16.ppm");
        File dest2 = new File("ppm\\Eagle-Gray.ppm");
        try {
            PPMFile ppmFile = ppm.read(file);
            ppm.colorQuantify(ppmFile, 16);
            ppm.write(ppmFile, dest);
            
            ppmFile = ppm.read(file);
            ppm.colorGrayScale(ppmFile, 256);
            ppm.write(ppmFile, dest2);
            
        } catch (Exception ex) {
            Logger.getLogger(IcgTrabE1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
