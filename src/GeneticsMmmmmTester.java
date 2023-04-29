import java.util.ArrayList;

public class GeneticsMmmmmTester {
    public static void main(String[] args){
        GeneticsMmmmm g = new GeneticsMmmmm();

        ArrayList<Double> doubles = new ArrayList<>();
        ArrayList<Double> doubles2 = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            doubles.add((double)i);
            doubles2.add((double)((i+4)%10));
        }

        //g.singleSwap(doubles);
        g.cycleCrossOver(doubles, doubles2);
        for (int i = 0; i < doubles.size(); i++){
            System.out.println(doubles.get(i) + " " + doubles2.get(i));
        }
    }
}
