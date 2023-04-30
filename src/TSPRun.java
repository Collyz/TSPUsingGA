import java.io.IOException;

public class TSPRun {
    public static void main(String[] args) throws IOException {
        TSPReader reader = new TSPReader("dj38.tsp");
        reader.readFile();
        TSPPermutation tspPerm = new TSPPermutation(38, 100,reader.getXCords(), reader.getYCords());
        /*int min = Integer.MAX_VALUE;
        for(int i = 0; i < 1000; i++){
            tspPerm.generateNewPopulation();
            int cost = tspPerm.generateBestPopCost();
            min = Math.min(min, cost);
        }
        System.out.println(min);*/

        System.out.println(tspPerm.generateBestPopCost());
        tspPerm.run(0.1, 0.01, 1000);
        System.out.println(tspPerm.generateBestPopCost());
    }
}
