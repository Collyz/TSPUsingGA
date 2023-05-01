import java.io.IOException;

public class TSPRun {
    public static void main(String[] args) throws IOException {
        //String filename = args[0];
        TSPReader reader = new TSPReader("dj38.tsp");
        reader.readFile();
        VBSS vbss = new VBSS(reader.getNumOfCities(), 7, reader.getXCords(), reader.getYCords());
        vbss.run(50000);
    }
}
