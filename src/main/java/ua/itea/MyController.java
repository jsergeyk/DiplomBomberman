package ua.itea;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.*;

/**
 * @author Kalchenko Serhii
 */
public class MyController implements Initializable {
	
	private static FXMLLoader fxmlLoader;
	private static FXMLLoader fxmlLoader2;
	private static Parent root;
	private static Parent root2;
	private static Scene sc;
	private static Scene sc2;
	private static String btnSaveText;
	
	static {
		try {
			btnSaveText = "Save";
			fxmlLoader = new FXMLLoader(MyController.class.getResource("SaveFrom.fxml"));
			root = (Parent) fxmlLoader.load();
			btnSaveText = "Load";
			fxmlLoader2 = new FXMLLoader(MyController.class.getResource("SaveFrom.fxml"));
			root2 = (Parent) fxmlLoader2.load();			//вызывает initialize
			sc= new Scene(root);
			sc2= new Scene(root2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
	@FXML
	private MenuBar mainMenu;

	@FXML
	private TextField txtName;
	
	@FXML
	private Button btnSave = new Button();
	@FXML
	private Button btnCancel;
    @FXML
    private void exitApp(ActionEvent event) {
    	DBUtil.closeConnection();
        System.exit(0);
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		btnSave.setText(btnSaveText);
	}
	
    @FXML
    private void saveApp(ActionEvent event) {
    	Stage stage = new Stage();
	    stage.initModality(Modality.APPLICATION_MODAL);
	    stage.setResizable(false);
	    stage.setTitle("Save game");
	    stage.setScene(sc);			
	    stage.show();
	}
    
    @FXML
    private void loadApp(ActionEvent event) {
    	Stage stage = new Stage();
	    stage.initModality(Modality.APPLICATION_MODAL);
	    stage.setResizable(false);
	    stage.setTitle("Load game");
	    stage.setScene(sc2); 
	    stage.show();
    }
    
    @FXML
    private void saveAction(ActionEvent event) {
    	if ("Save".equals(btnSave.getText())) {
    		DBUtil.saveGame(txtName.getText(), Game.level, Game.getScore());		//пока уровень 1
    	} else {
    		DBUtil.loadGame(txtName.getText());
    	}
    	Stage nStage = (Stage) btnSave.getScene().getWindow();
    	nStage.close();
    }
    
    @FXML
    private void cancelAction(ActionEvent event) {
    	txtName.setText(null);
    	Stage nStage = (Stage) btnCancel.getScene().getWindow();
    	nStage.close();    	
    }
}
