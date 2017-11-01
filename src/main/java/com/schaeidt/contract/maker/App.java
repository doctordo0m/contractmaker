package com.schaeidt.contract.maker;

import com.schaeidt.contract.maker.controller.MainWindowController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Contract Maker main Class
 */
public class App extends Application {
    
   @Override
   public void start(Stage stage) throws Exception {
	   stage.setTitle("Contract Maker");
	   FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainWindow.fxml"));
	   
	   MainWindowController controller = new MainWindowController();
	   loader.setController(controller);
	   
       Parent root = loader.load();
        
       Scene scene = new Scene(root);
        
       stage.setScene(scene);
       stage.show();
   }

   /**
    * @param args the command line arguments
    */
   public static void main(String[] args) {
       launch(args);
   }
}
