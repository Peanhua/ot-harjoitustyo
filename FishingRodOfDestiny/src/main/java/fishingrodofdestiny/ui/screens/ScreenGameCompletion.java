/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.ui.screens;

import fishingrodofdestiny.ui.widgets.UserInterfaceFactory;
import fishingrodofdestiny.world.Game;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 *
 * @author joyr
 */
public class ScreenGameCompletion extends Screen {
    private final Game game;

    public ScreenGameCompletion(Game game, Screen parent, Stage stage) {
        super(parent, stage);
        this.game = game;
    }

    @Override
    protected Node createUserInterface() {
        BorderPane pane = new BorderPane();

        pane.setTop(UserInterfaceFactory.createLogo());

        String plot = this.game.getPlotFinish().stream().reduce("", (a, b) -> a + "\n" + b);
        Text t = UserInterfaceFactory.createText(plot);
        t.setFont(new Font(20));
        t.setWrappingWidth(500);
        t.setTextAlignment(TextAlignment.CENTER);
        pane.setCenter(t);

        String[] labels = { "Close" };
        List<Button> buttons = new ArrayList<>();
        Node brow = UserInterfaceFactory.createButtonRow(labels, buttons);
        brow.getStyleClass().add("windowButtonRow");
        buttons.get(0).setOnAction(e-> this.showHighscores());
        pane.setBottom(brow);
        
        return pane;
    }
    
    
    private void showHighscores() {
        this.close();
        
        Screen screen = new ScreenHighscores(this.getParent(), this.getStage());
        screen.show();
    }
}
