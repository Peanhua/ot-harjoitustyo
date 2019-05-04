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
package fishingrodofdestiny.world;

import java.util.Random;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author joyr
 */
public class EmptyLevelGeneratorTest {
    
    private EmptyLevelGenerator generator;
    
    @Before
    public void setUp() {
        Random random = new Random(42);
        this.generator = new EmptyLevelGenerator(new CaveSettings(random));
    }
    
    @Test
    public void canLevelBeCreated() {
        Level level = this.generator.generateLevel(0);
        assertTrue(level != null);
    }
}
