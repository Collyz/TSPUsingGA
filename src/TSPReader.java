/**
 * Group Members: Mohammed Mowla, Brendan Lee
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TSPReader {
    private double[] xCords;
    private double[] yCords;
    private int numOfCities;
    private String filename;

    public TSPReader(String filename){
        xCords = new double[0];
        yCords = new double[0];
        numOfCities = 0;
        this.filename = filename;
    }

    public void readFile(){
        try (BufferedReader br = new BufferedReader(new FileReader(this.filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("DIMENSION")) {
                    numOfCities = Integer.parseInt(line.split(":")[1].trim());
                } else if (line.startsWith("NODE_COORD_SECTION")) {
                    xCords = new double[numOfCities];
                    yCords = new double[numOfCities];
                    for (int i = 0; i < numOfCities; i++) {
                        line = br.readLine();
                        String[] coords = line.split("\\s+");
                        xCords[i] = Double.parseDouble(coords[1]);
                        yCords[i] = Double.parseDouble(coords[2]);
                    }
                    break; // stop reading file once coordinates are parsed
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printXYValues(){
        for(int i = 0; i < xCords.length; i++){
            System.out.println("Line " + (i+1));
            System.out.print(xCords[i] + "    ");
            System.out.println(yCords[i]);
        }
    }

    public double[] getXCords(){
        return this.xCords;
    }

    public double[] getYCords(){
        return this.yCords;
    }

    public int getNumOfCities(){
        return this.numOfCities;
    }

    public String getFileName(){
        return this.filename;
    }

}
