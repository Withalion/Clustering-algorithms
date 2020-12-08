package ViewControls;

import Utils.KMC;
import Utils.Point;
import Utils.PointGenerator;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Controller {
    private View mainView;
    private Chart clusterChart;
    private KMC seekAlgo;
    PointGenerator generator;
    String clusterAlgo;

    void pairController(View newView){
        this.mainView = newView;
    }

    void generateData(String showType){ //funkcia vypise chybovu hlasku ak nebol zvoleny algoritmus, ak hej tak spusti zvoleny algoritmus
        this.clusterAlgo = showType;

        if (showType == null){
            mainView.errorInput();
            return;
        }
        generator = new PointGenerator();
        generator.createDataSet();

        switch (showType) {
            case "k-means, centroid":
                seekAlgo = new KMC();
                seekAlgo.KMCalgo(this);
                break;
            case "k-means, medoid":
                break;
            case "aglo. zhlukovanie, centroid":
                break;
            case "diviz. zhlukovanie, centroid":
                break;
        }

        clusterChart = new Chart(this);
        clusterChart.start(new Stage());

    }

    public ArrayList<Point> getData(){
        return generator.pointList;
    }

    public ArrayList<Point> getCentroids(){
        return seekAlgo.centroidList;
    }

}
