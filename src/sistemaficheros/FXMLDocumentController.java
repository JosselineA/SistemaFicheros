/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaficheros;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author AcerV3
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    ChoiceBox tipoCB, tipoAr, tipoTam, ordenar;
    @FXML
    TextField tam;
    @FXML
    TextField sect, bytesSec, nameAr, nameDel, tamAr;
    @FXML
    Button acept;
    @FXML AnchorPane directorio, informacion;
    
    sectorArranque sA;
    @FXML
    public void setInfo() {
        double tamano = Double.parseDouble(tam.getText());
        int sectores = Integer.parseInt(sect.getText());
        int sector = Integer.parseInt(bytesSec.getText());
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
                sA= new sectorArranque(tamanoSF, secPC, FAT, Dir, Math.ceil(total), Math.ceil(ocupado), sectores,sector);
                break;
            case "MB":
                tamanoSF = (tamano * 1048576)/sector;
                FAT = tamanoSF / sector;
                secPC = tamanoSF / sectores;
                Dir = (secPC * 32) / sector;
                ocupado=(1+FAT+Dir)/sectores;
                total=secPC-ocupado;
                System.out.println("mb");
                sA= new sectorArranque(tamanoSF, secPC, FAT, Dir, total, ocupado, sectores,sector);
                break;
            case "KB":
                tamanoSF = (tamano * 1024)/sector;
                FAT = tamanoSF / sector;
                secPC = tamanoSF / sectores;
                Dir = (secPC * 32) / sector;
                ocupado=(1+FAT+Dir)/sectores;
                total=secPC-ocupado;
                System.out.println("kb");
                sA= new sectorArranque(tamanoSF, secPC, FAT, Dir, total, ocupado, sectores,sector);
                break;
            case "Bytes":
                tamanoSF = (tamano)/sector;
                FAT = tamanoSF / sector;
                secPC = tamanoSF / sectores;
                Dir = (secPC * 32) / sector;
                ocupado=(1+FAT+Dir)/sectores;
                total=secPC-ocupado;
                System.out.println("bytes");
                sA= new sectorArranque(tamanoSF, secPC, FAT, Dir, total, ocupado, sectores,sector);
                break;
            default:
                break;
        }
        informacion.setVisible(false);
        directorio.setVisible(true);
    }
    
    @FXML public void regresar(){
        informacion.setVisible(true);
        directorio.setVisible(false);
    }
    
    @FXML public void getDatosArch(){
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
 
    }
}