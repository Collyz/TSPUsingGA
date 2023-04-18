import java.util.SplittableRandom;

public class TSPBitStringPopulation {
    private boolean[][] population;
    private SplittableRandom sRand;


    public TSPBitStringPopulation(int size, int populationSize){
        this.population = new boolean[populationSize][size];
        sRand = new SplittableRandom();
        for(boolean[] individual : population){
            individual = new boolean[size];
        }
    }

    private void assignRandomValues(){
        for(boolean[] individual : population){
            for(boolean value : individual){
                value = sRand.nextInt(2) == 1;
            }
        }
   }

   public String printBitString(){
        return null;
   }

    private void set(int individual, int index, boolean value){
        population[individual][index] = value;
    }

    private void crossOver(double rate){

    }

    private void mutation(){

    }

    private void rouletteSelection(){
        
    }
}
