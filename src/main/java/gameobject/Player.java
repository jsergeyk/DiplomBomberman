package gameobject;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * @author Admin
 */
public class Player extends GameObject { 

	public static Image playerSrc;
    //public int posX;
    //public int posY;
    public int speed = 10;
    
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
}
