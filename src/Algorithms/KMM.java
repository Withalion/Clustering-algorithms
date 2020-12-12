// K-means algoritmus s medoidom
package Algorithms;

import Algorithms.Algorithm;
import Utils.Distance;
import Utils.Point;
import ViewControls.Controller;

import java.util.ArrayList;

public class KMM extends Algorithm implements Distance {

    @Override
    public void clusterAlgorithm(Controller controller){
        final int MEDOID_NUM = 20;
        int index;
        double distance = 0;
        boolean change = true;
        for (int i = 0; i < MEDOID_NUM; i++){   //vytvori sa dany pocet medoidov na poziciach nahodneho bodu, vzdialenost od seba budu mat vacsiu ako 700
            Point newRandom = controller.getData().get((int)(Math.random() * controller.getData().size()));
            CMlist.add(new Point(newRandom.X, newRandom.Y));
            for (Point medoid: CMlist) {
                if (medoid != CMlist.get(i) && this.getDistance(CMlist.get(i).X, CMlist.get(i).Y, medoid.X, medoid.Y) <= 700){
                    CMlist.remove(i);
                    i--;
                    break;
                }
                else if (medoid == CMlist.get(i)){
                    CMlist.get(i).friends = new ArrayList<>();
                }
            }
        }
        while (change) {
            int innerChange = 0;
            for (Point medoid : CMlist) {
                medoid.friends.clear();
            }
            for (Point point : controller.getData()) {    //kazdy bod si vyrata najblizsi medoid a prida sa medoidu do zoznamu
                index = 0;
                for (int i = 0; i < CMlist.size(); i++) {
                    if (i == 0) {
                        distance = this.getDistance(point.X, point.Y, CMlist.get(i).X, CMlist.get(i).Y);
                    } else if (distance > this.getDistance(point.X, point.Y, CMlist.get(i).X, CMlist.get(i).Y)) {
                        distance = this.getDistance(point.X, point.Y, CMlist.get(i).X, CMlist.get(i).Y);
                        index = i;
                    }
                }
                CMlist.get(index).friends.add(point);
            }
            for (Point medoid : CMlist){    //cyklus prejde vsetky medoidy a kazdy medoid si vyrata celkove vzdialenosti od prisluchajucich bodov
                Point betterMedoid = null;
                double bestDissim = 0, medoidDissim = 0;
                for (Point point : medoid.friends){ //kazdy bod medoidu si vyrata celkovu vzdialenost od bodov medoidu, ulozi sa najlepsie zlepsenie
                    double dissimilarity = this.getDistance(medoid.X, medoid.Y, point.X, point.Y);
                    medoidDissim += this.getDistance(medoid.X, medoid.Y, point.X, point.Y);
                    for (Point point1 : medoid.friends){
                        dissimilarity += this.getDistance(point1.X, point1.Y, point.X, point.Y);
                    }
                    if (bestDissim == 0){
                        bestDissim = dissimilarity;
                        betterMedoid = point;
                    }
                    else if (dissimilarity < bestDissim){
                        bestDissim = dissimilarity;
                        betterMedoid = point;
                    }
                }
                if (medoidDissim > bestDissim){     //ak nejaky bod z prisluchajucich bodov je lepsi medoid stane sa medoidom
                    medoid.X = betterMedoid.X;
                    medoid.Y = betterMedoid.Y;
                    innerChange++;
                }
            }
            if (innerChange == 0) change = false;
        }
    }
}
