package view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Visualization extends Canvas implements IVisualization {


    private static int customerCount = 0;
    private final GraphicsContext[] gcs;
    private final Color[] backgroundColors;
    private final String[] types;
    double i = 0;
    double j = 10;
    double a = 0;
    double b = 10;

    public Visualization(Canvas[] canvases, Color[] backgroundColors, String[] types) {
        this.types = types;
        this.gcs = new GraphicsContext[canvases.length];
        this.backgroundColors = backgroundColors;
        for (int y = 0; y < canvases.length; y++) {
            this.gcs[y] = canvases[y].getGraphicsContext2D();
        }

        clearScreen();
        for (int y = 0; y < canvases.length; y++) {
            gcs[y].setFill(Color.WHEAT);
            gcs[y].setFont(new javafx.scene.text.Font(20));
            gcs[y].fillText(types[y], 300, 25);
            System.out.println(gcs[y]);
            System.out.println(backgroundColors[y]);
            System.out.println(types[y]);
        }
    }

    public void clearScreen() {

        gcs[0].setFill(backgroundColors[0]);
        gcs[1].setFill(backgroundColors[1]);
        gcs[2].setFill(backgroundColors[2]);
        gcs[3].setFill(backgroundColors[3]);
        gcs[4].setFill(backgroundColors[4]);
        gcs[0].fillRect(0, 0, gcs[0].getCanvas().getWidth(), gcs[0].getCanvas().getHeight());
        gcs[1].fillRect(0, 0, gcs[1].getCanvas().getWidth(), gcs[1].getCanvas().getHeight());
        gcs[2].fillRect(0, 0, gcs[2].getCanvas().getWidth(), gcs[2].getCanvas().getHeight());
        gcs[3].fillRect(0, 0, gcs[3].getCanvas().getWidth(), gcs[3].getCanvas().getHeight());
        gcs[4].fillRect(0, 0, gcs[4].getCanvas().getWidth(), gcs[4].getCanvas().getHeight());
    }

    public void newCustomer() {

        customerCount++;
        

        gcs[0].setFill(Color.LIGHTGRAY);
        gcs[0].fillOval(i,j,10,10);

        i = (i + 10) % gcs[0].getCanvas().getWidth();
        //j = (j + 12) % this.getHeight();
        if (i==0) j+=10;
    }

    public int getCustomerAmount() {
        return customerCount;
    }

    public void removeCustomer() {
        gcs[0].setFill(Color.DARKGRAY);
        gcs[0].fillRect(a,b,10,10);
        a = (a + 10) % gcs[0].getCanvas().getWidth();
        //j = (j + 12) % this.getHeight();
        if (a==0) b+=10;
    }

}