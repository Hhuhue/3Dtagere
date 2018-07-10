/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package old.UI;

/**
 *
 * @author thomasdrouin
 */
import old.Controller.Controller;
import old.Domaine.Planche;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
 
  public class DrawingPanel extends javax.swing.JPanel{
 
   private List<Rectangle2D.Double> planches = new ArrayList<>();
   private List<Rectangle2D.Double> planchesSelectionnees = new ArrayList<>();
   
    public DrawingPanel(){
    }   
        
    public void update(Controller controller){
    this.planches.clear();
    planchesSelectionnees.clear();
    for(Planche planche : controller.getZoomedPlanches(this.getRatio(controller))){
            this.centerPlanche(planche, controller);
            this.addPlanche(planche);
        }
    this.repaint();   
    }
    
    public double getRatio(Controller controller){
        double ratio = this.getSize().getWidth()/50.00;
        if(controller.getEtagere().getHauteur()*ratio>this.getSize().getHeight()-40 || controller.getEtagere().getLargeur()*ratio>this.getSize().getWidth()-40){
            while(controller.getEtagere().getHauteur()*ratio>this.getSize().getHeight()-40 || controller.getEtagere().getLargeur()*ratio>this.getSize().getWidth()-40){
                ratio -= 0.01;
            }
        }
        return ratio;
    }    
    

    public void centerPlanche(Planche planche, Controller controller){
            planche.setOrigine(planche.getOrigine().addX(this.getSize().getWidth()/2-controller.getEtagere().getLargeur()*this.getRatio(controller)/2));
            planche.setOrigine(planche.getOrigine().addY(this.getSize().getHeight()/2-controller.getEtagere().getHauteur()*this.getRatio(controller)/2));
    }
        
    public void addPlanche(Planche planche){
        double x= planche.getOrigine().getX();
        double y= planche.getOrigine().getY();
        double w= planche.getX();
        double h= planche.getY();
        Rectangle2D.Double rectangle = new Rectangle2D.Double(x, y, w, h);
        if(planche.getOrigineZ()==0){
            planches.add(rectangle);
        }
    }
    
    public void selectionPlanche(Planche planche, Controller controller){
        planchesSelectionnees.clear();
        this.centerPlanche(planche, controller);
        double x= planche.getOrigine().getX();
        double y= planche.getOrigine().getY();
        double w= planche.getX();
        double h= planche.getY();
        Rectangle2D.Double rectangle = new Rectangle2D.Double(x, y, w, h);
        planchesSelectionnees.add(rectangle);
        this.repaint();   
    }
    
    public void selectionPlanches(ArrayList<Planche> planches, Controller controller){
        planchesSelectionnees.clear();
        for(Planche planche:planches){
            this.centerPlanche(planche, controller);
            double x= planche.getOrigine().getX();
            double y= planche.getOrigine().getY();
            double w= planche.getX();
            double h= planche.getY();
            Rectangle2D.Double rectangle = new Rectangle2D.Double(x, y, w, h);
            if(planche.getOrigineZ()==0){
                planchesSelectionnees.add(rectangle);
            }        }
        this.repaint();   
    }
    
    public void clearSelectionPlanche(Controller controller){
        planchesSelectionnees.clear();
        this.update(controller);
    }
    
    @Override
    protected void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            //Met l'origine en bas a droite
            g2.scale(1, -1);
            g2.translate(0, -getHeight());

            //trait des lignes
            g2.setStroke(new BasicStroke(1f, BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL));
            g2.setColor(Color.BLACK);
            
            for (Rectangle2D.Double rect : planches) {
                g2.draw(rect);            
            }
            g2.setColor(Color.RED);
            for(Rectangle2D.Double rect : planchesSelectionnees){
                g2.draw(rect);            
            }
            
        }
     
        
  }
        

