/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaficheros;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 *  @author Josseline, Hugo, Javier
 */
public class FATdir {
    StringProperty Numero;
    StringProperty Cluster;
    StringProperty NFile;
 
    
    public void setNumero(String numero){
        this.Numero=new SimpleStringProperty(numero);
    }
    
    public void setCluster(String Cluster){
        this.Cluster=new SimpleStringProperty(Cluster);
    }
    
    public void setNFile(String NFile){
        this.NFile=new SimpleStringProperty(NFile);
    }
    

    
    public String getNumero(){
        return Numero.get();
    }
    
    public String getCluster(){
        return Cluster.get();
    }
    
    public String getNFile(){
        return NFile.get();
    }
    
    
    
    public StringProperty NumeroProperty(){
        return Numero;
    }
    
    public StringProperty ClusterProperty(){
        return Cluster;
    }
    
    public StringProperty NFileProperty(){
        return NFile;
    }
    
}
