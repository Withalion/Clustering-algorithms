// K-means algoritmus s centroidom
package Utils;

import ViewControls.Controller;

import java.util.ArrayList;

public class KMC implements Distance{
    public ArrayList<Point> centroidList = new ArrayList<>();

    public void KMCalgo(Controller controller){
        double distance = 0;
        final int CENTROID_NUM = 20;    //pocet ocakavanych centroidov
        int index, change = 2 * CENTROID_NUM;
        for (int i = 0; i < CENTROID_NUM; i++){   //vytvori sa dany pocet centroidov na poziciach nahodneho bodu, vzdialenost od seba budu mat vacsiu ako 700
            Point newRandom = controller.getData().get((int)(Math.random() * controller.getData().size()));
            centroidList.add(new Point(newRandom.X, newRandom.Y));
            for (Point centroid: centroidList) {
                if (centroid != centroidList.get(i) && this.getDistance(centroidList.get(i).X, centroidList.get(i).Y, centroid.X, centroid.Y) <= 700){
                    centroidList.remove(i);
                    i--;
                    break;
                }
                else if (centroid == centroidList.get(i)){
                    centroidList.get(i).friends = new ArrayList<>();
                }
            }
        }
        while (change != 0) {
            change = 2 * CENTROID_NUM;
            for (Point centroid : centroidList) {
                centroid.friends.clear();
            }
            for (Point point : controller.getData()) {    //kazdy bod si vyrata najblizsi centroid a prida sa centroidu do zoznamu
                index = 0;
                for (int i = 0; i < centroidList.size(); i++) {
                    if (i == 0) {
                        distance = this.getDistance(point.X, point.Y, centroidList.get(i).X, centroidList.get(i).Y);
                    } else if (distance > this.getDistance(point.X, point.Y, centroidList.get(i).X, centroidList.get(i).Y)) {
                        distance = this.getDistance(point.X, point.Y, centroidList.get(i).X, centroidList.get(i).Y);
                        index = i;
                    }
                }
                centroidList.get(index).friends.add(point);
            }
            for (Point centroid : centroidList) {    //kazdy centroid si prerata suradnice
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
