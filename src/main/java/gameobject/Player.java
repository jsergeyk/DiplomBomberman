package gameobject;

import static ua.itea.Game.cellSize;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import ua.itea.Direction;
import ua.itea.Drawable;
import static ua.itea.Game.currentField;

/**
 * @author Admin
 */
public class Player extends GameObject implements Drawable{ 

	public static Image playerSrc;
	private int speed = 5;
    private Direction direction = Direction.NONE;
    
    /**
     * Указывает на передвижение персонажа  по оси X.
     * 1 - движение вправо, -1 - движение влево, 0 - нет движения.
     */
    //public int directionX = 0;
     /**
     * Указывает на передвижение персонажа  по оси Y.
     * 1 - движение вниз, -1 - движение вверх, 0 - нет движения.
     */
    //public int directionY = 0;
    
    /**
     * Размер персонажа по ширине.
     * Нужно знать для вычисления возможности прохождения по карте.
     */
    public int width = 25;
    
    /**
     * Размер персонажа по высоте.
     * Нужно знать для вычисления возможности прохождения по карте.
     */
    public int height = 25;
    
    /**
     * Поле хранит путь движения персонажа.
     */
   // public MapPath mapPath;
    
    public Player(int x, int y) {
    	super(x, y);
    }
    
    @Override
    public void draw(GraphicsContext gc) {
    	gc.drawImage(playerSrc, x, y, width, height);
    }

	/**
	 * @param direction направление движения
	 */
	public void move(int speed) {
		if (direction == Direction.LEFT) {
        	int topleft = currentField[y / cellSize] [(x -speed) / cellSize];
        	int downleft = currentField[(y + height -1) / cellSize] [(x -speed) / cellSize];
    		if (topleft != 1 && downleft != 1 && topleft != 2 && downleft != 2) {
    			x = Math.max(0, x - speed);
    		} else if (speed == 1) {
    			changeDirection();
    		} else {
    			move(--speed);
    		}
		} else if (direction == Direction.UP) {
        	int topleft = currentField[(y -speed) / cellSize ] [x / cellSize];
        	int topright = currentField[(y -speed) / cellSize] [(x + width -1) / cellSize];
    		if(topleft != 1 && topright != 1 && topleft != 2 && topright != 2) {
    			y = Math.max(0, y - speed);
    		} else if (speed == 1) {
    			changeDirection();
    		} else {
    			move(--speed);
    		}
    	} else if (direction == Direction.RIGHT) {
        	int topright = currentField[y / cellSize] [(x + width -1 + speed) / cellSize];					//width уже включает +1
        	int downright = currentField[(y + height-1) / cellSize] [(x + width -1 + speed) / cellSize];
        	if (topright != 1 && downright != 1 && topright != 2 && downright != 2) {
        		x = Math.min(width * cellSize - height, x + speed);
    		} else if (speed == 1) {
    			changeDirection();
    		} else {
    			move(--speed);
    		}
		} else if (direction == Direction.DOWN) {
        	int downleft = currentField[(y + height -1 + speed) / cellSize] [x / cellSize];
        	int downright = currentField[(y + height -1 + speed) / cellSize] [(x + width -1) / cellSize];	//height уже включает +1
        	if (downleft != 1 && downright != 1 && downleft != 2 && downright != 2) {
        		y = Math.min(height * cellSize - + width, y + speed);
    		} else if (speed == 1) {
    			changeDirection();
    		} else {
    			move(--speed);
    		}
		}
	}

	/**
	 * @return скорость движения
	 */
	public int getSpeed() {
		return speed;
	}

	protected void changeDirection() {}

	/**
	 * @param direction направление движения
	 */
	public void setDirection(Direction direction) {
		this.direction = direction;		
	}

	/**
	 * @return the direction направление движения
	 */
	public Direction getDirection() {
		return direction;
	}
}
