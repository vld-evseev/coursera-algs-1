import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.*;

public class FastCollinearPoints {

    private Point[] pointsArray;
    private LineSegment[] segments = {};
    private Set<Line> lineSet = new HashSet<>();

    // finds all line segments containing 4 or more pointsArray
    public FastCollinearPoints(Point[] points) {
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
        Point[] copyArray = Arrays.copyOf(points, points.length);

        for (int i = 0; i < points.length; i++) {
            Point origin = points[i];
            Arrays.sort(copyArray, origin.slopeOrder());

            ArrayList<Point> groupedBySlope = new ArrayList<>();

            for (int j = 0; j < copyArray.length - 1; j++) {
                Point q = copyArray[j];
                Point p = copyArray[j + 1];

                double slopeQ = origin.slopeTo(q);
                double slopeP = origin.slopeTo(p);

                if (slopeP == slopeQ) {
                    if (!groupedBySlope.contains(p) && !p.equals(origin))
                        groupedBySlope.add(p);

                    if (!groupedBySlope.contains(q) && !q.equals(origin))
                        groupedBySlope.add(q);
                } else {
                    if (groupedBySlope.size() >= 3) {
                        groupedBySlope.add(origin);
                        Collections.sort(groupedBySlope);
                        Point min = groupedBySlope.get(0);
                        Point max = groupedBySlope.get(groupedBySlope.size() - 1);
                        Line line = new Line(min, max);
                        if (!addedTo(lineSet, line))
                            lineSet.add(line);
                    }

                    if (!groupedBySlope.isEmpty())
                        groupedBySlope.clear();
                }
            }
        }

        for (Line line : lineSet) {
            list.add(new LineSegment(line.getX(), line.getY()));
        }

        return list;
    }

    private boolean addedTo(Set<Line> set, Line line) {
        for (Line val : set) {
            if (val.equals(line))
                return true;
        }
        return false;
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

    private class Line {
        private Point x;
        private Point y;

        Line(Point x, Point y) {
            if (x == null || y == null)
                throw new java.lang.NullPointerException();

            this.x = x;
            this.y = y;
        }

        public Point getX() {
            return x;
        }

        public Point getY() {
            return y;
        }

        @Override
        public boolean equals(Object obj) {
            Line that = (Line) obj;
            return this.x == that.x && this.y == that.y;
        }
    }

    public static void main(String[] args) {
        // read the N points from a file
        String path = "C:\\Users\\Vlad\\Documents\\coursera-algs-1\\Collinear\\collinear-testing\\kw1260.txt";
        In in = new In(path);
        int N = in.readInt();
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        edu.princeton.cs.algs4.StdDraw.show(0);
        edu.princeton.cs.algs4.StdDraw.setXscale(0, 32768);
        edu.princeton.cs.algs4.StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }

    }
}
