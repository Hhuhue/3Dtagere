package old.UI;

import old.Controller.Controller;
import old.Domaine.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Stack;
import javax.swing.JFileChooser;
import javax.swing.JLabel;

public class MainWindow extends javax.swing.JFrame {

    public Controller controller;
    private boolean formatMetric;
    private boolean dragDrop;
    private double lastY;
    private double lastX;
    public Stack<Etagere> stackUndo;
    public Stack<Etagere> stackRedo;   
    private PlanCoupeWindow planCoupeWindow;
    
    public double getRatio(){
        return this.drawingPanel.getRatio(controller);
    }   

    public MainWindow() {
        this.setTitle("3Dtagere");
        this.controller = new Controller();
        this.formatMetric = false;
        this.dragDrop = false;
        this.stackUndo = new Stack<>();
        this.stackUndo.add(controller.getCopyEtagere());
        this.stackRedo = new Stack<>();
        this.planCoupeWindow = new PlanCoupeWindow();
        //Code auto-generate par le UI, ne pas toucher
        initComponents();
        selectionDefault();
        textFieldLargeurEtagere.setText(doubleToString(this.controller.LARGEUR_ETAGERE_DEFAUT));
        textFieldHauteurEtagere.setText(doubleToString(this.controller.HAUTEUR_ETAGERE_DEFAUT));
        textFieldProfondeurEtagere.setText(doubleToString(this.controller.PROFONDEUR_ETAGERE_DEFAUT));
        toggleImperial.setSelected(true);
        toggleImperial.setEnabled(false);
        planCoupeWindow.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

    }

    private void setFormatMetric(boolean bool){
        this.formatMetric = bool;
    }
    
    private void saveEtagereInStackUndo(){
        stackUndo.push(controller.getCopyEtagere());
        stackRedo.clear();
    }
    
    private Etagere undo(){
        Etagere lastEtagere = this.controller.getEtagere();
        if(!stackUndo.empty()){
            lastEtagere = stackUndo.pop();
            stackRedo.push(this.controller.getCopyEtagere());
        }
        return lastEtagere;
    }
    
    private Etagere redo(){
        Etagere lastEtagere = this.controller.getEtagere();
        if(!stackRedo.empty()){
            lastEtagere = stackRedo.pop();
            stackUndo.push(this.controller.getCopyEtagere());
        }
        return lastEtagere;
    } 

    private boolean isStringDouble(String input) {
        boolean bool;
        try {
            bool = true;
            Double.parseDouble(input);
        } catch (NumberFormatException e) {
            bool = false;
        }
        return bool;
    }
    
    private void dragDropEtage(double y){
        double currentY;
        int numEtage = Integer.parseInt(textSelectionEtage.getText());
        Etage etage = controller.getEtageByNum(numEtage);
        if(dragDrop == false){
            this.lastY = y;
        }
        currentY = y;
        double deltaHauteur = (currentY - lastY);
        controller.changeHauteurEtage(numEtage, deltaHauteur);
        lastY = y;
        this.textSelectionLargeur.setText(doubleToString(etage.getLargeurDispo()));
        this.textSelectionHauteur.setText(doubleToString(etage.getHauteurDispo()));
        this.textSelectionProfondeur.setText(doubleToString(etage.getProfondeur()));
        this.textOrigineX.setText(doubleToString(etage.getOrigine().getX()));
        this.textOrigineY.setText(doubleToString(etage.getOrigine().getY()));
        this.textOrigineZ.setText(doubleToString(etage.getOrigine().getZ()));
        this.textFieldProportionEtage.setText(doubleToString(controller.getProportionEtage(etage.getHauteurDispo())));
        this.textFieldProportionCaisson.setText("");                
        this.drawingPanel.update(controller);
        if(formatMetric){
            changeToMetric();
        }   
        drawingPanel.selectionPlanches(controller.getZoomedPlanchesEtage(getRatio(),numEtage), controller);
        
    }

    private void dragDropCaisson(double x){
        double currentX;
        int numEtage = Integer.parseInt(textSelectionEtage.getText());
        int numCaisson = Integer.parseInt(textSelectionCaisson.getText());
        Caisson caisson = controller.getCaisson(numEtage, numCaisson);
        if(dragDrop == false){
            this.lastX = x;
        }
        currentX = x;
        double deltaLargeur = (currentX - lastX);
        controller.changeLargeurCaisson(numEtage, numCaisson, deltaLargeur);
        lastX = x;
        this.textSelectionLargeur.setText(doubleToString(caisson.getLargeurDispo()));
        this.textSelectionHauteur.setText(doubleToString(caisson.getHauteurDispo()));
        this.textSelectionProfondeur.setText(doubleToString(caisson.getProfondeur()));
        this.textOrigineX.setText(doubleToString(caisson.getOrigine().getX()));
        this.textOrigineY.setText(doubleToString(caisson.getOrigine().getY()));
        this.textOrigineZ.setText(doubleToString(caisson.getOrigine().getZ()));
        this.textFieldProportionCaisson.setText(doubleToString(controller.getProportionCaisson(numEtage, caisson.getLargeurDispo())));
        this.drawingPanel.update(controller);
        drawingPanel.selectionPlanches(controller.getZoomedPlanchesCaisson(getRatio(),numEtage, numCaisson), controller); 
        if(formatMetric){
            changeToMetric();
        }
        
    }
    
    private void dragDropEtagereX(double x){
        double currentX;
        if(dragDrop == false){
            this.lastX = x;
        }
        currentX = x;
        double deltaLargeur = (currentX - lastX);
        controller.changeLargeurEtagere(deltaLargeur);
        lastX = x;
            this.textSelectionHauteur.setText(doubleToString(controller.getEtagere().getHauteur()));
            this.textSelectionLargeur.setText(doubleToString(controller.getEtagere().getLargeur()));
            this.textSelectionProfondeur.setText(doubleToString(controller.getEtagere().getProfondeur()));
            this.textFieldLargeurEtagere.setText(doubleToString(controller.getEtagere().getLargeur()));
            if(formatMetric){
                changeToMetric();
                this.textFieldLargeurEtagere.setText(doubleToString(this.controller.getEtagere().getLargeur()*25.4));
            }
            drawingPanel.update(controller);
            drawingPanel.selectionPlanches(controller.getZoomedPlanchesCadreX(getRatio()), controller); 
    }
        private void dragDropEtagereY(double y){
        double currentY;
        if(dragDrop == false){
            this.lastY = y;
        }
        currentY = y;
        double deltaHauteur = (currentY - lastY);
        controller.changeHauteurEtagere(deltaHauteur);
        lastY = y;
        this.textSelectionHauteur.setText(doubleToString(controller.getEtagere().getHauteur()));
            this.textSelectionLargeur.setText(doubleToString(controller.getEtagere().getLargeur()));
            this.textSelectionProfondeur.setText(doubleToString(controller.getEtagere().getProfondeur()));
            this.drawingPanel.update(controller);
            this.textFieldHauteurEtagere.setText(doubleToString(controller.getEtagere().getHauteur()));
            if(formatMetric){
                changeToMetric();
                this.textFieldHauteurEtagere.setText(doubleToString(this.controller.getEtagere().getHauteur()*25.4));
            }
            drawingPanel.update(controller);
            drawingPanel.selectionPlanches(controller.getZoomedPlanchesCadreY(getRatio()), controller); 
        
    }
    
    private void selectionDefault(){
        this.drawingPanel.clearSelectionPlanche(controller);
        textFieldHauteurEtagere.setText(doubleToString(this.controller.getEtagere().getHauteur()));
        textFieldLargeurEtagere.setText(doubleToString(this.controller.getEtagere().getLargeur()));
        textFieldProportionEtage.setText("");
        textFieldProportionCaisson.setText("");
        this.textSelection.setText("");
        this.textSelectionPlanche.setText("");
        this.textSelectionEtage.setText("");
        this.textSelectionCaisson.setText("");
        this.textSelectionLargeur.setText("");
        this.textSelectionHauteur.setText("");
        this.textSelectionProfondeur.setText("");
        this.textOrigineX.setText("");
        this.textOrigineY.setText("");
        this.textOrigineZ.setText("");
        if(formatMetric){
            this.textFieldLargeurEtagere.setText(doubleToString(this.controller.getEtagere().getLargeur()*25.4));
            this.textFieldHauteurEtagere.setText(doubleToString(this.controller.getEtagere().getHauteur()*25.4));
            changeToMetric();
        }
    }
    
    private void selectionEtagere(){
        this.drawingPanel.clearSelectionPlanche(controller);
        this.drawingPanel.selectionPlanches(this.controller.getZoomedPlanchesCadre(getRatio()), controller);
        this.textSelection.setText("Etagere");
        this.textSelectionPlanche.setText("");
        textFieldHauteurEtagere.setText(doubleToString(this.controller.getEtagere().getHauteur()));
        textFieldLargeurEtagere.setText(doubleToString(this.controller.getEtagere().getLargeur()));
        textFieldProportionEtage.setText("");
        textFieldProportionCaisson.setText("");
        this.textSelectionEtage.setText(Integer.toString(this.controller.getEtagere().getNombreEtage()));
        this.textSelectionCaisson.setText(Integer.toString(this.controller.getEtagere().getNombreCaisson()));
        this.textSelectionLargeur.setText(doubleToString(this.controller.getEtagere().getLargeur()));
        this.textSelectionHauteur.setText(doubleToString(this.controller.getEtagere().getHauteur()));
        this.textSelectionProfondeur.setText(doubleToString(this.controller.getEtagere().getProfondeur()));
        this.textOrigineX.setText(doubleToString(this.controller.getEtagere().getOrigine().getX()));
        this.textOrigineY.setText(doubleToString(this.controller.getEtagere().getOrigine().getY()));
        this.textOrigineZ.setText(doubleToString(this.controller.getEtagere().getOrigine().getZ()));
        if(formatMetric){
            this.textFieldLargeurEtagere.setText(doubleToString(this.controller.getEtagere().getLargeur()*25.4));
            this.textFieldHauteurEtagere.setText(doubleToString(this.controller.getEtagere().getHauteur()*25.4));
            this.textFieldProfondeurEtagere.setText(doubleToString(this.controller.getEtagere().getProfondeur()*25.4));
            changeToMetric();
        }
    }

    private void changeJlabelToMetric(JLabel jLabel){
        String inputString = jLabel.getText();
        
        if(inputString.equals("")){
            inputString = "0";
        }
        double input = Double.parseDouble(inputString);
        input *= 25.4;
        jLabel.setText(doubleToString(input));
    }
    
    
    private void changeToMetric(){
        changeJlabelToMetric(textSelectionLargeur);
        changeJlabelToMetric(textSelectionHauteur);
        changeJlabelToMetric(textSelectionProfondeur);
        changeJlabelToMetric(textOrigineX);
        changeJlabelToMetric(textOrigineY);
        changeJlabelToMetric(textOrigineZ);
    }
    
    
    private String doubleToString(double number){
        return new DecimalFormat("##.##").format(number);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonPanel = new javax.swing.JPanel();
        boutonAjouterCaisson = new javax.swing.JButton();
        boutonSupprimerEtage = new javax.swing.JButton();
        toggleTriple = new javax.swing.JToggleButton();
        textFieldHauteurEtagere = new javax.swing.JTextField();
        toggleAvecFond = new javax.swing.JToggleButton();
        boutonAjouterEtage = new javax.swing.JButton();
        toggleTopLong = new javax.swing.JToggleButton();
        boutonSupprimerCaisson = new javax.swing.JButton();
        labelHauteurEtagere = new javax.swing.JLabel();
        labelHauteurEtagere2 = new javax.swing.JLabel();
        textFieldLargeurEtagere = new javax.swing.JTextField();
        textFieldProportionCaisson = new javax.swing.JTextField();
        labelHauteurEtage1 = new javax.swing.JLabel();
        textFieldProportionEtage = new javax.swing.JTextField();
        labelHauteurEtage = new javax.swing.JLabel();
        labelProfondeurEtagere = new javax.swing.JLabel();
        textFieldProfondeurEtagere = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        boutonAnnuler = new javax.swing.JButton();
        boutonRetablir = new javax.swing.JButton();
        toggleMetric = new javax.swing.JToggleButton();
        toggleImperial = new javax.swing.JToggleButton();
        drawingPanel = new old.UI.DrawingPanel();
        jPanel2 = new javax.swing.JPanel();
        panelOrigine = new javax.swing.JPanel();
        labelSelection1 = new javax.swing.JLabel();
        labelOrigineX = new javax.swing.JLabel();
        textOrigineX = new javax.swing.JLabel();
        labelOrigineY = new javax.swing.JLabel();
        textOrigineY = new javax.swing.JLabel();
        labelOrigineZ = new javax.swing.JLabel();
        textOrigineZ = new javax.swing.JLabel();
        panelOrigine3 = new javax.swing.JPanel();
        labelSelection2 = new javax.swing.JLabel();
        labelOrigineX1 = new javax.swing.JLabel();
        textSourisX = new javax.swing.JLabel();
        labelOrigineY1 = new javax.swing.JLabel();
        textSourisY = new javax.swing.JLabel();
        labelSelection5 = new javax.swing.JLabel();
        panelOrigine2 = new javax.swing.JPanel();
        labelSelectionEtage = new javax.swing.JLabel();
        textSelectionEtage = new javax.swing.JLabel();
        textSelectionCaisson = new javax.swing.JLabel();
        labelSelectionCaisson = new javax.swing.JLabel();
        panelOrigine1 = new javax.swing.JPanel();
        labelSelection3 = new javax.swing.JLabel();
        textSelectionLargeur = new javax.swing.JLabel();
        textSelectionHauteur = new javax.swing.JLabel();
        labelSelection4 = new javax.swing.JLabel();
        labelSelection8 = new javax.swing.JLabel();
        textSelectionProfondeur = new javax.swing.JLabel();
        boutonEnregistrer = new javax.swing.JButton();
        boutonCharger = new javax.swing.JButton();
        boutonListe = new javax.swing.JButton();
        boutonPlan = new javax.swing.JButton();
        boutonPlan3D = new javax.swing.JButton();
        boutonPlanches3D = new javax.swing.JButton();
        textSelection = new javax.swing.JLabel();
        textSelectionPlanche = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBounds(new java.awt.Rectangle(0, 0, 800, 424));
        setMinimumSize(new java.awt.Dimension(20, 20));
        setSize(new java.awt.Dimension(800, 424));

        buttonPanel.setMinimumSize(new java.awt.Dimension(20, 20));

        boutonAjouterCaisson.setText("Ajouter Caisson");
        boutonAjouterCaisson.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                boutonAjouterCaissonMouseClicked(evt);
            }
        });

        boutonSupprimerEtage.setText("Supprimer Etage");
        boutonSupprimerEtage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                boutonSupprimerEtageMouseClicked(evt);
            }
        });

        toggleTriple.setText("Périmètre Triple");
        toggleTriple.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                toggleTripleMouseClicked(evt);
            }
        });

        textFieldHauteurEtagere.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textFieldHauteurEtagereKeyPressed(evt);
            }
        });

        toggleAvecFond.setText("Avec Fond");
        toggleAvecFond.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                toggleAvecFondMouseClicked(evt);
            }
        });
        toggleAvecFond.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toggleAvecFondActionPerformed(evt);
            }
        });

        boutonAjouterEtage.setText("Ajouter Étage");
        boutonAjouterEtage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                boutonAjouterEtageMouseClicked(evt);
            }
        });

        toggleTopLong.setText("Top long");
        toggleTopLong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                toggleTopLongMouseClicked(evt);
            }
        });

        boutonSupprimerCaisson.setText("Supprimer Caisson");
        boutonSupprimerCaisson.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                boutonSupprimerCaissonMouseClicked(evt);
            }
        });

        labelHauteurEtagere.setText("Hauteur Etagere");

        labelHauteurEtagere2.setText("Largeur Etagere");

        textFieldLargeurEtagere.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textFieldLargeurEtagereKeyPressed(evt);
            }
        });

        textFieldProportionCaisson.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textFieldProportionCaissonKeyPressed(evt);
            }
        });

        labelHauteurEtage1.setText("% largeur caisson ");

        textFieldProportionEtage.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textFieldProportionEtageKeyPressed(evt);
            }
        });

        labelHauteurEtage.setText("% hauteur etage");

        labelProfondeurEtagere.setText("Profondeur Etagere");

        textFieldProfondeurEtagere.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textFieldProfondeurEtagereKeyPressed(evt);
            }
        });

        boutonAnnuler.setText("Annuler");
        boutonAnnuler.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                boutonAnnulerMouseClicked(evt);
            }
        });

        boutonRetablir.setText("Retablir");
        boutonRetablir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                boutonRetablirMouseClicked(evt);
            }
        });

        toggleMetric.setText("Metric");
        toggleMetric.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                toggleMetricMouseClicked(evt);
            }
        });

        toggleImperial.setText("Imperial");
        toggleImperial.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                toggleImperialMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(toggleImperial)
                    .addComponent(boutonAnnuler, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(boutonRetablir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(toggleMetric, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(boutonRetablir)
                    .addComponent(boutonAnnuler))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(toggleImperial)
                    .addComponent(toggleMetric, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(7, 7, 7))
        );

        javax.swing.GroupLayout buttonPanelLayout = new javax.swing.GroupLayout(buttonPanel);
        buttonPanel.setLayout(buttonPanelLayout);
        buttonPanelLayout.setHorizontalGroup(
            buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(labelHauteurEtage)
                        .addComponent(labelHauteurEtage1)
                        .addComponent(boutonSupprimerCaisson, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(boutonAjouterCaisson, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(boutonAjouterEtage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(boutonSupprimerEtage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(toggleAvecFond, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(toggleTriple, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(toggleTopLong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelHauteurEtagere)
                        .addComponent(labelHauteurEtagere2)
                        .addComponent(textFieldHauteurEtagere, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(textFieldLargeurEtagere, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(textFieldProportionCaisson)
                        .addComponent(textFieldProportionEtage))
                    .addComponent(labelProfondeurEtagere)
                    .addComponent(textFieldProfondeurEtagere, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        buttonPanelLayout.setVerticalGroup(
            buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(toggleTopLong)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(toggleTriple)
                .addGap(4, 4, 4)
                .addComponent(toggleAvecFond)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(boutonAjouterEtage)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(boutonSupprimerEtage)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(boutonAjouterCaisson)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(boutonSupprimerCaisson)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(labelHauteurEtagere)
                .addGap(7, 7, 7)
                .addComponent(textFieldHauteurEtagere, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelHauteurEtagere2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textFieldLargeurEtagere, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelProfondeurEtagere)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textFieldProfondeurEtagere, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelHauteurEtage)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textFieldProportionEtage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelHauteurEtage1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textFieldProportionCaisson, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        drawingPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        drawingPanel.setMinimumSize(new java.awt.Dimension(400, 300));
        drawingPanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                drawingPanelMouseMoved(evt);
            }
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                drawingPanelMouseDragged(evt);
            }
        });
        drawingPanel.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                drawingPanelMouseWheelMoved(evt);
            }
        });
        drawingPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                drawingPanelMouseReleased(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                drawingPanelMouseClicked(evt);
            }
        });
        drawingPanel.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                drawingPanelComponentResized(evt);
            }
        });

        javax.swing.GroupLayout drawingPanelLayout = new javax.swing.GroupLayout(drawingPanel);
        drawingPanel.setLayout(drawingPanelLayout);
        drawingPanelLayout.setHorizontalGroup(
            drawingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 398, Short.MAX_VALUE)
        );
        drawingPanelLayout.setVerticalGroup(
            drawingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        labelSelection1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelSelection1.setText("Origine");

        labelOrigineX.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelOrigineX.setText("x: ");

        textOrigineX.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        textOrigineX.setText(" ");

        labelOrigineY.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelOrigineY.setText("y: ");

        textOrigineY.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        textOrigineY.setText(" ");

        labelOrigineZ.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelOrigineZ.setText("z: ");

        textOrigineZ.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        textOrigineZ.setText(" ");

        javax.swing.GroupLayout panelOrigineLayout = new javax.swing.GroupLayout(panelOrigine);
        panelOrigine.setLayout(panelOrigineLayout);
        panelOrigineLayout.setHorizontalGroup(
            panelOrigineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOrigineLayout.createSequentialGroup()
                .addComponent(labelSelection1, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelOrigineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelOrigineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(labelOrigineY, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelOrigineZ, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(labelOrigineX, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelOrigineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textOrigineX, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(textOrigineY, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(textOrigineZ, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelOrigineLayout.setVerticalGroup(
            panelOrigineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOrigineLayout.createSequentialGroup()
                .addGroup(panelOrigineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelSelection1)
                    .addComponent(labelOrigineX)
                    .addComponent(textOrigineX))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelOrigineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelOrigineY)
                    .addComponent(textOrigineY))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelOrigineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelOrigineZ)
                    .addComponent(textOrigineZ)))
        );

        labelSelection2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelSelection2.setText("Souris");

        labelOrigineX1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelOrigineX1.setText("x: ");

        textSourisX.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        textSourisX.setText(" ");

        labelOrigineY1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelOrigineY1.setText("y: ");

        textSourisY.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        textSourisY.setText(" ");

        javax.swing.GroupLayout panelOrigine3Layout = new javax.swing.GroupLayout(panelOrigine3);
        panelOrigine3.setLayout(panelOrigine3Layout);
        panelOrigine3Layout.setHorizontalGroup(
            panelOrigine3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOrigine3Layout.createSequentialGroup()
                .addComponent(labelSelection2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelOrigine3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(labelOrigineY1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelOrigineX1, javax.swing.GroupLayout.Alignment.LEADING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelOrigine3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textSourisX, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(textSourisY, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        panelOrigine3Layout.setVerticalGroup(
            panelOrigine3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOrigine3Layout.createSequentialGroup()
                .addGroup(panelOrigine3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelSelection2)
                    .addComponent(labelOrigineX1)
                    .addComponent(textSourisX))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelOrigine3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelOrigineY1)
                    .addComponent(textSourisY)))
        );

        labelSelection5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelSelection5.setText("Selection");

        labelSelectionEtage.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelSelectionEtage.setText("Etage(s):");

        textSelectionEtage.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        textSelectionEtage.setText(" ");

        textSelectionCaisson.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        textSelectionCaisson.setText(" ");

        labelSelectionCaisson.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelSelectionCaisson.setText("Caisson(s):");

        javax.swing.GroupLayout panelOrigine2Layout = new javax.swing.GroupLayout(panelOrigine2);
        panelOrigine2.setLayout(panelOrigine2Layout);
        panelOrigine2Layout.setHorizontalGroup(
            panelOrigine2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOrigine2Layout.createSequentialGroup()
                .addGroup(panelOrigine2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelSelectionEtage, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelSelectionCaisson, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelOrigine2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textSelectionEtage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelOrigine2Layout.createSequentialGroup()
                        .addComponent(textSelectionCaisson, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(3, 3, 3))))
        );
        panelOrigine2Layout.setVerticalGroup(
            panelOrigine2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOrigine2Layout.createSequentialGroup()
                .addGroup(panelOrigine2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelSelectionEtage)
                    .addComponent(textSelectionEtage))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelOrigine2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textSelectionCaisson)
                    .addComponent(labelSelectionCaisson))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        labelSelection3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelSelection3.setText("Largeur:");

        textSelectionLargeur.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        textSelectionLargeur.setText(" ");

        textSelectionHauteur.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        textSelectionHauteur.setText(" ");

        labelSelection4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelSelection4.setText("Hauteur:");

        labelSelection8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelSelection8.setText("Profondeur:");

        textSelectionProfondeur.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        textSelectionProfondeur.setText(" ");

        javax.swing.GroupLayout panelOrigine1Layout = new javax.swing.GroupLayout(panelOrigine1);
        panelOrigine1.setLayout(panelOrigine1Layout);
        panelOrigine1Layout.setHorizontalGroup(
            panelOrigine1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOrigine1Layout.createSequentialGroup()
                .addGroup(panelOrigine1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelSelection3, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelSelection4, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelSelection8, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelOrigine1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textSelectionProfondeur, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(textSelectionLargeur, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(textSelectionHauteur, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        panelOrigine1Layout.setVerticalGroup(
            panelOrigine1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOrigine1Layout.createSequentialGroup()
                .addGroup(panelOrigine1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelSelection3)
                    .addComponent(textSelectionLargeur))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelOrigine1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textSelectionHauteur)
                    .addComponent(labelSelection4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelOrigine1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textSelectionProfondeur)
                    .addComponent(labelSelection8))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        boutonEnregistrer.setText("Enregistrer");
        boutonEnregistrer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                boutonEnregistrerMouseClicked(evt);
            }
        });

        boutonCharger.setText("Charger");
        boutonCharger.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                boutonChargerMouseClicked(evt);
            }
        });

        boutonListe.setText("Generer liste");
        boutonListe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                boutonListeMouseClicked(evt);
            }
        });

        boutonPlan.setText("Generer Plan de Coupe");
        boutonPlan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                boutonPlanMouseClicked(evt);
            }
        });

        boutonPlan3D.setText("Generer Plan 3D");
        boutonPlan3D.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                boutonPlan3DMouseClicked(evt);
            }
        });

        boutonPlanches3D.setText("Generer Planches 3D");
        boutonPlanches3D.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                boutonPlanches3DMouseClicked(evt);
            }
        });

        textSelection.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        textSelection.setText(" ");

        textSelectionPlanche.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        textSelectionPlanche.setText(" ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelOrigine, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelOrigine1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelOrigine2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelOrigine3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelSelection5, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(boutonEnregistrer, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(boutonCharger, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(boutonListe, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(boutonPlan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(boutonPlan3D, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(boutonPlanches3D, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(textSelection, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(textSelectionPlanche, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(labelSelection5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelOrigine3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(textSelection)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textSelectionPlanche)
                .addGap(18, 18, 18)
                .addComponent(panelOrigine2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelOrigine1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panelOrigine, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(boutonEnregistrer, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(boutonCharger)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(boutonListe)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(boutonPlan)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(boutonPlan3D)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(boutonPlanches3D)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(buttonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(drawingPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(buttonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(drawingPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void toggleAvecFondActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toggleAvecFondActionPerformed
        Choix.getInstance().setAvecFond(rootPaneCheckingEnabled);
    }//GEN-LAST:event_toggleAvecFondActionPerformed

    private void boutonAjouterEtageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_boutonAjouterEtageMouseClicked
        saveEtagereInStackUndo();
        this.controller.ajoutEtage();
        this.drawingPanel.update(this.controller);
        this.selectionDefault();
        selectionEtagere();
    }//GEN-LAST:event_boutonAjouterEtageMouseClicked

    private void textFieldHauteurEtagereKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textFieldHauteurEtagereKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String input = textFieldHauteurEtagere.getText();
            if (this.isStringDouble(input)) {
                double inputDouble = Double.parseDouble(input);
                if(toggleMetric.isSelected()){
                    inputDouble /= 25.4;
                }
                if (inputDouble < this.controller.HAUTEUR_ETAGERE_MIN) {
                    inputDouble = this.controller.HAUTEUR_ETAGERE_MIN;
                } 
                else if (inputDouble > this.controller.HAUTEUR_ETAGERE_MAX) {
                    inputDouble = this.controller.HAUTEUR_ETAGERE_MAX;
                }
                
                if(this.controller.testNewHauteurEtagere(inputDouble)){
                    saveEtagereInStackUndo();
                    this.controller.setHauteur(inputDouble);
                    if(toggleMetric.isSelected()){
                        inputDouble *= 25.4;
                    }
                    textFieldHauteurEtagere.setText(doubleToString(inputDouble));
                }
                else{
                    inputDouble = this.controller.getEtagere().getHauteur();
                    if(toggleMetric.isSelected()){
                        inputDouble *= 25.4;
                    }
                    textFieldHauteurEtagere.setText(doubleToString(inputDouble));

                }
            this.drawingPanel.update(controller);
            selectionEtagere();
            }
            else{
                double inputDouble = this.controller.getEtagere().getHauteur();
                if(toggleMetric.isSelected()){
                    inputDouble *= 25.4;
                }
                textFieldHauteurEtagere.setText(doubleToString(inputDouble));      
            }
        }

    }//GEN-LAST:event_textFieldHauteurEtagereKeyPressed

    private void boutonSupprimerEtageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_boutonSupprimerEtageMouseClicked
        if(textSelection.getText()=="Etage"){
            saveEtagereInStackUndo();
            this.controller.supprimeEtageSelec(Integer.parseInt(textSelectionEtage.getText()));
        }
        else if(textSelection.getText()=="Etagere"){
            saveEtagereInStackUndo();
            this.controller.supprimeEtage();
        }
        
        this.drawingPanel.update(this.controller);
        selectionEtagere();
    }//GEN-LAST:event_boutonSupprimerEtageMouseClicked

    private void toggleTopLongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toggleTopLongMouseClicked
        if (toggleTopLong.isSelected()) {
            Choix.getInstance().setLongTop(true);
            this.drawingPanel.update(this.controller);
        } else {
            Choix.getInstance().setLongTop(false);
            this.drawingPanel.update(this.controller);
        }
    }//GEN-LAST:event_toggleTopLongMouseClicked

    private void toggleTripleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toggleTripleMouseClicked
        if (toggleTriple.isSelected()) {
            this.controller.setTripleContour(true);
            this.drawingPanel.update(this.controller);
        } else {
            this.controller.setTripleContour(false);
            this.drawingPanel.update(this.controller);
        }
    }//GEN-LAST:event_toggleTripleMouseClicked

    private void boutonAjouterCaissonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_boutonAjouterCaissonMouseClicked
        if(textSelection.getText() == "Etage"){
            int numEtage = Integer.parseInt(textSelectionEtage.getText());
            saveEtagereInStackUndo();
            this.controller.addCaisson(numEtage);
            this.drawingPanel.update(this.controller);
            this.textSelectionCaisson.setText(Integer.toString(this.controller.getNombreCaissonEtage(numEtage)));
            drawingPanel.selectionPlanches(controller.getZoomedPlanchesEtage(getRatio(),numEtage), controller);
        }
    }//GEN-LAST:event_boutonAjouterCaissonMouseClicked

    private void boutonSupprimerCaissonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_boutonSupprimerCaissonMouseClicked
        if(textSelection.getText() == "Etage"){        
            int numEtage = Integer.parseInt(textSelectionEtage.getText());
            saveEtagereInStackUndo();
            this.controller.deleteCaisson(numEtage);
            this.drawingPanel.update(this.controller);
            this.textSelectionCaisson.setText(Integer.toString(this.controller.getNombreCaissonEtage(numEtage)));
            drawingPanel.selectionPlanches(controller.getZoomedPlanchesEtage(getRatio(),numEtage), controller);
        }
      }//GEN-LAST:event_boutonSupprimerCaissonMouseClicked

    private void toggleAvecFondMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toggleAvecFondMouseClicked
        if (toggleAvecFond.isSelected()) {
            Choix.getInstance().setAvecFond(true);
            this.drawingPanel.update(this.controller);
        } else {
            Choix.getInstance().setAvecFond(false);
            this.drawingPanel.update(this.controller);
        }
    }//GEN-LAST:event_toggleAvecFondMouseClicked

    private void drawingPanelMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_drawingPanelMouseMoved
        java.awt.Point actualMousePoint = evt.getPoint();
        
        double mousePanelX = actualMousePoint.getX();
        double mousePanelY = -1 * (actualMousePoint.getY() - drawingPanel.getSize().getHeight());

        double mouseEtagereX = (mousePanelX - drawingPanel.getSize().getWidth() / 2 + controller.getEtagere().getLargeur() * getRatio() / 2) / getRatio();
        double mouseEtagereY = (mousePanelY - drawingPanel.getSize().getHeight() / 2 + controller.getEtagere().getHauteur() * getRatio() / 2) / getRatio();
        
        if (mouseEtagereX > 0 && mouseEtagereX < controller.getEtagere().getLargeur() && mouseEtagereY > 0 && mouseEtagereY < controller.getEtagere().getHauteur()) {
            if(formatMetric){
                mouseEtagereX*=25.4;
                mouseEtagereY*=25.4;
            }
            textSourisX.setText(doubleToString(mouseEtagereX));
            textSourisY.setText(doubleToString(mouseEtagereY));
        } else {
            textSourisX.setText("");
            textSourisY.setText("");
        }
    }//GEN-LAST:event_drawingPanelMouseMoved

    private void drawingPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_drawingPanelMouseClicked
        java.awt.Point actualMousePoint = evt.getPoint();

        double mousePanelX = actualMousePoint.getX();
        double mousePanelY = -1 * (actualMousePoint.getY() - drawingPanel.getSize().getHeight());

        double mouseEtagereX = (mousePanelX - drawingPanel.getWidth() / 2 + controller.getEtagere().getLargeur() * getRatio() / 2) / getRatio();
        double mouseEtagereY = (mousePanelY - drawingPanel.getHeight() / 2 + controller.getEtagere().getHauteur() * getRatio() / 2) / getRatio();

        boolean clickInEtagere = ( mouseEtagereX > 0 
                                && mouseEtagereX < controller.getEtagere().getLargeur() 
                                && mouseEtagereY > 0 
                                && mouseEtagereY < controller.getEtagere().getHauteur());
        
            if (evt.getButton() == 2) {
                if(clickInEtagere){
                    Planche planche = controller.getPlanche(mouseEtagereX, mouseEtagereY);
                    if(planche!= null){
                        this.textSelection.setText("Planche");
                        this.textSelectionPlanche.setText(planche.getNom());
                        this.textSelectionEtage.setText("");
                        this.textSelectionCaisson.setText("");
                        this.textSelectionLargeur.setText(doubleToString(planche.getLargeur()));
                        this.textSelectionHauteur.setText(doubleToString(planche.getHauteur()));
                        this.textSelectionProfondeur.setText(doubleToString(planche.getProfondeur()));
                        this.textOrigineX.setText(doubleToString(planche.getOrigine().getX()));
                        this.textOrigineY.setText(doubleToString(planche.getOrigine().getY()));
                        this.textOrigineZ.setText(doubleToString(planche.getOrigine().getZ()));
                        this.textFieldProportionEtage.setText("");
                        this.textFieldProportionCaisson.setText("");
                        drawingPanel.selectionPlanche(controller.getZoomedPlanche(getRatio(),mouseEtagereX, mouseEtagereY), controller);
                        if(formatMetric){
                            changeToMetric();
                        }   
                    }
                }

            }
            if (evt.getButton() == 1) {
                if(clickInEtagere){
                    Etage etage = controller.getEtageByClick(mouseEtagereX, mouseEtagereY);
                    if(etage != null){
                        int numEtage = controller.getNumEtage(etage);
                        this.textSelection.setText("Etage");
                        this.textSelectionPlanche.setText("");
                        this.textSelectionEtage.setText(Integer.toString(numEtage));
                        this.textSelectionCaisson.setText(Integer.toString(etage.getCaissons().size()));
                        this.textSelectionLargeur.setText(doubleToString(etage.getLargeurDispo()));
                        this.textSelectionHauteur.setText(doubleToString(etage.getHauteurDispo()));
                        this.textSelectionProfondeur.setText(doubleToString(etage.getProfondeur()));
                        this.textOrigineX.setText(doubleToString(etage.getOrigine().getX()));
                        this.textOrigineY.setText(doubleToString(etage.getOrigine().getY()));
                        this.textOrigineZ.setText(doubleToString(etage.getOrigine().getZ()));
                        this.textFieldProportionEtage.setText(doubleToString(controller.getProportionEtage(etage.getHauteurDispo())));
                        this.textFieldProportionCaisson.setText("");
                        if(formatMetric){
                            changeToMetric();
                        }
                        drawingPanel.selectionPlanches(controller.getZoomedPlanchesEtage(getRatio(),numEtage), controller);
                    }
                }
                else{
                    this.selectionDefault();
                }
            }
            if(evt.getButton() == 3){
                if(clickInEtagere){
                    Etage etage = controller.getEtageByClick(mouseEtagereX, mouseEtagereY);
                    if(etage != null){
                        int numEtage = controller.getNumEtage(etage);
                        int numCaisson = controller.selectionCaisson(mouseEtagereX, numEtage);
                        Caisson caisson = controller.getCaisson(numEtage, numCaisson);
                        this.textSelection.setText("Caisson");
                        this.textSelectionEtage.setText(Integer.toString(numEtage));
                        this.textSelectionCaisson.setText(Integer.toString(numCaisson));
                        this.textSelectionLargeur.setText(doubleToString(caisson.getLargeurDispo()));
                        this.textSelectionHauteur.setText(doubleToString(caisson.getHauteurDispo()));
                        this.textSelectionProfondeur.setText(doubleToString(caisson.getProfondeur()));
                        this.textOrigineX.setText(doubleToString(caisson.getOrigine().getX()));
                        this.textOrigineY.setText(doubleToString(caisson.getOrigine().getY()));
                        this.textOrigineZ.setText(doubleToString(caisson.getOrigine().getZ()));
                        this.textFieldProportionEtage.setText(doubleToString(controller.getProportionEtage(etage.getHauteurDispo())));
                        this.textFieldProportionCaisson.setText(doubleToString(controller.getProportionCaisson(numEtage, caisson.getLargeurDispo())));
                        drawingPanel.selectionPlanches(controller.getZoomedPlanchesCaisson(getRatio(),numEtage, numCaisson), controller); 
                        if(formatMetric){
                            changeToMetric();
                        }
                    }
                    else if(controller.isCadreX(mouseEtagereX, mouseEtagereY)){
                        this.selectionEtagere();
                        drawingPanel.selectionPlanches(controller.getZoomedPlanchesCadreX(getRatio()), controller); 
                        this.textSelection.setText("EtagereX");
                    }
                    else if(controller.isCadreY(mouseEtagereX, mouseEtagereY)){
                        this.selectionEtagere();
                        drawingPanel.selectionPlanches(controller.getZoomedPlanchesCadreY(getRatio()), controller); 
                        this.textSelection.setText("EtagereY");
                    }                
                    else{
                        this.selectionEtagere();
                    }
                }
            }
    }//GEN-LAST:event_drawingPanelMouseClicked

    private void textFieldLargeurEtagereKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textFieldLargeurEtagereKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String input = textFieldLargeurEtagere.getText();
            if (this.isStringDouble(input)) {
                double inputDouble = Double.parseDouble(input);
                if(toggleMetric.isSelected()){
                    inputDouble /= 25.4;
                }
                if (inputDouble < this.controller.LARGEUR_ETAGERE_MIN) {
                    inputDouble = this.controller.LARGEUR_ETAGERE_MIN;
                } else if (inputDouble > this.controller.LARGEUR_ETAGERE_MAX) {
                    inputDouble = this.controller.LARGEUR_ETAGERE_MAX;
                }
                
                if(this.controller.testNewLargeurEtagere(inputDouble)){
                    saveEtagereInStackUndo();
                    this.controller.setLargeur(inputDouble);
                    if(toggleMetric.isSelected()){
                        inputDouble *= 25.4;
                    }
                    textFieldLargeurEtagere.setText(doubleToString(inputDouble));
                    this.drawingPanel.update(controller);         
                }
                else{       
                    inputDouble = controller.getEtagere().getLargeur();
                    if(toggleMetric.isSelected()){
                        inputDouble *= 25.4;
                    }
                    textFieldLargeurEtagere.setText(doubleToString(inputDouble));
                }
            }
            else{
                double inputDouble = this.controller.getEtagere().getLargeur();
                if(toggleMetric.isSelected()){
                    inputDouble *= 25.4;
                }
                textFieldHauteurEtagere.setText(doubleToString(inputDouble));      
            }
        }
    }//GEN-LAST:event_textFieldLargeurEtagereKeyPressed

    private void drawingPanelComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_drawingPanelComponentResized
        this.drawingPanel.update(controller);
    }//GEN-LAST:event_drawingPanelComponentResized

    private void drawingPanelMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_drawingPanelMouseWheelMoved
        if(textSelection.getText().equals("Etage")){
                int numEtage = Integer.parseInt(textSelectionEtage.getText());
                Etage etage = controller.getEtageByNum(numEtage);
                double y = (double)-evt.getWheelRotation()/4;
                controller.changeHauteurEtage(numEtage, y);
                this.textSelectionLargeur.setText(doubleToString(etage.getLargeurDispo()));
                this.textSelectionHauteur.setText(doubleToString(etage.getHauteurDispo()));
                this.textSelectionProfondeur.setText(doubleToString(etage.getProfondeur()));
                this.textOrigineX.setText(doubleToString(etage.getOrigine().getX()));
                this.textOrigineY.setText(doubleToString(etage.getOrigine().getY()));
                this.textOrigineZ.setText(doubleToString(etage.getOrigine().getZ()));
                this.textFieldProportionEtage.setText(doubleToString(controller.getProportionEtage(etage.getHauteurDispo())));
                this.textFieldProportionCaisson.setText("");                
                this.drawingPanel.update(controller);
                if(formatMetric){
                    changeToMetric();
                }   
                drawingPanel.selectionPlanches(controller.getZoomedPlanchesEtage(getRatio(),numEtage), controller);
        }
        if(textSelection.getText().equals("Caisson")){
            if(Integer.parseInt(textSelectionCaisson.getText())<controller.getNombreCaissonEtage(Integer.parseInt(textSelectionEtage.getText()))){
                int numEtage = Integer.parseInt(textSelectionEtage.getText());
                int numCaisson = Integer.parseInt(textSelectionCaisson.getText());
                Etage etage = controller.getEtageByNum(numEtage);
                Caisson caisson = controller.getCaisson(numEtage, numCaisson);
                double x = (double)-evt.getWheelRotation()/4;
                controller.changeLargeurCaisson(numEtage, numCaisson, x);
                this.textSelectionLargeur.setText(doubleToString(caisson.getLargeurDispo()));
                this.textSelectionHauteur.setText(doubleToString(caisson.getHauteurDispo()));
                this.textSelectionProfondeur.setText(doubleToString(caisson.getProfondeur()));
                this.textOrigineX.setText(doubleToString(caisson.getOrigine().getX()));
                this.textOrigineY.setText(doubleToString(caisson.getOrigine().getY()));
                this.textOrigineZ.setText(doubleToString(caisson.getOrigine().getZ()));
                this.textFieldProportionEtage.setText(doubleToString(controller.getProportionEtage(etage.getHauteurDispo())));
                this.textFieldProportionCaisson.setText(doubleToString(controller.getProportionCaisson(numEtage, caisson.getLargeurDispo())));                
                this.drawingPanel.update(controller);
                if(formatMetric){
                    changeToMetric();
                }
                drawingPanel.selectionPlanches(controller.getZoomedPlanchesCaisson(getRatio(),numEtage, numCaisson), controller); 
            }
        }
        if(textSelection.getText().equals("EtagereX")){
            Etagere etagere = controller.getEtagere();
            double x = (double)-evt.getWheelRotation()/4;
            controller.changeLargeurEtagere(x);
            this.textSelectionHauteur.setText(doubleToString(etagere.getHauteur()));
            this.textSelectionLargeur.setText(doubleToString(etagere.getLargeur()));
            this.textSelectionProfondeur.setText(doubleToString(etagere.getProfondeur()));
            this.textFieldLargeurEtagere.setText(doubleToString(etagere.getLargeur()));
            if(formatMetric){
                changeToMetric();
                this.textFieldLargeurEtagere.setText(doubleToString(this.controller.getEtagere().getLargeur()*25.4));
            }
            drawingPanel.update(controller);
            drawingPanel.selectionPlanches(controller.getZoomedPlanchesCadreX(getRatio()), controller); 
        }
        if(textSelection.getText().equals("EtagereY")){
            Etagere etagere = controller.getEtagere();
            double y = (double)-evt.getWheelRotation()/4;
            controller.changeHauteurEtagere(y);
            this.textSelectionHauteur.setText(doubleToString(etagere.getHauteur()));
            this.textSelectionLargeur.setText(doubleToString(etagere.getLargeur()));
            this.textSelectionProfondeur.setText(doubleToString(etagere.getProfondeur()));
            this.drawingPanel.update(controller);
            this.textFieldHauteurEtagere.setText(doubleToString(etagere.getHauteur()));
            if(formatMetric){
                changeToMetric();
                this.textFieldHauteurEtagere.setText(doubleToString(this.controller.getEtagere().getHauteur()*25.4));
            }
            drawingPanel.update(controller);
            drawingPanel.selectionPlanches(controller.getZoomedPlanchesCadreY(getRatio()), controller); 
        }
    }//GEN-LAST:event_drawingPanelMouseWheelMoved

    private void toggleImperialMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toggleImperialMouseClicked
        if(toggleImperial.isEnabled()){
            this.setFormatMetric(false);
            selectionDefault();
            textFieldHauteurEtagere.setText(doubleToString(controller.getEtagere().getHauteur()));
            textFieldLargeurEtagere.setText(doubleToString(controller.getEtagere().getLargeur()));  
            textFieldProfondeurEtagere.setText(doubleToString(controller.getEtagere().getProfondeur()));  
            toggleMetric.setSelected(false);
            toggleMetric.setEnabled(true);
            toggleImperial.setEnabled(false);
        }
    }//GEN-LAST:event_toggleImperialMouseClicked

    private void toggleMetricMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toggleMetricMouseClicked
        if(toggleMetric.isEnabled()){
            this.setFormatMetric(true);
            selectionDefault();
            textFieldHauteurEtagere.setText(doubleToString(controller.getEtagere().getHauteur()*25.4));
            textFieldLargeurEtagere.setText(doubleToString(controller.getEtagere().getLargeur()*25.4));            
            textFieldProfondeurEtagere.setText(doubleToString(controller.getEtagere().getProfondeur()*25.4));            
            toggleImperial.setSelected(false);
            toggleImperial.setEnabled(true);    
            toggleMetric.setEnabled(false);   
        }    
    }//GEN-LAST:event_toggleMetricMouseClicked

    private void textFieldProportionEtageKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textFieldProportionEtageKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if(textSelection.getText().equals("Etage")){
                String input = textFieldProportionEtage.getText();
                int numEtage = Integer.parseInt(textSelectionEtage.getText());
                double inputDouble;
                if (this.isStringDouble(input)) {
                    inputDouble = Double.parseDouble(input);
                    if (inputDouble < 0) {
                        inputDouble = 0;
                    } 
                    if(inputDouble > 100){
                        inputDouble = 100;
                    }
                    inputDouble/=100;
                    saveEtagereInStackUndo();
                    controller.setProportionEtage(numEtage, inputDouble); 
                    this.drawingPanel.update(controller);
                }
            }
        }
    }//GEN-LAST:event_textFieldProportionEtageKeyPressed

    private void textFieldProportionCaissonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textFieldProportionCaissonKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if(textSelection.getText().equals("Caisson")){
                String input = textFieldProportionCaisson.getText();
                int numEtage = Integer.parseInt(textSelectionEtage.getText());
                int numCaisson = Integer.parseInt(textSelectionCaisson.getText());
                double inputDouble;
                if (this.isStringDouble(input)) {
                    inputDouble = Double.parseDouble(input);
                    if (inputDouble < 0) {
                        inputDouble = 0;
                    } 
                    if(inputDouble > 100){
                        inputDouble = 100;
                    }
                    inputDouble/=100;
                    saveEtagereInStackUndo();
                    controller.setProportionCaisson(numEtage, numCaisson, inputDouble); 
                    this.drawingPanel.update(controller);
                }
            }
        }
    }//GEN-LAST:event_textFieldProportionCaissonKeyPressed

    private void drawingPanelMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_drawingPanelMouseDragged
        if (evt.getButton() == 1) {
            java.awt.Point actualMousePoint = evt.getPoint();
        

            double mousePanelX = actualMousePoint.getX();
            double mousePanelY = -1 * (actualMousePoint.getY() - drawingPanel.getSize().getHeight());

            double mouseEtagereX = (mousePanelX - drawingPanel.getSize().getWidth() / 2 + controller.getEtagere().getLargeur() * getRatio() / 2) / getRatio();
            double mouseEtagereY = (mousePanelY - drawingPanel.getSize().getHeight() / 2 + controller.getEtagere().getHauteur() * getRatio() / 2) / getRatio();


            if(textSelection.getText().equals("Etage")){
                dragDropEtage(mouseEtagereY);
                dragDrop = true;
            }
            if(textSelection.getText().equals("Caisson")){
                dragDropCaisson(mouseEtagereX);
                dragDrop = true;
            }
            if(textSelection.getText().equals("EtagereX")){
                dragDropEtagereX(mouseEtagereX);
                dragDrop = true;
            }
            if(textSelection.getText().equals("EtagereY")){
                dragDropEtagereY(mouseEtagereY);
                dragDrop = true;
            }    
        }
    }//GEN-LAST:event_drawingPanelMouseDragged

    private void drawingPanelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_drawingPanelMouseReleased
        saveEtagereInStackUndo();
        dragDrop = false;
    }//GEN-LAST:event_drawingPanelMouseReleased

    private void boutonListeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_boutonListeMouseClicked
        controller.getListePlanches();
    }//GEN-LAST:event_boutonListeMouseClicked

    private void boutonChargerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_boutonChargerMouseClicked
        Etagere etagereACharger = null;
        try{
            JFileChooser fileChooser = new JFileChooser();
            File file = null;
            if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                file = fileChooser.getSelectedFile();
            }
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            etagereACharger = (Etagere) in.readObject();
            fileIn.close();
            controller.setEtagere(etagereACharger);
            this.drawingPanel.update(controller);
        }
        catch(IOException i){
            i.printStackTrace();
        }
        catch(ClassNotFoundException c){
            System.out.println("Classe Etagere introuvable");
            c.printStackTrace();
        }
    }//GEN-LAST:event_boutonChargerMouseClicked

    private void boutonEnregistrerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_boutonEnregistrerMouseClicked
        try{
            String filename = "Etagere.ser";
            File file = new File(filename);
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setSelectedFile(file);
            if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
              file = fileChooser.getSelectedFile();
            }
            FileOutputStream fileOut = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(controller.getEtagere());
            out.close();
            fileOut.close();
        }
        catch(IOException i){
            i.printStackTrace();
        }
    }//GEN-LAST:event_boutonEnregistrerMouseClicked

    private void boutonAnnulerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_boutonAnnulerMouseClicked
        this.controller.setEtagere(undo());
        this.drawingPanel.update(controller);
    }//GEN-LAST:event_boutonAnnulerMouseClicked

    private void boutonRetablirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_boutonRetablirMouseClicked
        if(stackRedo.size()>0){
            this.controller.setEtagere(redo());
        }
        this.drawingPanel.update(controller);
    }//GEN-LAST:event_boutonRetablirMouseClicked

    private void boutonPlan3DMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_boutonPlan3DMouseClicked
        STL.makeEtagereSTL(controller.getAllPlanches());
    }//GEN-LAST:event_boutonPlan3DMouseClicked

    private void textFieldProfondeurEtagereKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textFieldProfondeurEtagereKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String input = textFieldProfondeurEtagere.getText();
            if (this.isStringDouble(input)) {
                double inputDouble = Double.parseDouble(input);
                if(toggleMetric.isSelected()){
                    inputDouble /= 25.4;
                }
                if (inputDouble < this.controller.PROFONDEUR_ETAGERE_MIN) {
                    inputDouble = this.controller.PROFONDEUR_ETAGERE_MIN;
                } else if (inputDouble > this.controller.PROFONDEUR_ETAGERE_MAX) {
                    inputDouble = this.controller.PROFONDEUR_ETAGERE_MAX;
                }
                
                saveEtagereInStackUndo();
                this.controller.setProfondeur(inputDouble);
                if(toggleMetric.isSelected()){
                    inputDouble *= 25.4;
                }
                textFieldProfondeurEtagere.setText(doubleToString(inputDouble));
                this.drawingPanel.update(controller);         

            }
            else{
                double inputDouble = this.controller.getEtagere().getProfondeur();
                if(toggleMetric.isSelected()){
                    inputDouble *= 25.4;
                }
                textFieldProfondeurEtagere.setText(doubleToString(inputDouble));      
            }
        }
    }//GEN-LAST:event_textFieldProfondeurEtagereKeyPressed

    private void boutonPlanches3DMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_boutonPlanches3DMouseClicked
        if(textSelection.getText().equals("Planche")){
            Planche planche = null;
            ArrayList<Planche> planches = new ArrayList<>();
            for(Planche myPlanche : controller.getAllPlanches()){
                if(myPlanche.getNom().equals(this.textSelectionPlanche.getText())){
                    planche = myPlanche;
                }
            }
            planches.add(planche);
            STL.makeEtagereSTL(planches);
        }
    }//GEN-LAST:event_boutonPlanches3DMouseClicked

    private void boutonPlanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_boutonPlanMouseClicked
        // TODO add your handling code here:
        planCoupeWindow.setVisible(true);
        planCoupeWindow.setPCWController(controller);
        planCoupeWindow.getDrawingPanelPlanCoupe().setDPController(controller); 
        planCoupeWindow.getDrawingPanelPlanCoupe().dessinerPanneaux();
    }//GEN-LAST:event_boutonPlanMouseClicked

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton boutonAjouterCaisson;
    private javax.swing.JButton boutonAjouterEtage;
    private javax.swing.JButton boutonAnnuler;
    private javax.swing.JButton boutonCharger;
    private javax.swing.JButton boutonEnregistrer;
    private javax.swing.JButton boutonListe;
    private javax.swing.JButton boutonPlan;
    private javax.swing.JButton boutonPlan3D;
    private javax.swing.JButton boutonPlanches3D;
    private javax.swing.JButton boutonRetablir;
    private javax.swing.JButton boutonSupprimerCaisson;
    private javax.swing.JButton boutonSupprimerEtage;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JPanel buttonPanel;
    private old.UI.DrawingPanel drawingPanel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel labelHauteurEtage;
    private javax.swing.JLabel labelHauteurEtage1;
    private javax.swing.JLabel labelHauteurEtagere;
    private javax.swing.JLabel labelHauteurEtagere2;
    private javax.swing.JLabel labelOrigineX;
    private javax.swing.JLabel labelOrigineX1;
    private javax.swing.JLabel labelOrigineY;
    private javax.swing.JLabel labelOrigineY1;
    private javax.swing.JLabel labelOrigineZ;
    private javax.swing.JLabel labelProfondeurEtagere;
    private javax.swing.JLabel labelSelection1;
    private javax.swing.JLabel labelSelection2;
    private javax.swing.JLabel labelSelection3;
    private javax.swing.JLabel labelSelection4;
    private javax.swing.JLabel labelSelection5;
    private javax.swing.JLabel labelSelection8;
    private javax.swing.JLabel labelSelectionCaisson;
    private javax.swing.JLabel labelSelectionEtage;
    private javax.swing.JPanel panelOrigine;
    private javax.swing.JPanel panelOrigine1;
    private javax.swing.JPanel panelOrigine2;
    private javax.swing.JPanel panelOrigine3;
    private javax.swing.JTextField textFieldHauteurEtagere;
    private javax.swing.JTextField textFieldLargeurEtagere;
    private javax.swing.JTextField textFieldProfondeurEtagere;
    private javax.swing.JTextField textFieldProportionCaisson;
    private javax.swing.JTextField textFieldProportionEtage;
    private javax.swing.JLabel textOrigineX;
    private javax.swing.JLabel textOrigineY;
    private javax.swing.JLabel textOrigineZ;
    private javax.swing.JLabel textSelection;
    private javax.swing.JLabel textSelectionCaisson;
    private javax.swing.JLabel textSelectionEtage;
    private javax.swing.JLabel textSelectionHauteur;
    private javax.swing.JLabel textSelectionLargeur;
    private javax.swing.JLabel textSelectionPlanche;
    private javax.swing.JLabel textSelectionProfondeur;
    private javax.swing.JLabel textSourisX;
    private javax.swing.JLabel textSourisY;
    private javax.swing.JToggleButton toggleAvecFond;
    private javax.swing.JToggleButton toggleImperial;
    private javax.swing.JToggleButton toggleMetric;
    private javax.swing.JToggleButton toggleTopLong;
    private javax.swing.JToggleButton toggleTriple;
    // End of variables declaration//GEN-END:variables
}
