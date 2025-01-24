import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TrainSorter {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        in.nextLine();
        String fullTrainIn = in.nextLine();
        String[] fullTrainStr = fullTrainIn.split("\\s");
        List<Integer> fullTrain = new ArrayList<>();
        for (String i : fullTrainStr) {
            fullTrain.add(Integer.parseInt(i));
        }
        int position = 1;
        List<Integer> stoper = new ArrayList<>();
        List<Step> steps = new ArrayList<>();

        for (Integer vagon : fullTrain) {
            if (vagon == position) {
                steps.add(new Step((short) 1, (short) 1));
                steps.add(new Step((short) 2, (short) 1));
                position++;
                while (!stoper.isEmpty() && stoper.getLast() == position) {
                    steps.add(new Step((short) 2, (short) 1));
                    stoper.removeLast();
                    position++;
                }
            } else {
                stoper.add(vagon);
                steps.add(new Step((short)1, (short) 1));
            }
        }

        while (!stoper.isEmpty()) {
            if (stoper.getLast() == position) {
                steps.add(new Step((short) 2, (short) 1));
                stoper.removeLast();
                position++;
            } else {
                System.out.println(0);
                break;
            }
        }
        if (stoper.isEmpty()) {
            for (Step step : steps) {
                step.printStep();
            }
        }
    }

    public static class Step {
        private short way;
        private short vagon;

        public Step(short way, short vagon) {
            this.way = way;
            this.vagon = vagon;
        }

        public void printStep() {
            System.out.println(way + " " + vagon);
        }
    }
}
