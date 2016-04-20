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
 * @author AcerV3
 */
public class directorio {
    StringProperty Nombre;
    StringProperty Fecha;
    StringProperty Tipo;
    StringProperty Tamano;
    StringProperty CI;
    StringProperty CF;
    
    public void setNombre(String nombre){
        this.Nombre=new SimpleStringProperty(nombre);
    }
    public void setCI(String CI){
        this.CI=new SimpleStringProperty(CI);
    }
    public void setCF(String CF){
        this.CF=new SimpleStringProperty(CF);
    }
    
    public void setFecha(String fecha){
        this.Fecha=new SimpleStringProperty(fecha);
    }
    
    public void setTipo(String tipo){
        this.Tipo=new SimpleStringProperty(tipo);
    }
    
    public void setTamano(String tamano){
        this.Tamano=new SimpleStringProperty(tamano);
    }
    
    public String getNombre(){
        return Nombre.get();
    }
    public String getCI(){
        return CI.get();
    }
    public String getCF(){
        return CF.get();
    }
    
    public String getFecha(){
        return Fecha.get();
    }
    
    public String getTipo(){
        return Tipo.get();
    }
    
    public String getTamano(){
        return Tamano.get();
    }
    
    public StringProperty nombreProperty(){
        return Nombre;
    }
    
    public StringProperty fechaProperty(){
        return Fecha;
    }
    
    public StringProperty tipoProperty(){
        return Tipo;
    }
    
    public StringProperty tamanoProperty(){
        return Tamano;
    }
    public StringProperty CIProperty(){
        return CI;
    }
    public StringProperty CFProperty(){
        return CF;
    }
}
