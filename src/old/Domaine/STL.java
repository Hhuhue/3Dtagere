/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package old.Domaine;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import javax.swing.JFileChooser;

/**
 *
 * @author thomasdrouin
 */
public class STL {
        
    public static void makeEtagereSTL(ArrayList<Planche> planches){
    Writer writer = null;
        try {
            
            String filename = "Etagere.stl";
            File file = new File(filename);
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setSelectedFile(file);
            if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
              file = fileChooser.getSelectedFile();
            }
            FileOutputStream fileOut = new FileOutputStream(file);
            
            writer = new BufferedWriter(new OutputStreamWriter(fileOut, "utf-8"));
            writer.write("solid Etagere_3Dtagere\n");
            for(Planche planche : planches){
                ArrayList<Triangle> triangles = planche.getListeTriangles();
                for(Triangle triangle : triangles){
                    double n1 = triangle.getNormalVector().x;
                    double n2 = triangle.getNormalVector().y;
                    double n3 = triangle.getNormalVector().z;
                    double v1x = triangle.getP1().getX();
                    double v1y = triangle.getP1().getY();
                    double v1z = triangle.getP1().getZ();                        
                    double v2x = triangle.getP2().getX();
                    double v2y = triangle.getP2().getY();
                    double v2z = triangle.getP2().getZ();                        
                    double v3x = triangle.getP3().getX();
                    double v3y = triangle.getP3().getY();
                    double v3z = triangle.getP3().getZ();
                    writer.write("facet normal "+n1+" "+n2+" "+n3+"\n");
                    writer.write("\touter loop"+"\n");
                    writer.write("\t\tvertex "+v1x+" "+v1y+" "+v1z+"\n");
                    writer.write("\t\tvertex "+v2x+" "+v2y+" "+v2z+"\n");
                    writer.write("\t\tvertex "+v3x+" "+v3y+" "+v3z+"\n");
                    writer.write("\tendloop"+"\n");
                    writer.write("endfacet"+"\n");
                }
            }
            writer.write("endsolid Etagere_3Dtagere");
        } 
        catch (IOException ex) {
            // Report
        } 
        finally {
           try {writer.close();} catch (Exception ex) {/*ignore*/}
        }       
    }
  
}
