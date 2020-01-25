/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictac.logic;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author zidan
 */
public class MainTest extends Application {
    public static void main(String[] args){      
           launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GameTestUi ui = new GameTestUi();
        final Stage endStage = new Stage();
        EndGameUi endUi = new EndGameUi();
      
       SingleMode tp= new SingleMode(false, new Player(), PlayerSign.Cross,ui  , endUi );
     // TwoPlayersMode tp = new TwoPlayersMode(false, new Player(), PlayerSign.Circle, ui);
        tp.startActionHandling();
 
       Scene scene = new Scene(ui);
       primaryStage.setScene(scene);
       primaryStage.show();
    }
    
    
}
