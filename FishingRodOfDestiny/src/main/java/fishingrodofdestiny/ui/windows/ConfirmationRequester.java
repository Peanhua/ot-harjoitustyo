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
package fishingrodofdestiny.ui.windows;

import fishingrodofdestiny.ui.screens.Screen;
import fishingrodofdestiny.ui.widgets.UserInterfaceFactory;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * A requester that asks the user confirmation to something.
 * 
 * @author joyr
 */
public class ConfirmationRequester extends Window {
    
    public interface ConfirmationHandler {
        void confirmed();
    }

    private final String messageText;
    private final String cancelText;
    private final String confirmText;
    private final ConfirmationHandler handler;
    
    /**
     * Create a new requester.
     * <p>
     * The handler is called only if the user confirms (ie. clicks the button with the confirm label).
     * 
     * @param screen  The screen where to place this requester.
     * @param message The confirmation message (possibly a long text)
     * @param cancel  The label for the "cancel" button
     * @param confirm The label for the "ok" button
     * @param handler The handler to be called if the user clicks "ok"
     */
    public ConfirmationRequester(Screen screen, String message, String cancel, String confirm, ConfirmationHandler handler) {
        super(screen);
        this.messageText = message;
        this.cancelText  = cancel;
        this.confirmText = confirm;
        this.handler     = handler;
    }
    
    @Override
    protected Node createUserInterface() {
        BorderPane pane = new BorderPane();
        pane.getStyleClass().add("window");
        pane.setMaxSize(300, 200);
        
        pane.setTop(this.createWindowTitle("CONFIRM"));
        pane.setCenter(this.createContent());
        pane.setBottom(this.createButtons());

        return pane;
    }
    
    private Node createContent() {
        Text content = UserInterfaceFactory.createText(this.messageText);
        content.setWrappingWidth(250);
        content.setTextAlignment(TextAlignment.CENTER);
        return content;
    }
    
    private Node createButtons() {
        String[] labels = { this.cancelText, this.confirmText };
        List<Button> buttons = new ArrayList<>();
        Node brow = UserInterfaceFactory.createButtonRow(labels, buttons);
        brow.getStyleClass().add("windowButtonRow");
        
        buttons.get(0).setOnAction(e -> {
            this.close();
        });

        buttons.get(1).setOnAction(e -> {
            this.close();
            this.handler.confirmed();
        });
        
        this.setFocusDefault(buttons.get(0));
        
        return brow;
    }
}
