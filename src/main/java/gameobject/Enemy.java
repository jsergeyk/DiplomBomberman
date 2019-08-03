package gameobject;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.util.Random;

import ua.itea.Direction;
import ua.itea.Game;

/**
 * @author SergeyK
 */
public class Enemy extends Player {
		
	public int score = 5 * Game.level;
	public static Image enemySrc;
	private int speed = (int)(1.5 * Game.level);	
	private Random r;
	
	/**
	 * @param x
	 * @param y
	 */
	public Enemy(int x, int y) {
		super(x, y);
		r = new Random();
		setDirection(Direction.UP);
	}
	
	public void move() {
		super.move(speed);
	}
	
	@Override
	public int getSpeed() {
		return speed;
	}

	@Override
	public void draw(GraphicsContext gc) {
		gc.drawImage(enemySrc, x, y, width, height);
	}	
	
	
	/**
	 * Изменить направление движения на случайное
	 */
	@Override
	protected void changeDirection() {
		
		switch (r.nextInt(4)) {
		case 0:
			setDirection(Direction.UP);
			break;
		case 1:
			setDirection(Direction.RIGHT);
			break;
		case 2:
			setDirection(Direction.LEFT);
			break;
		case 3:
			setDirection(Direction.DOWN);
			break;
		default:
			setDirection(Direction.UP);
			break;
		}
	}
}
