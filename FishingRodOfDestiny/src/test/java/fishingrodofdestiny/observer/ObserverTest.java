/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.observer;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author joyr
 */
public class ObserverTest {
    
    private Subject subject;

    private class MyObserver implements Observer {
        public int callCount = 0;

        @Override
        public void update() {
            this.callCount++;
        }
    };
    
    @Before
    public void setUp() {
        this.subject = new Subject();
    }

    @Test
    public void observerIsCalled() {
        MyObserver observer = new MyObserver();
        this.subject.addObserver(observer);
        this.subject.notifyObservers();
        assertEquals(1, observer.callCount);
    }

    @Test
    public void removedObserverIsNotCalled() {
        MyObserver observer = new MyObserver();
        this.subject.addObserver(observer);
        this.subject.removeObserver(observer);
        this.subject.notifyObservers();
        assertEquals(0, observer.callCount);
    }
}
