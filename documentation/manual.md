# The Fishing Rod of Destiny user manual

## Configuration

### Highscores

By default the highscores are saved in a SQLite database named *FishingRodOfDestiny.db*. To change this, use the environment variable *FISHINGRODOFDESTINY_HIGHSCORES* to define the location as follows:

* Start with "jdbc:" to define a Jdbc database connection URI, for example: "jdbc:sqlite:FishingRodOfDestiny.db"
* Start with "file:" to define a filename base, for example "file:./highscores" will save the score based highscores to a file named *highscores-Score*. Each type of highscores are saved in their own file.
* Start with anything else to use in memory highscores that are not saved anywhere.


## In game controls

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
