# Architecture of The Fishing Rod of Destiny

<div>Basic architecture is shown below:</div>
<div><img src="architecture.svg" alt="Architecture" width="700" /></div>

## Packages

List of all packages:

<table>
  <tr>
    <th>Package</th><th>Description</th>
  </tr>
  <tr>
    <td>fishingrodofdestiny.dao</td>
    <td>Data access objects and their support classes.</td>
  </tr>
  <tr>
    <td>fishingrodofdestiny.highscores</td>
    <td>Highscore handling. Each type of highscore has its own class containing the method to calculate the points for that particular type of highscore.</td>
  </tr>
  <tr>
    <td>fishingrodofdestiny.observer</td>
    <td>Helper classes to implement observer pattern.</td>
  </tr>
  <tr>
    <td>fishingrodofdestiny.resources</td>
    <td>Resource loading and saving caches, all caches use the singleton pattern.</td>
  </tr>
  <tr>
    <td>fishingrodofdestiny.settings</td>
    <td>User settings (keyboard mapping etc).</td>
  </tr>
  <tr>
    <td>fishingrodofdestiny.ui</td>
    <td>The user interface base.</td>
  </tr>
  <tr>
    <td>fishingrodofdestiny.ui.screens</td>
    <td>The different screens (views) of the application.</td>
  </tr>
  <tr>
    <td>fishingrodofdestiny.ui.widgets</td>
    <td>Widgets used in the screens.</td>
  </tr>
  <tr>
    <td>fishingrodofdestiny.world</td>
    <td>The game world and its logic.</td>
  </tr>
  <tr>
    <td>fishingrodofdestiny.world.actions</td>
    <td>Actions characters can perform, for example move and attack.</td>
  </tr>
  <tr>
    <td>fishingrodofdestiny.world.controllers</td>
    <td>Character controllers, used to determine the next action the controlled character should make.</td>
  </tr>
  <tr>
    <td>fishingrodofdestiny.world.gameobjects</td>
    <td>Movable objects in the game, for example player, weapon.</td>
  </tr>
  <tr>
    <td>fishingrodofdestiny.world.tiles</td>
    <td>Static parts of the levels.</td>
  </tr>
</table>


## User interface

User interface is made of a number of *screens*, each defined in their own class in *fishingrodofdestiny.ui.screens* -package. The *screens* use both JavaFX widgets and custom widgets located in *fishingrodofdestiny.ui.widgets* -package, using the interface defined in the *Widget* -class. The class *UserInterfaceFactory* contains static methods to create common JavaFX widgets or a combination of them.

During an active on-going game, the user interface handles updating the screen based on events from the game and by querying the current state of it.


## Application logic

Application logic is contained in the *fishingrodofdestiny.world* and its sub-packages. A single instance of a *Game* holds a single game and its state. A game contains levels, and a game also contains reference to the player game object for ease of access / finding it.

A level defines (class *Level*) one section of the cave, and can be thought to be like a floor in a building, the player starts from the top floor and descends down to bottom floor (and then back up to the top floor). A level is made of 2d grid of adjacent tiles (class *Tile*), each tile having the exact same dimensions. The actual tile objects in the level are stored in a container class *LevelMap*.

A tile contains game objects (class *GameObject*), and game objects can contain other game objects. A game object is a movable object, whereas a tile is a static object. Certains types of tiles can have link to other tile, for example stairs down is connected to stairs up on another level.

<div><img src="application_logic.svg" alt="Application logic" width="200" /></div>

The game advances when player picks the next action to be performed by the player character (class *Player*, instance of *GameObject*). The *tick()* method is called on the *Game* object, which in turn selects the levels to process, the selection is based on the location of the player character, only nearby levels are processed. The levels then call the *tick()* method of all of the game objects in the level, and the game objects do whatever they are destined to do. For example the player character might want to move one step to the east.

The game is controlled by the player, and is thus not realtime; the game is not progressing when the player is choosing the next action. This means that triggering the game to progress is done from the user interface. Also the initialization of the game is triggered from the user interface, be it starting a new game or loading a saved game.


### Character controllers

Both the player character and non player characters are controlled by controllers. The controllers are attached to the characters, each having their own instance of a controller. Both the character and its controller know about each others.

The player controller uses input from the user (through JavaFX events) to determine the next action. More complicated actions may cause the player controller to show additional user interface controls on screen. For example, when player presses the key to pick up item when there are multiple items on the ground, the controller opens up a item chooser requester for the user to choose which item to pick up. After the user chooses the item, the appropriate action is created.

The non player characters uses artificial intelligence to determine the next action.


### Monster hits player

The following sequence diagram describes what happens when a monster hits the player, and the player dies. The possible event listeners for onChange events are not shown, for the player this would be the CharacterStatus widget showing the players current status. Also the internal works of Location objects are not shown (which again for the players Location object would contain a triggered event, causing the LevelView to update).
<div><img src="monster_hit_player_sequence_diagram.svg" alt="Sequence diagram about monster hitting the player" width="900" /></div>

