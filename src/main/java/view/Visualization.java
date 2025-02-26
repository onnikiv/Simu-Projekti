package view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Visualization extends Canvas implements IVisualization {


    private static int customerCount = 0;
    private final GraphicsContext[] gcs;
    private final Color[] backgroundColors;
    private final String[] types;
    private final double[] i, j, a, b;

    public Visualization(Canvas[] canvases, Color[] backgroundColors, String[] types) {
        this.types = types;
        this.gcs = new GraphicsContext[canvases.length];
        this.backgroundColors = backgroundColors;
        this.i = new double[canvases.length];
        this.j = new double[canvases.length];
        this.a = new double[canvases.length];
        this.b = new double[canvases.length];

        for (int y = 0; y < canvases.length; y++) {
            this.gcs[y] = canvases[y].getGraphicsContext2D();
            this.i[y] = 0;
            this.j[y] = 10;
            this.a[y] = 0;
            this.b[y] = 10;
        }

        clearScreen();
        for (int y = 0; y < canvases.length; y++) {
            gcs[y].setFill(Color.WHEAT);
            gcs[y].setFont(new javafx.scene.text.Font(20));
            gcs[y].fillText(types[y], 300, 25);
        }
    }

    public void clearScreen() {

        for (int d = 0; d<gcs.length; d++) {
            gcs[d].setFill(backgroundColors[d]);
            gcs[d].fillRect(0, 0, gcs[d].getCanvas().getWidth(), gcs[d].getCanvas().getHeight());
        }
    }

    public int getCustomerAmount() {
        return customerCount;
    }

    public void newCustomer(int customer) {
        customerCount++;
        gcs[customer].setFill(Color.LIGHTGRAY);
        gcs[customer].fillOval(i[customer], j[customer], 10, 10);
        i[customer] = (i[customer] + 10) % (gcs[customer].getCanvas().getWidth()-180);
        if (i[customer] == 0) j[customer] += 10;
    }

    public void removeCustomer(int customer) {
        gcs[customer].setFill(backgroundColors[customer]);
        gcs[customer].fillRect(a[customer], b[customer], 10, 10);
        a[customer] = (a[customer] + 10) % (gcs[customer].getCanvas().getWidth()-180);
        if (a[customer] == 0) b[customer] += 10;
    }

}