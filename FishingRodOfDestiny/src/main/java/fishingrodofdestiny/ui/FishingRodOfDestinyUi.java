/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.ui;

import fishingrodofdestiny.ui.screens.ScreenMainMenu;
import fishingrodofdestiny.ui.screens.Screen;
import fishingrodofdestiny.world.GameObjectFactory;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author joyr
 */
public class FishingRodOfDestinyUi extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("The Fishing Rod Of Destiny");
        primaryStage.setResizable(false);
        
        Screen mainmenu = new ScreenMainMenu(null, primaryStage);
        mainmenu.show();
        
        primaryStage.show();
        
        System.out.println("gold coin = " + GameObjectFactory.create("gold coin"));
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
