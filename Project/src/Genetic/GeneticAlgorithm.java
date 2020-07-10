package Genetic;

import lib.ICONSTANTS;

import javax.swing.plaf.IconUIResource;
import java.awt.*;
import java.util.HashMap;
import java.util.Random;

import java.util.Vector;

public class GeneticAlgorithm implements ICONSTANTS {
    private Vector<Integer> fitPopulation;
    private DistributionTable distributionTable;
    private boolean pause;
    public GeneticAlgorithm(DistributionTable pDistributionTable){
        fitPopulation = new Vector<Integer>();
        distributionTable = pDistributionTable;
        pause = false;

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
        String binaryFirstParent = String.format("%16s",Integer.toBinaryString(firstParent)).replace(" ", "0");
        String binarySecondParent = String.format("%16s",Integer.toBinaryString(secondParent)).replace(" ", "0");
        Random random = new Random();
        int intersectionPoint = random.nextInt(CHROMOSOMES_BITS);
        String binaryChild = "";
        binaryChild += binaryFirstParent.substring(0, intersectionPoint);
        binaryChild += binarySecondParent.substring(intersectionPoint);
        return Integer.parseInt(binaryChild,2);
    }

    public Integer mutate(Integer individual){
        Random random = new Random();
        if(random.nextDouble()<= MUTATION_PERCENTAGE){
            String binaryIndividual = String.format("%16s",Integer.toBinaryString(individual)).replace(" ", "0");
            int shiftPosition = random.nextInt(CHROMOSOMES_BITS);
            String mutatedIndividual = binaryIndividual.substring(0,shiftPosition);
            if(binaryIndividual.charAt(shiftPosition) == '0'){
                mutatedIndividual += "1";
            }else{
                mutatedIndividual += "0";
            }
            mutatedIndividual += binaryIndividual.substring(shiftPosition+1);
            individual = Integer.parseInt(mutatedIndividual,2);
        }
        return individual;
    }

    public Vector<Integer> newGeneration(Vector<Integer> pPopulation){
        Random rand = new Random();
        int childQuantity = pPopulation.size();
        evaluatePopulation(pPopulation);
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

        return pPopulation;

    }

    public Vector<Integer> PopulationControl(){
        int generationCounter = 0;
        Vector<Integer> currentPopulation = generatePopulation();
        while(/*!pause ||*/ this.fitPopulation.size() < MAX_POPULATION_SIZE){
            System.out.println(this.fitPopulation.size());
            currentPopulation = newGeneration(currentPopulation);
            generationCounter++;
        }
        System.out.println("Cantidad de generaciones : " + generationCounter);
        return this.fitPopulation;
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
