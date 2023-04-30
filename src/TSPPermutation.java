import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.SplittableRandom;

public class TSPPermutation {
    private int[][] population;
    private double[] selectionProb;
    private SplittableRandom sRand;
    private double[] xValues;
    private double[] yValues;

    /**
     * Constructor that initializes several important parameters for TSP problem as a permutation
     * @param numberOfCities - The number of cities, taken from file
     * @param population - The size of the population
     * @param x - Latitude values from file
     * @param y - Longitude value from file
     */
    public TSPPermutation(int numberOfCities, int population, double[] x, double[] y){
        //Initialization of private objects
        this.population = new int[population][numberOfCities];
        this.selectionProb = new double[population];
        this.sRand = new SplittableRandom();
        this.xValues = x;
        this.yValues = y;


        int[] defaultTour = new int[numberOfCities];
        for(int i = 0; i < numberOfCities; i++){
            defaultTour[i] = i;
        }
        for(int i = 0; i < population; i++){
            int[] defaultTourCopy = Arrays.copyOf(defaultTour, numberOfCities);
            for(int j = 0; j < numberOfCities; j++){
                int randomIndex1 = sRand.nextInt(0, numberOfCities);
                int randomIndex2 = sRand.nextInt(0, numberOfCities);
                swap(defaultTourCopy, randomIndex1, randomIndex2);
            }
            this.population[i] = defaultTourCopy;
        }
    }

    public void generateNewPopulation(){
        int numberOfCities = population[0].length;
        int[] defaultTour = new int[numberOfCities];
        for(int i = 0; i < numberOfCities; i++){
            defaultTour[i] = i;
        }
        for(int i = 0; i < 100; i++){
            int[] defTourCopy = Arrays.copyOf(defaultTour, numberOfCities);
            for(int j = 0; j < 1000; j++){
                int randomIndex1 = sRand.nextInt(numberOfCities);
                int randomIndex2 = sRand.nextInt(numberOfCities);
                swap(defTourCopy, randomIndex1, randomIndex2);
            }
            this.population[i] = defTourCopy;
        }
    }

    /**
     * Helper method to create a random population.
     * Simply swaps two locations in an individual
     * @param array - The array to manipulate
     * @param pos1 - The first position to swap
     * @param pos2 - The second position to swap
     */
    private void swap(int[] array, int pos1, int pos2){
        int temp = array[pos1];
        array[pos1] = array[pos2];
        array[pos2] = temp;
    }

    /**
     * Generates the cost of every individual in the population
     * @return The best (lowest) cost in the current population
     */
    public int generateBestPopCost(){
        int best = Integer.MAX_VALUE;
        for (int i = 0; i < population.length; i++) {
            int cost = 0;
            for (int j = 0; j < population[i].length; j++) {
                int city1 = population[i][j];
                int city2;
                if ((j + 1) < population[i].length) {
                    city2 = population[i][j + 1];
                } else {
                    city2 = population[i][0];
                }
                cost += costBetweenTwoPoints(city1, city2);
            }
            selectionProb[i] = cost;
            //System.out.println("Individual " + i + ":" +tempSum);
            best = Math.min(best, cost);
        }
        return best;
    }

    public int generateCostOfIndividual(int[] individual){
        int totalCost = 0;
        for (int j = 0; j < individual.length; j++) {
            int city1 = individual[j];
            int city2;
            if ((j + 1) < individual.length) {
                city2 = individual[j + 1];
            } else {
                city2 = individual[0];
            }
            totalCost += costBetweenTwoPoints(city1, city2);
        }
        return totalCost;
    }

    /**
     * Helper method for generateBestPopCost and generateCostOfIndividual
     * @param city1 - City one
     * @param city2 - City two
     * @return - The cost between the two cities
     */
    private int costBetweenTwoPoints(int city1, int city2){
        double x1 = xValues[city1];
        double x2 = xValues[city2];
        double y1 = yValues[city1];
        double y2 = yValues[city2];
        return (int)Math.round(Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2)));
    }


    /**
     * Calc
     */
    public void calculateFitness() {
        double sum = 0;
        // Calculate the inverse of each number
        for (int i = 0; i < this.selectionProb.length; i++) {
            selectionProb[i] = 1.0 / selectionProb[i];
            sum += selectionProb[i];
        }

        // Normalize the fitness values
        for (int i = 0; i < selectionProb.length; i++) {
            selectionProb[i] /= sum;
        }

    }

    /**
     * Sorts the population based on the cost of each tour in the population
     */
    public void sortPopulation(){
        // Sort the array based on the cost of the tour
        Arrays.sort(this.population, Comparator.comparingInt(this::generateCostOfIndividual));
    }

    /**
     * Selection operator where N number of individuals are picked from the current population to move on to the next generation
     */
    public void stochasticUniversalSampling() {
        int[][] nextGeneration = new int[population.length][];
        double N = 1.0 / (double) nextGeneration.length;
        double randomValue = sRand.nextDouble(N);

        double bound = 0.0;
        int index = 0;

        for (int i = 0; i < selectionProb.length; i++) {
            bound += selectionProb[i];

            while (bound > randomValue) {
                nextGeneration[index++] = population[i];
                randomValue += N;
            }
        }

        population = nextGeneration;
    }

    /**
     * Performs cycle crossover on the
     * @param parent1 - Parent 1
     * @param parent2 - Parent 2
     */
    private void cycleCrossOver(int[] parent1, int[] parent2) {
        ArrayList<Integer> swapList = new ArrayList<>();

        //initialize random starting point
        int startingIndex = sRand.nextInt(parent1.length);
        int firstValue = parent1[startingIndex]; //keep track of first value for when the loop neds
        int correspondingValue = parent2[startingIndex];
        swapList.add(startingIndex); //add index to swap list
        boolean keepGoing = true;

        //keep entering in indexes into the swap arraylist till the cycle ends
        while (keepGoing) {
            for (int i = 0; i < parent1.length; i++) {
                if (correspondingValue == parent1[i]) {
                    swapList.add(i);
                    correspondingValue = parent2[i];
                    if (firstValue == parent2[i]) {
                        keepGoing = false; //we've reached the end of the cycle, end the while loop
                    }
                    break;
                }
            }
        }

        //now swap all each index with the other
        int temp;
        for (Integer integer : swapList) {
            temp = parent1[integer];
            parent1[integer] = parent2[integer];
            parent2[integer] = temp;
        }
    }

    /**
     * Single swap mutation to be used as the mutation operator for the population
     * @param array - The array to mutate
     */
    private void singleSwapMutation(int[] array){
        int indexOne = 0;
        int indexTwo = 0;
        //ensures the two swapped indices will never be the same
        while (indexOne == indexTwo) {
            indexOne = sRand.nextInt(array.length);
            indexTwo = sRand.nextInt(array.length);
        }
        //Swap the values in each index
        int temp = array[indexOne];
        array[indexOne] = array[indexTwo];
        array[indexTwo] = temp;
    }

    /**
     * Get the current population as a 2D array where rows are each individual and columns are the indexes
     * into the path of the individual solution
     * @return The current 2D array that is the population
     */
    public int[][] getPopulation(){
        return this.population;
    }

    /**
     * Prints every individual in the current population
     */
    public void printPopulation(){
        int individual = 1;
        for (int[] person : this.population) {
            System.out.print("Individual " + individual + ": ");
            individual++;
            System.out.println(Arrays.toString(person));
        }
    }

    public void run(double crossoverRate, double mutationRate, int numOfGenerations){
        for(int i = 0; i < numOfGenerations; i++) {
            double randNum = sRand.nextDouble();
            if (randNum < crossoverRate) {
                int randTour1 = sRand.nextInt(population.length);
                int randTour2 = sRand.nextInt(population.length);
                cycleCrossOver(population[randTour1], population[randTour2]);
            }
            if (mutationRate < crossoverRate) {
                int randTour1 = sRand.nextInt(population.length);
                singleSwapMutation(population[randTour1]);
            }

            sortPopulation();
            generateBestPopCost();
            calculateFitness();
            stochasticUniversalSampling();
        }

    }


}
