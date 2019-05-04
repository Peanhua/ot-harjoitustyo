# Issues and further development

## Bugs
There are no known bugs, but with about 100 java files, there is bound to be some.

## Error conditions
Error conditions have received less testing and thought, for example if the user specifies slightly incorrect version of a URL for one of the configurable environment variables, the fall-through method is to use a memory based version of the data access object, without any informative message given to the user.

## Shared save data
The highscore lists and other saved stuffs can't be simultaneously successfully accessed by multiple instances of the game. So for example shared highscore lists, which would be a nice addition, are not currently possible.

## Inefficient data structures
The data structures used are not efficient for large cave systems. Having each tile and object in game be a separate object instance should be changed to arrays of data for better performance. However, the size of the game have been kept rather small, so it is not currently an issue.

## Content
The game could easily be expanded by adding more content such as items and non-player characters, effects. Different level generators could be added. Multiple caves to go through. And so on.

## Non-player character AI
The artificial intelligence, ie. the NPC controller, is really dumb and same for all types of creatures.

## Attributes
The GameObject and Character have some attributes (hit points, attack, defence, etc), but each one is coded separately. All the attributes should be refactored to use the same code base. This would make lot of the code cleaner and simpler.

## Weapon and armor separation
Weapons and armor could all be based on "Equipment" base class, simplifying the system around them.

## Speed attribute
Different creatures should move and act at different speeds, also different actions should have different execution times. The code base already partially supports this (a delta time value is passed through the tick() methods), and the user interface doesn't care, so this should be rather easy to implement.

## Object durability
The game objects should utilize the hit point system for durability, for example armor that gets hit enough times would break.

## More information to the player
The player should be able to obtain more information about the environment.

For example the player should be able to look at the individual tiles on the level and see what's in them without physically moving the player character onto them, the displayed amount would of course depend on whether the player has explored the tile in question, and whether the player currently sees into the tile.

Also the player should be able to get some information about the items in the game, for example what is the AC value of a piece of armor.

## Difficulty levels
The game could support different levels of difficulties, choosable when starting a new game. The different difficulty levels should have their own high score lists.

## Saving and loading
There should be an option to save an ongoing game, and then later load and continue it.
