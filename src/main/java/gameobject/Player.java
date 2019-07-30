package gameobject;

import static ua.itea.Game.cellSize;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import ua.itea.Direction;
import ua.itea.FieldsMatrix;

/**
 * @author Admin
 */
public class Player extends GameObject { 

	public static Image playerSrc;
    //public int posX;
    //public int posY;
    public int speed = 5;
    
    /**
     * Указывает на передвижение персонажа  по оси X.
     * 1 - движение вправо, -1 - движение влево, 0 - нет движения.
     */
    public int directionX = 0;
     /**
     * Указывает на передвижение персонажа  по оси Y.
     * 1 - движение вниз, -1 - движение вверх, 0 - нет движения.
     */
    public int directionY = 0;
    
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
        //String name = "/img/player.png";
        //imageSrc = new ImageIcon(getClass().getResource(name)).getImage();
        //imageSrc = new Image("/img/player.png");
    }

	/**
	 * 
	 */
	public void move() {
		//x = x + speed;
	}
    
    //@Override
    public void draw(GraphicsContext gc) {
    	gc.drawImage(playerSrc, x, y, width, height);
    }

	/**
	 * @param direction направление движения
	 */
	public void move(Direction direction) {
		int speed = getSpeed();
		if (direction == Direction.LEFT) {
        	int topleft = FieldsMatrix.FIELD[y / cellSize] [(x -speed) / cellSize];
        	int downleft = FieldsMatrix.FIELD[(y + height -1) / cellSize] [(x -speed) / cellSize];
    		if (topleft != 1 && downleft != 1 && topleft != 2 && downleft != 2) {// - speed == FieldsMatrix.FIELD[i][j] == 1) {
    			x = Math.max(0, x - speed);
    		} else {
    			changeDirection();
    		}
		} else if (direction == Direction.UP) {
        	int topleft = FieldsMatrix.FIELD[(y -speed) / cellSize ] [x / cellSize];
        	int topright = FieldsMatrix.FIELD[(y -speed) / cellSize] [(x + width -1) / cellSize];
    		if(topleft != 1 && topright != 1 && topleft != 2 && topright != 2) {
    			y = Math.max(0, y - speed);
    		} else {
    			changeDirection();
    		}
    	} else if (direction == Direction.RIGHT) {
        	int topright = FieldsMatrix.FIELD[y / cellSize] [(x + width) / cellSize];					//width уже включает +1
        	int downright = FieldsMatrix.FIELD[(y + height-1) / cellSize] [(x + width) / cellSize];
        	if (topright != 1 && downright != 1 && topright != 2 && downright != 2) {
        		x = Math.min(width * cellSize - height, x + speed);
    		} else {
    			changeDirection();
    		}
		} else if (direction == Direction.DOWN) {
        	int downleft = FieldsMatrix.FIELD[(y + height) / cellSize] [x / cellSize];
        	int downright = FieldsMatrix.FIELD[(y + height) / cellSize] [(x + width -1) / cellSize];	//height уже включает +1
        	if (downleft != 1 && downright != 1 && downleft != 2 && downright != 2) {
        		y = Math.min(height * cellSize - + width, y + speed);
    		} else {
    			changeDirection();
    		}
		}
	}

	/**
	 * @return скорость движения
	 */
	protected int getSpeed() {
		return speed;
	}

	protected void changeDirection() {}
}
