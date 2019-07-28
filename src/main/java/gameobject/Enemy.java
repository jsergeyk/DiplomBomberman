package gameobject;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * @author SergeyK
 */
public class Enemy extends Player {
	
	//private boolean isAlive = true;
	public int score = 5;
	public static Image enemySrc;
	
	/**
	 * @param x
	 * @param y
	 */
	public Enemy(int x, int y) {
		super(x, y);
	}

	/*public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}*/
	
	@Override
	public void draw(GraphicsContext gc) {
		gc.drawImage(enemySrc, x, y, width, height);
	}
}
