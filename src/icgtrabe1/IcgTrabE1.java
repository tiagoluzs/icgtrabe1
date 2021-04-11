package icgtrabe1;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tiago Luz
 */
public class IcgTrabE1 {
    static void printHelp() {
        System.out.println("java -jar IcgTrabE1.jar <cmd> <colors> source target");
        System.out.println("cmd: color|gray");
        System.out.println("colors: >0");
        System.out.println("source: <file>");
        System.out.println("target: <file>");
        System.out.println("");
        System.out.println("java -jar IcgTrabE1.jar gray 2 ../ppm/Eagle.ppm ../ppm/Eagle-2.ppm");
        System.out.println("java -jar IcgTrabE1.jar gray 16 ../ppm/Eagle.ppm ../ppm/Eagle-16.ppm");
        System.out.println("java -jar IcgTrabE1.jar color 100 ../ppm/Eagle.ppm ../ppm/Eagle-100.ppm");
        
    }
    public static void main(String[] args) {
        String cmd = "";
        int colors = 0;
        String src = "";
        String dst = "";
        
        try {
            cmd = args[0];
            colors = Integer.parseInt(args[1],10);
            src = args[2];
            dst = args[3];
        } catch(Exception e) {
            System.err.println("Parâmetros inválidos.");
            printHelp();
            System.exit(-1);
        }
        
        PPMHandler ppm = new PPMHandler();
        File file = new File(src);
        File dest = new File(dst);
        
        if(!file.exists() ) {
            System.err.println("Arquivo não existe");
            System.exit(-1);
        }
        
        if(!file.isFile() ) {
            System.err.println("Origem não é arquivo.");
            System.exit(-1);
        }
        
        try {
            PPMFile ppmFile = ppm.read(file);
            
            if(cmd.equals("color")) {
                ppm.colorQuantify(ppmFile, colors);
                ppm.write(ppmFile, dest);
            } else {
                ppm.colorGrayScale(ppmFile, colors);
                ppm.write(ppmFile, dest);
            }
            
            
        } catch (Exception ex) {
            Logger.getLogger(IcgTrabE1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
