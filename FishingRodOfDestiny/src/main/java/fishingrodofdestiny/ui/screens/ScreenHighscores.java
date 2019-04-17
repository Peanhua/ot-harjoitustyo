/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.ui.screens;

import fishingrodofdestiny.highscores.Highscore;
import fishingrodofdestiny.highscores.HighscoreList;
import fishingrodofdestiny.resources.HighscoreListCache;
import fishingrodofdestiny.ui.widgets.HighscoreListView;
import fishingrodofdestiny.ui.widgets.UserInterfaceFactory;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author joyr
 */
public class ScreenHighscores extends Screen {

    public ScreenHighscores(Screen parent, Stage stage) {
        super(parent, stage);
    }

    @Override
    protected Node createUserInterface() {
        BorderPane pane = new BorderPane();

        pane.setTop(UserInterfaceFactory.createLogo());
        
        HighscoreList hslist = HighscoreListCache.getInstance().get(Highscore.Type.SCORE);
        HighscoreListView hlv = new HighscoreListView(hslist);
        pane.setCenter(hlv.createUserInterface());
        
        VBox buttons = new VBox(10);
        buttons.setAlignment(Pos.CENTER);

        Button close = new Button("Close");
        close.setOnAction(e-> this.close());
        buttons.getChildren().addAll(close);
        pane.setBottom(buttons);
        
        return pane;
    }
}
