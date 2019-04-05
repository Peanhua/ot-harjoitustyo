/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.ui;

import fishingrodofdestiny.world.Level;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author joyr
 */
public class LevelViewTest {
    
    private Level mockedLevel;
    
    public LevelViewTest() {
    }
    
    @Before
    public void setUp() {
        this.mockedLevel = mock(Level.class);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void doesLevelViewCallLevelDrawingMethod() {
        LevelView lview = new LevelView(100, 100);
        lview.createUserInterface();
        lview.setLevel(this.mockedLevel);
        verify(this.mockedLevel, atLeastOnce()).draw(notNull());
    }
}
