/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.ui;

import javafx.scene.Node;

/**
 *
 * @author joyr
 */
public abstract class Widget {
    public abstract Node createUserInterface();
    public abstract void refresh();
}
