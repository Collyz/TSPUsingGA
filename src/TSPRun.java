import java.util.SplittableRandom;

public class TSPRun {
    public static void main(String[] args){

        TSPBitStringPopulation tspBitStringPopulation = new TSPBitStringPopulation(10, 50);
        System.out.println(tspBitStringPopulation.printBitString());
        SplittableRandom sr = new SplittableRandom();
        int count = 0;
        while(count != 100){
            System.out.println(sr.nextInt(2));
            count++;
        }



    }
}
