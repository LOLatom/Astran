package com.anonym.astran.helpers;


import java.util.Random;

public class AdvancedMath {
    /*
    Used for naturalising different stuff (mostly shapes like snail shells)
     */
    public static double PHI = 1.61803398875;

    /*
    Mostly used for natural like progression and growth (less natural than PHI)
     */
    public static final double SUPER_GOLDEN_RATIO = 1.839286755214161;

    /*
    Used for fast converging Rarity curves (A natural rarity)
     */
    public static final double RECIPROCAL_FIBONACCI = 3.3598856662431776;

    /*
    High precision double Check , so like basically mostly for physics stuff bla bla bla
     */
    public static double EPSILON = 1e-9;

    /*
    Used for Tiling geometry that has 45°/90° angles aka (Octagon and Square)
    Also Used in audio to determine none-standard Harmonic intervals
     */
    public static final double SILVER_RATIO = 1.0 + Math.sqrt(2.0);

    /*
       Used for Procedural Generation , 3D Modeling / Tiling , Growth Patterns and Timing & Rhythms
     */
    public static final double PLASTIC_RATIO = 1.324717957244746;

    //I got lazy so I stopped writing
    public static final double GOLDEN_ANGLE_RAD = (2.0 * Math.PI) / (PHI * PHI);


    public static final double GOLDEN_ANGLE_DEG = Math.toDegrees(GOLDEN_ANGLE_RAD);


    public static final double MAGIC_ANGLE_RAD = Math.acos(1.0 / Math.sqrt(3.0));


    public static final double MAGIC_ANGLE_DEG = Math.toDegrees(MAGIC_ANGLE_RAD);

    public static final double TRIBONACCI_CONSTANT = 1.839286755214161;

    public static final double VISWANATH_CONSTANT = 1.13198824;

    public static final double EMBREE_TREFETHEN_CONSTANT = 1.1308;

    public static final double LOCHS_CONSTANT = 0.970270;

    public static final double KHINCHIN_CONSTANT = 2.6854520010;

    public static final double EULER_MASCHERONI = 0.5772156649015329;

    public static final double MEISSEL_MERTENS = 0.2614972128;

    public static final double LEGENDRE_CONSTANT = 1.08366;

    public static final double OMEGA_CONSTANT = 0.567143290409783;

    public static final double GELFONDS_CONSTANT = Math.exp(Math.PI);

    public static final double GELFONDS_SCHNEIDER_CONSTANT = Math.pow(2.0, Math.sqrt(2.0));

    public static final double CAHENS_CONSTANT = 0.6434105462927;

    public static final double CATALANS_CONSTANT = 0.915965594;

    public static final double APERY_CONSTANT = 1.202056903159594;

    public static final double WALLIS_CONSTANT = Math.PI / 2.0;

    public static final double SOMO_QUADRATIC_RECURRENCE_CONSTANT = 1.6616879496;




    public static double estimateViswanath(int steps, int trials) {
        Random rand = new Random();
        double logSum = 0.0;
        for (int t = 0; t < trials; t++) {
            double a = 1, b = 1;
            for (int i = 0; i < steps; i++) {
                double next = (rand.nextBoolean() ? 1 : -1) * a +
                        (rand.nextBoolean() ? 1 : -1) * b;
                a = b;
                b = next;
            }
            logSum += Math.log(Math.abs(b));
        }
        return Math.exp(logSum / (steps * trials));
    }

}
