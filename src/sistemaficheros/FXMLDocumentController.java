/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaficheros;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author AcerV3
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    ChoiceBox tipoCB;
    @FXML
    TextField tam;
    @FXML
    TextField sect;
    @FXML
    Button acept;
    int sector = 128;

    @FXML
    public void setInfo() {
        double x = Math.round(2.2);
        System.out.println("value" + tipoCB.getValue());
        double tamano = Double.parseDouble(tam.getText());
        int sectores = Integer.parseInt(sect.getText());
        String tipo = (String) tipoCB.getValue();
        double tamanoSF;
        double FAT;
        double secPC;
        double Dir;
        double total;
        double ocupado;
        switch (tipo) {
            case "GB":
                tamanoSF = (tamano * 1073741824)/128;
                FAT = tamanoSF / sector;
                secPC = tamanoSF / sectores;
                Dir = (secPC * 32) / sector;
                ocupado=(1+FAT+Dir)/sectores;
                total=secPC-ocupado;
                System.out.println("gb");
                System.out.println(tamanoSF + " sectores");
                System.out.println(secPC + " clusters");
                System.out.println("FAT " + FAT+" sectores " + (FAT/sectores)+" clusters");
                System.out.println("Directorio " + Dir+" sectores "+(Dir/sectores)+" clusters");
                System.out.println("Clusters disponibles: "+total);
                System.out.println("Ocupado " + ocupado +" sectores");
                break;
            case "MB":
                tamanoSF = (tamano * 1048576)/sector;
                FAT = tamanoSF / sector;
                secPC = tamanoSF / sectores;
                Dir = (secPC * 32) / sector;
                ocupado=(1+FAT+Dir)/sectores;
                total=secPC-ocupado;
                System.out.println("mb");
                System.out.println(tamanoSF + " sectores");
                System.out.println(secPC + " clusters");
                System.out.println("FAT " + FAT+" sectores " + (FAT/sectores)+" clusters");
                System.out.println("Directorio " + Dir+" sectores "+(Dir/sectores)+" clusters");
                System.out.println("Clusters disponibles: "+total);
                System.out.println("Ocupado " + ocupado +" sectores");
                break;
            case "KB":
                tamanoSF = (tamano * 1024)/sector;
                FAT = tamanoSF / sector;
                secPC = tamanoSF / sectores;
                Dir = (secPC * 32) / sector;
                ocupado=(1+FAT+Dir)/sectores;
                total=secPC-ocupado;
                System.out.println("kb");
                System.out.println(tamanoSF + " sectores");
                System.out.println(secPC + " clusters");
                System.out.println("FAT " + FAT+" sectores " + (FAT/sectores)+" clusters");
                System.out.println("Directorio " + Dir+" sectores "+(Dir/sectores)+" clusters");
                System.out.println("Clusters disponibles: "+total);
                System.out.println("Ocupado " + ocupado +" sectores");
                break;
            case "Bytes":
                tamanoSF = (tamano)/sector;
                FAT = tamanoSF / sector;
                secPC = tamanoSF / sectores;
                Dir = (secPC * 32) / sector;
                ocupado=(1+FAT+Dir)/sectores;
                total=secPC-ocupado;
                System.out.println("bytes");
                System.out.println(tamanoSF + " sectores");
                System.out.println(secPC + " clusters");
                System.out.println("FAT " + FAT+" sectores " + (FAT/sectores)+" clusters");
                System.out.println("Directorio " + Dir+" sectores "+(Dir/sectores)+" clusters");
                System.out.println("Clusters disponibles: "+total);
                System.out.println("Ocupado " + ocupado +" sectores");
                break;
            default:
                break;
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
 
    }

}
