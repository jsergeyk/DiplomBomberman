package gameobject;

import javafx.scene.canvas.GraphicsContext;
import static ua.itea.Game.cellSize;

import java.sql.Time;
import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import ua.itea.Direction;
import ua.itea.FieldsMatrix;
import ua.itea.Game;

/**
 * @author SergeyK
 */
public class Enemy extends Player {
		
	public int score = 5 * Game.level;
	public static Image enemySrc;
	private int speed = (int)1.5 * Game.level;
	private Direction direction = Direction.RIGHT;
	private Random r;
	
	/**
	 * @param x
	 * @param y
	 */
	public Enemy(int x, int y) {
		super(x, y);
		r = new Random();
	}
	
	public void move() {
		super.move(direction);
	}
	
	@Override
	protected int getSpeed() {
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
			direction = Direction.UP;
			break;
		case 1:
			direction = Direction.RIGHT;
			break;
		case 2:
			direction = Direction.LEFT;			
			break;
		case 3:
			direction = Direction.DOWN;
			break;
		default:
			direction = Direction.RIGHT;
			break;
		}
	}
}
