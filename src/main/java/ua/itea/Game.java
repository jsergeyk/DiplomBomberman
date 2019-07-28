package ua.itea;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.ImageIcon;

import gameobject.Bomb;
import gameobject.Enemy;
import gameobject.GameObject;
import gameobject.Player;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @author Admin
 */
public class Game extends Application {
	
	private Player player;
	private List<Enemy> enemyList = new ArrayList<>();
	private Pane root;
    private static final int width = 20;
    private static final int height =20;
    private boolean isGameStopped;
    private ExitDoor exitDoor;
    public static int cellSize =25;
    private Canvas canvas;
    private GraphicsContext gc;
    private Stage primaryStage;
    private StackPane[][] cells;
    private Timeline timeline = new Timeline();
    private int timerStep = 0;
	private TextFlow dialogContainer;
	private Text scoreText;
	
	private static int score;
    public static List<Bomb> bombs;			//список бомб
    public static volatile int countBombs = 3; 		//Колво одновременных бомб 2
    
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.scoreText = new Text("Score: 0");
        DBUtil.initDataBase();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/ua/itea/MyScene.fxml"));
		root = loader.load();
		Scene scene = new Scene(root);
        scene.setOnKeyPressed((event) -> {
        	this.onKeyPress(event.getCode());
        });
		this.initialize();				
		primaryStage.setScene(scene);
        primaryStage.setTitle("Sergeyk Game");
        primaryStage.setResizable(false);
        primaryStage.show();
		timeline.playFromStart(); //запуск
	}
	
	/**
	 * @param code
	 */
	private void onKeyPress(KeyCode code) {
        if (isGameStopped && (code == KeyCode.SPACE || code == KeyCode.ENTER)) {
        	dialogContainer.setVisible(false);
            createGame();
        } else {
        	if (code == KeyCode.LEFT) {
				player.x = Math.max(0, player.x - player.speed);
			} else if (code == KeyCode.UP) {
				player.y = Math.max(0, player.y - player.speed);
        	} else if (code == KeyCode.RIGHT) {
				player.x = Math.min(width * cellSize - player.height, player.x + player.speed);
			} else if (code == KeyCode.DOWN) {
				player.y = Math.min(height * cellSize - + player.width, player.y + player.speed);
			}
			if (code == KeyCode.SPACE && countBombs > 0) {
				Bomb bomb = new Bomb(player.x, player.y, this.gc);
				bombs.add(bomb);			//new Thread(bomb).start();
				countBombs--;
			}
        }
	}

	private void initialize() {
        setScreenSize();
		createContent();
		this.createScorePanel();
	    this.timeline.setCycleCount(-1);
	    this.timeline.playFromStart();
		createGame();
	}
	
	private void createGame() {
		score = 0;
		setScore(score);
		isGameStopped = false;
		canvas = new Canvas(width * cellSize + 100, height * cellSize + 100); //25*20
		canvas.setLayoutX(100);
		canvas.setLayoutY(100);
        root.getChildren().add(canvas);
 		player = new Player(cellSize, cellSize);
        for (int i = 0; i < width; i++) {
        	for (int j = 0; j < height; j++) {
        		if (FieldsMatrix.FIELD[i][j] == 8) {									//враг        			
        			enemyList.add(new Enemy(cellSize * j,  cellSize * i));
        		}
        	}
        }
 		Player.playerSrc = new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("player.png"));
 		Enemy.enemySrc = new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("enemy.png"));
		bombs = new CopyOnWriteArrayList<>();
		drawScene();
		setTurnTimer(40);
	}
	
    private void drawScene() {
    	gc = canvas.getGraphicsContext2D();
    	gc.clearRect(0, 0, width * cellSize, height * cellSize);		//(100, 100, width * cellSize + 100, height * cellSize + 100);
        drawField();    
        player.draw(gc);        	//gc.drawImage(Player.imageSrc, player.x, player.y, player.width, player.height);
        for (Enemy enemy : enemyList) {
			enemy.draw(gc);
        }
        for (Bomb bomb : bombs) {
        	bomb.draw(gc);        	//gc.drawImage(Bomb.bombSrc, bomb.x, bomb.y, 20, 20);
		}
    }
	
    /**
     * Рисование поля, элементов поля
     */
    private void drawField() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {    
            	ObservableList<Node> children = this.cells[i][j].getChildren();
                ((Rectangle)children.get(0)).setFill(Color.GREEN);
            }
        }
        for (int i = 0; i < width; i++) {
        	for (int j = 0; j < height; j++) {
        		if (FieldsMatrix.FIELD[i][j] == 1) {										//стен
        			gc.drawImage(FieldsMatrix.wallSrc, cellSize * j, cellSize * i, cellSize, cellSize);
        		} else if (FieldsMatrix.FIELD[i][j] == 9) {									//дверь
        			gc.drawImage(FieldsMatrix.doorSrc, cellSize * j, cellSize * i, cellSize, cellSize);
        			exitDoor = new ExitDoor(cellSize * j, cellSize * i);
        		} else if (FieldsMatrix.FIELD[i][j] == 2) {									//кирпич
        			gc.drawImage(FieldsMatrix.brickSrc, cellSize * j, cellSize * i, cellSize, cellSize);
        		}
        	}
        }
    }
    
	private void createContent() {
		this.root.setPrefSize((double)(this.width * cellSize), (double)(this.height * cellSize)); //+ 200
	       for(int x = 0; x < this.width; x++) {
	            for(int y = 0; y < this.height; y++) {
	                ObservableList<Node> children = this.cells[x][y].getChildren();                
	                Rectangle cell;
	                /*if (children.size() > 0) {			//сетка
	                    cell = (Rectangle)children.get(0);
	                    cell.setWidth((double)(cellSize - 1));
	                    cell.setHeight((double)(cellSize - 1));
	                    cell.setStroke(Color.valueOf("red"));
	                    cell.setFill(Color.valueOf("white"));
	                }*/
	                /*if (children.size() > 2) { 			//координаты для debug
	                    Text coordinate = (Text)children.get(2);
	                    coordinate.setFont(Font.font((double)cellSize * 0.4D));
	                    StackPane.setAlignment(coordinate, Pos.TOP_LEFT);
	                    coordinate.setText(x + " - " + y);
	                    coordinate.setStroke(Color.valueOf("red"));
	                }*/
	                if (children.size() > 0) {
	                    cell = (Rectangle)children.get(0);
	                    cell.setWidth((double)cellSize);
	                    cell.setHeight((double)cellSize);
	                    this.cells[x][y].setLayoutX((double)(y * cellSize + 100)); //100 отступ
	                    this.cells[x][y].setLayoutY((double)(x * cellSize + 100));
	                    this.root.getChildren().add(this.cells[x][y]);
	                }
	            }
	        }
	}
	
	/**
	 * @param width
	 * @param height
	 */
	private void setScreenSize() {
        this.cells = new StackPane[this.width][this.height];

        for(int x = 0; x < this.width; x++) {
            for(int y = 0; y < this.height; y++) {
                this.cells[x][y] = new StackPane(new Node[]{new Rectangle(), new Text(), new Text()});
            }
        }		
	}
	
    private void setTurnTimer(int timeMs) {
        this.timeline.stop();
        KeyFrame frame = new KeyFrame(Duration.millis((double)timeMs), (event) -> {
            if (!this.isGameStopped) {        	
                this.onTurn(++this.timerStep);
            }

        }, new KeyValue[0]);
        this.timeline.getKeyFrames().clear();
        this.timeline.getKeyFrames().add(frame);
        this.timeline.play();
    }
    
	/**
	 * 
	 */
	private void onTurn(int step) {
        for (Enemy enemy : enemyList) {
    		if (player.isCollision(enemy)) {
    			gameOver(); //Проиграл
    		}
        }        
        for (Bomb bomb : bombs) {
        	if (bomb.checkBurn(player)) {
        		gameOver();
        	}
            Iterator<Enemy> it = enemyList.iterator();
            while (it.hasNext()) {
            	Enemy enemy = it.next();
            	if (bomb.checkBurn(enemy)) {            		
            		it.remove();
        			score += enemy.score;
            		setScore(score);
            		//DBUtil.updateScore(score);		//сохранить в бд	
            	}
            }
		}
		if (player.isCollision(exitDoor)) {
			win();
		}
		player.move();
		//setScore(score);
		drawScene();	//перерисовать
	}
	
    /**
     * Обновить счет
	 * @param score очки
	 */
	private void setScore(int score) {
		this.scoreText.setText("Score: " + score);
	}

	private void gameOver() {
        isGameStopped = true;
        Player.playerSrc = null;
        Enemy.enemySrc = null;
        bombs.clear();
        this.timeline.stop();
        showMessageDialog(Color.RED, "Вы проиграли!", Color.BLACK, 30);
    }    

    private void win() {
        isGameStopped = true;
        Player.playerSrc = null;        
        bombs.clear();
        this.timeline.stop();
        showMessageDialog(Color.GREEN, "Вы выиграли!", Color.YELLOW, 50);
    }
    
    public void showMessageDialog(Color cellColor, String message, Color textColor, int textSize) {
    	if (dialogContainer == null) {
            dialogContainer = new TextFlow();
            this.root.getChildren().add(dialogContainer);
        }

        dialogContainer.getChildren().clear();
        Text messageText = new Text();
        messageText.setFont(Font.font("Verdana", FontWeight.BOLD, (double)textSize));
        messageText.setText(message);
        double preferredWidth = messageText.getLayoutBounds().getWidth();
        messageText.setFill(textColor);
        dialogContainer.setLayoutX((this.root.getWidth() - preferredWidth) / 2.0D);
        dialogContainer.setLayoutY(root.getHeight() / 2.0D - 30.0D);
        dialogContainer.setBackground(new Background(new BackgroundFill[]{new BackgroundFill(cellColor, CornerRadii.EMPTY, Insets.EMPTY)}));
        dialogContainer.setVisible(true);
        dialogContainer.getChildren().add(messageText);
        //isMessageShown = true;
    }
    
    private void createScorePanel() {
        this.scoreText.setFont(Font.font("Verdana", FontWeight.BOLD, 16.0D));
        this.scoreText.setFill(Color.BLACK);
        StackPane scorePane = new StackPane(new Node[]{this.scoreText});
        scorePane.setBorder(new Border(new BorderStroke[]{new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)}));
        scorePane.setLayoutY((double)(this.height * cellSize + 110 + 6));
        Rectangle rectangle = new Rectangle((double)(this.width * cellSize / 2), 20.0, Color.WHITE);
        scorePane.setLayoutX((double)(124 + this.width * cellSize / 4));
        scorePane.getChildren().add(0, rectangle);
        this.root.getChildren().add(scorePane);
    }
    
	public static int getScore() {
		return score;
	}
	
    class ExitDoor extends GameObject {

		public ExitDoor(int x, int y) {
			super(x, y);
		}

		@Override
		public void draw(GraphicsContext gc) {			
		}    	
    }
}