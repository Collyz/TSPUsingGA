import java.util.ArrayList;
import java.util.SplittableRandom;

public class GeneticsMmmmm {
    private SplittableRandom split;

    public GeneticsMmmmm(){
        split = new SplittableRandom();
    }

    /**
     * Picks two random indices in the inputted Array List and swaps their values.
     *
     * @param input The Array List you would like to be mutated
     */
    public void singleSwap(ArrayList<Double> input){
        if (input.size() > 1) {                         //Don't want an infinite while loop
            int indexOne = 0;
            int indexTwo = 0;

            //ensures the two swapped indices will never be the same
            while (indexOne == indexTwo) {
                indexOne = split.nextInt(input.size());
                indexTwo = split.nextInt(input.size());
            }

            //Swap the values in each index
            double temp = input.get(indexOne);
            input.set(indexOne, input.get(indexTwo));
            input.set(indexTwo, temp);
        }
    }

    public void cycleCrossOver(ArrayList<Double> inputOne, ArrayList<Double> inputTwo){
        ArrayList<Integer> swapList = new ArrayList<>();

        //initialize random starting point
        int startingIndex = split.nextInt(inputOne.size());
        double firstValue = inputOne.get(startingIndex); //keep track of first value for when the loop neds
        double correspondingValue = inputTwo.get(startingIndex);
        swapList.add(startingIndex);             //add index to swap list

        boolean keepGoing = true; System.out.println(startingIndex);

        //keep entering in indexes into the swap arraylist till the cycle ends
        while (keepGoing) {
            for (int i = 0; i < inputOne.size(); i++) {
                if (Double.compare(correspondingValue, inputOne.get(i)) == 0) {
                    swapList.add(i);
                    correspondingValue = inputTwo.get(i);
                    if (Double.compare(firstValue, inputTwo.get(i)) == 0) {
                        keepGoing = false; //we've reached the end of the cycle, end the while loop
                    }
                    break;
                }
            }
        }

        //now swap all each index with the other
        double temp;
        for (int i = 0; i < swapList.size(); i++){
            temp = inputOne.get(swapList.get(i));
            inputOne.set(swapList.get(i), inputTwo.get(swapList.get(i)));
            inputTwo.set(swapList.get(i), temp);
        }

    }
}
