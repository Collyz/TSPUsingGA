/**
 * Group Members: Mohammed Mowla, Brendan Lee
 */
import java.util.Arrays;
import java.util.SplittableRandom;

public class VBSS {
    private int[] tour;
    private final SplittableRandom sRand;
    private final double[] xValues;
    private final double[] yValues;
    private final int B;

    /**
     * Constructor that accepts 4 parameters of importance.
     * @param numCities - The number of cities
     * @param B - The to raise heuristic values to
     * @param xValues - The X values for each city
     * @param yValues - The Y Values for each city
     */
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

    /**
     * Generate a new random tour. Used primarily once to make sure the default starting tour
     * isn't 1,2,3,4, etc.
     */
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

    /**
     * Distance based heuristic that assigns a 'rank' to each city in the tour.
     * The heuristic uses the very FIRST city in the tour as the bases for assigning values to every other city
     * Calculates distance from FIRST city to every other city and rank is closest = 1 farthest = (num > 1)
     * @return An array of the heuristic values for each index based on distance form FIRST city
     */
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

    /**
     * The sum of the heuristic inverses used for further calculations
     * @param rank The heuristic values that get inverted and then summed
     * @return The double that is the sum of the heuristic inverses
     */
    public double getHeuristicInverseSum(int[] rank){
        double inverseSum = 0;
        //Skip the first value since its always zero
        for(int i = 1 ; i < rank.length; i++){
            inverseSum += (1/(double)rank[i]);
        }
        return inverseSum;
    }

    /**
     * Provides an array of heuristic values that are inverted for further calculations
     * @param rank - The heuristic values to invert
     * @return The inverted heuristic value array of doubles
     */
    public double[] getHeuristicInverses(int[] rank){
        double[] inverses = new double[rank.length];
        for(int i = 1; i < rank.length; i++){
            inverses[i] =  (1/(double)rank[i]);
        }
        return inverses;
    }

    /**
     * Gets the heuristic values, inverse heuristic values, and inverse heuristic sum and returns the probabilities
     * i.e. 1/(inverse heuristic values)
     * @return An array of the probabilities for each index based on heuristic inverses.
     */
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

    /**
     * Remove the value at the index from the tour
     * @param tour - The array where the value should be removed from.
     * @param index - The index where to remove from the tour.
     * @return - A new array that is the original array with the
     */
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

    /**
     * Method that performs the actual Value Biased Stochastic Sampling
     */
    public void vbssNewTour(){
        int[] newTour = new int[tour.length]; //Initializing an empty new tour
        double[] probs = setProbabilities(); //Determine the probabilities
        double cumulativeProb; //The cumulative probability
        //Loop through for the size of the tour
        for(int i = 0; i < newTour.length; i++){
            double randNum = sRand.nextDouble(); //Generate a random number between [0.0, 1.0)
            cumulativeProb = 0;
            //Loop through remaining cities
            for (int j = 0; j < tour.length; j++) {
                //If the tour is only one city there is no need to do any more checking, simply add to end of newTour
                if(tour.length == 1){
                    newTour[i] = tour[0];
                    break;
                }
                cumulativeProb += probs[j];
                /*
                Range check for the random number, if it falls within the range then that is the next city to base
                heuristic off of.
                 */
                if (randNum < cumulativeProb && randNum > (cumulativeProb - probs[j])) {
                    int tempCityValue = tour[j];
                    newTour[i] = tour[0];
                    this.tour = removeAtIndex(this.tour, 0); //Remove zeroth index since it's best city previously determined
                    swapValue(this.tour,tempCityValue); //Swap next 'best' city with city at zeroth index
                    probs = setProbabilities(); //Generate new probabilities based on the current remaining cities
                    break;
                }
            }
        }
        //Make the newTour the current tour as the current tour has been removed from
        this.tour = newTour;
    }

    /**
     * Helper function to vbssNewTour method.
     * Performs a swap of the value in the tour and the zeroth index
     * @param array - The current tour essentially
     * @param value - The value to swap with the zeroth index in the array
     */
    private void swapValue(int[] array, int value) {
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

    /**
     * Prints the current tour
     */
    public void printTour(){
        System.out.print(Arrays.toString(this.tour));
        System.out.println(tour.length);
    }

    /**
     * Run method created to allow for multiple iterations of VBSS
     * Chooses the best value and array
     * @param iterations - The number of runs to run VBSS for
     */
    public void run(int iterations){
        //Generate a random tour or else the default tour is 1,2,3... etc. will be the tour
        generateNewTour();
        //Check the newly generated tour
        int best = getTourCost();
        int[] bestTour = new int[tour.length];
        //Loop through iterations and assigning the least cost to best
        for(int i = 0; i < iterations; i++){
            vbssNewTour();
            int temp = getTourCost();
            if(temp < best){
                best = temp;
                bestTour = this.tour;
            }
        }
        System.out.println(best);
        String print = "";
        for(int i = 0; i < bestTour.length; i++){
            print += (bestTour[i] + 1) + " ";
        }
        System.out.println(print);
    }
}
