/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package elect1ammad;

/**
 *
 * @author Mr_Abdelhake
 */
public class Parametre {
    private double d ;
    private double c;
    private double[] poid;
    private double[][] MPerf;

    public Parametre() {
    }

    public Parametre(double d, double c, double[] poid, double[][] MPerf) {
        this.d = d;
        this.c = c;
        this.poid = poid;
        this.MPerf = MPerf;
    }

    public double getD() {
        return d;
    }

    public void setD(double d) {
        this.d = d;
    }

    public double getC() {
        return c;
    }

    public void setC(double c) {
        this.c = c;
    }

    public double[] getPoid() {
        return poid;
    }

    public void setPoid(double[] poid) {
        this.poid = poid;
    }

    public double[][] getMPerf() {
        return MPerf;
    }

    public void setMPerf(double[][] MPerf) {
        this.MPerf = MPerf;
    }
    
}
