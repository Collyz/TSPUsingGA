import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GeneticsMmmmmTester {
    public static void main(String[] args){
        GeneticsMmmmm g = new GeneticsMmmmm();

        ArrayList<Double> doubles = new ArrayList<>();
        ArrayList<Double> doubles2 = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            doubles.add((double)i);
            doubles2.add((double)((i+6)%10));
        }

        //g.singleSwap(doubles);
        g.cycleCrossOver(doubles, doubles2);
        for (int i = 0; i < doubles.size(); i++){
            System.out.println(doubles.get(i) + " " + doubles2.get(i));
        }

        int[][] poo = new int[10][2];
        for (int i = 0; i < poo.length; i++){
            poo[i] = getArray();
        }

        for (int i = 0; i < poo.length; i++){
            System.out.println(poo[i][0] + " " + poo[i][1]);
        }

        Arrays.sort(poo, (a, b) -> Integer.compare(b[0],a[0]));

        System.out.println("Sorted hopefully");
        for (int i = 0; i < poo.length; i++){
            System.out.println(poo[i][0] + " " + poo[i][1]);
        }
    }

    public static int[] getArray(){
        Random r = new Random();
        int[] in = {r.nextInt(10), r.nextInt(10)};
        return in;
    }
}
