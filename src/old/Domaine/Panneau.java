/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package old.Domaine;

import java.util.ArrayList;

/**
 *
 * @author user
 */
public class Panneau {
    
    private ArrayList<ArrayList<Planche>> mesPlanches;
    private double[] LargeurDispo;
    private int nbEtage;
    private Etagere e;
    public Panneau (Etagere etagere){
        e = etagere ;
        nbEtage = (int) (48.00/e.getProfondeur());
        LargeurDispo = new double[nbEtage];
        for (int k = 0; k < nbEtage;k++){
            double val = 96.00;
            LargeurDispo[k] = val;
        }
        mesPlanches = new ArrayList<ArrayList<Planche>>();
        for(int i = 0; i < nbEtage; i++)
        {
            ArrayList<Planche> etageTemp = new ArrayList<>();
            mesPlanches.add(etageTemp);
        }    
    }
    
    public double getLargeurDispo(int x){
        return this.LargeurDispo[x];
    }
    
    public void setLargeurDispo(int x, double y){
        this.LargeurDispo[x] = y;
    }
    
    public int getNbEtage(){
        return this.nbEtage;
    }
    
    public ArrayList<ArrayList<Planche>> getPlanchesPanneau(){
        return this.mesPlanches;
    }
    
    public int fit(double largeurPlanche, int noEtage){
        //RETOURNE -1 si c'est pas possible, sinon retourne l'index de l'etage
        if(this.LargeurDispo[noEtage] >= largeurPlanche){
            return noEtage;
            }
        
        return -1;
    }
    public void addPlanche(int noEtage, Planche p){
        ((this.mesPlanches).get(noEtage)).add(p);
    }

    //POURRAIT ETRE DANS ETAGERE (remplacer e par this)
  
   



 }