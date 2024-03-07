package exercitiul1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Parabola {
    private int a, b, c;

    public Parabola(int a, int b, int c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public int[] vertex() {
        int x_vertex = -b / (2 * a);
        int y_vertex = a * x_vertex * x_vertex + b * x_vertex + c;
        return new int[]{x_vertex, y_vertex};
    }
    public String toString() {
        return "f(x) = " + a + "x^2 + " + b + "x + " + c;
    }

    public static int[] midpoint(Parabola p1, Parabola p2) {
        int[] vertex1 = p1.vertex();
        int[] vertex2 = p2.vertex();
        int x_midpoint = (vertex1[0] + vertex2[0]) / 2;
        int y_midpoint = (vertex1[1] + vertex2[1]) / 2;
        return new int[]{x_midpoint, y_midpoint};
    }

    public static double distance(Parabola p1, Parabola p2) {
        int[] vertex1 = p1.vertex();
        int[] vertex2 = p2.vertex();
        double distance = Math.hypot(vertex1[0] - vertex2[0], vertex1[1] - vertex2[1]);
        return distance;
    }

    public static double segmentLength(Parabola p1, Parabola p2) {
        int[] vertex1 = p1.vertex();
        int[] vertex2 = p2.vertex();
        double length = Math.hypot(vertex1[0] - vertex2[0], vertex1[1] - vertex2[1]);
        return length;
    }
}

public class Main {
    public static void main(String[] args) {
        List<Parabola> parabolas = new ArrayList<>();


        try (BufferedReader br = new BufferedReader(new FileReader("src\\exercitiul1\\in.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] coeffs = line.split(" ");
                int a = Integer.parseInt(coeffs[0]);
                int b = Integer.parseInt(coeffs[1]);
                int c = Integer.parseInt(coeffs[2]);
                parabolas.add(new Parabola(a, b, c));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        for (Parabola p : parabolas) {
            System.out.println(p);
            int[] vertex = p.vertex();
            System.out.println("Vârful parabolei: (" + vertex[0] + ", " + vertex[1] + ")");
        }


        for (int i = 0; i < parabolas.size() - 1; i++) {
            Parabola p1 = parabolas.get(i);
            Parabola p2 = parabolas.get(i + 1);
            int[] midpoint = Parabola.midpoint(p1, p2);
            double distance = Parabola.distance(p1, p2);
            System.out.println("Mijlocul segmentului dintre parabolele " + (i + 1) + " și " + (i + 2) + ": (" + midpoint[0] + ", " + midpoint[1] + ")");
            System.out.println("Lungimea segmentului dintre parabolele " + (i + 1) + " și " + (i + 2) + ": " + distance);
        }
    }
}