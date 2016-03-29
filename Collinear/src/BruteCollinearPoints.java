import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

    private Point[] pointsArray;
    private LineSegment[] segments = {};

    // finds all line segments containing 4 pointsArray
    public BruteCollinearPoints(Point[] points) {
        checkForExceptions(points);
        pointsArray = new Point[points.length];

        for (int i = 0; i < points.length; i++)
            pointsArray[i] = points[i];

        Arrays.sort(pointsArray);
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        final ArrayList<LineSegment> collinear = findCollinears(pointsArray);
        segments = collinear.toArray(new LineSegment[collinear.size()]);
        final LineSegment[] res = new LineSegment[numberOfSegments()];
        for (int i = 0; i < numberOfSegments(); i++) {
            res[i] = segments[i];
        }
        return res;
    }

    private ArrayList<LineSegment> findCollinears(Point[] points) {
        ArrayList<LineSegment> list = new ArrayList<>();
        System.out.println("------------------");

        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                for (int k = j + 1; k < points.length; k++) {
                    Point p1 = points[i];
                    Point p2 = points[j];
                    Point p3 = points[k];

                    if (p1.slopeTo(p2) == p1.slopeTo(p3)) {
                        for (int q = k + 1; q < points.length; q++) {
                            Point p4 = points[q];
                            if (p1.slopeTo(p3) == p1.slopeTo(p4)) {
                                LineSegment segment = new LineSegment(p1, p4);
                                list.add(segment);
                                break;
                            }
                        }
                        break;
                    }
                }
            }
        }
        return list;
    }

    private void checkForExceptions(Point[] array) {
        if (array == null)
            throw new java.lang.NullPointerException();

        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] == null || array[i + 1] == null)
                throw new NullPointerException();

            if (array[i].equals(array[i + 1]))
                throw new java.lang.IllegalArgumentException();
        }
    }
}
