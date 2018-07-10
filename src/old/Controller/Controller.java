package old.Controller;
import old.Domaine.*;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Controller {

    public static Point ORIGINE_ETAGERE_DEFAUT = new Point(0, 0, 0);
    public static double LARGEUR_ETAGERE_DEFAUT = 48.00; 
    public static double HAUTEUR_ETAGERE_DEFAUT = 48.00;
    public static double PROFONDEUR_ETAGERE_DEFAUT = 20.00;
    public static double EPAISSEUR_PLANCHE_DOUBLE_DEFAUT = .75;
    public static double EPAISSEUR_PLANCHE_TRIPLE_DEFAUT = .5;
    public static double EPAISSEUR_FOND_DEFAUT = .5;
    
    public static double HAUTEUR_ETAGERE_MIN = 20;
    public static double HAUTEUR_ETAGERE_MAX = 96;
    public static double LARGEUR_ETAGERE_MIN = 20;    
    public static double LARGEUR_ETAGERE_MAX = 96;    
    public static double PROFONDEUR_ETAGERE_MIN = 20;    
    public static double PROFONDEUR_ETAGERE_MAX = 50;    
    public static double HAUTEUR_ETAGE_MIN = 5;
    public static double LARGEUR_CAISSON_MIN = 5;
    public static double HAUTEUR_epD_MIN = 0.3;
    public static double HAUTEUR_epD_MAX = 1.0;
    public static double HAUTEUR_epT_MIN = 0.3;
    public static double HAUTEUR_epT_MAX = 1.0;
    

    
    public Controller() {
        Choix.setInstance(false, false, false, false);
        this.etagere = new Etagere( ORIGINE_ETAGERE_DEFAUT,
                                    LARGEUR_ETAGERE_DEFAUT, 
                                    HAUTEUR_ETAGERE_DEFAUT, 
                                    PROFONDEUR_ETAGERE_DEFAUT, 
                                    EPAISSEUR_PLANCHE_DOUBLE_DEFAUT, 
                                    EPAISSEUR_PLANCHE_TRIPLE_DEFAUT);
    }
    
    private Etagere etagere;
    
    public void setEtagere(Etagere etagere){
        this.etagere = etagere;
    }
    
    public void setProfondeur(double profondeur){
        this.etagere.setProfondeur(profondeur);
    }
    
    public Etagere getCopyEtagere(){
        return (Etagere)deepClone(etagere);
    }
    
    public Planche getPlanche(double x, double y){
        return this.etagere.getPlanche(x, y);
    }

    public void changeLargeurEtagere(double x){
        this.etagere.changeLargeurEtagere(x, LARGEUR_ETAGERE_MAX, LARGEUR_ETAGERE_MIN, LARGEUR_CAISSON_MIN);
    }
        
    public void changeHauteurEtagere(double y){
        this.etagere.changeHauteurEtagere(y, HAUTEUR_ETAGERE_MAX, HAUTEUR_ETAGERE_MIN, HAUTEUR_ETAGE_MIN);
    }
     
    public void changeHauteurEtage(int numEtage, double y){
        this.etagere.changeHauteurEtage(numEtage, y, HAUTEUR_ETAGE_MIN);
    }
    
    public void changeLargeurCaisson(int numEtage, int numCaisson, double x){
        this.etagere.changeLargeurCaisson(numEtage, numCaisson, x, LARGEUR_CAISSON_MIN);
    }

        
    public void setTripleContour(boolean bool){
        Choix.getInstance().setTripleContour(bool);
        this.etagere.updateOrigines();
    }
    
    public int getNumEtage(Etage etage){
        return etagere.getNumEtage(etage);
    }
    
    public int selectionCaisson(double x, int numEtage){
        return etagere.selectionCaisson(x, numEtage);
    }
    
    public Etagere getEtagere(){
        return this.etagere;
    }
    
    public Caisson getCaisson(int numEtage, int numCaisson){
        return this.etagere.getCaisson(numEtage, numCaisson);
    }

    public Etage getEtageByClick(double x, double y){
        return this.etagere.getEtageByClick(x, y);
    }
    
    public Etage getEtageByNum(int numEtage){
        return this.etagere.getEtageByNum(numEtage);
    }
    
    public int getNombreCaissonEtage(int numEtage){
        return this.etagere.getNombreCaissonEtage(numEtage);
    }
    
    public void addCaisson(int numEtage){
        this.etagere.addCaisson(numEtage, LARGEUR_CAISSON_MIN);
    }
    
    public ArrayList<Planche> getAllPlanches(){
        return etagere.getAllPlanches();
    }
    
    private Etagere createZoomedEtagere(double ratio){
        Etagere zoomEtagere = (Etagere)deepClone(etagere);
        zoomEtagere.setEpD(etagere.getEpD()*ratio);
        zoomEtagere.setEpT(etagere.getEpT()*ratio);
        zoomEtagere.setLargeur(etagere.getLargeur()*ratio);
        zoomEtagere.setHauteur(etagere.getHauteur()*ratio);
        zoomEtagere.updateOrigines();
        return zoomEtagere;
    };    
    
    public double getProportionEtage(double hauteur){
        return etagere.getProportionEtage(hauteur);
    }
    
    public double getProportionCaisson(int numEtage, double largeur){
        return etagere.getProportionCaisson(numEtage, largeur);
    }
    
    public ArrayList<Planche> getZoomedPlanches(double ratio){
        Etagere zoomEtagere = createZoomedEtagere(ratio);
        return zoomEtagere.getAllPlanches();
    }

    public Planche getZoomedPlanche(double ratio, double x, double y){
        Etagere zoomEtagere = createZoomedEtagere(ratio);
        return zoomEtagere.getPlanche(ratio*x, ratio*y);
    }
    
    public ArrayList<Planche> getZoomedPlanchesEtage(double ratio, int numEtage){
        Etagere zoomEtagere = createZoomedEtagere(ratio);
        return zoomEtagere.getPlanchesEtage(numEtage);
    }
    
    public ArrayList<Planche> getZoomedPlanchesCaisson(double ratio, int numEtage, int numCaisson){
        Etagere zoomEtagere = createZoomedEtagere(ratio);
        return zoomEtagere.getPlanchesCaisson(numEtage, numCaisson);
    }
    
    public ArrayList<Planche> getZoomedPlanchesCadre(double ratio){
        Etagere zoomEtagere = createZoomedEtagere(ratio);
        return zoomEtagere.getPlanchesCadre();
    }
    
    public ArrayList<Planche> getZoomedPlanchesCadreX(double ratio){
        Etagere zoomEtagere = createZoomedEtagere(ratio);
        return zoomEtagere.getPlanchesCadreX();
    }

    public ArrayList<Planche> getZoomedPlanchesCadreY(double ratio){
        Etagere zoomEtagere = createZoomedEtagere(ratio);
        return zoomEtagere.getPlanchesCadreY();
    }
    
    public void ajoutEtage(){
        this.etagere.ajoutEtage(HAUTEUR_ETAGE_MIN);
    }
    
    public void deleteCaisson(int numEtage){
        this.etagere.deleteCaisson(numEtage);
    }
    
    public void supprimeEtage(){
        this.etagere.supprimeEtage();
    }    
    
    public boolean isCadreX(double x, double y){
        return etagere.isCadreX(x, y);
    }
    
    public boolean isCadreY(double x, double y){
        return etagere.isCadreY(x, y);
    }
    
    public void supprimeEtageSelec(int numEtage){
        this.etagere.supprimeEtageSelec(numEtage);
    }    
    
    public boolean testNewHauteurEtagere(double newHauteurEtagere){
        return this.etagere.testHauteurEtagere(newHauteurEtagere, HAUTEUR_ETAGE_MIN);
    }    
    
    public boolean testNewLargeurEtagere(double newLargeurEtagere){
        return this.etagere.testLargeurEtagere(newLargeurEtagere, LARGEUR_CAISSON_MIN);
    }   
//    public double findBestRatioHauteur(){
//        return this.etagere.findBestRatioHauteur();
//    }    
        
    public void setProportionEtage(int numEtage, double proportion){
        this.etagere.setProportionEtage(numEtage, proportion, HAUTEUR_ETAGE_MIN);
    }    
    
    public void setProportionCaisson(int numEtage, int numCaisson, double proportion){
        this.etagere.setProportionCaisson(numEtage, numCaisson, proportion, LARGEUR_CAISSON_MIN);
    }        
    
    public void setHauteur(double hauteur){
        this.etagere.setHauteur(hauteur);
    }
    
    public void setLargeur(double largeur){
        this.etagere.setLargeur(largeur);
    }

    public void getListePlanches(){
        ArrayList<Planche> planchesReverse = new ArrayList<>();
        for(Planche planche : this.getAllPlanches()){
            planchesReverse.add(planche);
        }
        Collections.reverse(planchesReverse);
        List<String> liste = new ArrayList<String>();
        for (int i = 1; i < planchesReverse.size()+1; i++) {
            liste.add(Integer.toString(i));
        }
        Iterator iterator = liste.iterator();
        
            Writer writer = null;

            try {
                writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("Liste de planches.txt"), "utf-8"));
                for(Planche planche : planchesReverse){
                    List<String> dim = makeListeFromDim(planche);
                    List<String> ori = makeListeFromOri(planche);
                    writer.write("Planche "+iterator.next()+"\n");
                    writer.write(planche.getNom()+"\n");
                    writer.write("Origine: "+ori.get(0)+" "+ori.get(1)+" "+ori.get(2)+"\n");
                    writer.write("Dimensions: "+dim.get(0)+" "+dim.get(1)+" "+dim.get(2)+"\n\n");
                }
            } catch (IOException ex) {
                // Report
            } finally {
               try {writer.close();} catch (Exception ex) {/*ignore*/}
            }     
    }
    
    private List<String> makeListeFromDim(Planche planche){
        List<Double> dim=new ArrayList();
            List<String> dimString=new ArrayList();
            dim.add(planche.getX());
            dim.add(planche.getY());
            dim.add(planche.getZ());
            Collections.sort(dim);
            Collections.reverse(dim);
            for(double doub : dim){
                dimString.add(doubleToString(doub));
            }
            return dimString;
    }
    
    private List<String> makeListeFromOri(Planche planche){
        List<Double> dim=new ArrayList();
            List<String> dimString=new ArrayList();
            dim.add(planche.getOrigine().getX());
            dim.add(planche.getOrigine().getY());
            dim.add(planche.getOrigine().getZ());
            for(double doub : dim){
                dimString.add(doubleToString(doub));
            }
            return dimString;
    }
    
    private String doubleToString(double number){
        return new DecimalFormat("##.###").format(number);
    }
    
    public static Object deepClone(Object object) {
        try {
          ByteArrayOutputStream baos = new ByteArrayOutputStream();
          ObjectOutputStream oos = new ObjectOutputStream(baos);
          oos.writeObject(object);
          ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
          ObjectInputStream ois = new ObjectInputStream(bais);
          return ois.readObject();
        }
        catch (Exception e) {
          e.printStackTrace();
          return null;
        }
    
    }
    
    public ArrayList<ArrayList<Planche>> getPDPT(){
        ArrayList<Planche> planches = this.getAllPlanches();
        ArrayList<Planche> planchesED = new ArrayList<>();
        ArrayList<Planche> planchesET = new ArrayList<>();
        for(Planche planche : planches){
            List<Double> dims = new ArrayList<Double>();
            dims.add(planche.getHauteur());
            dims.add(planche.getLargeur());
            dims.add(planche.getProfondeur());
            Collections.sort(dims);
            Collections.reverse(dims);
            if(dims.get(dims.size()-1) == EPAISSEUR_PLANCHE_DOUBLE_DEFAUT){
                planchesED.add(planche);
            }
            else{
                planchesET.add(planche);
            }
        }
        ArrayList<ArrayList<Planche>> groupePlanche = new ArrayList<>();
        groupePlanche.add(planchesED);
        groupePlanche.add(planchesET);
        return groupePlanche;
    }
    
    public ArrayList<Panneau> genererPlan(ArrayList<Planche> lp){
	//this.getAllPlanches() en arg pour faire les panneaux 3/4

        ArrayList<Panneau> mesPanneaux = new ArrayList<>();
        Panneau panneau1 = new Panneau(this.getEtagere());//init d'un panneau
        mesPanneaux.add(panneau1);
        
        for(int i = 0; i < lp.size();i++){
            
            Planche plancheTempo = lp.get(i);
            double largeurPlanche;
            if (plancheTempo.getHauteur()>plancheTempo.getLargeur())
            {   
                largeurPlanche = plancheTempo.getHauteur();     
            }                
            else{largeurPlanche = plancheTempo.getLargeur();}
        
            boolean verify = true;
            
            while(verify){
                for (int j = 0; j < mesPanneaux.size();j++){
                    Panneau panneauTemp = mesPanneaux.get(j);
                    for(int k = 0; k < panneauTemp.getNbEtage(); k++){
                        int fits = panneauTemp.fit(largeurPlanche, k);
                        if (fits != -1){
                            panneauTemp.addPlanche(k,plancheTempo);
                            panneauTemp.setLargeurDispo(k,panneauTemp.getLargeurDispo(k)-largeurPlanche);
                            verify = false;
                            System.out.print("planche placee " + i + " " + (int) largeurPlanche +"\n");
                            break;
                        }
                        }
                }
                    
                if (verify){
                    Panneau panneauTemp = new Panneau(this.getEtagere());
                    mesPanneaux.add(panneauTemp);
                    System.out.print("Nouveau Panneau"+"\n");
                    if (panneauTemp.fit(plancheTempo.getLargeur(),0) != -1){
                            panneauTemp.addPlanche(0,plancheTempo);
                            panneauTemp.setLargeurDispo(0,panneauTemp.getLargeurDispo(0)-plancheTempo.getLargeur());
                            verify = false;
                            System.out.print("planche placee"+"\n");                        }
                        }
                }            
            
            
        
        }
        return mesPanneaux;
    }
     
    public ArrayList<Panneau> genererPlan2(){

        ArrayList<Panneau> mesPanneaux = new ArrayList<>();
        
        if (!Choix.getInstance().isTripleContour())
        {
            mesPanneaux = this.genererPlan(this.getPDPT().get(1));
            mesPanneaux.addAll(this.genererPlan(this.getPDPT().get(0)));
        } 
        else {
            mesPanneaux = this.genererPlan(getAllPlanches());
        }
        return mesPanneaux;
    }
}


