package ImageGenerator;

import Genetic.DistributionTable;
import Genetic.GeneticAlgorithm;
import Greedy.GreedyColorAnalyser;
import ImageManagment.Pixel;

import java.awt.*;
import java.util.Vector;

public class Controller {
    private GreedyColorAnalyser greedyColorAnalyser;
    private GeneticAlgorithm petalGenetic;
    private GeneticAlgorithm centerGenetic;
    private DistributionTable centerDistributionTable;
    private DistributionTable petalDistributionTable;

    public Controller(){
        greedyColorAnalyser = new GreedyColorAnalyser();
        centerDistributionTable = new DistributionTable();
        petalDistributionTable = new DistributionTable();
        getDistributions();
        petalGenetic = new GeneticAlgorithm(petalDistributionTable);
        centerGenetic = new GeneticAlgorithm(centerDistributionTable);
       // petalDistributionTable.printTable();
        // centerDistributionTable.printTable();
        System.out.println("Population control: Petal");
        printMainColor(petalDistributionTable.getDistribution().getMainColor());
        petalGenetic.PopulationControl();
        petalGenetic.PopulationControl();
        petalGenetic.PopulationControl();
        System.out.println("Population control: Center");
        printMainColor(centerDistributionTable.getDistribution().getMainColor());
        centerGenetic.PopulationControl();

    }
    public void getDistributions(){
        for(Vector<Pixel> pixelVector : greedyColorAnalyser.getOptimalPixelsPETAL()){
            petalDistributionTable.insertDistribution(pixelVector);
        }
        for(Vector<Pixel> pixelVector : greedyColorAnalyser.getOptimalPixelsCENTER()){
            centerDistributionTable.insertDistribution(pixelVector);
        }
        centerDistributionTable.setTableValues();
        petalDistributionTable.setTableValues();
    }
    public void printMainColor(Color color){
        System.out.print("Main Color: ");
        System.out.print("Color: " + color.getRed() + " " + color.getGreen() + " " + color.getBlue() + " ");
        System.out.println();
    }

    public GreedyColorAnalyser getGreedyColorAnalyser() {
        return greedyColorAnalyser;
    }

    public void setGreedyColorAnalyser(GreedyColorAnalyser greedyColorAnalyser) {
        this.greedyColorAnalyser = greedyColorAnalyser;
    }

    public GeneticAlgorithm getPetalGenetic() {
        return petalGenetic;
    }

    public void setPetalGenetic(GeneticAlgorithm petalGenetic) {
        this.petalGenetic = petalGenetic;
    }

    public GeneticAlgorithm getCenterGenetic() {
        return centerGenetic;
    }

    public void setCenterGenetic(GeneticAlgorithm centerGenetic) {
        this.centerGenetic = centerGenetic;
    }

    public DistributionTable getCenterDistributionTable() {
        return centerDistributionTable;
    }

    public void setCenterDistributionTable(DistributionTable centerDistributionTable) {
        this.centerDistributionTable = centerDistributionTable;
    }

    public DistributionTable getPetalDistributionTable() {
        return petalDistributionTable;
    }

    public void setPetalDistributionTable(DistributionTable petalDistributionTable) {
        this.petalDistributionTable = petalDistributionTable;
    }
}
