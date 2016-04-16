/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaficheros;

/**
 *
 * @author AcerV3
 */
public class sectorArranque {

    double sectores;
    double tamanoSF;
    double FAT;
    double secPC;
    double Dir;
    double total;
    double ocupado;
    int sector;

    public sectorArranque(double tamanoSF, double secPC, double FAT, double Dir, double total, double ocupado, double sectores, int sector) {
        this.sectores = sectores;
        this.tamanoSF = tamanoSF;
        this.FAT = FAT;
        this.secPC = secPC;
        this.Dir = Dir;
        this.total = Math.ceil(total);
        this.sector=sector;
        this.ocupado=Math.ceil(ocupado);
        imprime();
    }
    
    public sectorArranque(){
        
    }

    public double getBytesPSector() {
        System.out.println("Bytes por sector " + sector);
        return sector;
    }
    
    public double getSectoresPCluster(){
        System.out.println("Sectores por cluster "+sectores);
        return sectores;
    }

    public double getTotalSectores() {
        System.out.println(tamanoSF + " sectores");
        return tamanoSF;
    }

    public double getTotalClusters() {
        System.out.println(secPC + " clusters");
        return secPC;
    }

    public double getSectoresFAT() {
        System.out.println("FAT " + FAT + " sectores ");
        return FAT;
    }

    public double getClustersFAT() {
        System.out.println("FAT " + (FAT / sectores) + " clusters");
        return FAT / sectores;
    }

    public double getSectoresDirectorio() {
        System.out.println("Directorio " + Dir + " sectores ");
        return Dir;
    }

    public double getClustersDirectorio() {
        System.out.println("Directorio " + (Dir / sectores) + " clusters");
        return Dir / sectores;
    }

    public double getClustersDispo() {
        System.out.println("Clusters disponibles: " + total);
        return total;
    }

    public double getClustersOcupados() {
        System.out.println("Ocupado " + ocupado + " clusters");
        return ocupado;
    }
    
    public void imprime(){
        System.out.println("---------------------------");
        getBytesPSector();
        getSectoresPCluster();
        getTotalSectores();
        getTotalClusters();
        getSectoresFAT();
        getClustersFAT();
        getSectoresDirectorio();
        getClustersDirectorio();
        getClustersDispo();
        getClustersOcupados();
        System.out.println("---------------------------");
    }
}
