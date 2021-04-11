/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icgtrabe1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Tiago Luz
 */
public class PPMHandler {
    List<RGB> PALLETE_APPLE;
    
    {
     PALLETE_APPLE = Arrays.asList(new RGB(255, 255, 255), new RGB(251, 243, 5),new RGB(1, 100, 3), new RGB(221, 9, 7), new RGB(242, 8, 132), new RGB(71, 0, 165), new RGB(0, 0, 211), new RGB(2, 171, 234), new RGB(31, 183, 20), new RGB(0, 100, 18), new RGB(86, 44, 5), new RGB(144, 113, 58), new RGB(192, 192, 192), new RGB(128, 128, 128), new RGB(64, 64, 64), new RGB(0, 0, 0));
    
    }
    
    void colorGrayScale(PPMFile ppm, int size) {
        
        ArrayList<RGB> pallete = new ArrayList();
        
        if(size > 256) size = 256;
       
        int step = 256 / size;
        
        for(int i = 0; i<256;i+=step) {
            pallete.add(new RGB(i,i,i));
        }
        
        for (int i = 0; i < ppm.data.size();i++) {
            for (int j = 0; j < ppm.data.get(i).size();j++) {
                RGB pixel = ppm.data.get(i).get(j);
                double min = Double.MAX_VALUE;
                RGB novaCor = null;
                for (RGB pcor : pallete) {
                    double dist = this.dist(pcor,pixel);
                    if (dist < min) {
                        min = dist;
                        novaCor = pcor;
                    }
                }
                ppm.data.get(i).set(j, novaCor);
            }
        }
        ppm.maxColorValue = size;
    }
    
    void colorQuantify(PPMFile ppm, int max) {
        float maxAtual = (float) ppm.maxColorValue;

        ArrayList<RGB> list = new ArrayList<>();

        for (ArrayList<RGB> line : ppm.data) {
            for (RGB pixel : line) {
                if (list.contains(pixel)) {
                    list.get(list.indexOf(pixel)).qtd++;
                } else {
                    list.add(pixel);
                }
            }
        }
        
        Collections.sort(list, (x, y) -> y.qtd - x.qtd);
        
        if (max > list.size()) {
            max = list.size();
        }
        
        ArrayList<RGB> pallete = new ArrayList();
        pallete.addAll(PALLETE_APPLE);
        
        if(max > PALLETE_APPLE.size()) {
            pallete.addAll(list.subList(0, max - PALLETE_APPLE.size()));
        }
                
        for (int i = 0; i < ppm.data.size(); i++) {    
            for (int j = 0; j < ppm.data.get(i).size();j++) {
                RGB pixel = ppm.data.get(i).get(j);
                double min = Double.MAX_VALUE;
                RGB novaCor = null;
                int corIndex = 0;
                for (RGB pcor : pallete) {
                    double dist = this.dist(pcor,pixel);
                    if (dist < min) {
                        min = dist;
                        novaCor = pcor;
                        corIndex = pallete.indexOf(novaCor);
                    }
                }
                ppm.data.get(i).set(j, novaCor);
            }
        }

        ppm.maxColorValue = max;
    }
    
    public static void main(String[] args) {
        
        PPMHandler ppm = new PPMHandler();
        
        System.out.println("CORES:");
        for(RGB cor : ppm.PALLETE_APPLE) {
            System.out.println(cor);
        }
        System.out.println("=========");
        double min = Double.MAX_VALUE;
        
        RGB branco = new RGB(255,255,255);
        RGB novaCor = branco;
        for(RGB cor : ppm.PALLETE_APPLE) {
            double dist = ppm.dist(branco, cor);
            System.out.print(dist + " " + min);
            if(dist < min){
                min = dist;
                novaCor = cor;
                System.out.print(" ==> " + novaCor );
            } else {
                System.out.print(" > " + cor );
            }
            System.out.println("");
        }
        System.out.println(min);
        System.out.println(novaCor);
    }
    
    public double dist(RGB c1, RGB c2) {
        return Math.sqrt(Math.pow(c1.r - c2.r, 2) + Math.pow(c1.g - c2.g, 2) + Math.pow(c1.b - c2.b, 2));
    }

    void write(PPMFile ppm, File dest) throws IOException {
        FileWriter fw = new FileWriter(dest);
        String lb = System.lineSeparator();
        fw.write(ppm.type + lb);
        fw.write(ppm.width + " " + ppm.height + lb);
        if(ppm.type.equals("P3"))
            fw.write(ppm.maxColorValue + lb);
        for (ArrayList<RGB> list : ppm.data) {
            for (RGB rgb : list) {
                switch(ppm.type) {
                    case "P1":
                        fw.write(rgb.toStringBinary());
                        break;
                    case "P3":
                        fw.write(rgb.toString());
                        break;
                }    
            }
            fw.write(lb);
        }
        fw.close();
    }

    PPMFile read(File file) throws FileNotFoundException, IOException, Exception {
        PPMFile ppm = new PPMFile();
        try (
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);) {
            String linha = br.readLine();
            StringBuilder sb = new StringBuilder();
            int count = 1;
            while (linha != null) {
                // desconsidera comentários
                if (linha.startsWith("#")) {
                    linha = br.readLine();
                    continue;
                }
                if (count == 1) {
                    ppm.type = linha.trim();
                } else if (count == 2) {
                    String s[] = linha.trim().split(" ");
                    ppm.width = Integer.parseInt(s[0]);
                    ppm.height = Integer.parseInt(s[1]);
                } else if(count == 3 && ppm.type.equals("P3")) {
                    ppm.maxColorValue = Integer.parseInt(linha);
                } else {
                    sb.append(linha.trim()).append(System.lineSeparator());
                }

                count++;
                linha = br.readLine();
            }
            
            
            String linhas[];
            switch (ppm.type) {
                case "P1": // Portable BitMap ASCII (plain)
                    linhas = sb.toString().split(System.lineSeparator());
                    for (int l = 0; l < linhas.length; l++) {
                        String li = linhas[l].trim().replaceAll(" ", "").replaceAll("\n", "");
                        for (int i = 0; i < li.length(); i++) {
                            ppm.addPixel(new RGB(li.charAt(i) == '0'));
                        }
                        ppm.addLine();
                    }
                    break;
                case "P2": // Portable GrayMap ASCII (plain)
                    linhas = sb.toString().split(System.lineSeparator());
                    for (int l = 0; l < linhas.length; l++) {
                        String data[] = linhas[l].trim().split(" ");
                        for (int i = 0; i < data.length; i += 3) {
                            ppm.addPixel(new RGB(data[i].equals(1)));
                        }
                        ppm.addLine();
                    }
                    break;
                case "P3": // Portable PixMap ASCII (plain)
                    linhas = sb.toString().split(System.lineSeparator());
                    for (int l = 0; l < linhas.length; l++) {
                        String data[] = linhas[l].trim().split(" ");
                        for (int i = 0; i < data.length; i += 3) {
                            ppm.addPixel(new RGB(data[i], data[i + 1], data[i + 2]));
                        }
                        ppm.addLine();
                    }
                    break;
                case "P4": // Portable BitMap Binary (raw)
                    throw new Exception("Unimplemented");
                case "P5": // Portable GrayMap Binary (raw)
                    throw new Exception("Unimplemented");
                case "P6": // Portable PixMap Binary (raw)
                    throw new Exception("Unimplemented");
                case "P7": // PAM
                    throw new Exception("Unimplemented");
            }

            return ppm;

        }

    }
}
