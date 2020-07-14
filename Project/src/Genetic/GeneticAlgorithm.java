package Genetic;

import lib.ICONSTANTS;

import java.awt.*;
import java.util.*;


public class GeneticAlgorithm implements ICONSTANTS {
    private Vector<Integer> fitPopulation;
    private HashMap<Color,Vector<Float>> fitPopulationDistribution;
    private DistributionTable distributionTable;
    private boolean pause;
    private int generationCount;
    private Vector<Integer> currentPopulation;
    private Vector<Integer> recentlyAddFitIndividuals;

    public GeneticAlgorithm(DistributionTable pDistributionTable){
        fitPopulation = new Vector<Integer>();
        distributionTable = pDistributionTable;
        pause = false;
        fitPopulationDistribution = new HashMap<Color,Vector<Float>>();
        generationCount = 0;
        currentPopulation = generatePopulation();
        recentlyAddFitIndividuals = new Vector<Integer>();
    }

    public Vector<Integer> generatePopulation(){
        Vector<Integer> population = new Vector<Integer>();
        Random rand = new Random();
        int individual = 0;
        for(int j=0; j<INITIAL_POPULATION_SIZE; j++){
            individual = rand.nextInt(MAX_CHROMOSOME_VALUE + 1);
            population.add(individual);
        }
        return population;
    }

    public void evaluatePopulation(Vector<Integer> pPopulation){
        for(int individual : pPopulation){
            if(isFitness(individual, distributionTable.distribution)){
                recentlyAddFitIndividuals.add(individual);
                fitPopulation.add(individual);
            }
        }
    }

    public boolean isFitness(int individual, ColorNode colorNode){
        if(colorNode.isMainColor(individual)){
            return true;
        }else return colorNode.getSimilarity(individual) <= APT_PERCENTAGE;
    }


    public void printPopulation(Vector<Integer> pPopulation){
        for(int individual : pPopulation){
            distributionTable.distribution.printColor(individual);
        }
    }

    public Integer crossover(Integer firstParent, Integer secondParent){
        Random rand = new Random();
        int intersectionPoint = rand.nextInt(CHROMOSOMES_BITS);
        int bitMask = MAX_CHROMOSOME_VALUE;
        int child;
        int highBits;
        bitMask >>>= CHROMOSOMES_BITS - intersectionPoint;
        child = firstParent & bitMask;
        highBits = secondParent | bitMask;
        highBits >>>= CHROMOSOMES_BITS - intersectionPoint;
        highBits <<= CHROMOSOMES_BITS - intersectionPoint;
        child |= highBits;
        return child;
    }

    public Integer mutate(Integer individual){
        Random random = new Random();
        if(random.nextDouble() <= MUTATION_PERCENTAGE){
            int bitMask = BIT_MASK;
            int shiftAmount = random.nextInt(CHROMOSOMES_BITS - 1);
            bitMask <<= shiftAmount;
            long bitValue = individual & bitMask;
            bitValue >>= shiftAmount;
            if(bitValue == 1) {
                bitMask = MAX_CHROMOSOME_VALUE;
                bitMask -= Math.pow(2, shiftAmount);
                individual &= bitMask;
            }else{
                bitMask = BIT_MASK;
                bitMask <<= shiftAmount;
                individual |= bitMask;
            }
        }
        return individual;
    }

    public Vector<Integer> newGeneration(){
        Random rand = new Random();
        int childQuantity = currentPopulation.size();
        evaluatePopulation(currentPopulation);
        if(!fitPopulation.isEmpty() && fitPopulation.size() > 1){
            currentPopulation.clear();
            while(childQuantity>0){
                int firstParentIndex = rand.nextInt(fitPopulation.size()-1);
                int secondParentIndex = rand.nextInt(fitPopulation.size()-1);
                Integer firstParent = fitPopulation.get(firstParentIndex);
                Integer secondParent = fitPopulation.get(secondParentIndex);

                Integer child = crossover(firstParent, secondParent);
                currentPopulation.add(mutate(child));

                childQuantity--;
            }
        }
        currentPopulation.clear();
        currentPopulation = generatePopulation();
        return currentPopulation;

    }

    public HashMap<Color,Vector<Float>> PopulationControl(){
        int localGenerationCounter = 0;
        while(localGenerationCounter < GENERATIONS_GROWTH){
              newGeneration();
              generationCount++;
              calculateFitDistribution();
              recentlyAddFitIndividuals.clear();
              System.out.println("Fit color distribution, Generation number: "+generationCount);
              printFITdistribution();
              localGenerationCounter++;
        }
        System.out.println();
        return fitPopulationDistribution;
    }
    public void calculateFitDistribution(){
        for (Integer individual: recentlyAddFitIndividuals){
            Color individualColor = distributionTable.getDistribution().getValueOf(individual);
            if(fitPopulationDistribution.containsKey(individualColor)){
                Float newValue = fitPopulationDistribution.get(individualColor).get(CANT_INDEX)+1;
                fitPopulationDistribution.get(individualColor).set(CANT_INDEX,newValue);
            }else{
                Vector<Float> newVector = new Vector<Float>();
                newVector.add(CANT_INDEX,1f);
                newVector.add(PERCENTAGE_INDEX,0f);
                fitPopulationDistribution.put(individualColor,newVector);
            }
        }
        float totalFIT = fitPopulation.size();
        for (Map.Entry<Color,Vector<Float>> entry : fitPopulationDistribution.entrySet()) {
            float cant = entry.getValue().get(CANT_INDEX);
            entry.getValue().set(PERCENTAGE_INDEX,cant/totalFIT);

        }
    }
    public void printFITdistribution(){
        if(!fitPopulationDistribution.isEmpty()){
            for (Map.Entry<Color,Vector<Float>> entry : fitPopulationDistribution.entrySet()) {
                Color color =  entry.getKey();
                if(entry.getValue() != null){
                    System.out.print("Color: " + color.getRed() + " " + color.getGreen() + " " + color.getBlue() + " ");
                    System.out.println("Distribution: " + entry.getValue().get(PERCENTAGE_INDEX));
                }

            }
        }

    }

    public Vector<Integer> getFitPopulation() {
        return fitPopulation;
    }

    public void setFitPopulation(Vector<Integer> fitPopulation) {
        this.fitPopulation = fitPopulation;
    }

    public DistributionTable getDistributionTable() {
        return distributionTable;
    }

    public void setDistributionTable(DistributionTable distributionTable) {
        this.distributionTable = distributionTable;
    }

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public HashMap<Color, Vector<Float>> getFitPopulationDistribution() {
        return fitPopulationDistribution;
    }

    public void setFitPopulationDistribution(HashMap<Color, Vector<Float>> fitPopulationDistribution) {
        this.fitPopulationDistribution = fitPopulationDistribution;
    }

    public int getGenerationCount() {
        return generationCount;
    }

    public void setGenerationCount(int generationCount) {
        this.generationCount = generationCount;
    }

    public Vector<Integer> getCurrentPopulation() {
        return currentPopulation;
    }

    public void setCurrentPopulation(Vector<Integer> currentPopulation) {
        this.currentPopulation = currentPopulation;
    }

    public Vector<Integer> getRecentlyAddFitIndividuals() {
        return recentlyAddFitIndividuals;
    }

    public void setRecentlyAddFitIndividuals(Vector<Integer> recentlyAddFitIndividuals) {
        this.recentlyAddFitIndividuals = recentlyAddFitIndividuals;
    }
}
