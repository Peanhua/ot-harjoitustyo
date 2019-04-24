# The Fishing Rod of Destiny user manual

## Configuration

### Highscores

By default the highscores are saved in a SQLite database named *FishingRodOfDestiny.db*. To change this, use the environment variable *FISHINGRODOFDESTINY_HIGHSCORES* to define the location as follows:

* Start with "jdbc:" to define a Jdbc database connection URI, for example: "jdbc:sqlite:FishingRodOfDestiny.db"
* Start with "file:" to define a filename base, for example "file:./highscores" will save the score based highscores to a file named *highscores-Score*. Each type of highscores are saved in their own file.
* Start with anything else to use in memory highscores that are not saved anywhere.

### Statistics

Statistics are saved similarly to highscores. The controlling environment variable is named *FISHINGRODOFDESTINY_STATISTICS*.


### Settings

Settings are loaded from and saved to a [INI-file](https://en.wikipedia.org/wiki/INI_file) named "settings.ini".

For keyboard settings, the file contains section named "keyboard", with mappings from keys to action/command.
The keys are named after [JavaFX enum KeyCode](https://docs.oracle.com/javase/8/javafx/api/javafx/scene/input/KeyCode.html), and the actions/commands are listed below:

Actions:
* WAIT          - Skip turn.
* MOVE_NORTH    - Move north.
* MOVE_SOUTH
* MOVE_WEST
* MOVE_EAST
* ACTIVATE_TILE - Tile specific action.
* ATTACK        - Attack whoever is in the same tile.
* PICK_UP       - Pick an item from the floor.
* DROP          - Drop an item to the floor.
* USE           - Use an item from the inventory.
* LEVEL_UP      - Level up the player character (if the player has enough experience points).

Commands:
* ZOOM_IN       - Zoom the level map view in (make things look bigger).
* ZOOM_OUT      - Zoom the level map view out (make things look smaller).
* EXIT          - Exit the game.

Example of a settings file:
```INI
[keyboard]
# Actions
UP=MOVE_NORTH
DOWN=MOVE_SOUTH
LEFT=MOVE_WEST
RIGHT=MOVE_EAST
E=ACTIVATE_TILE
A=ATTACK
W=WAIT
P=PICK_UP
D=DROP
U=USE
L=LEVEL_UP
# Commands
PAGE_DOWN=ZOOM_IN
PAGE_UP=ZOOM_OUT
ESCAPE=EXIT
```


#### Default keyboard settings

* `up/down/left/right` to move
* `a` to attack enemies in same tile
* `e` to climb ladders up/down
* `page up` to zoom out
* `page down` to zoom in
* `w` to wait
* `p` to pick up items
* `d` to drop items
* `u` to use items
* `l` to level up the character
