package old.Domaine;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class Etagere implements Serializable{

    public Etagere( Point origine, double largeur, double hauteur, double profondeur, double epD, double epT) {               
        
        this.origine = origine;
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.profondeur = profondeur;
        this.epD = epD;
        this.epT = epT;           
        this.etages = this.createEtagesDeBase();
    }

    private Point origine;
    private double largeur;
    private double hauteur;
    private double profondeur;
    private double epD;
    private double epT;
    private Etages etages;
         
    public Planche getPlanche(double x, double y){
        Planche plancheSelection = null;
        for (Planche planche : this.getAllPlanches()) {
                    if (   x> planche.getOrigine().getX() 
                        && x < planche.getOrigine().getX() + planche.getX()
                        && y > planche.getOrigine().getY()
                        && y < planche.getOrigine().getY() + planche.getY()) {
                        plancheSelection = planche;
                    }
        }
        return plancheSelection;
    }    
    
    public boolean isCadreY(double x, double y){
        Planche planche = getPlanche(x, y);
        boolean isCadreY = false;
        ArrayList<Planche> planches = this.getPlanchesCadre();
        if(planche.isSameAs(planches.get(1)) || planche.isSameAs(planches.get(3))){
            isCadreY = true;
        }
        return isCadreY;
    }
    
    public boolean isCadreX(double x, double y){
        Planche planche = getPlanche(x, y);
        boolean isCadreX = false;
        ArrayList<Planche> planches = this.getPlanchesCadre();
        if(planche.isSameAs(planches.get(0)) || planche.isSameAs(planches.get(2))){
            isCadreX = true;
        }
        return isCadreX;
    }
    
    public void setProfondeur(double profondeur){
        this.profondeur = profondeur;
        this.etages.setProfondeur(profondeur);
    }
    
    public double getProportionEtage(double hauteur){
        return hauteur/etages.getHauteurDispo()*100;
    }
    
    public void setProportionEtage(int numEtage, double proportion, double HAUTEUR_ETAGE_MIN){
        if(this.etages.size()>1){
            etages.setProportionEtage(numEtage, proportion, HAUTEUR_ETAGE_MIN);
        }
    }
    
    public void setProportionCaisson(int numEtage, int numCaisson, double proportion, double LARGEUR_CAISSON_MIN){
        this.etages.get(numEtage).getCaissons().setProportionCaisson(numCaisson, proportion, LARGEUR_CAISSON_MIN);
    }        
    
    public double getProportionCaisson(int numEtage, double largeur){
        return largeur/etages.get(numEtage).getCaissons().getLargeurDispo()*100;
    }
    
    public void updateOrigines(){
        this.etages.setOrigine(this.getOrigineEtages());
    }
    
    public void changeLargeurCaisson(int numEtage, int numCaisson, double x, double largeurCaissonMin){
        this.getCaissons(numEtage).changeLargeurCaisson(numCaisson, x, largeurCaissonMin);
    }
    
    public Caissons getCaissons(int numEtage){
        return this.etages.get(numEtage).getCaissons();
    }
    
    public Etagere getEtagere(){
        return this;
    }
    
    public Etage getEtageByClick(double x, double y){
        return etages.getEtageByClick(x, y);
    }
    
    public Etage getEtageByNum(int numetage){
        return this.etages.get(numetage);
    }
    
    public int getNumEtage(Etage etage){
        return this.etages.getNumEtage(etage);
    }    
    
    public int selectionCaisson(double x, int numEtage){
        return this.etages.selectionCaisson(x,numEtage);
    }   
    
    public Caisson getCaisson(int numEtage, int numCaisson){
        return this.etages.get(numEtage).getCaissons().get(numCaisson);
    }    
    public Point getOrigine(){
        return this.origine;
    }
    
    public double getEpD(){
        return this.epD;
    }
        
    public double getEpT(){
        return this.epT;
    }
            
    public double getProfondeur(){
        return this.profondeur;
    }    
    public void setEpD(double epD){
        this.epD = epD;
        this.etages.setEpD(epD);
    }
    
    public void setEpT(double epT){
        this.epT = epT;
        this.etages.setEpT(epT);
    }
    
    public double getLargeur(){
        return this.largeur;
    }
    
    public double getHauteur(){
        return this.hauteur;
    }
    

    
    public int getNombreEtage(){
        return this.etages.size();
    }
    
    public int getNombreCaissonEtage(int numEtage){
        return this.etages.get(numEtage).getCaissons().size();
    }
    
    public int getNombreCaisson(){
        int nbCaisson=0;
        for(Etage etage : this.etages){
            nbCaisson+=etage.getCaissons().size();
        }
        return nbCaisson;
    }
            
    public void changeHauteurEtage(int numEtage, double y, double HAUTEUR_ETAGE_MIN){
        this.etages.changeHauteurEtage(numEtage, y, HAUTEUR_ETAGE_MIN);
    }
    
    public double getHauteurDispoEtages(){
        double hauteurDispoEtages;
        if(Choix.getInstance().isTripleContour()){
            hauteurDispoEtages = this.hauteur - 6*epT - (3*epT)*(this.etages.size()-1);
        }
        else{
            hauteurDispoEtages = this.hauteur - 4*epD - (3*epT)*(this.etages.size()-1);
        }    
        return hauteurDispoEtages;
    }
    
    public double getLargeurDispoEtages(){
        double largeurDispoEtages;
        if(Choix.getInstance().isTripleContour()){
            largeurDispoEtages = this.largeur - 6*epT;
        }
        else{
            largeurDispoEtages = this.largeur - 4*epD;
        }    
        return largeurDispoEtages;
    }
    
    //TODO
    //changer la hauteurDispo de chaque etage
    public void setHauteur(double hauteur){
            this.hauteur=hauteur;       
            this.etages.setHauteurDispoEtages(getHauteurDispoEtages());   
    }
    
//    public double findBestRatioHauteur(){
//        return this.etages.findBestRatioHauteur();   
//    }

    //TODO
    public void setLargeur(double newLargeurEtagere){
        this.largeur=newLargeurEtagere;
        this.etages.setLargeurDispoEtages(getLargeurDispoEtages());
    }
    
    public void changerProfondeur(double profondeur){
        
    }
            
    public void ajoutEtage(double HAUTEUR_MIN_ETAGE){
        this.etages.addEtage(HAUTEUR_MIN_ETAGE);
    }
              

    public void supprimeEtage(){
        if(this.etages.size()>1){
            this.etages.supprimeEtage();
        }
    }
    
    public void supprimeEtageSelec(int numEtage){
        if(this.etages.size()>1){
            this.etages.supprimeEtageSelec(numEtage);
        }
    }
        
    public void addEtageMouse(Point argPoint){

    }
                
    public void addCaisson(int numEtage, double LARGEUR_CAISSON_MIN){
        this.etages.addCaisson(numEtage, LARGEUR_CAISSON_MIN);
    }
    
    public void deleteCaisson(int numEtage){
        this.etages.deleteCaisson(numEtage);
    }    
    
    public void addCaissonMouse(Point argPoint){

    }
    
    private Etages createEtagesDeBase(){
        double largeurDispoEtages;
        double hauteurDispoEtages;
        if(Choix.getInstance().isTripleContour()){
            largeurDispoEtages = this.largeur - 6*epT;
            hauteurDispoEtages = this.hauteur - 6*epT;
        }
        else{
            largeurDispoEtages = this.largeur - 4*epD;
            hauteurDispoEtages = this.hauteur - 4*epD;
        }    
    
        return new Etages(getOrigineEtages(), largeurDispoEtages, hauteurDispoEtages, this.profondeur, this.epD, this.epT);
    }
    
    private Point getOrigineEtages(){
        Point origineEtage;
        if(Choix.getInstance().isTripleContour()){
            origineEtage = this.origine.getPoint().addX(epT).addY(epT);
        }
        else{
            origineEtage = this.origine.getPoint().addX(epD).addY(epD);
        }

        return origineEtage;
    }
        
    public ArrayList<Planche> getPlanchesEtage(int numEtage){
        return this.etages.getPlanchesEtage(numEtage);
    }
    
    public ArrayList<Planche> getPlanchesCaisson(int numEtage, int numCaisson){
        ArrayList<Planche> planches = this.etages.get(numEtage).getCaissons().getPlanchesCaisson(numEtage, numCaisson);
        for (Iterator<Planche> itr = planches.iterator(); itr.hasNext();) {
            if (itr.next() == null){
                itr.remove();
            }
        }
        return planches;   
    }
    
    public ArrayList<Planche> getPlanchesCadreY(){
        ArrayList<Planche> planches = new ArrayList<>();
        planches.add(this.getPlanchesCadre().get(1));
        planches.add(this.getPlanchesCadre().get(3));
        return planches;
    }
    
    public ArrayList<Planche> getPlanchesCadreX(){
        ArrayList<Planche> planches = new ArrayList<>();
        planches.add(this.getPlanchesCadre().get(0));
        planches.add(this.getPlanchesCadre().get(2));
        return planches;
    }

    public boolean testHauteurEtagere(double newHauteurEtagere, double HAUTEUR_ETAGE_MIN){
        return this.etages.testHauteurEtage(newHauteurEtagere, HAUTEUR_ETAGE_MIN);   
    }   
    
    public boolean testLargeurEtagere(double newLargeurEtagere, double LARGEUR_CAISSON_MIN){
        return this.etages.testLargeurCaisson(newLargeurEtagere, LARGEUR_CAISSON_MIN);   
    }
    
    public void changeLargeurEtagere(double x, double maxLargeurEtagere, double minLargeurEtagere, double minLargeurCaisson){
        if(x>0 && this.largeur+x <= maxLargeurEtagere){
            this.setLargeur(this.largeur+x);
        }
        else if(x<0 && this.largeur+x >= minLargeurEtagere){
            double newLargeurEtagere = this.largeur+x;  
            if(etages.testLargeurCaisson(newLargeurEtagere, minLargeurCaisson)){
                this.setLargeur(this.largeur+x);
            }
        }
    }
    
    public void changeHauteurEtagere(double y, double maxHauteurEtagere, double minHauteurEtagere, double minHauteurEtage){
        if(y>0 && this.hauteur+y <= maxHauteurEtagere){
            this.setHauteur(this.hauteur+y);
        }
        else if(y<0 && this.hauteur+y >= minHauteurEtagere){
            double newHauteurEtagere = this.hauteur+y;
            if(this.etages.testHauteurEtage(newHauteurEtagere, minHauteurEtage)){
                this.setHauteur(this.hauteur+y);
            }
        }
    }
    
    public ArrayList<Planche> getPlanchesCadre(){
        ArrayList<Planche> planches = new ArrayList<>();
        Point origine = this.origine.getPoint();
        double epaisseur = epD;
        
        if(Choix.getInstance().isTripleContour()){
            epaisseur = epT;
        }

        Planche montantGauche = new Planche(    origine, 
                                                epaisseur, 
                                                this.hauteur, 
                                                this.profondeur,
                                                "Cadre: Montant Gauche");
        
        Planche base = new Planche( origine.addX(epaisseur),
                                    this.largeur-2*epaisseur, 
                                    epaisseur, 
                                    this.profondeur,
                                    "Cadre: Base");
        
        Planche montantDroit = new Planche( origine.addX(epaisseur+base.getX()),
                                            epaisseur, 
                                            this.hauteur, 
                                            this.profondeur,
                                            "Cadre: Montant Droit");
       
        Planche top = new Planche(  (origine.addX(epaisseur)
                                    .addY(this.hauteur-epaisseur)),
                                    this.largeur-2*epaisseur, 
                                    epaisseur, 
                                    this.profondeur,
                                    "Cadre: Top");
        
        if(Choix.getInstance().isLongTop()){
            top.setOrigine(top.getOrigine().subX(epaisseur));
            top.setX(this.largeur);
            montantGauche.setY(montantGauche.getHauteur()-epaisseur);
            montantDroit.setY(montantDroit.getHauteur()-epaisseur);
        }
        
        planches.add(montantGauche);
        planches.add(base);
        planches.add(montantDroit);
        planches.add(top);
                
        return planches;
    }
    
     public ArrayList<Planche> getAllPlanches(){
        ArrayList<Planche> planches = new ArrayList<>();
        planches.addAll(this.getPlanchesCadre());
        planches.addAll(this.etages.getAllPlanches());

        for (Iterator<Planche> itr = planches.iterator(); itr.hasNext();) {
            if (itr.next() == null){
                itr.remove();
            }
        }
        
        ArrayList<Planche> planchesReverse = new ArrayList<>();
        for(Planche planche : planches){
            planchesReverse.add(planche);
        }
        Collections.reverse(planchesReverse);
        return planchesReverse;
     }
    
    ArrayList<Planche> planchesReverse = new ArrayList<>();
}