package view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Visualization2 extends Canvas implements IVisualization {

	private GraphicsContext gc;

	int customerCount = 0;

	public Visualization2(int w, int h) {
		super(w, h);
		gc = this.getGraphicsContext2D();
		clearScreen();
	}


	public void clearScreen() {
		gc.setFill(Color.YELLOW);
		gc.fillRect(0, 0, this.getWidth(), this.getHeight());
	}

	@Override
	public void newCustomer(int customer) {

	}

	public void newCustomer() {

		customerCount++;

		gc.setFill(Color.YELLOW);
		gc.fillRect(100,80, 100, 20);
		gc.setFill(Color.RED);
		gc.setFont(new Font(20));
		gc.fillText("Asiakas " + customerCount, 100, 100);

	}
	public void removeCustomer() {

		gc.fillRect(100,80, 100, 20);
		gc.setFill(Color.RED);
		gc.setFont(new Font(20));
		gc.fillText("Asiakas " + customerCount, 100, 100);

	}


	public int getCustomerAmount() {
		return 0;
	}

	@Override
	public void removeCustomer(int customer) {

	}


}