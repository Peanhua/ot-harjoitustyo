# Requirements Specification

Title of the software (game): The Fishing Rod of Destiny

## Purpose

The software is a [roguelike game](https://en.wikipedia.org/wiki/Roguelike) in which the player descends through a set of dungeon levels to find a certain item, and then ascends back to the surface. The player will face dangers such as enemies and traps. The player character can be levelled up, and the player can find tools (mainly armor and weapons) to help in the quest.

### The plot

A princess falls into a lake. The only way to save the princess is to retrieve a magical fishing rod from the depths of a cave.

The princess can also be a prince depending on players initial choices.

## Users

As a single player game, there is just one user role: the player.


## User Interface


## Product Functions

### Before a game is started
* The player can start a new game
* The player can load a saved game
* The player can see the current highscore lists

### When a new game is started
* The player creates a character
  * The player names the character
  * The player has certain amount of points to distribute amongst the attributes
    * Maximum hit points
    * Attack
    * Defence
    * Carrying capacity
  * The player chooses whether the player wants to try to save a princess or a prince
  * The random name is generated for the princess/prince
* The player can select the random seed used for all the content generation
* The player is presented with the plot/quest

### While the game is on
* Every character in game can do one action per turn
* The player is presented a visual layout of the current level the player character is on
  * The player has limited vision which determines how far the player character sees movable objects
  * The player has a memory of the area the player has seen (aka explored)
    The explored area is drawn/shown to the player, whereas the parts that are not yet explored are hidden.
* The player can save the progress of an on-going game
* The player can complete the game, ending the game in victory
* The player character can die, ending the game
* The player character can move around in a procedurally generated cave
  * The cave is divided into multiple levels
  * Each level is a 2d grid of adjacent squares
  * The movement is done to the north, south, west, or east
  * The player character can move between the levels via stairs and (fall down through) trapdoors
* The player character can attack enemies
* The player character can pick up items
* The player character can drop items
* The player character can view inventory
* The player character can change equipped armor
* The player character can change wield weapon
* The player character can level up
  * Levelling up increases one of the characters attributes
  * The attribute to increase is determined with a weighted random based on current attributes, favoring to increase already attributes that are already high
* Enemies are controlled by a simple artificial intelligence
  * Enemies move around in the same environment as the player
  * Enemies can attack the player
  * Enemies are spawned when the level is generated

### When the player character is fighting with an enemy
* The game autoplays the combat
* Both parties hit each others until one dies
* The damage done per hit is based on the attributes and equipment of both parties
* The player is shown what happens

### When the game ends
* If the player obtained enough score, the player gets placed in the appropriate highscore lists

### Character attributes
Every character (both the player and enemies) have the following attributes:
* Name
* Current hit points, if current hit points reaches 0, then the character dies
* Maximum hit points
* Attack, used in combat together with the defence attribute to determine whether an attack is successful or a miss
* Defence
* Carrying capacity
* Weapon wield
* List of equipped armor
* List of inventory items
* Level, generic information how dangerous the character is
* Experience points, experience points are gained for each successful combat (ie. every kill)
* Buffs, special temporary conditions for the character
  * Buffs can be obtained by potions, equipped armor, traps, and from enemy hits during combat
  * Buff is removed either by a timer, or when the linked armor is unequipped
  * Generic buff attributes:
    * Buff amount, can be negative
    * Buff time
  * Buff types:
    * Regeneration: extra regeneration of current hit points per turn
    * Armor class boost: added to the total when calculating characters armor class value
    * Character attribute boost potion: increases one attribute of the character
      * Valid types are: attack, defence, max hit points, and carrying capacity

### Items
Common attributes for all items:
* Type: armor, weapon, potion, or the magical fishing rod
* Weight
* Durability, this is decreased when the item is used, the item is destroyed if the durability reaches 0

#### Armor items
Extra attributes for armor items:
* Armor class value representing how much incoming damage the armor absorbs if worn
* Armor slot, only one armor item per slot is allowed
  * Head (helmets, hats, etc)
  * Chest (shirts etc)
  * Legs (pants etc)
  * Hands (gloves)
  * Feet (boots)
  * Finger (rings)
* Buff (and its attributes) to give when equipped

#### Weapon items
Extra attributes for weapon items:
* Damage value, used to calculate the amount of damage during combat
* Negative buff given to the target
  * Type of buff and its attributes
  * The random chance (percentage value) for the negative buff to be given
  * Governing attacker attack attribute amount used to adjust the random chance, two values:
    * The minimum required attack value for greater than zero random chance
    * The maximum attack value at which the random chance reaches its peak value    

#### Potion items
Potions are consumables that can be used only once, and when used gives the character a buff:
* Healing potion, immediately increases the current hit points of the character
* Regeneration potion (buff)
  * Extra attributes: amount, buff time
* Armor class boost potion (buff)
  * Extra attributes: amount, buff time
* Character attribute boost potion (buff)
  * Extra attributes: type, amount, buff time
* Reveal map potion, immediately reveals the current cave level as if the player character had visited every square of it

### Level features
Each square in a level has a type and associated attributes. And in addition, each square contains a list of items located in the square.

Square types:
* Empty space (ground)
* Trap
  * Bear trap, does damage once and is then deactivated
    * Can be re-activated
    * A negative buff for the triggering character can be given
  * Trapdoor, character falls down to the level below
    * Falling causes damage to the player
* Wall
* Stairs up and down
* Door

### Highscore lists
* Several types of highscore lists are provided:
  * The players position in the list is determined by player character level and experience points
    * Entering this highscore list does not require the game to be completed
  * The players position in the list is determined by by the number of actions performed
    * Entering this highscore list requires the game to be completed
  * The players position in the list is determined by the real time used
    * Entering this highscore list requires the game to be completed
* Same game can grant position in all the highscore lists
