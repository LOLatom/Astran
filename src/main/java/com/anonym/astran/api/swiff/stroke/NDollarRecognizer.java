package com.anonym.astran.api.swiff.stroke;// NDollarRecognizer.java
import java.util.*;

/**
 * Clean, idiomatic Java rewrite of the $N Multistroke Recognizer (ported from JS).
 * Single file with nested classes for convenience.
 *
 * Note: default templates are not fully populated here to keep the file a bit shorter.
 * Use addGesture(...) to add templates or paste templates into loadDefaultTemplates().
 */
public class NDollarRecognizer {

    public static class Point {
        public final double x;
        public final double y;
        public Point(double x, double y) { this.x = x; this.y = y; }
        @Override public String toString() { return String.format("Point(%.3f,%.3f)", x, y); }
    }

    public static class Rectangle {
        public final double x, y, width, height;
        public Rectangle(double x, double y, double width, double height) {
            this.x=x; this.y=y; this.width=width; this.height=height;
        }
    }

    public static class Result {
        private final String name;
        private final double score;
        private final long timeMs;
        public Result(String name, double score, long timeMs) {
            this.name=name; this.score=score; this.timeMs=timeMs;
        }
        public String getName() { return name; }
        public double getScore() { return score; }
        public long getTimeMs() { return timeMs; }
    }

    private static class Unistroke {
        final String name;
        final List<Point> points;
        final Point startUnitVector;
        final double[] vector;

        Unistroke(String name, boolean useBoundedRotationInvariance, List<Point> pointsIn, int numPoints, double squareSize, double oneDThreshold, int startAngleIndex) {
            this.name = name;
            List<Point> pts = resample(pointsIn, numPoints);
            double radians = indicativeAngle(pts);
            pts = rotateBy(pts, -radians);
            pts = scaleDimTo(pts, squareSize, oneDThreshold);
            if (useBoundedRotationInvariance) {
                pts = rotateBy(pts, +radians);
            }
            pts = translateTo(pts, new Point(0,0));
            this.points = pts;
            this.startUnitVector = calcStartUnitVector(pts, startAngleIndex);
            this.vector = vectorize(pts, useBoundedRotationInvariance);
        }
    }

    private static class Multistroke {
        final String name;
        final int numStrokes;
        final List<Unistroke> unistrokes;

        Multistroke(String name, boolean useBoundedRotationInvariance, List<List<Point>> strokes, int numPoints, double squareSize, double oneDThreshold, int startAngleIndex) {
            this.name = name;
            this.numStrokes = strokes.size();

            List<int[]> orders = new ArrayList<>();
            int[] baseOrder = new int[strokes.size()];
            for (int i=0;i<baseOrder.length;i++) baseOrder[i]=i;
            heapPermute(strokes.size(), baseOrder, orders);

            List<List<Point>> uniPointLists = makeUnistrokes(strokes, orders);

            this.unistrokes = new ArrayList<>(uniPointLists.size());
            for (List<Point> up : uniPointLists) {
                this.unistrokes.add(new Unistroke(name, useBoundedRotationInvariance, up, numPoints, squareSize, oneDThreshold, startAngleIndex));
            }
        }
    }

    private final int numPoints;
    private final double squareSize;
    private final double oneDThreshold;
    private final Point origin = new Point(0,0);
    private final double diagonal;
    private final double halfDiagonal;
    private final double angleRange;
    private final double anglePrecision;
    private final double phi;
    private final int startAngleIndex;
    private final double angleSimilarityThreshold;

    private final List<Multistroke> multistrokes = new ArrayList<>();

    public NDollarRecognizer(boolean useBoundedRotationInvariance) {
        this.numPoints = 96;
        this.squareSize = 250.0;
        this.oneDThreshold = 0.25;
        this.diagonal = Math.sqrt(squareSize * squareSize + squareSize * squareSize);
        this.halfDiagonal = 0.5 * diagonal;
        this.angleRange = deg2rad(45.0);
        this.anglePrecision = deg2rad(2.0);
        this.phi = 0.5 * (-1.0 + Math.sqrt(5.0));
        this.startAngleIndex = (numPoints / 8);
        this.angleSimilarityThreshold = deg2rad(30.0);
    }

    /**
     * Recognize a gesture represented as a list of strokes, where each stroke is a List<Point>.
     *
     * @param strokes list of strokes (each stroke = list of Points)
     * @param useBoundedRotationInvariance passed to candidate vectorization (Protractor)
     * @param requireSameNoOfStrokes if true, only match templates with same number of component strokes
     * @param useProtractor whether to use Protractor (optimal cosine distance) instead of distance-at-best-angle
     * @return Result (name, score in [0..1], time in ms)
     */
    public Result recognize(List<List<Point>> strokes, boolean useBoundedRotationInvariance, boolean requireSameNoOfStrokes, boolean useProtractor) {
        long t0 = System.currentTimeMillis();
        List<Point> points = combineStrokes(strokes);
        Unistroke candidate = new Unistroke("", useBoundedRotationInvariance, points, numPoints, squareSize, oneDThreshold, startAngleIndex);

        int bestTemplateIndex = -1;
        double bestDistance = Double.POSITIVE_INFINITY;

        for (int i=0;i<multistrokes.size();i++) {
            Multistroke m = multistrokes.get(i);
            if (!requireSameNoOfStrokes || strokes.size() == m.numStrokes) {
                for (Unistroke u : m.unistrokes) {
                    if (angleBetweenUnitVectors(candidate.startUnitVector, u.startUnitVector) <= angleSimilarityThreshold) {
                        double d;
                        if (useProtractor) {
                            d = optimalCosineDistance(u.vector, candidate.vector);
                        } else {
                            d = distanceAtBestAngle(candidate.points, u, -angleRange, +angleRange, anglePrecision);
                        }
                        if (d < bestDistance) {
                            bestDistance = d;
                            bestTemplateIndex = i;
                        }
                    }
                }
            }
        }
        long t1 = System.currentTimeMillis();
        if (bestTemplateIndex == -1) {
            return new Result("No match.", 0.0, t1 - t0);
        } else {
            Multistroke found = multistrokes.get(bestTemplateIndex);
            double score = useProtractor ? (1.0 - bestDistance) : (1.0 - bestDistance / halfDiagonal);
            return new Result(found.name, score, t1 - t0);
        }
    }

    /**
     * Adds a user gesture template.
     * @param name name of the gesture
     * @param useBoundedRotationInvariance parameter used when creating unistrokes for this template
     * @param strokes list of strokes, each stroke is a list of Points
     * @return number of templates with the given name (useful to track duplicates)
     */
    public int addGesture(String name, boolean useBoundedRotationInvariance, List<List<Point>> strokes) {
        Multistroke m = new Multistroke(name, useBoundedRotationInvariance, strokes, numPoints, squareSize, oneDThreshold, startAngleIndex);
        multistrokes.add(m);
        int count = 0;
        for (Multistroke mm : multistrokes) if (mm.name.equals(name)) count++;
        return count;
    }

    /**
     * Deletes any user gestures beyond an initial number (if you want to restore some baseline).
     * Here we clear all templates; user code can call loadDefaultTemplates() afterwards.
     * @return number of templates left (0)
     */
    public int deleteAllGestures() {
        multistrokes.clear();
        return multistrokes.size();
    }

    /**
     * Example loader which adds a couple of default templates.
     * Paste the rest of your templates here (converted to List<List<Point>>) if you want them prepopulated.
     */
    public void loadDefaultTemplates() {
        addGesture("line", false, Arrays.asList(
                Arrays.asList(new Point(12,347), new Point(119,347))
        ));

        addGesture("T", false, Arrays.asList(
                Arrays.asList(new Point(30,7), new Point(103,7)),
                Arrays.asList(new Point(66,7), new Point(66,87))
        ));

        addGesture("N", false, Arrays.asList(
                Arrays.asList(new Point(177,92),new Point(177,2)),
                Arrays.asList(new Point(182,1),new Point(246,95)),
                Arrays.asList(new Point(247,87),new Point(247,1))
        ));
        addGesture("Circle", false, Arrays.asList(
                Arrays.asList(new Point(127,141),new Point(124,140),new Point(120,139),new Point(118,139),
                        new Point(116,139),new Point(111,140),new Point(109,141),new Point(104,144),
                        new Point(100,147), new Point(96,152),new Point(93,157),new Point(90,163),
                        new Point(87,169),new Point(85,175), new Point(83,181),new Point(82,190),
                        new Point(82,195),new Point(83,200),new Point(84,205), new Point(88,213),
                        new Point(91,216),new Point(96,219),new Point(103,222),new Point(108,224),
                        new Point(111,224),new Point(120,224),new Point(133,223),new Point(142,222),
                        new Point(152,218), new Point(160,214),new Point(167,210),new Point(173,204),
                        new Point(178,198),new Point(179,196), new Point(182,188),new Point(182,177),
                        new Point(178,167),new Point(170,150),new Point(163,138), new Point(152,130),
                        new Point(143,129),new Point(140,131),new Point(129,136),new Point(126,139))
        ));


    }


    private static List<Point> combineStrokes(List<List<Point>> strokes) {
        List<Point> points = new ArrayList<>();
        for (List<Point> s : strokes) {
            for (Point p : s) {
                points.add(new Point(p.x, p.y));
            }
        }
        return points;
    }

    private static List<Point> resample(List<Point> points, int n) {
        double I = pathLength(points) / (n - 1);
        double D = 0.0;
        List<Point> newPoints = new ArrayList<>();
        newPoints.add(points.get(0));
        List<Point> pts = new ArrayList<>(points);
        int i = 1;
        while (i < pts.size()) {
            Point prev = pts.get(i - 1);
            Point curr = pts.get(i);
            double d = distance(prev, curr);
            if ((D + d) >= I) {
                double qx = prev.x + ((I - D) / d) * (curr.x - prev.x);
                double qy = prev.y + ((I - D) / d) * (curr.y - prev.y);
                Point q = new Point(qx, qy);
                newPoints.add(q);
                pts.add(i, q);
                D = 0.0;
                i++;
            } else {
                D += d;
                i++;
            }
        }
        if (newPoints.size() == n - 1) {
            Point last = pts.get(pts.size() - 1);
            newPoints.add(new Point(last.x, last.y));
        }
        return newPoints;
    }

    private static double indicativeAngle(List<Point> points) {
        Point c = centroid(points);
        return Math.atan2(c.y - points.get(0).y, c.x - points.get(0).x);
    }

    private static List<Point> rotateBy(List<Point> points, double radians) {
        Point c = centroid(points);
        double cos = Math.cos(radians), sin = Math.sin(radians);
        List<Point> newPoints = new ArrayList<>(points.size());
        for (Point p : points) {
            double qx = (p.x - c.x) * cos - (p.y - c.y) * sin + c.x;
            double qy = (p.x - c.x) * sin + (p.y - c.y) * cos + c.y;
            newPoints.add(new Point(qx, qy));
        }
        return newPoints;
    }

    private static List<Point> scaleDimTo(List<Point> points, double size, double ratio1D) {
        Rectangle B = boundingBox(points);
        boolean uniformly = Math.min(B.width / B.height, B.height / B.width) <= ratio1D;
        List<Point> newPoints = new ArrayList<>(points.size());
        double max = Math.max(B.width, B.height);
        for (Point p : points) {
            double qx = uniformly ? p.x * (size / max) : p.x * (size / B.width);
            double qy = uniformly ? p.y * (size / max) : p.y * (size / B.height);
            newPoints.add(new Point(qx, qy));
        }
        return newPoints;
    }

    private static List<Point> translateTo(List<Point> points, Point pt) {
        Point c = centroid(points);
        List<Point> newPoints = new ArrayList<>(points.size());
        for (Point p : points) {
            double qx = p.x + pt.x - c.x;
            double qy = p.y + pt.y - c.y;
            newPoints.add(new Point(qx, qy));
        }
        return newPoints;
    }

    private static double[] vectorize(List<Point> points, boolean useBoundedRotationInvariance) {
        double cos = 1.0, sin = 0.0;
        if (useBoundedRotationInvariance) {
            double iAngle = Math.atan2(points.get(0).y, points.get(0).x);
            double baseOrientation = (Math.PI / 4.0) * Math.floor((iAngle + Math.PI / 8.0) / (Math.PI / 4.0));
            cos = Math.cos(baseOrientation - iAngle);
            sin = Math.sin(baseOrientation - iAngle);
        }
        double sum = 0.0;
        double[] vector = new double[points.size() * 2];
        int idx = 0;
        for (Point p : points) {
            double newX = p.x * cos - p.y * sin;
            double newY = p.y * cos + p.x * sin;
            vector[idx++] = newX;
            vector[idx++] = newY;
            sum += newX * newX + newY * newY;
        }
        double magnitude = Math.sqrt(sum);
        for (int i=0;i<vector.length;i++) vector[i] /= magnitude;
        return vector;
    }

    private double optimalCosineDistance(double[] v1, double[] v2) {
        double a = 0.0, b = 0.0;
        for (int i=0;i<v1.length;i+=2) {
            a += v1[i] * v2[i] + v1[i+1] * v2[i+1];
            b += v1[i] * v2[i+1] - v1[i+1] * v2[i];
        }
        double angle = Math.atan2(b, a);
        return Math.acos(a * Math.cos(angle) + b * Math.sin(angle));
    }

    private double distanceAtBestAngle(List<Point> points, Unistroke T, double a, double b, double threshold) {
        double x1 = phi * a + (1.0 - phi) * b;
        double f1 = distanceAtAngle(points, T, x1);
        double x2 = (1.0 - phi) * a + phi * b;
        double f2 = distanceAtAngle(points, T, x2);
        while (Math.abs(b - a) > threshold) {
            if (f1 < f2) {
                b = x2;
                x2 = x1;
                f2 = f1;
                x1 = phi * a + (1.0 - phi) * b;
                f1 = distanceAtAngle(points, T, x1);
            } else {
                a = x1;
                x1 = x2;
                f1 = f2;
                x2 = (1.0 - phi) * a + phi * b;
                f2 = distanceAtAngle(points, T, x2);
            }
        }
        return Math.min(f1, f2);
    }

    private double distanceAtAngle(List<Point> points, Unistroke T, double radians) {
        List<Point> newPoints = rotateBy(points, radians);
        return pathDistance(newPoints, T.points);
    }

    private static Point centroid(List<Point> points) {
        double x = 0.0, y = 0.0;
        for (Point p : points) { x += p.x; y += p.y; }
        x /= points.size(); y /= points.size();
        return new Point(x,y);
    }

    private static Rectangle boundingBox(List<Point> points) {
        double minX = Double.POSITIVE_INFINITY, maxX = Double.NEGATIVE_INFINITY,
               minY = Double.POSITIVE_INFINITY, maxY = Double.NEGATIVE_INFINITY;
        for (Point p : points) {
            minX = Math.min(minX, p.x);
            minY = Math.min(minY, p.y);
            maxX = Math.max(maxX, p.x);
            maxY = Math.max(maxY, p.y);
        }
        return new Rectangle(minX, minY, maxX - minX, maxY - minY);
    }

    private double pathDistance(List<Point> pts1, List<Point> pts2) {
        double d = 0.0;
        for (int i=0;i<pts1.size();i++) d += distance(pts1.get(i), pts2.get(i));
        return d / pts1.size();
    }

    private static double pathLength(List<Point> points) {
        double d = 0.0;
        for (int i=1;i<points.size();i++) d += distance(points.get(i-1), points.get(i));
        return d;
    }

    private static double distance(Point p1, Point p2) {
        double dx = p2.x - p1.x;
        double dy = p2.y - p1.y;
        return Math.sqrt(dx*dx + dy*dy);
    }

    private static Point calcStartUnitVector(List<Point> points, int index) {
        Point v = new Point(points.get(index).x - points.get(0).x, points.get(index).y - points.get(0).y);
        double len = Math.sqrt(v.x * v.x + v.y * v.y);
        return new Point(v.x / len, v.y / len);
    }

    private double angleBetweenUnitVectors(Point v1, Point v2) {
        double n = (v1.x * v2.x + v1.y * v2.y);
        double c = Math.max(-1.0, Math.min(1.0, n));
        return Math.acos(c);
    }

    private static void heapPermute(int n, int[] order, List<int[]> ordersOut) {
        if (n == 1) {
            ordersOut.add(order.clone());
        } else {
            for (int i=0;i<n;i++) {
                heapPermute(n-1, order, ordersOut);
                if (n % 2 == 1) {
                    int tmp = order[0]; order[0] = order[n-1]; order[n-1] = tmp;
                } else {
                    int tmp = order[i]; order[i] = order[n-1]; order[n-1] = tmp;
                }
            }
        }
    }

    private static List<List<Point>> makeUnistrokes(List<List<Point>> strokes, List<int[]> orders) {
        List<List<Point>> unistrokes = new ArrayList<>();
        for (int[] ord : orders) {
            int combinations = 1 << ord.length;
            for (int b=0;b<combinations;b++) {
                List<Point> unistroke = new ArrayList<>();
                for (int i=0;i<ord.length;i++) {
                    List<Point> pts = strokes.get(ord[i]);
                    if (((b >> i) & 1) == 1) {
                        List<Point> copy = new ArrayList<>(pts);
                        Collections.reverse(copy);
                        for (Point p : copy) unistroke.add(new Point(p.x, p.y));
                    } else {
                        for (Point p : pts) unistroke.add(new Point(p.x, p.y));
                    }
                }
                unistrokes.add(unistroke);
            }
        }
        return unistrokes;
    }

    private static double deg2rad(double d) { return (d * Math.PI / 180.0); }
}
