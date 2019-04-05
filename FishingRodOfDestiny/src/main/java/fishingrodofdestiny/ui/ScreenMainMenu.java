/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.ui;

import fishingrodofdestiny.world.Game;
import fishingrodofdestiny.world.gameobjects.Player;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author joyr
 */
public class ScreenMainMenu extends Screen {

    public ScreenMainMenu(Screen parent, Stage stage) {
        super(parent, stage);
    }

    @Override
    protected void setup(Group root, Scene scene) {
        VBox vb = new VBox(0);

        vb.getChildren().add(UserInterfaceFactory.createVerticalSpacer(150));
        vb.getChildren().add(UserInterfaceFactory.createLogo(scene));
        vb.getChildren().add(UserInterfaceFactory.createVerticalSpacer(150));

        vb.getChildren().add(this.setupButtons());

        root.getChildren().add(vb);
    }

    private Node setupButtons() {
        VBox buttons = new VBox(2);
        buttons.setAlignment(Pos.CENTER);
            
        Button newgame = new Button("New Game");
        newgame.setOnAction(e -> this.startNewGame());

        Button loadgame = new Button("Load Saved Game");
        loadgame.setOnAction(e -> this.loadGame());
        loadgame.setDisable(true);

        Button highscores = new Button("Highscore lists");
        highscores.setOnAction(e -> this.showHighscores());

        Button quit = new Button("Quit");
        quit.setOnAction(e-> this.close());
            
        buttons.getChildren().addAll(newgame, loadgame, highscores, quit);

        return buttons;
    }
    
    private void startNewGame() {
        Screen ng = new ScreenNewGame(this, this.getStage());
        ng.show();
    }
    
    
    private void loadGame() {
        Game game = new Game(new Player());
        Screen g = new ScreenGame(game, this, this.getStage());
        g.show();
    }
    
    
    private void showHighscores() {
        Screen hs = new ScreenHighscores(this, this.getStage());
        hs.show();
    }
}
