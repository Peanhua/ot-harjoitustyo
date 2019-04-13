/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.ui.widgets;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author joyr
 */
public class ConfirmationRequester {
    
    private StackPane    parent;
    private Node         window;
    private List<Button> buttons;
    private final String messageText;
    private final String cancelText;
    private final String confirmText;
    
    public ConfirmationRequester(String message, String cancel, String confirm) {
        this.parent      = null;
        this.window      = null;
        this.messageText = message;
        this.cancelText  = cancel;
        this.confirmText = confirm;
    }
    
    private Node createUserInterface() {
        StackPane root = new StackPane();
        
        Region shadow = new Region();
        root.getChildren().add(shadow);
        shadow.setStyle("-fx-background-color: #000000;");
        shadow.setOpacity(0.5);
        
        BorderPane pane = new BorderPane();
        root.getChildren().add(pane);
        pane.setStyle(""
                + "-fx-background-color: #000000;"
                + "-fx-border-width: 2;"
                + "-fx-border-color: #ffffff;"
                + "-fx-border-radius: 5"
        );
        pane.setMaxSize(300, 200);
        
        Text content = UserInterfaceFactory.createText(this.messageText);
        content.setWrappingWidth(250);
        content.setTextAlignment(TextAlignment.CENTER);
        content.setStyle("-fx-padding: 10 10 10 10;");
        pane.setCenter(content);
        
        String[] labels = { this.cancelText, this.confirmText };
        this.buttons = new ArrayList<>();
        Node brow = UserInterfaceFactory.createButtonRow(labels, this.buttons);
        brow.setStyle("-fx-padding: 0 0 10 0;");
        pane.setBottom(brow);
        
        return root;
    }
    
    public List<Button> show(StackPane parent) {
        this.parent = parent;
        this.window = this.createUserInterface();
        parent.getChildren().add(this.window);
        this.buttons.get(0).requestFocus();
        return this.buttons;
    }

    public void close() {
        this.parent.getChildren().remove(this.window);
    }
}
