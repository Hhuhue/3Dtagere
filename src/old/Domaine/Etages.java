//TODO
//hauteurDisponible doit-elle soustraire l'epaisseur de planche? OUI

package old.Domaine;
import java.io.Serializable;
import java.util.ArrayList;

public class Etages extends ArrayList<Etage>  implements Serializable{

    public Etages(  Point origine, 
                    double largeur, 
                    double hauteur, 
                    double profondeur,
                    double epD,
                    double epT) {
        this.origine = origine;
        this.largeurDispo = largeur;
        this.hauteurDispo = hauteur;
        this.profondeur = profondeur;
        this.epD = epD;
        this.epT = epT;
        this.add(this.createEtage());
    }
   
    private Point origine;
    private double largeurDispo;
    private double hauteurDispo;
    private double profondeur;
    private double epD;
    private double epT;
    
    public void setOrigine(Point origine){
        this.origine = origine;
        updateOrigineAllEtage();
        
    }
    
    public double getHauteurDispo(){
        return this.hauteurDispo;
    }
    
    public void changeHauteurEtage(int numEtage, double y, double HAUTEUR_ETAGE_MIN){
        if(this.size()>1){
            if(numEtage+1<this.size()){
                if(     y<0       
                        && this.get(numEtage).getHauteurDispo()+y>=HAUTEUR_ETAGE_MIN
                        && this.get(numEtage+1).getHauteurDispo()-y>=HAUTEUR_ETAGE_MIN
                        || y>0 
                        && this.get(numEtage+1).getHauteurDispo()-y>=HAUTEUR_ETAGE_MIN
                        && this.get(numEtage).getHauteurDispo()+y>=HAUTEUR_ETAGE_MIN){
                    this.get(numEtage).setHauteurDispo(this.get(numEtage).getHauteurDispo()+y);
                    this.get(numEtage+1).setHauteurDispo(this.get(numEtage+1).getHauteurDispo()-y);
                    this.get(numEtage+1).setOrigine(this.get(numEtage+1).getOrigine().addY(y));
                }
            }
            else{
                if(     y<0       
                        && this.get(numEtage-1).getHauteurDispo()+y>=HAUTEUR_ETAGE_MIN
                        && this.get(numEtage).getHauteurDispo()-y>=HAUTEUR_ETAGE_MIN
                        || y>0 
                        && this.get(numEtage).getHauteurDispo()-y>=HAUTEUR_ETAGE_MIN
                        && this.get(numEtage-1).getHauteurDispo()+y>=HAUTEUR_ETAGE_MIN){
                    this.get(numEtage).setHauteurDispo(this.get(numEtage).getHauteurDispo()-y);
                    this.get(numEtage-1).setHauteurDispo(this.get(numEtage-1).getHauteurDispo()+y);  
                    this.get(numEtage).setOrigine(this.get(numEtage).getOrigine().addY(y));
                }
            }
            updateOrigineAllEtage();
        }

    }
    
    //Ajouter un etage en changeant tous les etages, en mettant la meme hauteur
    //pour tous les etages
    public void addEtage(double HAUTEUR_ETAGE_MIN){
        if((this.hauteurDispo - 3*epT)/(this.size()+1)>HAUTEUR_ETAGE_MIN){
            this.hauteurDispo -= 3*epT;
            double hauteurEtageDispo = this.hauteurDispo/(this.size()+1);
            this.get(this.size()-1).setTop(false);
            this.add(new Etage( this.origine.getPoint(), 
                                this.largeurDispo, 
                                hauteurEtageDispo, 
                                this.profondeur, 
                                true,
                                false,
                                this.epD,
                                this.epT));
            for(Etage etage : this){
                etage.setHauteurDispo(hauteurEtageDispo);
            }
            updateOrigineAllEtage();
        }
    }
    
    public void supprimeEtage(){
        this.hauteurDispo += 3*epT;
        double hauteurEtageDispo = this.hauteurDispo/(this.size()-1);
        this.get(this.size()-2).setTop(true);
        this.remove(this.size() - 1);
        for(Etage etage : this){
            etage.setHauteurDispo(hauteurEtageDispo);
        }
        updateOrigineAllEtage();
    }
    
    public void supprimeEtageSelec(int numEtage){
        this.hauteurDispo += 3*epT;
        double hauteurEtageDispo = this.hauteurDispo/(this.size()-1);
        
        if(numEtage == this.size()-1){
            this.get(this.size()-2).setTop(true);
            this.remove(this.size() - 1);
        }
        else if(numEtage ==0){
            this.get(numEtage+1).setBottom(true);
            this.remove(0);
        }
        else{
            this.remove(numEtage);
        }
        for(Etage etage : this){
            etage.setHauteurDispo(hauteurEtageDispo);
        }
        updateOrigineAllEtage();
    } 
    
    public void updateOrigineAllEtage(){
        Point origineEtage=this.origine.getPoint();
        this.get(0).setOrigine(origineEtage);
        if(Choix.getInstance().isTripleContour()){
            origineEtage = origineEtage.addY(   4*epT
                            +this.get(0).getHauteurDispo());
        }
        else{
            origineEtage = origineEtage.addY(   epD
                            +2*epT
                            +this.get(0).getHauteurDispo());
        }
        
        for(int i=1;i<this.size();i++){
            this.get(i).setOrigine(origineEtage);
                origineEtage = origineEtage.addY(   3*epT
                                                    +this.get(i).getHauteurDispo());
            }
    }
       
    public Etage createEtage(){
        return new Etage(   this.origine, 
                            this.largeurDispo, 
                            this.hauteurDispo, 
                            this.profondeur, 
                            true, 
                            true,
                            epD,
                            epT);
    }
        
    public void setEpD(double epD){
        this.epD = epD;
        for(Etage etage : this){
            etage.setEpD(epD);
        }
        updateOrigineAllEtage();
    }
    
    public void setEpT(double epT){
        this.epT = epT;
        for(Etage etage : this){
            etage.setEpT(epT);
        }
        updateOrigineAllEtage();
    }
    
    public int selectionCaisson(double x, int numEtage){
        return this.get(numEtage).selectionCaisson(x);
    }
    
    public void addCaisson(int numEtage, double LARGEUR_CAISSON_MIN){
        this.get(numEtage).addCaisson(LARGEUR_CAISSON_MIN);
    }
    
    public void deleteCaisson(int numEtage){
        this.get(numEtage).deleteCaisson();
    }
    
    public void setProfondeur(double profondeur){
        this.profondeur = profondeur;
        for(Etage etage : this){
            etage.setProfondeur(profondeur);
        }
    }
    
    public void setLargeurDispoEtages(double largeurDispoEtages){
        double ratio = largeurDispoEtages/this.largeurDispo;
        this.largeurDispo=largeurDispoEtages;
        for(Etage etage : this){
            etage.setLargeurDispo(etage.getLargeurDispo()*ratio);
        }
    }
    
    public void setProportionEtage(int numEtage, double proportion, double HAUTEUR_ETAGE_MIN){
        double newHauteurEtage = this.hauteurDispo*proportion;
        double hauteurAPartager = (this.hauteurDispo-newHauteurEtage)/(this.size()-1);
        if(hauteurAPartager>HAUTEUR_ETAGE_MIN && newHauteurEtage>HAUTEUR_ETAGE_MIN){
            this.get(numEtage).setHauteurDispo(newHauteurEtage);
            for(int i=0;i<this.size();i++){
                if(i!=numEtage){
                    this.get(i).setHauteurDispo(hauteurAPartager);
                }
            }
            updateOrigineAllEtage();
        }
    }
    
    public boolean testHauteurEtage(double testNewHauteurEtagere, double HAUTEUR_ETAGE_MIN){
        boolean hauteurValide = true;
        double hauteurDispoEtages;
        if(Choix.getInstance().isTripleContour()){
            hauteurDispoEtages = testNewHauteurEtagere - 6*epT - 3*epT*(this.size()-1); 
        }
        else{
            hauteurDispoEtages = testNewHauteurEtagere - 4*epD - 3*epT*(this.size()-1);
        }
        double ratio = hauteurDispoEtages/this.hauteurDispo;
        for(Etage etage : this){
            if(etage.getHauteurDispo()*ratio<HAUTEUR_ETAGE_MIN){
                hauteurValide=false;
            }
        }
        return hauteurValide;
    }
    
    public boolean testLargeurCaisson(double testLargeurEtagere, double LARGEUR_CAISSON_MIN){
        boolean largeurValide = true;
        double largeurDispoEtages;
        if(Choix.getInstance().isTripleContour()){
            largeurDispoEtages = testLargeurEtagere - 6*epT; 
        }
        else{
            largeurDispoEtages = testLargeurEtagere - 4*epD;
        }
        for(Etage etage : this){
            if(etage.testLargeurCaisson(largeurDispoEtages, LARGEUR_CAISSON_MIN) == false){
                largeurValide=false;
            }
        }
        return largeurValide;
    }
    
    public void setHauteurDispoEtages(double hauteurDispoEtages){
        double ratio = hauteurDispoEtages/this.hauteurDispo;
        this.hauteurDispo=hauteurDispoEtages;
        for(Etage etage : this){
            etage.setHauteurDispo(etage.getHauteurDispo()*ratio);
        }
        updateOrigineAllEtage();
    }
    
    public Etage getEtageByClick(double x, double y){
        Etage etageSelec = null;
        if(x>this.origine.getX() && x<this.origine.getX()+this.largeurDispo){
            for(Etage etage : this){
                if(y>etage.getHauteurOrigine() 
                        && y<etage.getHauteurOrigine() + etage.getHauteurDispo()){
                    etageSelec = etage;
                }
            }        
        }
        return etageSelec;
    }
    
    public int getNumEtage(Etage etage){
        return this.indexOf(etage);
    }
    
    public ArrayList<Planche> getPlafonds(int numEtage){
        ArrayList<Planche> plafonds = new ArrayList<>();
        for(Etage etage : this){
            plafonds.addAll(etage.getPlafonds(numEtage));
        }
        return plafonds;
    }
        
    public ArrayList<Planche> getPlanchesEtage(int numEtage){
        return this.get(numEtage).getAllPlanches(numEtage);
    }
    
    public ArrayList<Planche> getAllPlanches(){
        ArrayList<Planche> planches = new ArrayList<>();
        for(Etage etage : this){
            planches.addAll(etage.getAllPlanches(this.indexOf(etage)));
        }
        return planches;
    }
}