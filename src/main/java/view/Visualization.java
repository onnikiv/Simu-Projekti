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
    double i1 = 0;
    double j1 = 10;
    double a1 = 0;
    double b1 = 10;
    double i2 = 0;
    double j2 = 10;
    double a2 = 0;
    double b2 = 10;
    double i3 = 0;
    double j3 = 10;
    double a3 = 0;
    double b3 = 10;
    double i4 = 0;
    double j4 = 10;

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

        for (int d = 0; d<gcs.length; d++) {
            gcs[d].setFill(backgroundColors[d]);
            gcs[d].fillRect(0, 0, gcs[d].getCanvas().getWidth(), gcs[d].getCanvas().getHeight());
        }
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

    public void newCustomer1() {

        gcs[1].setFill(Color.LIGHTGRAY);
        gcs[1].fillOval(i1,j1,10,10);

        i1 = (i1 + 10) % gcs[1].getCanvas().getWidth();
        //j = (j + 12) % this.getHeight();
        if (i1==0) j1+=10;
    }

    public void removeCustomer1() {
        gcs[1].setFill(backgroundColors[1]);
        gcs[1].fillRect(a1,b1,10,10);
        a1 = (a1 + 10) % gcs[1].getCanvas().getWidth();
        //j = (j + 12) % this.getHeight();
        if (a1==0) b1+=10;
    }
    public void newCustomer2() {

        gcs[2].setFill(Color.LIGHTGRAY);
        gcs[2].fillOval(i2,j2,10,10);

        i2 = (i2 + 10) % gcs[2].getCanvas().getWidth();
        //j = (j + 12) % this.getHeight();
        if (i2==0) j2+=10;
    }

    public void removeCustomer2() {
        gcs[2].setFill(backgroundColors[2]);
        gcs[2].fillRect(a2,b2,10,10);
        a2 = (a2 + 10) % gcs[2].getCanvas().getWidth();
        //j = (j + 12) % this.getHeight();
        if (a2==0) b2+=10;
    }
    public void newCustomer3() {

        gcs[3].setFill(Color.LIGHTGRAY);
        gcs[3].fillOval(i3,j3,10,10);

        i3 = (i3 + 10) % gcs[3].getCanvas().getWidth();
        //j = (j + 12) % this.getHeight();
        if (i3==0) j3+=10;
    }

    public void removeCustomer3() {
        gcs[3].setFill(backgroundColors[3]);
        gcs[3].fillRect(a3,b3,10,10);
        a3 = (a3 + 10) % gcs[3].getCanvas().getWidth();
        //j = (j + 12) % this.getHeight();
        if (a3==0) b3+=10;
    }
    public void newCustomer4() {

        gcs[4].setFill(Color.LIGHTGRAY);
        gcs[4].fillOval(i4,j4,10,10);

        i4 = (i4 + 10) % gcs[3].getCanvas().getWidth();
        //j = (j + 12) % this.getHeight();
        if (i4==0) j4+=10;
    }


}