package Genetic;

import lib.ICONSTANTS;

import javax.swing.plaf.IconUIResource;
import java.awt.*;
import java.util.*;

import java.util.concurrent.ConcurrentHashMap;

public class GeneticAlgorithm implements ICONSTANTS {
    private Vector<Integer> fitPopulation;
    private HashMap<Color,Float> fitPopulationDistribution;
    private DistributionTable distributionTable;
    private boolean pause;
    public GeneticAlgorithm(DistributionTable pDistributionTable){
        fitPopulation = new Vector<Integer>();
        distributionTable = pDistributionTable;
        pause = false;
        fitPopulationDistribution = new HashMap<Color,Float>();

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

    public Vector<Integer> newGeneration(Vector<Integer> pPopulation){
        Random rand = new Random();
        int childQuantity = pPopulation.size();
        evaluatePopulation(pPopulation);
        if(!fitPopulation.isEmpty()){
            pPopulation.clear();
            while(childQuantity>0){
                int firstParentIndex = rand.nextInt(fitPopulation.size()-1);
                int secondParentIndex = rand.nextInt(fitPopulation.size()-1);
                Integer firstParent = fitPopulation.get(firstParentIndex);
                Integer secondParent = fitPopulation.get(secondParentIndex);

                Integer child = crossover(firstParent, secondParent);
                pPopulation.add(mutate(child));

                childQuantity--;
            }
        }
        pPopulation.clear();
        pPopulation = generatePopulation();
        return pPopulation;

    }

    public Vector<Integer> PopulationControl(){
        int generationCounter = 0;
        Vector<Integer> currentPopulation = generatePopulation();
        while(/*!pause ||*/ this.fitPopulation.size() < MAX_POPULATION_SIZE){
            currentPopulation = newGeneration(currentPopulation);
            generationCounter++;
            calculateFitDistribution();
            System.out.println("Fit color distribution, Generation number: "+generationCounter);
            printFITdistribution();
        }
        System.out.println("Cantidad de generaciones : " + generationCounter);
        return this.fitPopulation;
    }
    public void calculateFitDistribution(){
        for (Integer individual: fitPopulation){
            Color individualColor = distributionTable.getDistribution().getValueOf(individual);
            if(fitPopulationDistribution.containsKey(individualColor)){
                fitPopulationDistribution.put(individualColor,fitPopulationDistribution.get(individualColor)+1);
            }else{
                fitPopulationDistribution.put(individualColor,1f);
            }
        }
        float totalFIT = fitPopulation.size();
        for (Map.Entry<Color,Float> entry : fitPopulationDistribution.entrySet()) {
           entry.setValue((float)entry.getValue()/totalFIT);
        }
    }
    public void printFITdistribution(){
        for (Map.Entry<Color,Float> entry : fitPopulationDistribution.entrySet()) {
            Color color =  entry.getKey();
            System.out.print("Color: " + color.getRed() + " " + color.getGreen() + " " + color.getBlue() + " ");
            System.out.println("Distribution: " + entry.getValue());
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


}
