import java.io.IOException;
import java.util.SplittableRandom;

public class TSPRun {
    public static void main(String[] args) throws IOException {
        TSPReader reader = new TSPReader("dj38.tsp");
        reader.readFile();
        TSPPermutation tspPerm = new TSPPermutation(38, 100, .4, .1, .1, reader.getXCords(), reader.getYCords());
        int min = Integer.MAX_VALUE;
        for(int i = 0; i < 100000; i++){
            tspPerm.generateNewPopulation();
            int cost = tspPerm.generateCosts();
            min = Math.min(min, cost);
        }
        System.out.println(min);
    }
}
