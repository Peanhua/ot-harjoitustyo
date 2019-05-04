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
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 *
 * @author joyr
 */
public class ScreenPlot extends Screen {
    private Game game;
    
    public ScreenPlot(Game game, Screen parent, Stage stage) {
        super(parent, stage);
        this.game = game;
    }

    @Override
    protected Node createUserInterface() {
        VBox vb = new VBox(0);
        vb.setAlignment(Pos.CENTER);

        vb.getChildren().add(UserInterfaceFactory.createVerticalSpacer(50));
        vb.getChildren().add(UserInterfaceFactory.createLogo());

        vb.getChildren().add(UserInterfaceFactory.createVerticalSpacer(100));

        String plot = this.game.getPlot().stream().reduce("", (a, b) -> a + "\n" + b);
        Text t = UserInterfaceFactory.createText(plot);
        t.setFont(new Font(20));
        t.setWrappingWidth(500);
        t.setTextAlignment(TextAlignment.CENTER);
        vb.getChildren().add(t);
        
        vb.getChildren().add(UserInterfaceFactory.createVerticalSpacer(100));
        
        Button cont = new Button("Continue");
        cont.setOnAction(e-> this.gameOn());
        vb.getChildren().add(cont);
        
        return vb;
    }
    
    
    private void gameOn() {
        this.close();
        
        Screen screen = new ScreenGame(this.game, this.getParent(), this.getStage());
        screen.show();
    }
}
