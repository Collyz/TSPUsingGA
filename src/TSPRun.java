import java.io.IOException;

public class TSPRun {
    public static void main(String[] args) throws IOException {
        TSPReader reader = new TSPReader("dj38.tsp");
        reader.readFile();
        TSPUsingVBSS tspVBSS = new TSPUsingVBSS(reader.getNumOfCities(), reader.getXCords(), reader.getYCords(), 1);
        tspVBSS.generateNewTour();
        tspVBSS.printTour();
        tspVBSS.distanceBasedHeuristic();
    }
}
