package gameobject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import ua.itea.Drawable;
import ua.itea.Game;

import static ua.itea.Game.cellSize;
/**
 * @author SergeyK
 */
public class Bomb extends GameObject implements Drawable, Runnable {

	public static final Image bombSrc = new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("bomb.png"));
	public static final Image fireSrc = new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("fire.png"));
	public static final Image brickSrc = new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("brick.png"));
	private boolean visible = true;	//видимость бомбы 3 сек
	private List<Fire> fireAround = new ArrayList<>();
	/**
	 * @param x
	 * @param y
	 */
	public Bomb(int x, int y, GraphicsContext gc) {
		super(x, y);	
		new Thread(this).start();		
	}

	@Override
	public void draw(GraphicsContext gc) {
		if (visible) {
			gc.drawImage(Bomb.bombSrc, x, y, cellSize, cellSize);
		} else {
			for (Fire fire : fireAround) {
				fire.draw(gc);
			}			
		}
	}
	
	@Override
	public void run() {
		try {
			TimeUnit.SECONDS.sleep(3);
			visible = false;
			Game.countBombs ++;			
			Fire fire = new Fire(x, y, 20 ,20);
			fireAround.add(fire);
			
			TimeUnit.MILLISECONDS.sleep(50);
			fireAround.add(new Fire(x + 20, y, 20 ,20));
			fireAround.add(new Fire(x - 20, y, 20 ,20));
			fireAround.add(new Fire(x, y + 20, 20 ,20));
			fireAround.add(new Fire(x, y - 20, 20 ,20));
			TimeUnit.MILLISECONDS.sleep(50);
			fireAround.add(new Fire(x + 40, y, 20 ,20));
			fireAround.add(new Fire(x - 40, y, 20 ,20));
			fireAround.add(new Fire(x, y + 40, 20 ,20));
			fireAround.add(new Fire(x, y - 40, 20 ,20));
			TimeUnit.MILLISECONDS.sleep(800);
			fireAround.clear();
			Game.bombs.remove(this);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	class Fire extends GameObject {
		
		public Fire(int x, int y, int width, int height) {
			super(x, y, width, height);
		}

		//@Override
		public void draw(GraphicsContext gc) {
			//в отдельном потоке нельзя вызывать drawImage:
			//java.lang.NullPointerException
			//at com.sun.javafx.sg.prism.NGCanvas.handleRenderOp(NGCanvas.java:1324)
			
			//synchronized (Bomb.this) {
				gc.drawImage(fireSrc, x, y, cellSize, cellSize);
			//}
		}
	}

	/**
	 * Проверка пересечения игрока со взрывами бомбы
	 * @param player
	 */
	public boolean checkBurn(Player player) {
		for (Fire fire : fireAround) {
			if (fire.isCollision(player)) {
				return true;
			}
		}
		return false;
	}
}