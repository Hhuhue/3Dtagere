package old.Domaine;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author thomasdrouin
 */
public class Planche extends Vector implements Serializable{
    
    private Planche() {};
          
    public Planche( Point origine,
                    double x, 
                    double y, 
                    double z,
                    String nom) {

                this.origine = origine;
                this.x = x;
                this.y = y;
                this.z = z;
                this.nom = nom;
        
    }
    
    private String nom;
    
    public String getNom(){
        return this.nom;
    }
    public double getLargeur(){
        return this.x;
    }
    
    public double getHauteur(){
        return this.y;
    }    
    
    public double getProfondeur(){
        return this.z;
    } 
    
    public boolean isSameAs(Planche planche){
        boolean isSame = false;
        if( this.origine.getX() == planche.origine.getX() 
            && this.origine.getY() == planche.origine.getY() 
            && this.origine.getZ() == planche.origine.getZ() 
            && this.x == planche.x 
            && this.y == planche.y 
            && this.z == planche.z){
            isSame = true;
        }
        return isSame;
    }

    // Overriding clone() method of Object class
    public Object clone() {
        try {
          return super.clone();
        }
        catch (CloneNotSupportedException e) {
          System.out.println("Cloning not allowed.");
          return this;
        }
    }
   
    public double getOrigineZ(){
        return this.origine.getZ();
    }
    
    public Planche getPlanche(){
        return new Planche(this.origine, this.x, this.y, this.z, this.nom);
    }
    
    private ArrayList<Point> getSommets(){
        Point origine = this.getOrigine();
        ArrayList<Point> pointsSommet = new ArrayList<>();
        pointsSommet.add(origine);
        pointsSommet.add(origine.addX(this.getX()));
        pointsSommet.add(origine.addY(this.getY()));
        pointsSommet.add(pointsSommet.get(1).addY(this.getY()));
        pointsSommet.add(pointsSommet.get(0).addZ(this.getZ()));
        pointsSommet.add(pointsSommet.get(1).addZ(this.getZ()));
        pointsSommet.add(pointsSommet.get(2).addZ(this.getZ()));
        pointsSommet.add(pointsSommet.get(3).addZ(this.getZ()));
        return pointsSommet;
    }
    public ArrayList<Triangle> getListeTriangles(){
        ArrayList<Point> pts = this.getSommets();
        ArrayList<Triangle> triangles = new ArrayList<>();
        triangles.add(new Triangle(pts.get(3),pts.get(1),pts.get(0)));
        triangles.add(new Triangle(pts.get(0),pts.get(2),pts.get(3)));
        triangles.add(new Triangle(pts.get(0),pts.get(1),pts.get(5)));
        triangles.add(new Triangle(pts.get(0),pts.get(5),pts.get(4)));
        triangles.add(new Triangle(pts.get(0),pts.get(6),pts.get(2)));
        triangles.add(new Triangle(pts.get(0),pts.get(4),pts.get(6)));
        triangles.add(new Triangle(pts.get(7),pts.get(4),pts.get(5)));
        triangles.add(new Triangle(pts.get(7),pts.get(6),pts.get(4)));
        triangles.add(new Triangle(pts.get(7),pts.get(3),pts.get(2)));
        triangles.add(new Triangle(pts.get(7),pts.get(2),pts.get(6)));
        triangles.add(new Triangle(pts.get(7),pts.get(1),pts.get(3)));
        triangles.add(new Triangle(pts.get(7),pts.get(5),pts.get(1)));
        return triangles;
        
        
    }
}
