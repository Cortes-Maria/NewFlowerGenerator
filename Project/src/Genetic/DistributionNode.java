package Genetic;

public class DistributionNode {
    int frequency;
    float percentage;
    int min, max;

    DistributionNode(){
        frequency = 1;
        percentage = 0;
        min = 0;
        max = 0;
    }

    public void addToFrequency(){
        this.frequency++;
    }

    public String printNode(){
        return "Frequency: " + frequency + " Percentage: " + percentage + " Min: " +
                min + " Max: " + max;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
