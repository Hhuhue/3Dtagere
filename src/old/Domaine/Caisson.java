/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package old.Domaine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author thomasdrouin
 */
public class Caisson  implements Serializable{

    public Caisson( Point origine,
                    double largeur, 
                    double hauteur, 
                    double profondeur, 
                    boolean isTop, 
                    boolean isBottom, 
                    boolean isSideG,
                    boolean isSideD,
                    double epD,
                    double epT) {
        
        this.origine = origine;
        this.largeurDispo = largeur;
        this.hauteurDispo = hauteur;
        this.profondeur = profondeur;
        this.isTop = isTop;
        this.isBottom = isBottom;
        this.isSideG = isSideG;
        this.isSideD = isSideD;
        this.epD = epD;
        this.epT = epT;                
    }
    
    private Point origine;
    private double largeurDispo;
    private double hauteurDispo;
    private double profondeur;
    private boolean isTop;
    private boolean isBottom;
    private boolean isSideG;
    private boolean isSideD;
    private double epD;
    private double epT;

    public void setEpD(double epD){
        this.epD = epD;
    }
    
    public void setEpT(double epT){
        this.epT = epT;
    }

    public void setTop(boolean bool){
        this.isTop = bool;
    }
        
    public void addOrigineX(double x){
        this.setOrigineX(this.origine.getX()+x);
    }
    
    public void setOrigineY(double yOrigine){
        this.origine.setY(yOrigine);
    }   
    
    public void setOrigineX(double xOrigine){
        this.origine.setX(xOrigine);
    } 
        
    public void setOrigine(Point origine){
        this.origine = origine;
    }   
    
    private String getCaissonName(int numEtage, int numCaisson){
        return "Etage "+Integer.toString(numEtage)+", Caisson "+Integer.toString(numCaisson)+ ", ";
    }
    //TODO
    public ArrayList<Planche> getPlanches(int numEtage, int numCaisson){
        ArrayList<Planche> planches = new ArrayList<>();
        double profondeur = this.profondeur;
        List<String> liste = new ArrayList<String>();
        for (int i = 1; i < 10; i++) {
            liste.add(Integer.toString(i));
        }
        Iterator iterator = liste.iterator();
        
        if(Choix.getInstance().isAvecFond()){
            profondeur -= 0.5;
        }
        if(Choix.getInstance().isTripleContour()){
            
            if(this.isTop & this.isBottom){
                if(this.isSideG & this.isSideD){
                    planches.add(new Planche(   this.origine, 
                                                epT, 
                                                hauteurDispo+2*epT, 
                                                profondeur,
                                                getCaissonName(numEtage, numCaisson) + "Montant gauche externe"));  
                    
                     planches.add(new Planche(   this.origine.addX(epT).addY(epT), 
                                                epT, 
                                                hauteurDispo+epT, 
                                                profondeur,
                                                getCaissonName(numEtage, numCaisson) + "Montant gauche interne"));          
                     
                     planches.add(new Planche(   this.origine.addX(epT), 
                                                this.largeurDispo + 2*epT, 
                                                epT, 
                                                profondeur,
                                                getCaissonName(numEtage, numCaisson) + "Base externe"));
                    
                    planches.add(new Planche(   this.origine.addX(2*epT).addY(epT), 
                                                this.largeurDispo, 
                                                epT, 
                                                profondeur,
                                                getCaissonName(numEtage, numCaisson) + "Base interne"));    
                    
                    planches.add(new Planche(   this.origine.addX(largeurDispo + 2*epT).addY(epT), 
                                                epT, 
                                                hauteurDispo+epT, 
                                                profondeur,
                                                getCaissonName(numEtage, numCaisson) + "Montant droit interne"));
                    
                    planches.add(new Planche(   this.origine.addX(largeurDispo + 3*epT), 
                                                epT, 
                                                hauteurDispo+2*epT, 
                                                profondeur,
                                                getCaissonName(numEtage, numCaisson) + "Montant droit externe"));
                    
                    if(Choix.getInstance().isAvecFond()){
                        planches.add(new Planche(   this.origine.addX(epT).addY(epT).addZ(profondeur), 
                                                    this.largeurDispo+2*epT, 
                                                    this.hauteurDispo+2*epT, 
                                                    0.5,
                                                    getCaissonName(numEtage, numCaisson) + "fond"));             
                    }
                }
                else if(this.isSideG){
                    planches.add(new Planche(   this.origine, 
                                                epT, 
                                                hauteurDispo+2*epT, 
                                                profondeur,
                                                getCaissonName(numEtage, numCaisson) + "Montant gauche externe"));  
                    
                    planches.add(new Planche(   this.origine.addX(epT), 
                                                this.largeurDispo + epT, 
                                                epT, 
                                                profondeur,
                                                getCaissonName(numEtage, numCaisson) + "Base externe"));
                    
                    planches.add(new Planche(   this.origine.addX(epT).addY(epT), 
                                                epT, 
                                                hauteurDispo+epT, 
                                                profondeur,
                                                getCaissonName(numEtage, numCaisson) + "Montant gauche interne"));  
                    
                    planches.add(new Planche(   this.origine.addX(2*epT).addY(epT), 
                                                this.largeurDispo, 
                                                epT, 
                                                profondeur,
                                                getCaissonName(numEtage, numCaisson) + "Base interne"));  
                    
                    planches.add(new Planche(   this.origine.addX(largeurDispo + 2*epT), 
                                                epT, 
                                                hauteurDispo+2*epT, 
                                                profondeur,
                                                getCaissonName(numEtage, numCaisson) + "Montant droit"));  
                    
                    if(Choix.getInstance().isAvecFond()){
                        planches.add(new Planche(   this.origine.addX(epT).addY(epT).addZ(profondeur), 
                                                    this.largeurDispo+2*epT, 
                                                    this.hauteurDispo+2*epT, 
                                                    0.5, 
                                                    getCaissonName(numEtage, numCaisson) + "fond"));                
                    }
                }
                else if(this.isSideD){
                    planches.add(new Planche(   this.origine, 
                                                epT, 
                                                hauteurDispo+2*epT, 
                                                profondeur,
                                                getCaissonName(numEtage, numCaisson) + "Montant gauche"));  
                    
                    planches.add(new Planche(   this.origine.addX(epT), 
                                                this.largeurDispo + epT, 
                                                epT, 
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Base externe"));  
                    
                    planches.add(new Planche(   this.origine.addX(epT).addY(epT), 
                                                this.largeurDispo, 
                                                epT, 
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Base interne"));  
                    
                    planches.add(new Planche(   this.origine.addX(this.largeurDispo+epT).addY(epT), 
                                                epT, 
                                                this.hauteurDispo+epT, 
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Montant droit interne"));  
                    
                    planches.add(new Planche(   this.origine.addX(largeurDispo + 2*epT), 
                                                epT, 
                                                hauteurDispo+2*epT, 
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Montant droit externe"));  

                    if(Choix.getInstance().isAvecFond()){
                        planches.add(new Planche(   this.origine.addY(epT).addZ(profondeur), 
                                                    this.largeurDispo+2*epT,
                                                    this.hauteurDispo+2*epT, 
                                                    0.5, 
                                                    getCaissonName(numEtage, numCaisson) + "fond"));                
                    }
                }
                else{
                    planches.add(new Planche(   this.origine, 
                                                epT, 
                                                hauteurDispo+2*epT, 
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Montant gauche"));  
                    
                    planches.add(new Planche(   this.origine.addX(epT), 
                                                this.largeurDispo, 
                                                epT, 
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Base externe"));  
                
                    planches.add(new Planche(   this.origine.addX(epT).addY(epT), 
                                                this.largeurDispo, 
                                                epT, 
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Base interne"));  
                    
                    planches.add(new Planche(   this.origine.addX(largeurDispo + epT), 
                                                epT, 
                                                hauteurDispo+2*epT, 
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Montant droit"));  
                    
                    if(Choix.getInstance().isAvecFond()){
                        planches.add(new Planche(   this.origine.addY(epT).addZ(profondeur), 
                                                    this.largeurDispo+2*epT,
                                                    this.hauteurDispo+2*epT, 
                                                    0.5, 
                                                    getCaissonName(numEtage, numCaisson) + "fond"));                
                    }
                }
            }
            else if(this.isTop){
                if(this.isSideG & this.isSideD){
                    planches.add(new Planche(   this.origine, 
                                                this.largeurDispo+4*epT, 
                                                epT, 
                                                profondeur,
                                                getCaissonName(numEtage, numCaisson) + "Base"));  
                    
                    planches.add(new Planche(   this.origine.addY(epT), 
                                                epT, 
                                                hauteurDispo, 
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Montant gauche externe"));  
                    
                    planches.add(new Planche(   this.origine.addX(epT).addY(epT), 
                                                epT, 
                                                hauteurDispo, 
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Montant gauche interne"));  
                    
                    planches.add(new Planche(   this.origine.addX(largeurDispo+2*epT).addY(epT), 
                                                epT, 
                                                hauteurDispo, 
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Montant droit interne"));  
                    
                    planches.add(new Planche(   this.origine.addX(largeurDispo+3*epT).addY(epT), 
                                                epT, 
                                                hauteurDispo, 
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Montant droit externe"));  
                    if(Choix.getInstance().isAvecFond()){
                        planches.add(new Planche(   this.origine.addX(epT).addZ(profondeur), 
                                                    this.largeurDispo+2*epT,
                                                    this.hauteurDispo+2*epT, 
                                                    0.5, 
                                                    getCaissonName(numEtage, numCaisson) + "fond"));                
                    }
                }
                else if(this.isSideG){
                    planches.add(new Planche(   this.origine, 
                                                this.largeurDispo+2*epT, 
                                                epT, 
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Base"));  
                    
                    planches.add(new Planche(   this.origine.addY(epT), 
                                                epT, 
                                                hauteurDispo, 
                                                profondeur,
                                                getCaissonName(numEtage, numCaisson) + "Montant gauche externe"));  
                    
                    planches.add(new Planche(   this.origine.addX(epT).addY(epT), 
                                                epT, 
                                                hauteurDispo, 
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Montant gauche interne"));  
                    
                    planches.add(new Planche(   this.origine.addX(largeurDispo+2*epT), 
                                                epT, 
                                                hauteurDispo+epT, 
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Montant droit"));  
                    
                    if(Choix.getInstance().isAvecFond()){
                        planches.add(new Planche(   this.origine.addX(epT).addZ(profondeur), 
                                                    this.largeurDispo+2*epT,
                                                    this.hauteurDispo+2*epT, 
                                                    0.5, 
                                                    getCaissonName(numEtage, numCaisson) + "fond"));                
                    }
                }
                else if(this.isSideD){
                    planches.add(new Planche(   this.origine, 
                                                epT, 
                                                hauteurDispo+epT, 
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Montant gauche"));  
                        
                    
                    planches.add(new Planche(   this.origine.addX(epT), 
                                                largeurDispo+2*epT, 
                                                epT, 
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Base"));  
                    
                    planches.add(new Planche(   this.origine.addX(largeurDispo+epT).addY(epT), 
                                                epT, 
                                                hauteurDispo, 
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Montant droit interne"));  
                    
                    planches.add(new Planche(   this.origine.addX(largeurDispo+2*epT).addY(epT), 
                                                epT, 
                                                hauteurDispo, 
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Montant droit externe"));  
                    
                    if(Choix.getInstance().isAvecFond()){
                        planches.add(new Planche(   this.origine.addZ(profondeur), 
                                                    this.largeurDispo+2*epT,
                                                    this.hauteurDispo+2*epT, 
                                                    0.5, 
                                                    getCaissonName(numEtage, numCaisson) + "fond"));                
                    }                    
                }
                else{
                    planches.add(new Planche(   this.origine, 
                                                epT, 
                                                hauteurDispo+epT, 
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Montant gauche"));  
                        
                    
                    planches.add(new Planche(   this.origine.addX(epT), 
                                                largeurDispo, 
                                                epT, 
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Base"));  
                    
                    planches.add(new Planche(   this.origine.addX(largeurDispo+epT), 
                                                epT, 
                                                hauteurDispo+epT, 
                                                profondeur,
                                                getCaissonName(numEtage, numCaisson) + "Montant droit"));  
                    
                    if(Choix.getInstance().isAvecFond()){
                        planches.add(new Planche(   this.origine.addZ(profondeur), 
                                                    this.largeurDispo+2*epT,
                                                    this.hauteurDispo+2*epT, 
                                                    0.5, 
                                                    getCaissonName(numEtage, numCaisson) + "fond"));                
                    }
                }
            }
            else if(this.isBottom){
                if(this.isSideG & this.isSideD){
                    planches.add(new Planche(   this.origine, 
                                                epT, 
                                                hauteurDispo+2*epT, 
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Montant gauche externe"));  
                    
                    planches.add(new Planche(   this.origine.addY(hauteurDispo+2*epT), 
                                                largeurDispo+4*epT, 
                                                epT, 
                                                profondeur,
                                                getCaissonName(numEtage, numCaisson) + "plafond"));  
                    
                    planches.add(new Planche(   this.origine.addX(epT), 
                                                this.largeurDispo + 2*epT, 
                                                epT, 
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Base externe "));  
                    
                    planches.add(new Planche(   this.origine.addX(epT).addY(epT), 
                                                epT, 
                                                hauteurDispo+epT, 
                                                profondeur,
                                                getCaissonName(numEtage, numCaisson) + "Montant gauche interne"));  
                    
                    planches.add(new Planche(   this.origine.addX(2*epT).addY(epT), 
                                                this.largeurDispo, 
                                                epT, 
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Base interne "));  
                    
                    planches.add(new Planche(   this.origine.addX(largeurDispo + 2*epT).addY(epT), 
                                                epT, 
                                                hauteurDispo+epT, 
                                                profondeur,
                                                getCaissonName(numEtage, numCaisson) + "Montant droit interne"));  
                    
                    planches.add(new Planche(   this.origine.addX(largeurDispo + 3*epT), 
                                                epT, 
                                                hauteurDispo+2*epT, 
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Montant droit externe"));  
                    
                    if(Choix.getInstance().isAvecFond()){
                        planches.add(new Planche(   this.origine.addX(epT).addY(epT).addZ(profondeur), 
                                                    this.largeurDispo+2*epT,
                                                    this.hauteurDispo+2*epT, 
                                                    0.5, 
                                                    getCaissonName(numEtage, numCaisson) + "fond"));                
                    }
                }
                
                else if(this.isSideG){
                    planches.add(new Planche(   this.origine, 
                                                epT, 
                                                hauteurDispo+2*epT, 
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Montant gauche externe"));  
                    
                    planches.add(new Planche(   this.origine.addY(hauteurDispo+2*epT), 
                                                largeurDispo+3*epT, 
                                                epT, 
                                                profondeur,
                                                getCaissonName(numEtage, numCaisson) + "Plafond"));  
                    
                    planches.add(new Planche(   this.origine.addX(epT), 
                                                this.largeurDispo + epT, 
                                                epT, 
                                                profondeur,
                                                getCaissonName(numEtage, numCaisson) + "Base externe"));  
                    
                    planches.add(new Planche(   this.origine.addX(epT).addY(epT), 
                                                epT, 
                                                hauteurDispo+epT, 
                                                profondeur,
                                                getCaissonName(numEtage, numCaisson) + "Montant gauche interne"));  
                    
                    planches.add(new Planche(   this.origine.addX(2*epT).addY(epT), 
                                                this.largeurDispo, 
                                                epT, 
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Base interne"));  
                    
                    planches.add(new Planche(   this.origine.addX(largeurDispo + 2*epT), 
                                                epT, 
                                                hauteurDispo+2*epT, 
                                                profondeur,
                                                getCaissonName(numEtage, numCaisson) + "Montant droit"));  
                    
                    if(Choix.getInstance().isAvecFond()){
                        planches.add(new Planche(   this.origine.addX(epT).addY(epT).addZ(profondeur), 
                                                    this.largeurDispo+2*epT,
                                                    this.hauteurDispo+2*epT, 
                                                    0.5, 
                                                    getCaissonName(numEtage, numCaisson)+"fond"));                
                    }                }
                
                else if(this.isSideD){
                    planches.add(new Planche(   this.origine, 
                                                epT, 
                                                hauteurDispo+2*epT, 
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Montant gauche"));  
                    
                    planches.add(new Planche(   this.origine.addY(hauteurDispo+2*epT), 
                                                largeurDispo+3*epT, 
                                                epT, 
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Plafond"));  
                    
                    planches.add(new Planche(   this.origine.addX(epT), 
                                                this.largeurDispo + epT, 
                                                epT, 
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Base externe"));  
                    
                    planches.add(new Planche(   this.origine.addX(epT).addY(epT), 
                                                this.largeurDispo, 
                                                epT, 
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Base interne"));  
                    
                    planches.add(new Planche(   this.origine.addX(largeurDispo + epT).addY(epT), 
                                                epT, 
                                                hauteurDispo+epT, 
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Montant droit interne"));  
                    
                    planches.add(new Planche(   this.origine.addX(largeurDispo + 2*epT), 
                                                epT, 
                                                hauteurDispo+2*epT,
                                                profondeur,
                                                getCaissonName(numEtage, numCaisson) + "Montant droit externe"));  
                    
                    if(Choix.getInstance().isAvecFond()){
                        planches.add(new Planche(   this.origine.addY(epT).addZ(profondeur), 
                                                    this.largeurDispo+2*epT,
                                                    this.hauteurDispo+2*epT, 
                                                    0.5, 
                                                    getCaissonName(numEtage, numCaisson) + "fond"));                
                    }
                }
                else{
                    planches.add(new Planche(   this.origine, 
                                                epT, 
                                                hauteurDispo+2*epT, 
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Montant gauche"));  
                    
                    planches.add(new Planche(   this.origine.addY(hauteurDispo+2*epT), 
                                                largeurDispo+2*epT, 
                                                epT, 
                                                profondeur,
                                                getCaissonName(numEtage, numCaisson) + "Plafond"));  
                    
                    planches.add(new Planche(   this.origine.addX(epT), 
                                                this.largeurDispo, 
                                                epT, 
                                                profondeur,
                                                getCaissonName(numEtage, numCaisson) + "Base externe"));  
                    
                    planches.add(new Planche(   this.origine.addX(epT).addY(epT), 
                                                this.largeurDispo, 
                                                epT, 
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Base interne"));  
                    
                    planches.add(new Planche(   this.origine.addX(largeurDispo + epT), 
                                                epT, 
                                                hauteurDispo+2*epT, 
                                                profondeur,
                                                getCaissonName(numEtage, numCaisson) + "Montant droit"));  
                    
                    if(Choix.getInstance().isAvecFond()){
                        planches.add(new Planche(   this.origine.addY(epT).addZ(profondeur), 
                                                    this.largeurDispo+2*epT,
                                                    this.hauteurDispo+2*epT, 
                                                    0.5, 
                                                    getCaissonName(numEtage, numCaisson) + "fond"));                
                    }
                }
            }
            //etage du centre
            else{
                if(this.isSideG & this.isSideD){
                    planches.add(new Planche(   this.origine, 
                                                this.largeurDispo+4*epT, 
                                                epT, 
                                                profondeur,
                                                getCaissonName(numEtage, numCaisson) + "Base"));  
                    
                    planches.add(new Planche(   this.origine.addY(hauteurDispo+epT), 
                                                this.largeurDispo+4*epT, 
                                                epT, 
                                                profondeur,
                                                getCaissonName(numEtage, numCaisson) + "Plafond"));  
                    
                    planches.add(new Planche(   this.origine.addY(epT), 
                                                epT, 
                                                hauteurDispo, 
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Montant gauche externe"));  
                    
                    planches.add(new Planche(   this.origine.addX(epT).addY(epT), 
                                                epT, 
                                                hauteurDispo, 
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Montant gauche interne"));  
                    
                    planches.add(new Planche(   this.origine.addX(largeurDispo+2*epT).addY(epT), 
                                                epT, 
                                                hauteurDispo, 
                                                profondeur,
                                                getCaissonName(numEtage, numCaisson) + "Montant droit interne"));  
                    
                    planches.add(new Planche(   this.origine.addX(largeurDispo+3*epT).addY(epT), 
                                                epT, 
                                                hauteurDispo, 
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Montant droit externe"));  
                    
                    if(Choix.getInstance().isAvecFond()){
                        planches.add(new Planche(   this.origine.addX(epT).addZ(profondeur), 
                                                    this.largeurDispo+2*epT,
                                                    this.hauteurDispo+2*epT, 
                                                    0.5,
                                                    getCaissonName(numEtage, numCaisson) + "fond"));                
                    }
                }
                
                else if(this.isSideG){
                    planches.add(new Planche(   this.origine, 
                                                this.largeurDispo+2*epT, 
                                                epT, 
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Base"));  
                    
                    planches.add(new Planche(   this.origine.addY(hauteurDispo+epT), 
                                                this.largeurDispo+3*epT, 
                                                epT, 
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Plafond"));  
                                        
                    planches.add(new Planche(   this.origine.addY(epT), 
                                                epT, 
                                                hauteurDispo, 
                                                profondeur,
                                                getCaissonName(numEtage, numCaisson) + "Montant gauche externe"));  
                    
                    planches.add(new Planche(   this.origine.addX(epT).addY(epT), 
                                                epT, 
                                                hauteurDispo, 
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Montant gauche interne"));  
                    
                    planches.add(new Planche(   this.origine.addX(largeurDispo+2*epT), 
                                                epT, 
                                                hauteurDispo+epT, 
                                                profondeur,
                                                getCaissonName(numEtage, numCaisson) + "Montant droit"));  
                    
                    if(Choix.getInstance().isAvecFond()){
                        planches.add(new Planche(   this.origine.addX(epT).addZ(profondeur), 
                                                    this.largeurDispo+2*epT,
                                                    this.hauteurDispo+2*epT, 
                                                    0.5, getCaissonName(numEtage, numCaisson) + "fond"));                
                    }
                }
                else if(this.isSideD){
                    planches.add(new Planche(   this.origine, 
                                                epT, 
                                                hauteurDispo+epT, 
                                                profondeur,
                                                getCaissonName(numEtage, numCaisson) + "Montant gauche"));  
                        
                    planches.add(new Planche(   this.origine.addY(hauteurDispo+epT), 
                                                this.largeurDispo+3*epT, 
                                                epT, 
                                                profondeur,
                                                getCaissonName(numEtage, numCaisson) + "Plafond"));  
                    
                    planches.add(new Planche(   this.origine.addX(epT), 
                                                largeurDispo+2*epT, 
                                                epT, 
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Base"));  
                    
                    planches.add(new Planche(   this.origine.addX(largeurDispo+epT).addY(epT), 
                                                epT, 
                                                hauteurDispo, 
                                                profondeur,
                                                getCaissonName(numEtage, numCaisson) + "Montant droit interne"));  
                    
                    planches.add(new Planche(   this.origine.addX(largeurDispo+2*epT).addY(epT), 
                                                epT, 
                                                hauteurDispo, 
                                                profondeur,
                                                getCaissonName(numEtage, numCaisson) + "Montant droit externe"));  
                    
                    if(Choix.getInstance().isAvecFond()){
                        planches.add(new Planche(   this.origine.addZ(profondeur), 
                                                    this.largeurDispo+2*epT,
                                                    this.hauteurDispo+2*epT, 
                                                    0.5, 
                                                    getCaissonName(numEtage, numCaisson) + "fond"));                
                    }                    
                }
                else{
                    planches.add(new Planche(   this.origine, 
                                                epT, 
                                                hauteurDispo+epT, 
                                                profondeur,
                                                getCaissonName(numEtage, numCaisson) + "Montant gauche"));  
                        
                    planches.add(new Planche(   this.origine.addY(hauteurDispo+epT), 
                                                this.largeurDispo+2*epT, 
                                                epT, 
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Plafond"));  
                    
                    planches.add(new Planche(   this.origine.addX(epT), 
                                                largeurDispo, 
                                                epT, 
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Base"));  
                    
                    planches.add(new Planche(   this.origine.addX(largeurDispo+epT), 
                                                epT, 
                                                hauteurDispo+epT, 
                                                profondeur,
                                                getCaissonName(numEtage, numCaisson) + "Montant droit"));  
                    
                    if(Choix.getInstance().isAvecFond()){
                        planches.add(new Planche(   this.origine.addZ(profondeur), 
                                                    this.largeurDispo+2*epT,
                                                    this.hauteurDispo+2*epT, 
                                                    0.5, 
                                                    getCaissonName(numEtage, numCaisson) + "fond"));                
                    }
                }
            }
            
        }
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //isDoubleContour
        else{
            if(this.isTop & this.isBottom){
                if(this.isSideG & this.isSideD){
                    planches.add(new Planche(   this.origine, 
                                                epD, 
                                                hauteurDispo+epD, 
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Montant gauche"));  
                    
                    planches.add(new Planche(   this.origine.addX(epD), 
                                                this.largeurDispo, 
                                                epD,
                                                profondeur,
                                                getCaissonName(numEtage, numCaisson) + "Base"));  
                    
                    planches.add(new Planche(   this.origine.addX(epD+this.largeurDispo), 
                                                epD,      
                                                this.hauteurDispo + epD, 
                                                profondeur,
                                                getCaissonName(numEtage, numCaisson) + "Montant droit"));  
                    
                    if(Choix.getInstance().isAvecFond()){
                        planches.add(new Planche(   this.origine.addZ(profondeur), 
                                                    this.largeurDispo+2*epD, 
                                                    this.hauteurDispo+2*epD, 
                                                    0.5,
                                                    getCaissonName(numEtage, numCaisson) + "fond"));                
                    }              
                }
                else if(this.isSideG){
                    planches.add(new Planche(   this.origine, 
                                                epD, 
                                                this.hauteurDispo+epD, 
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Montant gauche"));  
                    
                    planches.add(new Planche(   this.origine.addX(epD), 
                                                this.largeurDispo, 
                                                epD,
                                                profondeur,
                                                getCaissonName(numEtage, numCaisson) + "Base"));  
                    
                    planches.add(new Planche(   this.origine.addX(epD+this.largeurDispo), 
                                                epT,      
                                                this.hauteurDispo + epD, 
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Montant droit"));  
                    
                    if(Choix.getInstance().isAvecFond()){
                        planches.add(new Planche(   this.origine.addZ(profondeur), 
                                                    this.largeurDispo+epD+epT, 
                                                    this.hauteurDispo+2*epD, 
                                                    0.5,
                                                    getCaissonName(numEtage, numCaisson) + "fond"));                
                    }              
                }
                else if(this.isSideD){
                    planches.add(new Planche(   this.origine, 
                                                epT, 
                                                this.hauteurDispo+epD, 
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Montant gauche"));  
                    
                    planches.add(new Planche(   this.origine.addX(epT), 
                                                this.largeurDispo, 
                                                epD,
                                                profondeur,
                                                getCaissonName(numEtage, numCaisson) + "Base"));  
                    
                    planches.add(new Planche(   this.origine.addX(epT+this.largeurDispo), 
                                                epD,      
                                                this.hauteurDispo + epD, 
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Montant droit"));  
                    
                    if(Choix.getInstance().isAvecFond()){
                        planches.add(new Planche(   this.origine.addZ(profondeur), 
                                                    this.largeurDispo+epD+epT, 
                                                    this.hauteurDispo+2*epD, 
                                                    0.5,
                                                    getCaissonName(numEtage, numCaisson) + "fond"));                
                    }    
                }
                else{
                    planches.add(new Planche(   this.origine, 
                                                epT, 
                                                this.hauteurDispo+epD, 
                                                profondeur,
                                                getCaissonName(numEtage, numCaisson) + "Montant gauche"));  
                    
                    planches.add(new Planche(   this.origine.addX(epT), 
                                                this.largeurDispo, 
                                                epD,
                                                profondeur,
                                                getCaissonName(numEtage, numCaisson) + "Base"));  
                    
                    planches.add(new Planche(   this.origine.addX(epT+this.largeurDispo), 
                                                epT,      
                                                this.hauteurDispo + epD, 
                                                profondeur,
                                                getCaissonName(numEtage, numCaisson) + "Montant droit"));  
                    
                    if(Choix.getInstance().isAvecFond()){
                        planches.add(new Planche(   this.origine.addZ(profondeur), 
                                                    this.largeurDispo+2*epT, 
                                                    this.hauteurDispo+2*epD, 
                                                    0.5,
                                                    getCaissonName(numEtage, numCaisson) + "fond"));                
                    }    
                }
            }
            else if(this.isTop){
                if(this.isSideG & this.isSideD){
                    planches.add(new Planche(   this.origine, 
                                                this.largeurDispo+2*epD, 
                                                epT,
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Base"));  
                    
                    planches.add(new Planche(   this.origine.addY(epT), 
                                                epD,
                                                this.hauteurDispo, 
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Montant gauche"));  
                    
                    planches.add(new Planche(   this.origine.addX(epD+this.largeurDispo).addY(epT), 
                                                epD,      
                                                this.hauteurDispo,                  
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Montant droit"));  
                    
                    if(Choix.getInstance().isAvecFond()){
                        planches.add(new Planche(   this.origine.addZ(profondeur), 
                                                    this.largeurDispo+2*epD, 
                                                    this.hauteurDispo+epT+epD, 
                                                    0.5, 
                                                    getCaissonName(numEtage, numCaisson) + "fond"));                
                    }    
                }
                else if(this.isSideG){
                    planches.add(new Planche(   this.origine, 
                                                this.largeurDispo+epD, 
                                                epT,
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Base"));  
                    
                    planches.add(new Planche(   this.origine.addY(epT), 
                                                epD,
                                                this.hauteurDispo, 
                                                profondeur,
                                                getCaissonName(numEtage, numCaisson) + "Montant gauche"));  
                    
     
                    planches.add(new Planche(   this.origine.addX(epD+this.largeurDispo), 
                                                epT,      
                                                this.hauteurDispo+epT,                  
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Montant droit"));  
                    
                    if(Choix.getInstance().isAvecFond()){
                        planches.add(new Planche(   this.origine.addZ(profondeur), 
                                                    this.largeurDispo+epT+epD, 
                                                    this.hauteurDispo+epT+epD, 
                                                    0.5, 
                                                    getCaissonName(numEtage, numCaisson) + "fond"));                
                    }   
                }
                else if(this.isSideD){
                    planches.add(new Planche(   this.origine, 
                                                epT, 
                                                this.hauteurDispo+epT,
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Montant gauche"));  
                    
                    planches.add(new Planche(   this.origine.addX(epT), 
                                                this.largeurDispo+epD,
                                                epT, 
                                                profondeur,
                                                getCaissonName(numEtage, numCaisson) + "Base"));  
                    
     
                    planches.add(new Planche(   this.origine.addX(epT+this.largeurDispo).addY(epT), 
                                                epD,      
                                                this.hauteurDispo,                  
                                                profondeur,
                                                getCaissonName(numEtage, numCaisson) + "Montant droit"));  
                    
                    if(Choix.getInstance().isAvecFond()){
                        planches.add(new Planche(   this.origine.addZ(profondeur), 
                                                    this.largeurDispo+epT+epD, 
                                                    this.hauteurDispo+epT+epD, 
                                                    0.5,
                                                    getCaissonName(numEtage, numCaisson) + "fond"));                
                    }   
                }
                else{
                    planches.add(new Planche(   this.origine, 
                                                epT, 
                                                this.hauteurDispo+epT,
                                                profondeur,
                                                getCaissonName(numEtage, numCaisson) + "Montant gauche"));  
                    
                    planches.add(new Planche(   this.origine.addX(epT), 
                                                this.largeurDispo,
                                                epT, 
                                                profondeur,
                                                getCaissonName(numEtage, numCaisson) + "Base"));  
                    
     
                    planches.add(new Planche(   this.origine.addX(epT+this.largeurDispo), 
                                                epT,      
                                                this.hauteurDispo+epT,                  
                                                profondeur,
                                                getCaissonName(numEtage, numCaisson) + "Montant droit"));  
                    
                    if(Choix.getInstance().isAvecFond()){
                        planches.add(new Planche(   this.origine.addZ(profondeur), 
                                                    this.largeurDispo+2*epT, 
                                                    this.hauteurDispo+epT+epD, 
                                                    0.5, 
                                                    getCaissonName(numEtage, numCaisson) + "fond"));                
                    }   
                }
            }
            else if(this.isBottom){
                if(this.isSideG & this.isSideD){
                    planches.add(new Planche(   this.origine, 
                                                epD, 
                                                epD+this.hauteurDispo,
                                                profondeur,
                                                getCaissonName(numEtage, numCaisson) + "Montant gauche"));  
                    
                    planches.add(new Planche(   this.origine.addX(epD), 
                                                this.largeurDispo,
                                                epD, 
                                                profondeur,
                                                getCaissonName(numEtage, numCaisson) + "Base"));  
                    
                    planches.add(new Planche(   this.origine.addY(epD+this.hauteurDispo), 
                                                this.largeurDispo+2*epD,      
                                                epT,                  
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Plafond"));  
                    
                    planches.add(new Planche(   this.origine.addX(epD+this.largeurDispo), 
                                                epD,      
                                                epD+this.hauteurDispo,                  
                                                profondeur,
                                                getCaissonName(numEtage, numCaisson) + "Montant droit"));  
                    
                    if(Choix.getInstance().isAvecFond()){
                        planches.add(new Planche(   this.origine.addZ(profondeur), 
                                                    this.largeurDispo+2*epD, 
                                                    this.hauteurDispo+epT+epD, 
                                                    0.5,
                                                    getCaissonName(numEtage, numCaisson) + "fond"));                
                    }   
                }
                else if(this.isSideG){
                    planches.add(new Planche(   this.origine, 
                                                epD, 
                                                epD+this.hauteurDispo,
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Montant gauche"));  
                    
                    planches.add(new Planche(   this.origine.addX(epD), 
                                                this.largeurDispo,
                                                epD, 
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Base"));  
                    
                    planches.add(new Planche(   this.origine.addY(epD+this.hauteurDispo), 
                                                this.largeurDispo+epD+epT,      
                                                epT,                  
                                                profondeur,
                                                getCaissonName(numEtage, numCaisson) + "Plafond"));  
                    
                    planches.add(new Planche(   this.origine.addX(epD+this.largeurDispo), 
                                                epT,      
                                                this.hauteurDispo+epD,                  
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Montant droit"));  
                    
                    if(Choix.getInstance().isAvecFond()){
                        planches.add(new Planche(   this.origine.addZ(profondeur), 
                                                    this.largeurDispo+epT+epD, 
                                                    this.hauteurDispo+epT+epD, 
                                                    0.5, 
                                                    getCaissonName(numEtage, numCaisson) + "fond"));                
                    }   
                }
                else if(this.isSideD){
                    planches.add(new Planche(   this.origine, 
                                                epT, 
                                                epD+this.hauteurDispo,
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Montant gauche"));  
                    
                    planches.add(new Planche(   this.origine.addX(epT), 
                                                this.largeurDispo,
                                                epD, 
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Base"));  
                    
                    planches.add(new Planche(   this.origine.addY(epD+this.hauteurDispo), 
                                                this.largeurDispo+epD+epT,      
                                                epT,                  
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Plafond"));  
                    
                    planches.add(new Planche(   this.origine.addX(epT+this.largeurDispo), 
                                                epD,      
                                                this.hauteurDispo+epD,                  
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Montant droit"));  
                    
                    if(Choix.getInstance().isAvecFond()){
                        planches.add(new Planche(   this.origine.addZ(profondeur), 
                                                    this.largeurDispo+epT+epD, 
                                                    this.hauteurDispo+epT+epD, 
                                                    0.5,
                                                    getCaissonName(numEtage, numCaisson) + "fond"));                
                    }  
                }
                else{
                    planches.add(new Planche(   this.origine, 
                                                epT, 
                                                epD+this.hauteurDispo,
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Montant gauche"));  
                    
                    planches.add(new Planche(   this.origine.addX(epT), 
                                                this.largeurDispo,
                                                epD, 
                                                profondeur,
                                                getCaissonName(numEtage, numCaisson) + "Base"));  
                    
                    planches.add(new Planche(   this.origine.addY(epD+this.hauteurDispo), 
                                                this.largeurDispo+2*epT,      
                                                epT,                  
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Plafond"));  
                    
                    planches.add(new Planche(   this.origine.addX(epT+this.largeurDispo), 
                                                epT,      
                                                this.hauteurDispo+epD,                  
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Montant droit"));  
                    
                    if(Choix.getInstance().isAvecFond()){
                        planches.add(new Planche(   this.origine.addZ(profondeur), 
                                                    this.largeurDispo+2*epT, 
                                                    this.hauteurDispo+epT+epD, 
                                                    0.5, 
                                                    getCaissonName(numEtage, numCaisson) + "fond"));                
                    }  
                }
            }
            //center
            else{
                if(this.isSideG & this.isSideD){
                    planches.add(new Planche(   this.origine, 
                                                this.largeurDispo+2*epD, 
                                                epT,
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Base"));  
                    
                    planches.add(new Planche(   this.origine.addY(epT), 
                                                epD,
                                                this.hauteurDispo, 
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Montant gauche"));  
                    
                    planches.add(new Planche(   this.origine.addX(epD+this.largeurDispo).addY(epT), 
                                                epD,      
                                                this.hauteurDispo,                  
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Montant droit"));  
                    
                    planches.add(new Planche(   this.origine.addY(epT+this.hauteurDispo), 
                                                this.largeurDispo+2*epD, 
                                                epT,
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Plafond"));  
                    
                    if(Choix.getInstance().isAvecFond()){
                        planches.add(new Planche(   this.origine.addZ(profondeur), 
                                                    this.largeurDispo+2*epD, 
                                                    this.hauteurDispo+2*epT, 
                                                    0.5, 
                                                    getCaissonName(numEtage, numCaisson) + "fond"));                
                    }  
                }
                else if(this.isSideG){
                    planches.add(new Planche(   this.origine, 
                                                this.largeurDispo+epD, 
                                                epT,
                                                profondeur,
                                                getCaissonName(numEtage, numCaisson) + "Base"));  
                    
                    planches.add(new Planche(   this.origine.addY(epT), 
                                                epD, 
                                                this.hauteurDispo,
                                                profondeur,
                                                getCaissonName(numEtage, numCaisson) + "Montant gauche"));  
                    
                    planches.add(new Planche(   this.origine.addY(epT+this.hauteurDispo), 
                                                this.largeurDispo+epD+epT,      
                                                epT,                  
                                                profondeur,
                                                getCaissonName(numEtage, numCaisson) + "Plafond"));  
                    
                    planches.add(new Planche(   this.origine.addX(epD+this.largeurDispo), 
                                                epT, 
                                                epT+hauteurDispo,
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Montant droit"));  
                    
                    if(Choix.getInstance().isAvecFond()){
                        planches.add(new Planche(   this.origine.addZ(profondeur), 
                                                    this.largeurDispo+epT+epD, 
                                                    this.hauteurDispo+2*epT, 
                                                    0.5, 
                                                    getCaissonName(numEtage, numCaisson) + "fond"));                
                    } 
                }
                else if(this.isSideD){
                    planches.add(new Planche(   this.origine, 
                                                epT, 
                                                this.hauteurDispo+epT,
                                                profondeur,
                                                getCaissonName(numEtage, numCaisson) + "Montant gauche"));  
                    
                    planches.add(new Planche(   this.origine.addX(epT), 
                                                this.largeurDispo+epD,
                                                epT, 
                                                profondeur,
                                                getCaissonName(numEtage, numCaisson) + "Base"));  
                    
                    planches.add(new Planche(   this.origine.addY(epT+this.hauteurDispo), 
                                                this.largeurDispo+epD+epT, 
                                                epT,
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Plafond"));  
                    
                    planches.add(new Planche(   this.origine.addX(epT+largeurDispo).addY(epT), 
                                                epD, 
                                                this.hauteurDispo,
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Montant droit"));  
                    
                    if(Choix.getInstance().isAvecFond()){
                        planches.add(new Planche(   this.origine.addZ(profondeur), 
                                                    this.largeurDispo+epT+epD, 
                                                    this.hauteurDispo+2*epT, 
                                                    0.5, getCaissonName(numEtage, numCaisson) + "fond"));                
                    } 
                }
                else{
                    planches.add(new Planche(   this.origine, 
                                                epT, 
                                                this.hauteurDispo+epT,
                                                profondeur,
                                                getCaissonName(numEtage, numCaisson) + "Montant gauche"));  
                    
                    planches.add(new Planche(   this.origine.addX(epT), 
                                                this.largeurDispo, 
                                                epT,
                                                profondeur,
                                                getCaissonName(numEtage, numCaisson) + "Base"));  
                    
                    planches.add(new Planche(   this.origine.addY(epT+this.hauteurDispo), 
                                                this.largeurDispo+2*epT, 
                                                epT,
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Plafond"));  
                    
                    planches.add(new Planche(   this.origine.addX(epT+largeurDispo), 
                                                epT, 
                                                this.hauteurDispo+epT,
                                                profondeur, 
                                                getCaissonName(numEtage, numCaisson) + "Montant droit"));  
                    
                    if(Choix.getInstance().isAvecFond()){
                        planches.add(new Planche(   this.origine.addZ(profondeur), 
                                                    this.largeurDispo+2*epT, 
                                                    this.hauteurDispo+2*epT, 
                                                    0.5, 
                                                    getCaissonName(numEtage, numCaisson) + "fond"));                
                    } 
                }
            }        
        }
        
        return planches;
    }
    
    
    
    public Point getOrigine() {
        return origine;
    }
    
    public double getLargeurDispo() {
        return largeurDispo;
    }

    public void setLargeurDispo(double largeur) {
        this.largeurDispo = largeur;
    }
    
    public double getHauteurDispo() {
        return this.hauteurDispo;
    }

    public void setHauteurDispo(double hauteur) {
        this.hauteurDispo = hauteur;
    }

    public double getProfondeur() {
        return profondeur;
    }

    public void setProfondeur(double profondeur) {
        this.profondeur = profondeur;
    }

    public Boolean isTop() {
        return isTop;
    }

    public void setTop(Boolean isTop) {
        this.isTop = isTop;
    }

    public Boolean isBottom() {
        return isBottom;
    }

    public void setBottom(Boolean isBottom) {
        this.isBottom = isBottom;
    }

    public Boolean isSideG() {
        return isSideG;
    }

    public void setSideG(Boolean isSideG) {
        this.isSideG = isSideG;
    }

    public Boolean isSideD() {
        return isSideD;
    }
        
    public void setSideD(Boolean isSideD) {
        this.isSideD = isSideD;
    }
}
