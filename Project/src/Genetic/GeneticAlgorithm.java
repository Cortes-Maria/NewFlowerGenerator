package Genetic;

import com.sun.xml.internal.ws.util.StringUtils;
import lib.ICONSTANTS;

import javax.swing.plaf.IconUIResource;
import java.awt.*;
import java.util.HashMap;
import java.util.Random;
import java.util.Vector;

public class GeneticAlgorithm implements ICONSTANTS {
    public Vector<Integer> centerPopulation;
    public Vector<Integer> petalPopulation;
    public Vector<Integer> aptPetal;
    public Vector<Integer> aptCenter;

    public GeneticAlgorithm(){
        aptPetal = new Vector<>();
        aptCenter = new Vector<>();
        petalPopulation = new Vector<>();
        centerPopulation = new Vector<>();
    }

    public void generatePopulation(){
        Random rand = new Random();
        int individual = 0;
        for(int j=0; j<POPULATION_SIZE; j++){
            individual = rand.nextInt(MAX_CHROMOSOME_VALUE + 1);
            petalPopulation.add(individual);
        }
        rand = new Random();
        for(int j=0; j<POPULATION_SIZE; j++){
            individual = rand.nextInt(MAX_CHROMOSOME_VALUE + 1);
            centerPopulation.add(individual);
        }
    }

    public void evaluatePopulation(DistributionTable distributionTable){
        for(int value : centerPopulation){
            if(isFitness(value, distributionTable.distributionCenter)){
                aptCenter.add(value);
            }
        }
        for(int value : petalPopulation){
            if(isFitness(value, distributionTable.distributionPetal)){
                aptPetal.add(value);
            }
        }
    }

    public boolean isFitness(int fitnessValue, ColorNode colorNode){
        if(colorNode.isMainColor(fitnessValue)){
            return true;
        }else if(colorNode.getSimilarity(fitnessValue) <= APT_PERCENTAGE){
            return true;
        }
        return false;
    }


    public void printPopulation(DistributionTable distributionTable){
        for(int value : petalPopulation){
            distributionTable.distributionPetal.printColor(value);
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
}
