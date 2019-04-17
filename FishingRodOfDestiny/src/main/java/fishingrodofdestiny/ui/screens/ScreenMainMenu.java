/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.ui.screens;

import fishingrodofdestiny.ui.widgets.Starfield;
import fishingrodofdestiny.ui.widgets.UserInterfaceFactory;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author joyr
 */
public class ScreenMainMenu extends Screen {
    private Starfield starfield;
    
    public ScreenMainMenu(Screen parent, Stage stage) {
        super(parent, stage);
        this.starfield = null;
    }

    @Override
    protected Node createUserInterface() {
        StackPane sp = new StackPane();
        
        this.starfield = new Starfield();
        sp.getChildren().add(this.starfield.createUserInterface());
        
        BorderPane pane = new BorderPane();
        sp.getChildren().add(pane);
        
        pane.setTop(UserInterfaceFactory.createLogo());
        pane.setCenter(this.setupButtons());

        return sp;
    }

    private Node setupButtons() {
        VBox buttons = new VBox(2);
        buttons.setAlignment(Pos.CENTER);
            
        Button newgame = new Button("New Game");
        newgame.setOnAction(e -> this.startNewGame());

        Button loadgame = new Button("Load Saved Game");
        loadgame.setOnAction(e -> this.loadGame());
        loadgame.setDisable(true);

        Button highscores = new Button("Highscores");
        highscores.setOnAction(e -> this.showHighscores());

        Button quit = new Button("Quit");
        quit.setOnAction(e-> this.close());
            
        buttons.getChildren().addAll(newgame, loadgame, highscores, quit);

        return buttons;
    }
    
    private void startNewGame() {
        this.starfield.disable();
        Screen ng = new ScreenNewGame(this, this.getStage());
        ng.show();
    }
    
    
    private void loadGame() {
        /*
        Game game = new Game(new Player());
        Screen g = new ScreenGame(game, this, this.getStage());
        g.show();
*/
    }
    
    
    private void showHighscores() {
        Screen hs = new ScreenHighscores(this, this.getStage());
        hs.show();
    }

    @Override
    public void onShow() {
        this.starfield.enable();
    }
}
