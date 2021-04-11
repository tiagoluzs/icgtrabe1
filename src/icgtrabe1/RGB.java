/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icgtrabe1;

/**
 *
 * @author Tiago Luz
 */
public class RGB implements Cloneable{
    int r;
    int g;
    int b;
    
    int qtd;
    
    {
        qtd = 1;
    }
    
    public RGB() {
    }
    
    public RGB(String hexa) {
        if(hexa.startsWith("#")) {
            hexa = hexa.substring(1);
        }
        this.r = Integer.parseInt(hexa.toUpperCase().substring(0, 2), 16);
        this.g = Integer.parseInt(hexa.toUpperCase().substring(2, 4), 16);
        this.b = Integer.parseInt(hexa.toUpperCase().substring(4, 6), 16);
    }

    public RGB(boolean isBlack) {
        this.r = this.g = this.b = isBlack ? 0 : 255;
    }
    
    public RGB(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }
    
    public RGB(String r, String g, String b) {
        this.r = Integer.parseInt(r);
        this.g = Integer.parseInt(g);
        this.b = Integer.parseInt(b);
    }
    
    public char toStringBinary() {
        return this.getColorValue() == 0 ? '0' : '1';
    }
    
    
    @Override
    public String toString() {
     StringBuilder sb = new StringBuilder();
     sb.append(" ").append(this.r).append(" ").append(this.g).append(" ").append(this.b);
     return sb.toString();
    }

    @Override
    public int hashCode() {
        return (int)getColorValue();
    }

    int getColorValue() {
        return (int)(this.r*Math.pow(256, 2)+this.g*Math.pow(256, 1)+this.b*Math.pow(256, 0));
    }
    
    @Override
    public boolean equals(Object o) {
        if(o instanceof RGB) {
            RGB r = (RGB)o;
            return this.getColorValue() == r.getColorValue();
        } else return false;
    }
    
    
    
}
