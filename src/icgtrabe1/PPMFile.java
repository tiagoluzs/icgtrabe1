/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icgtrabe1;

import java.util.ArrayList;

/**
 *
 * @author Tiago Luz
 */
public class PPMFile {
    // headers
    String type; // P3 - RGB color image in ASCII
    int width;
    int height;
    int maxColorValue; 
    ArrayList<ArrayList<RGB>> data;
    private ArrayList<RGB> currentLine;
    
    {
        type = "P3";
        data = new ArrayList<>();
    }
    
    void addLine() {
        this.currentLine = new ArrayList<RGB>();
        this.data.add(this.currentLine);
    }
    void addPixel(RGB rgb) {
        if(this.currentLine == null) {
            this.addLine();
        }
        this.currentLine.add(rgb);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String lb = System.lineSeparator();
        sb.append(this.type).append(lb);
        sb.append(this.width).append(" ").append(this.height).append(lb);
        sb.append(this.maxColorValue).append(lb);
        data.forEach((r) -> {
            
        });
        return sb.toString();
    }
    
}
