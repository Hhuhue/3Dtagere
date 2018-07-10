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
public class Vector implements Serializable{
    
    protected Vector(){}
    
    public Vector(  Point origine,
                    double x, 
                    double y, 
                    double z) {
        
        this.origine = origine;
        this.x = x;
        this.y = y;
        this.z = z;
        
    }
    
    public Vector(Vector vecteur) {
        
        this.origine = vecteur.getOrigine();
        this.x = vecteur.getX();
        this.y = vecteur.getY();
        this.z = vecteur.getZ();
        
    }
    protected Point origine;
    protected double x;
    protected double y;
    protected double z;
    
            
    public Point getOrigine(){
        return this.origine.getPoint();
    }
    
    public double getX(){
        double vX = this.x;
        return vX;
    }
    
    public double getY(){
        double vY = this.y;
        return vY;
    }
    
    public double getZ(){
        double vZ = this.z;
        return vZ;
    }
            
    public void setVector(Vector vecteur){
        this.origine = vecteur.origine;
        this.x = vecteur.getX();
        this.y = vecteur.getY();
        this.z = vecteur.getZ();
    }
    
    public void setX(double X){
        this.x = X;
    }    
    
    public void setY(double Y){
        this.y = Y;
    } 
    
    public void setZ(double Z){
        this.z = Z;
    }
    
    public void setOrigine(Point nouvelOrigine){
        this.origine = nouvelOrigine;
    }

    public void subX(double X){
        this.x -= X;
    }    
    
    public void subY(double Y){
        this.y = Y;
    } 
    
    public void subZ(double Z){
        this.z = Z;
    }
}
