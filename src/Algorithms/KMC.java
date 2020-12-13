// K-means algoritmus s centroidom
package Algorithms;

import Utils.Distance;
import Utils.Point;
import ViewControls.Controller;

import java.util.ArrayList;

public class KMC extends Algorithm implements Distance {

    @Override
    public void clusterAlgorithm(Controller controller){
        double distance = 0;
        final int CENTROID_NUM = 20;    //pocet ocakavanych centroidov
        int index, change = 2 * CENTROID_NUM;
        for (int i = 0; i < CENTROID_NUM; i++){   //vytvori sa dany pocet centroidov na poziciach nahodneho bodu, vzdialenost od seba budu mat vacsiu ako 700
            Point newRandom = controller.getData().get((int)(Math.random() * controller.getData().size()));
            CMlist.add(new Point(newRandom.X, newRandom.Y));
            for (Point centroid: CMlist) {
                if (centroid != CMlist.get(i) && getDistance(CMlist.get(i).X, CMlist.get(i).Y, centroid.X, centroid.Y) <= 700){
                    CMlist.remove(i);
                    i--;
                    break;
                }
                else if (centroid == CMlist.get(i)){
                    CMlist.get(i).friends = new ArrayList<>();
                }
            }
        }
        while (change != 0) {
            change = 2 * CENTROID_NUM;
            for (Point centroid : CMlist) {
                centroid.friends.clear();
            }
            for (Point point : controller.getData()) {    //kazdy bod si vyrata najblizsi centroid a prida sa centroidu do zoznamu
                index = 0;
                for (int i = 0; i < CMlist.size(); i++) {
                    if (i == 0) {
                        distance = getDistance(point.X, point.Y, CMlist.get(i).X, CMlist.get(i).Y);
                    } else if (distance > getDistance(point.X, point.Y, CMlist.get(i).X, CMlist.get(i).Y)) {
                        distance = getDistance(point.X, point.Y, CMlist.get(i).X, CMlist.get(i).Y);
                        index = i;
                    }
                }
                CMlist.get(index).friends.add(point);
            }
            for (Point centroid : CMlist) {    //kazdy centroid si prerata suradnice
                if (centroid.friends.size() == 0){
                    change -= 2;
                    continue;
                }
                int X = 0, Y = 0;
                for (Point friend : centroid.friends) {
                    X += friend.X;
                    Y += friend.Y;
                }
                if (centroid.X != X / centroid.friends.size()){ //suradnice sa prepisu, ak sa nezmenia tak sa zmensi change
                    centroid.X = X / centroid.friends.size();
                }
                else change--;
                if (centroid.Y != Y / centroid.friends.size()){
                    centroid.Y = Y / centroid.friends.size();
                }
                else change--;
            }
        }
    }
}
