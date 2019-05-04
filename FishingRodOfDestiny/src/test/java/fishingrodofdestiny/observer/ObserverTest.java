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
