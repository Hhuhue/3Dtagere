
package old.Domaine;
import java.io.Serializable;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public class Caissons extends ArrayList<Caisson>  implements Serializable{

    public Caissons(Point origineCaissons, 
                    double largeurDispo, 
                    double hauteurDispo, 
                    double profondeurDispo,
                    boolean isTop,
                    boolean isBottom,
                    double epD,
                    double epT) {
        
        this.origine = origineCaissons;
        this.largeurDispo = largeurDispo;
        this.hauteurDispo = hauteurDispo;
        this.profondeur = profondeurDispo;
        this.isTop = isTop;
        this.isBottom = isBottom;
        this.epD = epD;
        this.epT = epT;
        
        this.add(this.CreateCaisson());
    }
    
    private Point origine;
    private double largeurDispo;
    private double hauteurDispo;
    private double profondeur;
    private boolean isTop;
    private boolean isBottom;
    private double epD;
    private double epT;

    public void setProfondeur(double profondeur){
        this.profondeur = profondeur;
        for(Caisson caisson : this){
            caisson.setProfondeur(profondeur);
        }
    }
    
    public void setEpD(double epD){
        this.epD = epD;
        for(Caisson caisson : this){
            caisson.setEpD(epD);
        }
    }
    
    public double getLargeurDispo(){
        return largeurDispo;
    }
    
    public void setEpT(double epT){
        this.epT = epT;
        for(Caisson caisson : this){
            caisson.setEpT(epT);
        }
    }
    
    public void setProportionCaisson(int numCaisson, double proportion, double LARGEUR_CAISSON_MIN){
        if(this.size()>1){
            double newLargeurCaisson = this.largeurDispo*proportion;
            double largeurAPartager = (this.largeurDispo-newLargeurCaisson)/(this.size()-1);
            if(largeurAPartager>LARGEUR_CAISSON_MIN && newLargeurCaisson>LARGEUR_CAISSON_MIN){
                this.get(numCaisson).setLargeurDispo(newLargeurCaisson);
                for(int i=0;i<this.size();i++){
                    if(i!=numCaisson){
                        this.get(i).setLargeurDispo(largeurAPartager);
                    }
                }
                updateOrigineCaisson();
            }     
        }
 
    }    
    
    public void setOrigine(Point origine){
        this.origine=origine;
        updateOrigineCaisson();
    }
    
    public void updateOrigineCaisson(){
        for(Caisson caisson : this){
            caisson.setOrigineY(this.origine.getY());
        }
        Point origineCaisson = this.origine.getPoint();
        this.get(0).setOrigine(origineCaisson);
        if(Choix.getInstance().isTripleContour()){
            origineCaisson =   origineCaisson.addX(4*epT+this.get(0).getLargeurDispo());
        }
        else{
            origineCaisson =   origineCaisson.addX(epD+2*epT+this.get(0).getLargeurDispo());
        }        
        for(int i=1; i<this.size(); i++){
            this.get(i).setOrigine(origineCaisson);
            origineCaisson = origineCaisson.addX(this.get(i).getLargeurDispo()+3*epT);
        }
    }

    public boolean testLargeurCaisson(double largeurDispoEtages, double LARGEUR_CAISSON_MIN){
        double largeurDispo = largeurDispoEtages - (this.size()-1)*3*epT;
        boolean largeurValide = true;
        double ratio = largeurDispo/this.largeurDispo;
        for(Caisson caisson : this){
            if(caisson.getLargeurDispo()*ratio<LARGEUR_CAISSON_MIN){
                largeurValide=false;
            }
        }
        return largeurValide; 
    }
        
    private Caisson CreateCaisson(){
        return new Caisson( this.origine,
                            this.largeurDispo, 
                            this.hauteurDispo, 
                            this.profondeur, 
                            this.isTop, 
                            this.isBottom, 
                            true, 
                            true,
                            this.epD,
                            this.epT);
    }
    
    public void changeLargeurCaisson(int numCaisson, double x, double LARGEUR_CAISSON_MIN){
        if(this.size()>1){
            if(numCaisson+1<this.size()){
                if(     x<0
                        && this.get(numCaisson).getLargeurDispo()+x>=LARGEUR_CAISSON_MIN 
                        && this.get(numCaisson+1).getLargeurDispo()-x>=LARGEUR_CAISSON_MIN 
                        || x>0 
                        && this.get(numCaisson+1).getLargeurDispo()-x>=LARGEUR_CAISSON_MIN
                        && this.get(numCaisson).getLargeurDispo()+x>=LARGEUR_CAISSON_MIN){
                    this.get(numCaisson).setLargeurDispo(this.get(numCaisson).getLargeurDispo()+x);
                    this.get(numCaisson+1).setLargeurDispo(this.get(numCaisson+1).getLargeurDispo()-x);
                    this.get(numCaisson+1).setOrigine(this.get(numCaisson+1).getOrigine().addX(x));
                }
            }
            else{
                if(     x<0
                        && this.get(numCaisson-1).getLargeurDispo()+x>=LARGEUR_CAISSON_MIN 
                        && this.get(numCaisson).getLargeurDispo()-x>=LARGEUR_CAISSON_MIN 
                        || x>0 
                        && this.get(numCaisson).getLargeurDispo()-x>=LARGEUR_CAISSON_MIN
                        && this.get(numCaisson-1).getLargeurDispo()+x>=LARGEUR_CAISSON_MIN){
                    this.get(numCaisson).setLargeurDispo(this.get(numCaisson).getLargeurDispo()-x);
                    this.get(numCaisson-1).setLargeurDispo(this.get(numCaisson-1).getLargeurDispo()+x);
                    this.get(numCaisson).setOrigine(this.get(numCaisson).getOrigine().subX(-x));
                }
            }
        }
    }
    
    public void updateOrigineAddCaisson(Point origine){
        this.origine = origine;
        double largeurCaisson = this.largeurDispo/(this.size());
        Point origineCaisson=origine;
        double xPremierCaisson;
        
        if(Choix.getInstance().isTripleContour()){
            xPremierCaisson =   4*epT+largeurCaisson;
        }
        else{
            xPremierCaisson =   epD+largeurCaisson+2*epT;
        }        
        for(int i=0; i<this.size(); i++){
            switch (i) {
                case 0:
                    break;
                case 1:
                    origineCaisson = origineCaisson.addX(xPremierCaisson);
                    break;
                default:
                    origineCaisson = origineCaisson.addX(  largeurCaisson
                                        +3*epT);
                    break;
            }
            this.get(i).setOrigine(origineCaisson);
        }
    }
 
    public void setTop(boolean bool){
        this.isTop = bool;
        for(Caisson caisson : this){
            caisson.setTop(bool);
        }
    }
    
    public void setBottom(boolean bool){
        this.isBottom = bool;
        for(Caisson caisson : this){
            caisson.setBottom(bool);
        }
    }
    
    public void setHauteurDispo(double hauteurDispo){
        this.hauteurDispo = hauteurDispo;
        for (Caisson caisson : this){
            caisson.setHauteurDispo(hauteurDispo);
        }
    }
    
    public int selectionCaisson(double x){
        int numCaisson = -1;
        for(Caisson caisson : this){
            if(x<caisson.getOrigine().getX()+caisson.getLargeurDispo()){
                return this.indexOf(caisson);
            }
        }
        return numCaisson;
    }
    
    public ArrayList<Planche> getPlanchesCaisson(int numEtage, int numCaisson){
        return this.get(numCaisson).getPlanches(numEtage, numCaisson);
    }
    
    public void setLargeurDispo(double newLargeurDispo){
        double newLargeur = newLargeurDispo-(this.size()-1)*3*epT;
        double ratio = newLargeur/this.largeurDispo;
        this.largeurDispo = newLargeur;
        
        for(Caisson caisson : this){
            caisson.setLargeurDispo(caisson.getLargeurDispo()*ratio);
        }
        
        Point originePremierCaisson=this.origine;
        
        if(Choix.getInstance().isTripleContour()){
            originePremierCaisson = originePremierCaisson.addX(   4*epT
                            +this.get(0).getLargeurDispo());
        }
        else{
            originePremierCaisson = originePremierCaisson.addX(   epD
                            +2*epT
                            +this.get(0).getLargeurDispo());
        }
        
        for(Caisson caisson : this){
            if(!this.get(0).equals(caisson)){
                caisson.setOrigine(originePremierCaisson);
                originePremierCaisson = originePremierCaisson.addX(     3*epT
                                            +caisson.getLargeurDispo());
            }
        }   
    }    
    
        
    public void deleteCaisson(){
        if(this.size()>1){
            this.largeurDispo += 3*epT;
            double largeurCaisson = this.largeurDispo/(this.size()-1);
            this.get(this.size()-2).setSideD(true);
            this.remove(this.size()-1);

            double xPremierCaisson;

            if(Choix.getInstance().isTripleContour()){
                xPremierCaisson =   4*epT+largeurCaisson;
            }
            else{
                xPremierCaisson =   epD+largeurCaisson+2*epT;
            }

            Point origineCaisson=this.origine.getPoint();
            for(int i=0; i<this.size(); i++){
                switch (i) {
                    case 0:
                        break;
                    case 1:
                        origineCaisson = origineCaisson.addX(xPremierCaisson);
                        break;
                    default:
                        origineCaisson = origineCaisson.addX(  largeurCaisson
                                            +3*epT);
                        break;
                }
                this.get(i).setOrigine(origineCaisson);
                this.get(i).setLargeurDispo(largeurCaisson);
            }   
        }
    }
    
    public void addCaisson(double LARGEUR_CAISSON_MIN){
        if((this.largeurDispo - 3*epT)/(this.size()+1)>LARGEUR_CAISSON_MIN){
            this.largeurDispo -= 3*epT;
            double largeurCaisson = this.largeurDispo/(this.size()+1);
            this.get(this.size()-1).setSideD(false);
            this.add(new Caisson (  this.origine,
                                    largeurCaisson,
                                    this.hauteurDispo,
                                    this.profondeur,
                                    this.isTop,
                                    this.isBottom,
                                    false,
                                    true,
                                    this.epD,
                                    this.epT)); 

            double xPremierCaisson;

            if(Choix.getInstance().isTripleContour()){
                xPremierCaisson =   4*epT+largeurCaisson;
            }
            else{
                xPremierCaisson =   epD+largeurCaisson+2*epT;
            }

            Point origineCaisson=this.origine.getPoint();
            for(int i=0; i<this.size(); i++){
                switch (i) {
                    case 0:
                        break;
                    case 1:
                        origineCaisson = origineCaisson.addX(xPremierCaisson);
                        break;
                    default:
                        origineCaisson = origineCaisson.addX(  largeurCaisson
                                            +3*epT);
                        break;
                }
                this.get(i).setOrigine(origineCaisson);
                this.get(i).setLargeurDispo(largeurCaisson);
            }   
        }
        
    }    
    
    private String getNomSeparateur(int numEtage, int numSeparateur){
        return "Etage "+ Integer.toString(numEtage)+" : Separateur "+Integer.toString(numSeparateur);
    }
        
    public ArrayList<Planche> getAllPlanches(int numEtage){
        ArrayList<Planche> planches = new ArrayList<>();
        for(Caisson caisson : this){
            planches.addAll(caisson.getPlanches(numEtage, this.indexOf(caisson)));
        }
        if(this.size()>1){
            Point origineSep = this.origine;
            int numSeparateur = 0;
            for(Caisson caisson : this){
                if(!caisson.isSideD()){
                    if(Choix.getInstance().isTripleContour()){
                        if(caisson.isSideG()){
                            origineSep = caisson.getOrigine().addX( caisson.getLargeurDispo()
                                                                    +3*epT);
                        }
                        else{
                            origineSep= origineSep.addX(    caisson.getLargeurDispo()
                                                            +3*epT);
                        }
                        planches.add(new Planche(           origineSep, 
                                                            epT, 
                                                            getHauteurSeparateur(), 
                                                            this.profondeur,
                                                            getNomSeparateur(numEtage, numSeparateur)));     
                    }
                    else{
                        if(caisson.isSideG()){
                            origineSep = caisson.getOrigine().addX( caisson.getLargeurDispo()
                                                                    +epD
                                                                    +epT);
                        }
                        else{
                            origineSep= origineSep.addX(    caisson.getLargeurDispo()
                                                            +3*epT);
                        }
                        planches.add(new Planche(           origineSep, 
                                                            epT, 
                                                            getHauteurSeparateur(), 
                                                            this.profondeur,
                                                            getNomSeparateur(numEtage, numSeparateur)));    
                    }
                }
                numSeparateur+=1;
            }
        }
        return planches;
    }
    
    private double getHauteurSeparateur(){
        double hauteurSep=this.hauteurDispo;
        if(Choix.getInstance().isTripleContour()){
            if(this.isTop & this.isBottom){
                hauteurSep+=2*epT;
                return hauteurSep;
            }
            else if(this.isTop){
                hauteurSep+=epT;
                return hauteurSep;
            }
            else if(this.isBottom){
                hauteurSep+=3*epT;
                return hauteurSep;
            }
            else{
                hauteurSep+=2*epT;
                return hauteurSep;
            }
        }
        else{
            if(this.isTop & this.isBottom){
                hauteurSep+=epD;
                return hauteurSep;
            }
            else if(this.isTop){
                hauteurSep+=epT;
                return hauteurSep;
            }
            else if(this.isBottom){
                hauteurSep+=epT+epD;
                return hauteurSep;
            }
            else{
                hauteurSep+=2*epT;
                return hauteurSep;
            }
        }
    }
}