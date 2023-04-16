import java.util.SplittableRandom;

public class TSPBitStringPopulation {
    private boolean[] bitString;
    private boolean[] population;
    private SplittableRandom sRand = new SplittableRandom();


    public TSPBitStringPopulation(int size, int populationSize){
        bitString = new boolean[size];

    }

    public String printBitString(){
        StringBuilder sb = new StringBuilder();
        for(boolean bit : this.bitString){
            sb.append(bit ? "1":"0");
        }

        return sb.toString();
    }

    private void set(int index, boolean value){

    }

}
