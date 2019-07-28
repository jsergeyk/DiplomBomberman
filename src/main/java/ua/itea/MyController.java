package ua.itea;

import java.io.IOException;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @author Kalchenko Serhii
 */
public class MyController{
	
	private FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SaveFrom.fxml"));
	private Stage stage;
	private Parent root1;
	
	@FXML
	private MenuBar mainMenu;

	@FXML
	private TextField txtName;
	
	@FXML
	private Button btnSave;
	
    @FXML
    private void exitApp(ActionEvent event) {
        System.exit(0);
    }
    
    @FXML
    private void saveApp(ActionEvent event) {
		try {
			if (root1 == null) {
				root1 = (Parent) fxmlLoader.load();			
	    	    stage = new Stage();
	    	    stage.initModality(Modality.APPLICATION_MODAL);
	    	    //stage.initStyle(StageStyle.UNDECORATED);
	    	    stage.setTitle("Сохранить игру");
	    	    stage.setScene(new Scene(root1));
			}
    	    stage.show();    	    
		} catch (IOException e) {
			e.printStackTrace();
		}    
	}
    
    @FXML
    private void loadApp(ActionEvent event) {
		try {
			if (root1 == null) {
				root1 = (Parent) fxmlLoader.load();
	    	    stage = new Stage();
	    	    stage.initModality(Modality.APPLICATION_MODAL);
	    	    stage.setTitle("Загрузить игру");
	    	    //btnSave.setVisible(false);
	    	    stage.setScene(new Scene(root1)); 
			} 
    	    stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}   
    }
    
    @FXML
    private void saveAction(ActionEvent event) {
    	DBUtil.saveGame(txtName.getText(), 1, Game.getScore());		//пока уровень 1
    	txtName.setText(null);
    	Stage nStage = (Stage) btnSave.getScene().getWindow();
    	nStage.close();
    }
    
    @FXML
    private void cancelAction(ActionEvent event) {
    	txtName.setText(null);
    	Stage nStage = (Stage) btnSave.getScene().getWindow();
    	nStage.close();    	
    }
  
	/**
	 * Инициализация
	 */
	public void initData() {
		//System.out.print("my init");		
	}
}
