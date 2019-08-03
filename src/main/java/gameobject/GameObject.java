package gameobject;

import javafx.scene.canvas.GraphicsContext;

/**
 * @author Admin
 */
public abstract class GameObject {
    public int x;
    public int y;
    public int width = 20;
    public int height= 20;
    
    public GameObject(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public GameObject(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public boolean isCollision(GameObject gameObject) {
        if (!isCollisionPossible(gameObject))
            return false;

        for (int x = 0; x < gameObject.height; x++) {			//от 0 до 25	//x,y поменять?
            for (int y = 0; y < gameObject.width; y++) {		//от 0 до 25
                //if (gameObject.matrix[carY][carX] != 0) {
                    if (isCollision(x + gameObject.x, y + gameObject.y)) {		 // каждую координату other сравниваем с каждой this  
                        return true;
                    }
                //}
            }
        }
        return false;
    }
    
    private boolean isCollisionPossible(GameObject otherGameObject) {
        if (this.x > otherGameObject.x + otherGameObject.width || this.x + this.width < otherGameObject.x) {
            return false;
        }
        if (this.y > otherGameObject.y + otherGameObject.height || this.y + this.height < otherGameObject.y) {
            return false;
        }
        return true;
    }
    
    private boolean isCollision(int x, int y) { //0, 24
        for (int matrixX = 0; matrixX < height; matrixX++) {
            for (int matrixY = 0; matrixY < width; matrixY++) {
                if (matrixX + this.x == x && matrixY + this.y == y) {
                    return true;
                }
            }
        }
        return false;
    }
}
