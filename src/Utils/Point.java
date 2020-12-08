package Utils;

import java.util.ArrayList;

public class Point {
    public int X, Y;
    public ArrayList<Point> friends;
    //vytvorenie bodu v rozmedziach nasho 2D priestoru
    public Point(int X, int Y){
        if (X > 5000) this.X = 5000;
        else if (X < -5000) this.X = -5000;
        else {
            this.X = X;
        }
        if (Y > 5000) this.Y = 5000;
        else if (Y < -5000) this.Y = -5000;
        else {
            this.Y = Y;
        }
        friends = null;
    }
}
