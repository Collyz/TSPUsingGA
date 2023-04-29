import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

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

    public void readFile() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(this.filename));
        String currentLine;
        boolean coordinateFlag = false;
        while((currentLine = br.readLine()) != null){
            String[] temp = currentLine.split(" ");
            for(String word: temp){
                if(word.equals("DIMENSION:")){
                    numOfCities = Integer.parseInt(temp[1]);
                    xCords = new double[numOfCities];
                    yCords = new double[numOfCities];
                }
                if(word.equals("NODE_COORD_SECTION")){
                    coordinateFlag = true;
                    currentLine = br.readLine();
                }
                if(coordinateFlag){
                    temp = currentLine.split(" ");
                    int tempIndex = Integer.parseInt(temp[0])-1;
                    xCords[tempIndex] = Double.parseDouble(temp[1]);
                    yCords[tempIndex] = Double.parseDouble(temp[2]);
                }

            }
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

    public String getFileName(){
        return this.filename;
    }

}
