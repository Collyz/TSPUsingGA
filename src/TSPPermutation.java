import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.SplittableRandom;

public class TSPPermutation {
    private int[][] population;
    private double[] fitness;
    private final SplittableRandom sRand;
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
        this.fitness = new double[population];
        this.sRand = new SplittableRandom();
        this.xValues = x;
        this.yValues = y;
    }

    public void generateNewPopulation(){
        int numberOfCities = population[0].length;
        int[] defaultTour = new int[numberOfCities];
        for(int i = 0; i < numberOfCities; i++){
            defaultTour[i] = i;
        }
        for(int i = 0; i < 100; i++){
            int[] defTourCopy = Arrays.copyOf(defaultTour, numberOfCities);
            for(int j = 0; j < population[0].length; j++){
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


    private void swap(double[] array, int pos1, int pos2){
        double temp = array[pos1];
        array[pos1] = array[pos2];
        array[pos2] = temp;
    }

    /**
     * Generates the cost of every tour in the population
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
            //System.out.println("Individual " + i + ":" +tempSum);
            best = Math.min(best, cost);
        }
        return best;
    }

    public int generateCostOfIndividual(int[] individual){
        int totalCost = 0;
        for (int i = 0; i < individual.length; i++) {
            int city1 = individual[i];
            int city2;
            if ((i + 1) < individual.length) {
                city2 = individual[i + 1];
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
        //The fitness array is the cost of each tour for now
        for(int i = 0; i < population.length; i++){
            fitness[i] = generateCostOfIndividual(population[i]);
        }

        double sum = 0;
        //Then calculate the inverse of each tour cost
        for (int i = 0; i < this.fitness.length; i++) {
            fitness[i] = 1.0 / fitness[i];
            sum += fitness[i];
        }

        //Normalizing the fitness of each tour cost
        for (int i = 0; i < fitness.length; i++) {
            fitness[i] = fitness[i]/sum;
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

        for (int i = 0; i < fitness.length; i++) {
            bound += fitness[i];
            while (bound > randomValue) {
                nextGeneration[index++] = population[i];
                randomValue += N;
            }
        }
        population = nextGeneration;
    }

    public void fitnessProportionateSelection(){
        int[][] nextGeneration = new int[population.length][];
        double partialSum = fitness[0];
        double totalSum = 0;
        for(int i = 0; i < fitness.length; i++){
            totalSum += fitness[i];
        }
        double randNum = sRand.nextDouble();
        for(int i = 0; i < nextGeneration.length; i++){
            partialSum += fitness[i] ;
            if(partialSum >= randNum){
                nextGeneration[i] = population[i];
                break;
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

    private void reversalMutation(int[] array){
        int indexOne = 0;
        int indexTwo = 0;
        while(indexOne == indexTwo){
            indexOne = sRand.nextInt(array.length);
            indexTwo = sRand.nextInt(array.length);
        }
        if(indexOne> indexTwo){
            for(int i = indexTwo; i < indexOne; i++){

            }
        }
    }

    private void reversalMutation2(int[] array) {
        int indexOne = sRand.nextInt(array.length);
        int indexTwo = sRand.nextInt(array.length);
        while (indexOne == indexTwo) {
            indexTwo = sRand.nextInt(array.length);
        }
        if (indexOne > indexTwo) {
            int temp = indexOne;
            indexOne = indexTwo;
            indexTwo = temp;
        }
        int[] reversed = new int[indexTwo - indexOne + 1];
        for (int i = 0; i < reversed.length; i++) {
            reversed[i] = array[indexTwo - i];
        }
        for (int i = indexOne; i <= indexTwo; i++) {
            array[i] = reversed[i - indexOne];
        }
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

    /**
     * Runs the Permutation Genetic Algorithm given parameters
     * @param crossoverRate
     * @param mutationRate
     * @param numOfGenerations
     */
    public void run(double crossoverRate, double mutationRate, int numOfGenerations){
        int best = Integer.MAX_VALUE;
        generateNewPopulation();
        System.out.println(generateBestPopCost());
        for(int i = 0; i < numOfGenerations; i++) {
            double randNum = sRand.nextDouble();
            if(i > 1000 && i < 5000){
                mutationRate += .01;
            }
            if (randNum < crossoverRate) {
                int randTour = sRand.nextInt(population.length);
                reversalMutation(population[randTour]);
                int randTour1 = sRand.nextInt(population.length);
                int randTour2 = sRand.nextInt(population.length);
                cycleCrossOver(population[randTour1], population[randTour2]);
            }
            if (mutationRate < crossoverRate) {
                int randTour1 = sRand.nextInt(population.length);
                singleSwapMutation(population[randTour1]);
            }
            int temp = generateBestPopCost();
            if(temp < best){
                best = temp;
            }
            calculateFitness();
            stochasticUniversalSampling();
        }
        System.out.println("Best found using SUS: " + best);
    }


}
