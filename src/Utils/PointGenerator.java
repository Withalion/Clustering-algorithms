package Utils;

import java.util.ArrayList;

public class PointGenerator {
    public ArrayList<Point> pointList;

    public void createDataSet(){
        pointList = new ArrayList<>();
        for (int i = 0; pointList.size() != 20 ;i++) {   //vytvori sa 20 unikatnych bodov so suradnicami X a Y v rozmedzi -5000 az +5000
            Point newPoint = new Point((int) (Math.random() * 10000 - 5000), (int) (Math.random() * 10000 - 5000));
            if (i == 0) pointList.add(newPoint);
            else {
                for (int j = 0; j < pointList.size(); j++) {
                    if (newPoint.equals(pointList.get(j))) break;
                    else if (j == pointList.size() - 1) pointList.add(newPoint);
                }
            }
        }
        for (int i = 0; i < 40000; i++){    //vytvori sa dalsich 40 000 bodov s tym ze sa vyberie uz vytvoreny bod vypocita sa suradnicovy offset a prida novy bod
            int pickedIndex = (int) (Math.random()*pointList.size()), offsetX = (int)(Math.random() * 200 - 100), offsetY = (int)(Math.random() * 200 - 100);
            pointList.add(new Point(pointList.get(pickedIndex).X + offsetX, pointList.get(pickedIndex).Y + offsetY));
        }
    }

}
