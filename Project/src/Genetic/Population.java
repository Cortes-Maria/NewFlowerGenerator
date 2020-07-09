package Genetic;

import com.sun.org.apache.bcel.internal.generic.FLOAD;

import java.awt.*;
import java.util.HashMap;
import java.util.Vector;

public class Population {
    HashMap<Integer, Float> population;

    public Population(){
        population = new HashMap<>();
    }

    public void insert(Integer number, Float similarity){
        population.put(number, similarity);
    }

    public HashMap<Integer, Float> getPopulation() {
        return population;
    }

    public void setPopulation(HashMap<Integer, Float> population) {
        this.population = population;
    }

    public void printPopulation(){
        for(Integer key: population.keySet()){
            System.out.println(key + "  " + population.get(key));
        }
        System.out.println();
    }
}
