package Algorithms;

import Utils.Distance;
import Utils.Point;
import ViewControls.Controller;

import java.util.ArrayList;

public class Divisive extends Algorithm implements Distance {


    private double kmcAlgo(Point centroid1){
        ArrayList<Point> friendsArrayList = new ArrayList<>(centroid1.friends);
        int change = 4;
        boolean notNew = true;
        Point newCentroid = null;
        double newBiggestDistance = 0;
        //vytvori sa novy centroid na pozicii nahodneho bodu od stareho centroidu, vzdialenost od seba nebudu mat mensiu ako 500
        while (notNew) {
            newCentroid = centroid1.friends.get((int)(Math.random() * centroid1.friends.size()));
            if (getDistance(centroid1.X, centroid1.Y, newCentroid.X, newCentroid.Y) >= 500) {
                CMlist.add(new Point(newCentroid.X, newCentroid.Y));
                CMlist.get(CMlist.size() - 1).friends = new ArrayList<>();
                newCentroid = CMlist.get(CMlist.size() - 1);
                notNew = false;
            }
        }
        while (change != 0){    //body z povodneho centroidu sa preusporaduvaju medzi stary a novy centroid
            change = 4;
            newBiggestDistance = 0;
            centroid1.friends.clear();
            newCentroid.friends.clear();
            for (Point friends : friendsArrayList){
                double distanceCentroid1 = getDistance(centroid1.X, centroid1.Y, friends.X, friends.Y);
                double distanceCentroid2 = getDistance(newCentroid.X, newCentroid.Y, friends.X, friends.Y);
                if (distanceCentroid1 < distanceCentroid2){
                    centroid1.friends.add(friends);
                    if (distanceCentroid1 > newBiggestDistance){    //ukladame najvacsiu vzdialenostnu odchylku od stredu
                        newBiggestDistance = distanceCentroid1;
                    }
                }
                else{
                    newCentroid.friends.add(friends);
                }

            }
            int X = 0, Y = 0;                           //updatenu sa pozicie centroidov ak sa nezmenili je to finalny stav 
            for (Point point1 : centroid1.friends){
                X += point1.X;
                Y += point1.Y;
            }
            if (centroid1.X != (X / centroid1.friends.size())){
                centroid1.X = (X / centroid1.friends.size());
            }
            else change--;
            if (centroid1.Y != (Y / centroid1.friends.size())){
                centroid1.Y = (Y / centroid1.friends.size());
            }
            else change--;
            X = 0; Y = 0;
            for (Point point2 : newCentroid.friends){
                X += point2.X;
                Y += point2.Y;
            }
            if (newCentroid.X != (X / newCentroid.friends.size())){
                newCentroid.X = (X / newCentroid.friends.size());
            }
            else change--;
            if (newCentroid.Y != (Y / newCentroid.friends.size())){
                newCentroid.Y = (Y / newCentroid.friends.size());
            }
            else change--;
        }
        return newBiggestDistance;
    }

    @Override
    public void clusterAlgorithm(Controller controller) {
        double biggestDistance = 0, actualDistance;
        //vytvori sa novy "centroid" ktoremu budu patrit vsetky body
        CMlist.add(new Point((int)(Math.random() * 10000 - 5000), (int)(Math.random() * 10000 - 5000)));
        CMlist.get(0).friends = new ArrayList<>(controller.getData());
        for (Point friend : CMlist.get(0).friends){
            actualDistance = getDistance(friend.X, friend.Y, CMlist.get(0).X, CMlist.get(0).Y);
            if (actualDistance > biggestDistance){
                biggestDistance = actualDistance;
            }
        }
        int i = 0;
        while (i < CMlist.size()){              //cluster rozdelujem pokial je najvacsia odchylka bodu od centroidu viac ako 500
            biggestDistance = 0;
            for (Point friend : CMlist.get(i).friends){
                if (biggestDistance < getDistance(friend.X, friend.Y, CMlist.get(i).X, CMlist.get(i).Y)){
                    biggestDistance = getDistance(friend.X, friend.Y, CMlist.get(i).X, CMlist.get(i).Y);
                }
            }
            while (biggestDistance > 1000){
                biggestDistance = kmcAlgo(CMlist.get(i));
            }
            i++;
        }
    }
}
