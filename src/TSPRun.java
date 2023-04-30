import java.io.IOException;
import java.util.Arrays;

public class TSPRun {
    public static void main(String[] args) throws IOException {
        TSPReader reader = new TSPReader("dj38.tsp");
        reader.readFile();

        TSPUsingVBSS vbss = new TSPUsingVBSS(reader.getNumOfCities(), reader.getXCords(), reader.getYCords(), 1);
        vbss.run(1);

//        TSPPermutation tspPermutation = new TSPPermutation(reader.getNumOfCities(),100, reader.getXCords(), reader.getYCords());
//        tspPermutation.run(0.3, 0.16, 10000);
    }
}
