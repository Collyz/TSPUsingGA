import java.util.Arrays;
import java.util.SplittableRandom;

public class VBSS {
    private int[] tour;
    private final SplittableRandom sRand;
    private final double[] xValues;
    private final double[] yValues;
    private int B;

    public VBSS(int numCities, int B, double[] xValues, double[] yValues){
        this.tour = new int[numCities];
        this.sRand = new SplittableRandom();
        this.xValues = xValues;
        this.yValues = yValues;
        this.B = B;
        for(int i = 0; i < tour.length; i++){
            tour[i] = i;
        }

    }

    public void generateNewTour(){
        for(int i = 0; i < tour.length; i++){
            int randIndex1 = sRand.nextInt(tour.length);
            int randIndex2 = sRand.nextInt(tour.length);
            swap(this.tour, randIndex1, randIndex2);
        }
    }
    /**
     * Helper method to create a random tour.
     * Simply swaps two cities in the tour
     * @param array - The array to manipulate
     * @param pos1 - The first city to swap
     * @param pos2 - The second city to swap
     */
    private void swap(int[] array, int pos1, int pos2){
        int temp = array[pos1];
        array[pos1] = array[pos2];
        array[pos2] = temp;
    }

    public int getTourCost(){
        int totalCost = 0;
        for(int i = 0; i < tour.length; i++){
            int city1 = tour[i];
            int city2;
            if((i + 1) < tour.length){
                city2 = tour[i + 1];
            }else{
                city2 = tour[0];
            }
            totalCost += distBetweenCities(city1, city2);
        }
        return totalCost;
    }

    public int distBetweenCities(int city1, int city2){
        double x1 = xValues[city1]; //city1 x value
        double x2 = xValues[city2]; //city2 x value
        double y1 = yValues[city1]; //city1 y value
        double y2 = yValues[city2]; //city2 y value
        //The calculation for the distance formula
        return (int)Math.round(Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2)));
    }

    public int[] getHeuristic(){
        int[] costs = new int[tour.length];
        int[] ranks = new int[tour.length];
        costs[0] = 0;
        for(int i = 1; i < tour.length; i++){
            costs[i] = distBetweenCities(tour[0], tour[i]);
        }
        // Sort the costs array in ascending order
        Arrays.sort(costs);

        // Generate ranks based on the sorted array
        for(int i = 0; i < costs.length; i++){
            int heuristicVal = Arrays.binarySearch(costs, distBetweenCities(tour[0], tour[i]));
            ranks[i] = (int)Math.pow(heuristicVal, B);
        }
        return ranks;
    }

    public double getHeuristicInverseSum(int[] rank){
        double inverseSum = 0;
        //Skip the first value since its always zero
        for(int i = 1 ; i < rank.length; i++){
            inverseSum += (1/(double)rank[i]);
        }
        return inverseSum;
    }

    public double[] getHeuristicInverses(int[] rank){
        double[] inverses = new double[rank.length];
        for(int i = 1; i < rank.length; i++){
            inverses[i] =  (1/(double)rank[i]);
        }
        return inverses;
    }

    public double[] setProbabilities(){
        int[] rank = getHeuristic();
        double[] inverses = getHeuristicInverses(rank);
        double inverseSum = getHeuristicInverseSum(rank);

        double[] probs = new double[rank.length];
        for(int i = 0; i < probs.length; i++){
            probs[i] = inverses[i]/inverseSum;
        }
        return probs;
    }

    public int[] removeAtIndex(int[] tour, int index) {
        if (index < 0 || index >= tour.length) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds for array of length " + tour.length);
        }

        int[] newTour = new int[tour.length - 1];
        int j = 0;
        for (int i = 0; i < tour.length; i++) {
            if (i != index) {
                newTour[j] = tour[i];
                j++;
            }
        }
        return newTour;
    }


    public void vbssNewTour(){
        //Initializing an empty new tour
        int[] newTour = new int[tour.length];
        double cumulativeProb; //The cumulative probability
        double[] probs = setProbabilities(); //Determine the probabilities
        for(int i = 0; i < newTour.length; i++){
            double randNum = sRand.nextDouble();
            cumulativeProb = 0;
            for (int j = 0; j < tour.length; j++) {
                if(tour.length == 1){
                    newTour[i] = tour[0];
                    break;
                }
                cumulativeProb += probs[j];
                if (randNum < cumulativeProb && randNum > (cumulativeProb - probs[j])) {
                    int tempCityValue = tour[j];
                    newTour[i] = tour[0];
                    this.tour = removeAtIndex(this.tour, 0);
                    swapValue(this.tour,tempCityValue);
                    probs = setProbabilities();
                    break;
                }
            }
        }
        this.tour = newTour;
    }


    public void swapValue(int[] array, int value) {
        int i = 0;
        while (i < array.length && array[i] != value) {
            i++;
        }
        if (i < array.length) {
            int tempVal = array[0];
            array[0] = value;
            array[i] = tempVal;
        }
    }


    public void printTour(){
        System.out.print(Arrays.toString(this.tour));
        System.out.println(tour.length);
    }

    public void run(int iterations){
        int best = Integer.MAX_VALUE;
        //Generate a random tour or else the default tour from 1,2,3... etc. will be the tour
        generateNewTour();
        best = getTourCost();
        for(int i = 0; i < iterations; i++){
            vbssNewTour();
            printTour();
            int temp = getTourCost();
            if(temp < best){
                best = temp;
            }
        }
        System.out.println(best);
    }
}
