import java.util.Arrays;
import java.util.SplittableRandom;

//(int)Math.round(Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2)))

public class TSPPermutation {
    private int[][] population;
    private SplittableRandom sRand;
    private double[] xValues;
    private double[] yValues;
    private int numberOfCities;

    /**
     * Constructor that initializes several important parameters for TSP problem as a permutation
     * @param numberOfCities - The number of cities, taken from file
     * @param population - The size of the population
     * @param selectionNum - The selection rate
     * @param crossoverNum - The crossover rate
     * @param mutationNum - The mutation rate
     * @param x - Latitude values from file
     * @param y - Longitude value from file
     */
    public TSPPermutation(int numberOfCities, int population, double selectionNum, double crossoverNum, double mutationNum, double[] x, double[] y){
        //Initialization of private objects
        this.population = new int[population][numberOfCities];
        this.sRand = new SplittableRandom();
        this.xValues = x;
        this.yValues = y;
        this.numberOfCities = numberOfCities;

        int[] defaultTour = new int[numberOfCities];
        for(int i = 0; i < numberOfCities; i++){
            defaultTour[i] = i;
        }
        for(int i = 0; i < population; i++){
            int[] defTourCopy = Arrays.copyOf(defaultTour, numberOfCities);
            for(int j = 0; j < numberOfCities; j++){
                int randomIndex1 = sRand.nextInt(0, this.numberOfCities);
                int randomIndex2 = sRand.nextInt(0, this.numberOfCities);
                swap(defTourCopy, randomIndex1, randomIndex2);
            }
            this.population[i] = defTourCopy;
        }
    }

    public void generateNewPopulation(){
        int[] defaultTour = new int[numberOfCities];
        for(int i = 0; i < numberOfCities; i++){
            defaultTour[i] = i;
        }
        for(int i = 0; i < 100; i++){
            int[] defTourCopy = Arrays.copyOf(defaultTour, numberOfCities);
            for(int j = 0; j < 1000; j++){
                int randomIndex1 = sRand.nextInt(0, numberOfCities);
                int randomIndex2 = sRand.nextInt(0, numberOfCities);
                swap(defTourCopy, randomIndex1, randomIndex2);
            }
            this.population[i] = defTourCopy;
        }
    }

    private void swap(int[] array, int pos1, int pos2){
        int temp = array[pos1];
        array[pos1] = array[pos2];
        array[pos2] = temp;
    }

    public void printPopulation(){
        int individual = 1;
        for (int[] person : this.population) {
            System.out.print("Individual " + individual + ": ");
            individual++;
            System.out.println(Arrays.toString(person));
        }
    }

    public int generateCosts(){
        int best = Integer.MAX_VALUE;
        for(int i = 0; i < population.length; i++){
            int tempSum = 0;
            for(int j = 0; j < population[i].length; j++){
                if((j+1) < population[i].length){
                    int city1 = population[i][j];
                    int city2 = population[i][j + 1];
                    tempSum += costBetweenTwoPoints(city1, city2);
                }else{
                    int city1 = population[i][j];
                    int city2 = population[i][0];
                    tempSum += costBetweenTwoPoints(city1, city2);
                }
            }
            //System.out.println("Individual " + i + ":" +tempSum);
            best = Math.min(best, tempSum);
        }
        return best;
    }

    private int costBetweenTwoPoints(int city1, int city2){
        double x1 = xValues[city1];
        double x2 = xValues[city2];
        double y1 = yValues[city1];
        double y2 = yValues[city2];
        int cost = (int)Math.round(Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2)));
        //System.out.println(cost);
        return cost;
    }


}
