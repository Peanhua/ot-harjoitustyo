/*
 * Copyright (C) 2019 Joni Yrjänä <joniyrjana@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
 * The screen shown when player completes the game.
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
