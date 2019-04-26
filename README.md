# The Fishing Rod of Destiny

The software is a [roguelike game](https://en.wikipedia.org/wiki/Roguelike) in which the player descends through a set of dungeon levels to find certain item, and then ascends back to the surface. The player will face dangers such as enemies and traps. The player character can be levelled up, and the player can find tools (mainly armor and weapons) to help in the quest.

## Documentation

* [Architecture](documentation/architecture.md)
* [Requirements Specification](documentation/requirements_specification.md)
* [Timesheet](documentation/timesheet.md)
* [Manual](documentation/manual.md)


## License

This software is licensed under [The GNU General Public License version 3](LICENSE), with the following exceptions:

* [The RLTiles tileset](http://rltiles.sf.net) under [FishingRodOfDestiny/src/main/resources/fishingrodofdestiny/rltiles/](FishingRodOfDestiny/src/main/resources/fishingrodofdestiny/rltiles/) are in public domain. These are slightly modified, you can find the original tilesets at: [rltiles.sf.net](http://rltiles.sf.net).


## Releases

[Week 5](https://github.com/Peanhua/ot-harjoitustyo/releases/tag/week5)

[Week 6](https://github.com/Peanhua/ot-harjoitustyo/releases/tag/week6)


## Commandline functionality

All commands are supposed to be run in the project directory *FishingRodOfDestiny/*.

### Running

Fishing Rod of Destiny requires Java 8.

Assuming that the default Java is of proper version, the game can be run from commandline with the following command:
```mvn compile exec:java -Dexec.mainClass=fishingrodofdestiny.ui.FishingRodOfDestinyUi```


### Packaging

The game can be packaged as a *JAR* -file with the following command:
```mvn package```


### Testing

Tests can be run by:
```mvn test```

Test coverage can be generated with:
```mvn jacoco:report```

And the results can be seen by pointing browser to *target/site/jacoco/index.html*.


### JavaDoc

JavaDoc can be generated with:
```mvn javadoc:javadoc```

And the results can be seen by pointing browser to *target/site/apidocs/index.html*.
Some important classes to look at:
* fishingrodofdestiny.resources.ImageCache in *target/site/apidocs/fishingrodofdestiny/resources/ImageCache.html*
* fishingrodofdestiny.ui.screens.Screen in *target/site/apidocs/fishingrodofdestiny/ui/screens/Screen.html*
* fishingrodofdestiny.world.controllers.Controller in *target/site/apidocs/fishingrodofdestiny/world/controllers/Controller.html*
* fishingrodofdestiny.world.gameobjects.GameObject in *target/site/apidocs/fishingrodofdestiny/world/gameobjects/GameObject.html*
* fishingrodofdestiny.world.gameobjects.Location in *target/site/apidocs/fishingrodofdestiny/world/gameobjects/Location.html*


### Checkstyle

Checkstyle can be run by:
```mvn jxr:jxr checkstyle:checkstyle```

And the results can be seen by pointing browser to *target/site/checkstyle.html*.
