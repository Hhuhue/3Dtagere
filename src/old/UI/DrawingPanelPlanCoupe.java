/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package old.UI;


import old.Controller.Controller;
import old.Domaine.Panneau;
import old.Domaine.Planche;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 *
 * @author crouman
 */
public class DrawingPanelPlanCoupe extends javax.swing.JPanel
{
   private Controller controller;
   private ArrayList<Rectangle2D.Double> mesRectanglesPanneaux = new ArrayList<>();
   private ArrayList<Rectangle2D.Double> mesRectanglesPlanches = new ArrayList<>();
   private ArrayList<Panneau> mesPanneaux = new ArrayList<>();
   private ArrayList<Planche> mesPlanches = new ArrayList<>();
   private Panneau monPanneau;
   private String nomPlanche;
   private ArrayList<String> listeNoms = new ArrayList<>();
   
   
    public DrawingPanelPlanCoupe()
    {
        
    }   
    
     
     int nombreDePanneau = mesPanneaux.size();
   
     
     public void setDPController(Controller p_controller)  
     {
         this.controller = p_controller;
     }
   
   public void dessinerPanneaux()
   {
       double r = 1.5;
       double X = 10; // En majuscule veut dire pour les panneaux
       double Y = 10;
       double W = 96*r;
       double H = 48*r;
       int compteur = 0;
       double x = X;
       double y = Y;
       double w;
       double h;
       mesRectanglesPanneaux.clear();
       mesRectanglesPlanches.clear();
       mesPanneaux = controller.genererPlan2();
       
       
       
       
       for(int i = 0 ; i < this.mesPanneaux.size() ; i++ ) // Itere dans tous les panneaux
       {
           y = Y;
           x = X;
           
           Rectangle2D.Double monRectanglePanneau =  new Rectangle2D.Double(X,Y,W,H);
           mesRectanglesPanneaux.add(monRectanglePanneau);
           monPanneau = this.mesPanneaux.get(i);
           
           
           
           for(int j = 0 ; j < this.monPanneau.getNbEtage() ; j++)
           {
               mesPlanches = this.monPanneau.getPlanchesPanneau().get(j);
               for(int k = 0 ; k < mesPlanches.size() ; k++)
               {
                   Planche maPlanche = mesPlanches.get(k);
                   if(maPlanche.getLargeur() < maPlanche.getHauteur())
                   {
                       w = maPlanche.getHauteur()*r;
                   } else {
                       w = maPlanche.getLargeur()*r;
                   }
                   if (maPlanche.getProfondeur()>maPlanche.getHauteur()){
                       h = maPlanche.getProfondeur()*r;
                   }
                   else {
                       h = controller.getEtagere().getProfondeur()*r;
                   }
                   
                   
                   Rectangle2D.Double monRectanglePlanche = new Rectangle2D.Double(x,y,w,h);
                   listeNoms.add(maPlanche.getNom());
                   
                   x += w;
                   mesRectanglesPlanches.add(monRectanglePlanche);
                   
               }
               x = X;
               y += controller.getEtagere().getProfondeur()*r;
           }
           
           
           
           X = X + 100*r; // DÈcaler le prochain Panneau sur le DrawingPannel
           
           compteur++ ;
           if(compteur == 3)
           {
               X = 10;
               Y = Y + 60*r;
               compteur = 0;
           }
  
       }
       
       this.repaint();
   }
   
   
    
   
    
   /*public void update(Controller controller){
    this.planches.clear();
    planchesSelectionnees.clear();
    for(Planche planche : controller.getZoomedPlanches(this.getRatio(controller))){
            this.centerPlanche(planche, controller);
            this.addPlanche(planche);
        }
    this.repaint();   
    }*/


    
    
     @Override
    protected void paintComponent(Graphics g){
        
            super.paintComponent(g);
            
            Graphics2D g2 = (Graphics2D) g;
            //Met l'origine en bas a droite
            

            //trait des lignes
            g2.setStroke(new BasicStroke(2f, BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL));
            g2.setColor(Color.BLACK);

            Font myFont = new Font("Courier New", 1, 11);
            g2.setFont(myFont);
            for (Rectangle2D.Double rect : mesRectanglesPanneaux ) {
                g2.draw(rect);
                
            }
            
            g2.setStroke(new BasicStroke(1f, BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL));
            g2.setColor(Color.BLACK);
            int i = 1;
            for (Rectangle2D.Double rect : mesRectanglesPlanches) {
                g2.draw(rect);
                g2.drawString(""+i, (float)(rect.getX()), (float)(rect.getY()+ rect.getHeight()/2));
                i++;
                
            }
//            for (Rectangle2D.Double rect : mesRectanglesPlanches) {
//                g2.draw(rect);            
//            }
//            
            
            
        }
    
    
   }
