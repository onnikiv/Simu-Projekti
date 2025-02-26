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
        gcs[0].fillOval(i[0],j[0],10,10);

        i[0] = (i[0] + 10) % gcs[0].getCanvas().getWidth();
        //j = (j + 12) % this.getHeight();
        if (i[0]==0) j[0]+=10;
    }

    public int getCustomerAmount() {
        return customerCount;
    }

    public void newCustomer(int customer) {
        gcs[customer].setFill(Color.LIGHTGRAY);
        gcs[customer].fillOval(i[customer],j[customer],10,10);

        i[customer] = (i[customer] + 10) % gcs[0].getCanvas().getWidth();
        //j = (j + 12) % this.getHeight();
        if (i[customer]==0) j[customer]+=10;
    }

    public void removeCustomer() {
        gcs[0].setFill(Color.DARKGRAY);
        gcs[0].fillRect(a[0],b[0],10,10);
        a[0] = (a[0] + 10) % gcs[0].getCanvas().getWidth();
        //j = (j + 12) % this.getHeight();
        if (a[0]==0) b[0]+=10;
    }

    public void newCustomer1() {

        gcs[1].setFill(Color.LIGHTGRAY);
        gcs[1].fillOval(i[1],j[1],10,10);

        i[1] = (i[1] + 10) % gcs[1].getCanvas().getWidth();
        //j = (j + 12) % this.getHeight();
        if (i[1]==0) j[1]+=10;
    }

    public void removeCustomer1() {
        gcs[1].setFill(backgroundColors[1]);
        gcs[1].fillRect(a[1],b[1],10,10);
        a[1] = (a[1] + 10) % gcs[1].getCanvas().getWidth();
        //j = (j + 12) % this.getHeight();
        if (a[1]==0) b[1]+=10;
    }
    public void newCustomer2() {

        gcs[2].setFill(Color.LIGHTGRAY);
        gcs[2].fillOval(i[2],j[2],10,10);

        i[2]= (i[2] + 10) % gcs[2].getCanvas().getWidth();
        //j = (j + 12) % this.getHeight();
        if (i[2]==0) j[2]+=10;
    }

    public void removeCustomer2() {
        gcs[2].setFill(backgroundColors[2]);
        gcs[2].fillRect(a[2],b[2],10,10);
        a[2] = (a[2] + 10) % gcs[2].getCanvas().getWidth();
        //j = (j + 12) % this.getHeight();
        if (a[2]==0) b[2]+=10;
    }
    public void newCustomer3() {

        gcs[3].setFill(Color.LIGHTGRAY);
        gcs[3].fillOval(i[3],j[3],10,10);

        i[3] = (i[3] + 10) % gcs[3].getCanvas().getWidth();
        //j = (j + 12) % this.getHeight();
        if (i[3] == 0) j[3] += 10;
    }

    public void removeCustomer3() {
        gcs[3].setFill(backgroundColors[3]);
        gcs[3].fillRect(a[3],b[3],10,10);
        a[3] = (a[3] + 10) % gcs[3].getCanvas().getWidth();
        //j = (j + 12) % this.getHeight();
        if (a[3] == 0) b[3] += 10;
    }
    public void newCustomer4() {

        gcs[4].setFill(Color.LIGHTGRAY);
        gcs[4].fillOval(i[4],j[4],10,10);

        i[4] = (i[4] + 10) % gcs[3].getCanvas().getWidth();
        //j = (j + 12) % this.getHeight();
        if (i[4]==0) j[4]+=10;
    }

}