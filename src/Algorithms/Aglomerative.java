//Aglomerativne zhlukovanie s centroidom
package Algorithms;

import Utils.Distance;
import Utils.Point;
import ViewControls.Controller;

import java.util.ArrayList;

public class Aglomerative extends Algorithm implements Distance {

    @Override
    public void clusterAlgorithm(Controller controller){
        final int MAX_MIN_DISTANCE = 500;               //algoritmus bude spajat pokial vzdialenosti medzi clustrami budu mensie ako tato
        Point pointSwap1 = null, pointSwap2 = null;
        double smallestDistance = -1, actualDistance;
        int X, Y;
        for (Point point : controller.getData()){               //kazdy bod si prida sam seba do listu aby sa z neho mohol stat centroid
            CMlist.add(new Point(point.X, point.Y));
            CMlist.get(CMlist.size() - 1).friends = new ArrayList<>();
            CMlist.get(CMlist.size() - 1).friends.add(point);
        }
        while(smallestDistance <= MAX_MIN_DISTANCE){            //clustruje pokial vzdialenost bodov je mensia ako zadana
            smallestDistance = -1;
            X = 0;
            Y = 0;
            for (Point centroid : CMlist){                  //algoritmus prejde vsetky body a najde 2 body ktore su najblizsie
                for (int i = CMlist.indexOf(centroid) + 1; i < CMlist.size(); i++){
                    actualDistance = getDistance(centroid.X, centroid.Y, CMlist.get(i).X, CMlist.get(i).Y);
                    if (smallestDistance == -1){
                        smallestDistance = actualDistance;
                        pointSwap1 = centroid;
                        pointSwap2 = CMlist.get(i);
                    }
                    else if (actualDistance < smallestDistance){
                        smallestDistance = actualDistance;
                        pointSwap1 = centroid;
                        pointSwap2 = CMlist.get(i);
                    }
                }
            }
            if (smallestDistance <= MAX_MIN_DISTANCE){
                pointSwap1.friends.addAll(pointSwap2.friends);  //ak je najmensia vzdialenost mensia ako zadana maximalna tak spoji clustre
                CMlist.remove(pointSwap2);
                for (Point friend : pointSwap1.friends) {
                    X += friend.X;
                    Y += friend.Y;
                }
                pointSwap1.X = X / pointSwap1.friends.size();   //prepocitaju sa suradnice centroidu
                pointSwap1.Y = Y / pointSwap1.friends.size();
            }
        }
    }

}
