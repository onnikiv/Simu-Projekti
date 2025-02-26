package view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Visualization extends Canvas implements IVisualization {


    private static int customerCount = 0;

    private final GraphicsContext gc;
    double i = 0;
    double j = 10;
    double a = 0;
    double b = 10;

    public Visualization(Canvas canvas) {
        this.gc = canvas.getGraphicsContext2D();
        clearScreen();
    }

    public void clearScreen() {

        gc.setFill(Color.DARKGRAY);
        gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
    }

    public void newCustomer() {

        customerCount++;
        

        gc.setFill(Color.LIGHTGRAY);
        gc.fillOval(i,j,10,10);

        i = (i + 10) % gc.getCanvas().getWidth();
        //j = (j + 12) % this.getHeight();
        if (i==0) j+=10;
    }

    public int getCustomerAmount() {
        return customerCount;
    }

    public void removeCustomer() {
        gc.setFill(Color.DARKGRAY);
        gc.fillOval(a,b,10,10);
        a = (a + 10) % gc.getCanvas().getWidth();
        //j = (j + 12) % this.getHeight();
        if (a==0) b+=10;
    }

}