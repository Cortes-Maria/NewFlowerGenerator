package ImageGenerator;

import Genetic.DistributionTable;
import Genetic.GeneticAlgorithm;
import Greedy.GreedyColorAnalyser;
import ImageManagment.Pixel;

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
        petalDistributionTable.printTable();
        petalGenetic.PopulationControl();

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
}
