/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package old.Domaine;

import java.io.Serializable;

/**
 *
 * @author thomasdrouin
 */
public class Triangle implements Serializable{
    public Triangle(Point argP1, Point argP2, Point argP3){
       this.p1 = argP1; 
       this.p2 = argP2; 
       this.p3 = argP3; 
    }

    private Point p1;
    private Point p2;
    private Point p3;
    
    public Point getP1(){
        return p1;
    }    
    
    public Point getP2(){
        return p2;
    }   
    
    public Point getP3(){
        return p3;
    }
    
    public Vector getNormalVector(){
        Vector v = this.p2.getVectorBetween(this.p1);
        Vector w = this.p3.getVectorBetween(this.p1);
        double nX = (v.getY()*w.getZ()-v.getZ()*w.getY());
        double nY = (v.getZ()*w.getX()-v.getX()*w.getZ());
        double nZ = (v.getX()*w.getY()-v.getY()*w.getX());
        Vector normale = new Vector(new Point(0,0,0), nX, nY, nZ);
        return normale;
    }
}
