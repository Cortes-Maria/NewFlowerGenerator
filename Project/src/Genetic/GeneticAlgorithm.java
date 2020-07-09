package Genetic;

import com.sun.xml.internal.ws.util.StringUtils;
import lib.ICONSTANTS;

import javax.swing.plaf.IconUIResource;
import java.awt.*;
import java.util.HashMap;
import java.util.Random;
import java.util.Vector;

public class GeneticAlgorithm implements ICONSTANTS {
    public Vector<Population> centerPopulation;
    public Vector<Population> petalPopulation;
    public Vector<Vector<Integer>> aptPetal;
    public Vector<Vector<Integer>> aptCenter;

    public GeneticAlgorithm(){
        aptPetal = new Vector<>();
        aptCenter = new Vector<>();
        petalPopulation = new Vector<>();
        centerPopulation = new Vector<>();
    }

    public void generatePopulation(DistributionTable distributionTable){
        Random rand = new Random();
        for(int i=0; i<distributionTable.distributionPetal.size(); i++){
            petalPopulation.add(new Population());
            aptPetal.add(new Vector<>());
            for(int j=0; j<POPULATION_SIZE; j++){
                int individual = rand.nextInt(MAX_CHROMOSOME_VALUE + 1);
                Color chromosomeColor = distributionTable.distributionPetal.elementAt(i).getValueOf(individual);
                if(chromosomeColor!=null){
                    Color mainColor = distributionTable.distributionPetal.elementAt(i).mainColor();
                    if(isMainColor(mainColor,chromosomeColor)){
                        //aptPetal.add(chromosomeColor);
                        aptPetal.elementAt(i).add(individual);
                        continue;
                    }
                    float similarity = distributionTable.similarity(mainColor, chromosomeColor);
                    petalPopulation.elementAt(i).insert(individual,similarity);
                }else{
                    System.out.println("Something is wrong PETAL");
                }
            }
        }
        rand = new Random();
        for(int i=0; i<distributionTable.distributionCenter.size(); i++){
            centerPopulation.add(new Population());
            for(int j=0; j<POPULATION_SIZE; j++){
                int individual = rand.nextInt(MAX_CHROMOSOME_VALUE + 1);
                //System.out.println(individual);
                Color chromosomeColor = distributionTable.distributionCenter.get(i).getValueOf(individual);
                if(chromosomeColor!=null){
                    Color mainColor = distributionTable.distributionCenter.get(i).mainColor();
                    if(isMainColor(mainColor,chromosomeColor)){
                        //aptCenter.add(chromosomeColor);
                        aptCenter.elementAt(i).add(individual);
                        continue;
                    }
                    float similarity = distributionTable.similarity(mainColor, chromosomeColor);
                    centerPopulation.get(i).insert(individual,similarity);
                }else{
                    System.out.println("Something is wrong CENTER");
                }
            }
        }
    }

    public void evaluatePopulation(DistributionTable distributionTable){
        for(int i=0; i<petalPopulation.size(); i++){
            for(Integer value : petalPopulation.elementAt(i).population.keySet()){
                if(isFitness(value, distributionTable.distributionPetal.elementAt(i))){
                    System.out.println("Added P");
                    //aptPetal.add(distributionTable.distributionPetal.elementAt(i).getValueOf(value));
                    aptPetal.elementAt(i).add(value);
                }
            }
        }
        for(int i=0; i<centerPopulation.size(); i++){
            for(Integer value : centerPopulation.elementAt(i).population.keySet()){
                if(isFitness(value, distributionTable.distributionCenter.elementAt(i))){
                    System.out.println("Added C");
                    //aptCenter.add(distributionTable.distributionCenter.elementAt(i).getValueOf(value));
                    aptCenter.elementAt(i).add(value);
                }
            }
        }
    }

    public boolean isFitness(int fitnessValue, ColorNode colorNode){
        if(fitnessValue <= APT_PERCENTAGE || colorNode.isMainColor(fitnessValue)){
            return true;
        }
        return false;
    }

    public boolean isMainColor(Color color1, Color color2){
        if(color1!=null && color2!=null){
            if(color1.equals(color2)){
                return true;
            }
        }
        return false;
    }

    public void printPopulation(){
        /*petalPopulation.printPopulation();
        centerPopulation.printPopulation();*/
    }

    public Integer crossover(Integer firstParent, Integer secondParent){
        String binaryFirstParent = String.format("%16s",Integer.toBinaryString(firstParent)).replace(" ", "0");
        String binarySecondParent = String.format("%16s",Integer.toBinaryString(secondParent)).replace(" ", "0");

        System.out.println(binaryFirstParent + " " + binarySecondParent);
        Random random = new Random();

        int intersectionPoint = random.nextInt(CHROMOSOMES_BITS);
        int bitMask = MAX_CHROMOSOME_VALUE;

        String binaryChild = "";
        binaryChild += binaryFirstParent.substring(0, intersectionPoint);
        binaryChild += binarySecondParent.substring(intersectionPoint);

        System.out.println("IP: " + intersectionPoint);
        System.out.println("BC: " + binaryChild);

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
