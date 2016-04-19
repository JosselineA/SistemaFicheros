package sistemaficheros;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Josseline, Hugo, Javier
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    ChoiceBox tipoCB, tipoAr, tipoTam, ordenar;
    @FXML
    TextField tam, BPS, SPC, ST, CT, FS, FC, CD, CO, SD, SO;
    @FXML
    TextField sect, bytesSec, nameAr, nameDel, tamAr, DirS, DirC;
    @FXML
    Button acept;
    @FXML
    AnchorPane directorio, informacion, InfoIm;

    @FXML
    TableView<directorio> tablaDir;
    @FXML
    TableColumn<directorio, String> tablaNombre, tablaFecha, tablaTipo, tablaTamano;

    @FXML
    TableView<FATdir> FatDir;
    @FXML
    TableColumn<FATdir, String> numC, Cluster, NomC;
    double tamCluster;
    double ocupaC = 0;
    double ocupaS = 0;
    double SecDis = 0;
   String[] tamFileD = new String[2];
    sectorArranque sA;
    double ClustersOc = 0;
    double ClustersDisp = 0;
    ObservableList<directorio> tablaDirectorio = FXCollections.observableArrayList();
    ObservableList<FATdir> tablaFat = FXCollections.observableArrayList();

    @FXML
    public void NewFile() {
        Date date = new Date();
        DateFormat hourdateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        System.out.println("Hora y fecha: " + hourdateFormat.format(date));
        if (validarNombre() && validaTam()) {
            directorio Dir = new directorio();
            Dir.setNombre(nameAr.getText());
            Dir.setFecha(hourdateFormat.format(date));
            Dir.setTamano(tamAr.getText() + " " + tipoTam.getValue());
            Dir.setTipo(tipoAr.getValue() + "");
            tablaDirectorio.add(Dir);
            tablaDir.setItems(tablaDirectorio);
            tamFile();
            CO.setText(ClustersOc + "");
            CD.setText(ClustersDisp + "");
        }
    }

  
    public boolean validaTam() {
        String tipo = (String) tipoTam.getValue();
        double tamanoFile = 0;
        double oc = 0;
        double tamano = Double.parseDouble(tamAr.getText());
        
        double cantClusters = 0;
        switch (tipo) {
            case "GB":
                tamanoFile = (tamano * 1073741824);
                break;
            case "MB":
                tamanoFile = (tamano * 1048576);

                break;
            case "KB":
                tamanoFile = (tamano * 1024);

                break;
            case "Bytes":
                tamanoFile = (tamano);

                break;
            default:
                break;
        }
        double t2 = ClustersDisp - tamano;
        if ((tamanoFile/tamCluster) > t2) {
            Alert al = new Alert(AlertType.ERROR);
            al.setContentText("No hay suficiente espacio");
            al.showAndWait();
            return false;
        }

        return true;
    }

    public void tamFile() {
        String tipo = (String) tipoTam.getValue();
        double tamanoFile = 0;
        double oc = 0;
        double cantSec = 0;
        double tamano = Double.parseDouble(tamAr.getText());
        double cantClusters = 0;
        switch (tipo) {
            case "GB":
                tamanoFile = (tamano * 1073741824);
                break;
            case "MB":
                tamanoFile = (tamano * 1048576);

                break;
            case "KB":
                tamanoFile = (tamano * 1024);

                break;
            case "Bytes":
                tamanoFile = (tamano);

                break;
            default:
                break;
        }

        cantClusters = Math.ceil(tamanoFile) / Math.ceil(tamCluster);

        ClustersOc = Math.ceil(ClustersOc) + Math.ceil(ocupaC) + Math.ceil(cantClusters);
        cantSec = cantClusters * Double.parseDouble(bytesSec.getText());
        ocupaS = ClustersOc * Double.parseDouble(tam.getText());
        ClustersDisp = Math.ceil(ClustersDisp) - Math.ceil(cantClusters);
        System.out.println("Cantidad de Clusters " + Math.ceil(cantClusters));
        guardarFat((int) Math.ceil(cantClusters), nameAr.getText());
    }

    //true si es exitoso

    public boolean guardarFat(int cantidad, String file) {

        for (int i = 1; i < tablaFat.size(); i++) {
            if (cantidad > 0) {
                FATdir objeto = tablaFat.get(i);
                if (objeto.getCluster().equals("--")) {
                    cantidad--;
                    if (cantidad == 0) {
                        objeto.setCluster("-1");
                        objeto.setNFile(file);
                        tablaFat.remove(i);
                        tablaFat.add(i, objeto);

                    } else {
                        int aux = getNextPosicion(i);
                        if (aux == -1) {
                            //Ya no hay espacios
                            return false;
                        }
                        objeto.setCluster(aux + "");
                        objeto.setNFile(file);
                        tablaFat.remove(i);
                        tablaFat.add(i, objeto);
                    }
                    
                    FatDir.setItems(tablaFat);
                    FatDir.getColumns().get(1).setVisible(false);
                    FatDir.getColumns().get(1).setVisible(true);
                    FatDir.getColumns().get(2).setVisible(false);
                    FatDir.getColumns().get(2).setVisible(true);
 
                }
            } else {
                return true;
            }
        }
        return false;
    }

    public int getNextPosicion(int pos) {
        pos++;
        for (int i = pos; i < tablaFat.size(); i++) {
            FATdir objeto = tablaFat.get(i);
            if (objeto.getCluster().equals("--")) {
                return i;
            }
        }
        return -1;
    }

    public boolean validarNombre() {
        String nombre = nameAr.getText();
        for (int i = 0; i < tablaDirectorio.size(); i++) {
            directorio aux = tablaDirectorio.get(i);
            if (aux.getNombre().equals(nombre)) {
                Alert al = new Alert(AlertType.ERROR);
                al.setContentText("El nombre ya existe, ingrese otro");
                al.showAndWait();
                return false;
            }
        }
        return true;
    }

    @FXML
    public void DeleteFile() {
        String nombre = nameDel.getText();
        for (int i = 0; i < tablaDirectorio.size(); i++) {
            directorio aux = tablaDirectorio.get(i);
            if (aux.getNombre().equals(nombre)) {
                tamFileD =aux.getTamano().split(" ");
                        System.out.println(tamFileD[0]);
                        System.out.println(tamFileD[1]);
                        tamFileDel(tamFileD[1], tamFileD[0]);
                tablaDirectorio.remove(i);
                tablaDir.setItems(tablaDirectorio);
                delete(nombre);
                CO.setText(ClustersOc + "");
            CD.setText(ClustersDisp + "");
                return;
            }
        }

    }
    
    public void tamFileDel(String tipo, String tam) {
        double tamanoFile = 0;
        double oc = 0;
        double cantSec = 0;
        double tamano = Double.parseDouble(tam);
        double cantClusters = 0;
        switch (tipo) {
            case "GB":
                tamanoFile = (tamano * 1073741824);
                break;
            case "MB":
                tamanoFile = (tamano * 1048576);

                break;
            case "KB":
                tamanoFile = (tamano * 1024);

                break;
            case "Bytes":
                tamanoFile = (tamano);

                break;
            default:
                break;
        }

        cantClusters = tamanoFile / tamCluster;

        ClustersOc = Math.ceil(ClustersOc) - Math.ceil(ocupaC) - Math.ceil(cantClusters);
        ClustersDisp = Math.ceil(ClustersDisp) + Math.ceil(cantClusters);
        System.out.println("Cantidad de Clusters " + Math.ceil(cantClusters));
        guardarFat((int) Math.ceil(cantClusters), nameAr.getText());
    }

    public void delete(String file) {
        for (int i = 0; i < tablaFat.size(); i++) {
            FATdir objeto = tablaFat.get(i);
            if (objeto.getNFile().equals(file)) {
                tablaFat.remove(i);
                objeto.setCluster("--");
                objeto.setNFile("--");
                tablaFat.add(i, objeto);
                FatDir.setItems(tablaFat);
                  FatDir.getColumns().get(1).setVisible(false);
                    FatDir.getColumns().get(1).setVisible(true);
                    FatDir.getColumns().get(2).setVisible(false);
                    FatDir.getColumns().get(2).setVisible(true);
            }

        }

    }

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
        double total = 0;
        double ocupado;
        double t;

        switch (tipo) {
            case "GB":

                tamanoSF = (tamano * 1073741824) / 128;
                FAT = Math.ceil(tamanoSF) / Math.ceil(sector);
                secPC = (Math.ceil(tamanoSF) / Math.ceil(sectores));
                Dir = (secPC * 32) / sector;
                ocupado = (sectores +Math.ceil(FAT) + Math.ceil(Dir) )/ Math.ceil(sectores);
                total = Math.ceil(secPC) - Math.ceil(ocupado);
                t = (sectores + Math.ceil(FAT) + Math.ceil(Dir));
                this.tamCluster = Math.ceil(sector) * Math.ceil(sectores);
                System.out.println("gb");
                sA = new sectorArranque(Math.ceil(tamanoSF), Math.ceil(secPC), Math.ceil(FAT), Math.ceil(Dir), Math.ceil(total), Math.ceil(ocupado), Math.ceil(sectores), sector);
                setInfoV(Math.ceil(tamanoSF), Math.ceil(secPC), Math.ceil(FAT), Math.ceil(Dir), Math.ceil(total), Math.ceil(ocupado), Math.ceil(sectores), sector);
                System.out.println("tamaño por cluster " + tamCluster);
                crearFatTabla((int) Math.ceil(total));
                ClustersOc = ocupado;
                ClustersDisp = total;
                ocupaS = tamanoSF - t;
                SecDis = t;
                break;
            case "MB":
                tamanoSF = (tamano * 1048576) / sector;
                FAT = tamanoSF / sector;
                secPC = (tamanoSF / sectores);
                Dir = (secPC * 32) / sector;
                ocupado = (sectores + FAT + Dir) / sectores;
                ClustersOc = ocupado;
                total = secPC - ocupado;
                t = (sectores + FAT + Dir);
                System.out.println("mb");
               sA = new sectorArranque(Math.ceil(tamanoSF), Math.ceil(secPC), Math.ceil(FAT), Math.ceil(Dir), Math.ceil(total), Math.ceil(ocupado), Math.ceil(sectores), sector);
                setInfoV(Math.ceil(tamanoSF), Math.ceil(secPC), Math.ceil(FAT), Math.ceil(Dir), Math.ceil(total), Math.ceil(ocupado), Math.ceil(sectores), sector);
                this.tamCluster = sector * sectores;
                ClustersDisp = total;
                ocupaS = tamanoSF - t;
                SecDis = t;
                System.out.println("tamaño por cluster " + tamCluster);
                crearFatTabla((int) Math.ceil(total));
                break;
            case "KB":
                tamanoSF = (tamano * 1024) / sector;
                FAT = tamanoSF / sector;
                secPC = (tamanoSF / sectores);
                Dir = (secPC * 32) / sector;
                ocupado = (sectores + FAT + Dir) / sectores;
                total = secPC - ocupado;
                ClustersOc = ocupado;
                t = (sectores + FAT + Dir);
                System.out.println("kb");
                sA = new sectorArranque(Math.ceil(tamanoSF), Math.ceil(secPC), Math.ceil(FAT), Math.ceil(Dir), Math.ceil(total), Math.ceil(ocupado), Math.ceil(sectores), sector);
                setInfoV(Math.ceil(tamanoSF), Math.ceil(secPC), Math.ceil(FAT), Math.ceil(Dir), Math.ceil(total), Math.ceil(ocupado), Math.ceil(sectores), sector);
                this.tamCluster = sector * sectores;
                ClustersDisp = total;
                ocupaS = tamanoSF - t;
                SecDis = t;
                System.out.println("tamaño por cluster " + tamCluster);
                crearFatTabla((int) Math.ceil(total));
                break;
            case "Bytes":
                tamanoSF = (tamano) / sector;
                FAT = tamanoSF / sector;
                secPC = (tamanoSF / sectores);
                Dir = (secPC * 4) / sector;
                ocupado = (sectores + FAT + Dir) / sectores;
                total = secPC - ocupado;
                ClustersOc = ocupado;
                t = (sectores + FAT + Dir);
                System.out.println("bytes");
               sA = new sectorArranque(Math.ceil(tamanoSF), Math.ceil(secPC), Math.ceil(FAT), Math.ceil(Dir), Math.ceil(total), Math.ceil(ocupado), Math.ceil(sectores), sector);
                setInfoV(Math.ceil(tamanoSF), Math.ceil(secPC), Math.ceil(FAT), Math.ceil(Dir), Math.ceil(total), Math.ceil(ocupado), Math.ceil(sectores), sector);
                this.tamCluster = sector * sectores;
                System.out.println("tamaño por cluster " + tamCluster);
                ClustersDisp = total;
                ocupaS = tamanoSF - t;
                SecDis = t;
                crearFatTabla((int) Math.ceil(total));
                break;
            default:
                break;
        }

        informacion.setVisible(false);
        InfoIm.setVisible(true);
        directorio.setVisible(true);
    }

    public void setInfoV(double tamanoSF, double secPC, double FAT, double Dir, double total, double ocupado, double sectores, int sector) {
        BPS.setText(sector + "");
        double t = (1 + FAT + Dir);
        SPC.setText(sectores + "");
        ST.setText(tamanoSF + "");
        CT.setText(secPC + "");
        FS.setText(FAT + "");
        FC.setText(Math.ceil((FAT / sectores)) + "");
        CD.setText(total + "");
        CO.setText(ocupado + "");
        DirS.setText(Dir + "");
        DirC.setText(Math.ceil((Dir / sectores)) + "");
    }

    public void crearFatTabla(int tamano) {
        tablaFat.clear();
        for (int i = 0; i < tamano; i++) {
            FATdir objeto = new FATdir();
            if (i == 0) {
                objeto.setNumero(i + "");
                objeto.setCluster("Sector de Arranque");
                objeto.setNFile("Sector de Arranque");
            } else {
                objeto.setNumero(i + "");
                objeto.setCluster("--");
                objeto.setNFile("--");
            }
            tablaFat.add(objeto);
        }
        FatDir.setItems(tablaFat);
    }

    @FXML
    public void regresar() {
        informacion.setVisible(true);
        InfoIm.setVisible(false);
        directorio.setVisible(false);
    }

    @FXML
    public void getDatosArch() {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tablaNombre.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        tablaFecha.setCellValueFactory(cellData -> cellData.getValue().fechaProperty());
        tablaTipo.setCellValueFactory(cellData -> cellData.getValue().tipoProperty());
        tablaTamano.setCellValueFactory(cellData -> cellData.getValue().tamanoProperty());
        numC.setCellValueFactory(cellData -> cellData.getValue().NumeroProperty());
        NomC.setCellValueFactory(cellData -> cellData.getValue().NFileProperty());
        Cluster.setCellValueFactory(cellData -> cellData.getValue().ClusterProperty());

        //Checa que se seleciono en el combo box
        tipoAr.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                if (tipoAr.getValue() != null) {
                    if (tipoAr.getValue().equals("Texto")) {
                        tamAr.setDisable(false);
                    } else {
                        tamAr.setDisable(true);
                        tamAr.setText("0");
                        tipoTam.setValue("Bytes");
                    }
                }
            }
        });
    }
}
