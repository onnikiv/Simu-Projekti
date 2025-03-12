package view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * The Visualization class provides methods to visualize the simulation.
 * It extends the Canvas class and implements the IVisualization interface.
 */

public class Visualization extends Canvas implements IVisualization {

    private final GraphicsContext[] gcs;
    private final Color[] backgroundColors;
    private final String[] types;
    private final double[] i, j, a, b;

    /**
     * Constructs a Visualization with the specified canvases, background colors, and types.
     *
     * @param canvases         the canvases to visualize
     * @param backgroundColors the background colors of the canvases
     * @param types            the types of the canvases
     */

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

    /**
     * Clears the screen by filling the canvases with the background colors.
     * The method is synchronized to prevent multiple threads from accessing the screen at the same time.
     */

    @Override
    public synchronized void clearScreen() {

        for (int d = 0; d < gcs.length; d++) {
            gcs[d].setFill(backgroundColors[d]);
            gcs[d].fillRect(0, 0, gcs[d].getCanvas().getWidth(), gcs[d].getCanvas().getHeight());
        }
    }

    /**
     * Visualizes a new customer by filling a circle on the canvas.
     * The method is synchronized to prevent multiple threads from accessing the screen at the same time.
     *
     * @param customer the customer to visualize
     */

    @Override
    public synchronized void newCustomer(int customer, int listSize) {
        if (customer == 0 || customer == 5) {
            gcs[customer].setFill(Color.LIGHTGRAY);
            double canvasWidth = gcs[customer].getCanvas().getWidth();
            double canvasHeight = gcs[customer].getCanvas().getHeight();
            double ovalSize = 40;
            double x = (canvasWidth - ovalSize) / 2;
            double y = (canvasHeight - ovalSize) / 2;
            gcs[customer].fillOval(x, y, ovalSize, ovalSize); // Draw a centered bigger oval
        } else {
            gcs[customer].setFill(Color.LIGHTGRAY);
            for (int k = 0; k < listSize; k++) {
                gcs[customer].fillOval(i[customer], j[customer], 10, 10);
                j[customer] = (j[customer] + 10) % (gcs[customer].getCanvas().getHeight() - 180);
                if (j[customer] == 0) i[customer] += 10;
            }
        }
    }

    /**
     * Removes a customer by filling a rectangle on the canvas.
     * The method is synchronized to prevent multiple threads from accessing the screen at the same time.
     *
     * @param customer the customer to remove
     */

    @Override
    public synchronized void removeCustomer(int customer, int listSize) {
        if (customer == 0 || customer == 5) {
            clearCustomer(customer);
        } else {
            gcs[customer].setFill(backgroundColors[customer]);
            for (int k = 0; k < listSize; k++) {
                gcs[customer].fillRect(a[customer], b[customer], 10, 10);
                b[customer] = (b[customer] + 10) % (gcs[customer].getCanvas().getHeight() - 180);
                if (b[customer] == 0) a[customer] += 10;
            }
        }
        resetPositions();
    }

    private synchronized void clearCustomer(int customer) {
        gcs[customer].setFill(backgroundColors[customer]);
        gcs[customer].fillRect(0, 0, gcs[customer].getCanvas().getWidth(), gcs[customer].getCanvas().getHeight());
    }

    public synchronized void resetPositions() {
        for (int y = 0; y < gcs.length; y++) {
            i[y] = 0;
            j[y] = 10;
            a[y] = 0;
            b[y] = 10;
        }
    }
}