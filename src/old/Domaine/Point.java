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
public class Point  implements Serializable{
    public Point(   double x, 
                    double y, 
                    double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Point(Point point) {
        this.x = point.getX();
        this.y = point.getY();
        this.z = point.getZ();
    }
    
    private double x;
    private double y;
    private double z;
    
    public Point getPoint(){
        Point p2 = new Point(this.x, this.y, this.z);
        return p2;
    }
        
    public void setPoint(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }        
    
    public void setX(double x){
        this.x = x;
    }
    
    public void setY(double y){
        this.y = y;
    }
    
    public void setZ(double z){
        this.z = z;
    }
        
    public double getX(){
        double pX = this.x;
        return pX;
    }
    
    public double getY(){
        double pY =  this.y;
        return pY;
    }
    
    public double getZ(){
        double pZ = this.z;
        return pZ;
    }
    
    public Vector getVectorBetween(Point p2){
        double vX = Math.abs(p2.x - this.x);
        double vY = Math.abs(p2.y - this.y);
        double vZ = Math.abs(p2.z - this.z);
        return new Vector(this, vX, vY, vZ);
    }
    //return new point from the addition of the point and arg p2
    public Point addPoint(Point p2){
        Point p3 = new Point(0.00, 0.00, 0.00);
        p3.x = this.x + p2.x;
        p3.y = this.y + p2.y;
        p3.z = this.z + p2.z;
        return p3;
    }
    
        //return new point from the addition of the point and arg p2
    public Point subPoint(Point p2){
        Point p3 = new Point(0.00, 0.00, 0.00);
        p3.x = this.x - p2.x;
        p3.y = this.y - p2.y;
        p3.z = this.z - p2.z;
        return p3;
    }
    
    public Point addVector(Vector vector){
        Point p2 = new Point(this.x+vector.getX(), this.y+vector.getY(), this.z+vector.getZ());
        return p2;
    }
    
    public Point subVector(Vector argVector){
        Point p2 = new Point(this.x-argVector.getX(), this.y-argVector.getY(), this.z-argVector.getZ());
        return p2;
    }
    
    //add x to the point and return a new point
    public Point addX(double x){
        Point p2 = this.getPoint();
        p2.x += x;
        return p2;
    }
    
    //add y to the point and return a new point
    public Point addY(double y){
        Point p2 = this.getPoint();
        p2.y += y;
        return p2;
    }
    
    //add z to the point and return a new point
    public Point addZ(double z){
        Point p2 = this.getPoint();
        p2.z += z;
        return p2;
    }
    //sub x to the point and return a new point
    public Point subX(double x){
        Point p2 = this.getPoint();
        p2.x -= x;
        return p2;
    }
    
    //sub y to the point and return a new point
    public Point subY(double y){
        Point p2 = this.getPoint();
        p2.y -= y;
        return p2;
    }
    
    //sub z to the point and return a new point
    public Point subZ(double z){
        Point p2 = this.getPoint();
        p2.z -= z;
        return p2;
    }
}
