package old.Domaine;

public class Choix {
    private static Choix choix;
    
    public static void setInstance(boolean longTop, boolean tripleContour, boolean avecFond, boolean metric){
       choix = new Choix(longTop, tripleContour, avecFond, metric);
    }
        
    public static Choix getInstance(){
        return choix;
    }
        
    private Choix(  boolean longTop, 
                    boolean tripleContour, 
                    boolean avecFond,
                    boolean metric) {
        this.longTop = longTop;
        this.tripleContour = tripleContour;
        this.avecFond = avecFond;
        this.metric = metric;
    }
    
    private boolean longTop;
    private boolean tripleContour;
    private boolean avecFond;
    private boolean metric;
    
    public boolean isLongTop(){
        return this.longTop;
    }
    
    public boolean isTripleContour(){
        return this.tripleContour;
    }
    
    public boolean isAvecFond(){
        return this.avecFond;
    }
    
    public boolean isMetric(){
        return this.metric;
    }
    
    public void setLongTop(boolean boolLongTop)
    {
        this.longTop = boolLongTop;
    }
    
    public void setAvecFond(boolean boolAvecFond)
    {
        this.avecFond = boolAvecFond;
    }
    public void setTripleContour(boolean boolTripleContour)
    {
        this.tripleContour = boolTripleContour;
    }
    public void setMetric(boolean boolMetric)
    {
        this.metric = boolMetric;
    }

}
