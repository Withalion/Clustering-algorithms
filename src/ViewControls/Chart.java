package ViewControls;

import Utils.Point;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Chart extends Application {
    private Controller controller;
    Chart(Controller controller){
        this.controller = controller;
    }

    public void start(Stage stage) {    //funkcia vytvori nove okno s grafom na ktorom su clustre
        final NumberAxis xAxis = new NumberAxis(-5000, 5000, 500);
        final NumberAxis yAxis = new NumberAxis(-5000, 5000, 500);
        final ScatterChart<Number,Number> chart = new ScatterChart<Number,Number>(xAxis,yAxis);
        chart.setTitle(controller.clusterAlgo);
        //body sa roztriedia do skupin na grafe podla prislusnosti ku centroidu, centroidy su samostatna skupina
        ArrayList<XYChart.Series<Number, Number>> seriesArrayList = new ArrayList<>();
        int i = 0;
        XYChart.Series<Number, Number> centroidSeries = new XYChart.Series<>();
        centroidSeries.setName("Centroids");
        for(Point centroid : controller.getCentroids()){
            seriesArrayList.add(new XYChart.Series<>());
            i++;
            seriesArrayList.get(i - 1).setName("Cluster " + i);
            for (Point dataEntry: centroid.friends){
                seriesArrayList.get(i - 1).getData().add(new XYChart.Data<>(dataEntry.X, dataEntry.Y));
            }
            chart.getData().add(seriesArrayList.get(i - 1));
            centroidSeries.getData().add(new XYChart.Data<>(centroid.X, centroid.Y));
        }
        chart.getData().add(centroidSeries);

        Scene scene  = new Scene(chart, 1000, 1000);
        scene.getStylesheets().add("Utils/Chart.css");
        stage.setScene(scene);
        stage.show();
    }
}
