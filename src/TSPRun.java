import java.io.IOException;

public class TSPRun {
    public static void main(String[] args) throws IOException {
        String filename;
        int runsNumber;
        int B;

        if (args.length == 1){
            filename = args[0];
            runsNumber = 10000;
            B = 4;
        }
        else{
            filename = args[0];
            runsNumber = Integer.parseInt(args[1]);
            B = Integer.parseInt(args[2]);
        }

        TSPReader reader = new TSPReader(filename);
        reader.readFile();
        VBSS vbss = new VBSS(reader.getNumOfCities(), B, reader.getXCords(), reader.getYCords());
        vbss.run(runsNumber);
    }
}
