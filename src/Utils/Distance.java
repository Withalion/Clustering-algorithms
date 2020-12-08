package Utils;

public interface Distance {     //funkcia ktora vyrata vzdialenost 2 bodov
    default double getDistance(int X1, int Y1, int X2, int Y2){
        double sum = Math.pow(X2 - X1, 2);
        sum += Math.pow(Y2 - Y1, 2);
        return Math.sqrt(sum);
    }
}
