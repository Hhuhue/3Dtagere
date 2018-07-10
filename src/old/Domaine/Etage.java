package old.Domaine;
import java.io.Serializable;
import java.util.ArrayList;


public class Etage  implements Serializable{

    public Etage(   Point origine, 
                    double largeurDispo, 
                    double hauteurDispo, 
                    double profondeur, 
                    boolean isTop,
                    boolean isBottom,
                    double epD,
                    double epT) {
        
        this.origine = origine;
        this.largeurDispo = largeurDispo;
        this.hauteurDispo = hauteurDispo;
        this.profondeur = profondeur;
        this.isTop = isTop;
        this.isBottom = isBottom;
        this.epD = epD;
        this.epT = epT;
        this.caissons = this.createCaissons();
    }
    
    private Point origine;
    private double largeurDispo;
    private double hauteurDispo;
    private double profondeur;
    private boolean isTop;
    private boolean isBottom;
    private double epD;
    private double epT;

    private Caissons caissons;

    
    public void setProfondeur(double profondeur){
        this.profondeur = profondeur;
        this.caissons.setProfondeur(profondeur);
    }
    
    public void setEpD(double epD){
        this.epD = epD;
        this.caissons.setEpD(epD);
    }
    
    public void setEpT(double epT){
        this.epT = epT;
        this.caissons.setEpT(epT);
    }
    
    public void setOrigine(Point origine){
        this.origine=origine;
        this.caissons.setOrigine(origine);
    }
    
    public void addCaisson(double LARGEUR_CAISSON_MIN){
        this.caissons.addCaisson(LARGEUR_CAISSON_MIN);
    }
    
    public void deleteCaisson(){
        this.caissons.deleteCaisson();
    }
    
    public boolean isTop(){
        return this.isTop;
    }
    
    public boolean isBottom(){
        return this.isBottom;
    }
        

    public double getLargeurDispo() {
        return largeurDispo;
    }
    
    public double getProfondeur(){
        return this.profondeur;
    }
    
    public boolean testLargeurCaisson(double largeurDispoEtages, double LARGEUR_CAISSON_MIN){
        return caissons.testLargeurCaisson(largeurDispoEtages, LARGEUR_CAISSON_MIN);
    }
    
    private Caissons createCaissons(){
        return new Caissons(this.origine, 
                            this.largeurDispo, 
                            this.hauteurDispo, 
                            this.profondeur,
                            this.isTop,
                            this.isBottom,
                            this.epD,
                            this.epT);
    }
    
    
    public Caissons getCaissons() {
        return this.caissons;
    }
        
    public void setTop(boolean bool){
        this.isTop = bool;
        this.caissons.setTop(bool);
    }
    
    public void setBottom(boolean bool){
        this.isBottom = bool;
        this.caissons.setBottom(bool);
    }
    
    public Point getOrigine(){
        return this.origine;
    }
        
    public int selectionCaisson(double x){
        return this.caissons.selectionCaisson(x);
    }
    
    public void setHauteurDispo(double hauteur){
        this.hauteurDispo=hauteur;
        this.caissons.setHauteurDispo(hauteur);
    }
    
    public void setLargeurDispo(double largeur){
        this.largeurDispo=largeur;
        this.caissons.setLargeurDispo(largeur);
    }
    
    public double getHauteurOrigine() {
        return this.origine.getY();
    }
    
    public double getHauteurDispo() {
        return this.hauteurDispo;
    }

    private String getNomPlafond(int numEtage){
        return "Etage "+ Integer.toString(numEtage)+" : Plafond";
    }
    
    public ArrayList<Planche> getPlafonds(int numEtage){
        ArrayList<Planche> plafonds = new ArrayList<>();
        if(!this.isTop){
            if(Choix.getInstance().isTripleContour()){
                if(this.isBottom){
                    plafonds.add(new Planche(   this.origine.addY(  this.hauteurDispo
                                                                    +3*epT), 
                                                this.largeurDispo+4*epT,      
                                                epT,                  
                                                this.profondeur,
                                                getNomPlafond(numEtage)));      
                }
                else{
                    plafonds.add(new Planche(   this.origine.addY(  this.hauteurDispo
                                                                    +2*epT), 
                                                this.largeurDispo+4*epT,      
                                                epT,                  
                                                this.profondeur,
                                                getNomPlafond(numEtage))); 
                }
            }
            else{
                if(this.isBottom){
                    plafonds.add(new Planche(   this.origine.addY(  epD
                                                                    +this.hauteurDispo
                                                                    +epT), 
                                                this.largeurDispo+2*epD,      
                                                epT,                  
                                                this.profondeur,
                                                getNomPlafond(numEtage)));       
                }
                else{
                    plafonds.add(new Planche(   this.origine.addY(  this.hauteurDispo
                                                                    +2*epT),  
                                                this.largeurDispo+2*epD,      
                                                epT,                  
                                                this.profondeur,
                                                getNomPlafond(numEtage)));    
                }
            }
        }  
        else{
            if(Choix.getInstance().isTripleContour()){
                if(this.isBottom){
                    plafonds.add(new Planche( origine.addY(3*epT+hauteurDispo),
                                                largeurDispo+4*epT, 
                                                epT, 
                                                this.profondeur,
                                                getNomPlafond(numEtage)+" externe")); 
                    plafonds.add(new Planche( origine.addY(2*epT+hauteurDispo),
                                                largeurDispo+4*epT, 
                                                epT, 
                                                this.profondeur,
                                                getNomPlafond(numEtage)+" interne"));
                    if(Choix.getInstance().isAvecFond()){
                        plafonds.get(plafonds.size()-1).setZ(this.profondeur-0.5);
                    } 
                }              
                else{
                    plafonds.add(new Planche( origine.addY(2*epT+hauteurDispo),
                                                largeurDispo+4*epT, 
                                                epT, 
                                                this.profondeur,
                                                getNomPlafond(numEtage)+" externe")); 
                    plafonds.add(new Planche( origine.addY(epT+hauteurDispo),
                                                largeurDispo+4*epT, 
                                                epT, 
                                                this.profondeur,
                                                getNomPlafond(numEtage)+" interne"));  
                    if(Choix.getInstance().isAvecFond()){
                        plafonds.get(plafonds.size()-1).setZ(this.profondeur-0.5);
                    } 
                }  
            }
            else{
                if(this.isBottom){
                    plafonds.add(new Planche( origine.addY(epD+hauteurDispo),
                                                largeurDispo+2*epD, 
                                                epD, 
                                                this.profondeur,
                                                getNomPlafond(numEtage)));
                    if(Choix.getInstance().isAvecFond()){
                        plafonds.get(plafonds.size()-1).setZ(this.profondeur-0.5);
                    } 
                }
                else{
                    plafonds.add(new Planche( origine.addY(epT+hauteurDispo),
                                                largeurDispo+2*epD, 
                                                epD, 
                                                this.profondeur,
                                                getNomPlafond(numEtage))); 
                    if(Choix.getInstance().isAvecFond()){
                        plafonds.get(plafonds.size()-1).setZ(this.profondeur-0.5);
                    } 
                }
            }
        }
        return plafonds;
    }
    
    
    public ArrayList<Planche> getAllPlanches(int numEtage){
        ArrayList<Planche> planches = new ArrayList<>();
        planches.addAll(this.getPlafonds(numEtage));
        planches.addAll(this.caissons.getAllPlanches(numEtage));
        return planches;
    }

}
