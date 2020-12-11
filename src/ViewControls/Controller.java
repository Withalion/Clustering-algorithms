package ViewControls;

import Utils.*;
import javafx.stage.Stage;
import java.util.ArrayList;

public class Controller {
    private View mainView;
    private Chart clusterChart;
    private Algorithm seekAlgo;
    PointGenerator generator;
    String clusterAlgo;

    void pairController(View newView){
        this.mainView = newView;
    }

    void generateData(String showType){ //funkcia vypise chybovu hlasku ak nebol zvoleny algoritmus, ak hej tak spusti zvoleny algoritmus
        this.clusterAlgo = showType;
        long start, end, duration;

        if (showType == null){
            mainView.errorInput();
            return;
        }
        start = System.nanoTime();
        generator = new PointGenerator();
        generator.createDataSet();
        end = System.nanoTime();
        duration = end - start;                                                       //conversion na minuty /60,000,000,000 //conversion na sekundy /1,000,000,000
        System.out.println("Body boli vygenerovane za: " + (duration / 1000000000) + "," + ((duration % 1000000000) / 1000000) + " sec");

        switch (showType) {
            case "k-means, centroid":
                start = System.nanoTime();
                seekAlgo = new KMC();
                seekAlgo.clusterAlgorithm(this);
                end = System.nanoTime();
                break;
            case "k-means, medoid":
                start = System.nanoTime();
                seekAlgo = new KMM();
                seekAlgo.clusterAlgorithm(this);
                end = System.nanoTime();
                break;
            case "aglo. zhlukovanie, centroid":
                start = System.nanoTime();
                seekAlgo = new Aglomerative();
                seekAlgo.clusterAlgorithm(this);
                end = System.nanoTime();
                break;
            case "diviz. zhlukovanie, centroid":
                start = System.nanoTime();
                end = System.nanoTime();
                break;
        }
        duration = end - start;
        System.out.println("Clustre boli vygenerovane za: " + (duration / 1000000000) + "," + ((duration % 1000000000) / 1000000) + " sec");

        start = System.nanoTime();
        clusterChart = new Chart(this);
        clusterChart.start(new Stage());
        end = System.nanoTime();
        duration = end - start;
        System.out.println("Clustre boli vykreslene za: " + (duration / 1000000000) + "," + ((duration%1000000000) / 1000000) + " sec\n");
    }

    public ArrayList<Point> getData(){
        return generator.pointList;
    }

    public ArrayList<Point> getCentroids(){
        return seekAlgo.CMlist;
    }

}
