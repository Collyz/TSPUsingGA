import java.io.IOException;
import java.util.Arrays;

public class TSPRun {
    public static void main(String[] args) throws IOException {
        TSPReader reader = new TSPReader("dj38.tsp");
        reader.readFile();
        VBSS vbss = new VBSS(reader.getNumOfCities(), 1, reader.getXCords(), reader.getYCords());
        vbss.run(10);
//        TSPPermutation tspPermutation = new TSPPermutation(reader.getNumOfCities(),100, reader.getXCords(), reader.getYCords());
//        tspPermutation.run(0.3, 0.16, 10000);
    }
}
