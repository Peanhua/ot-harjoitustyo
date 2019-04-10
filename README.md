# The Fishing Rod of Destiny

The software is a [roguelike game](https://en.wikipedia.org/wiki/Roguelike) in which the player descends through a set of dungeon levels to find certain item, and then ascends back to the surface. The player will face dangers such as enemies and traps. The player character can be levelled up, and the player can find tools (mainly armor and weapons) to help in the quest.

## Documentation

* [Architecture](documentation/architecture.md)
* [Requirements Specification](documentation/requirements_specification.md)
* [Timesheet](documentation/timesheet.md)

## License

This software is licensed under [The GNU General Public License version 3](LICENSE), with the following exceptions:

* [The RLTiles tileset](rltiles.sf.net) under [FishingRodOfDestiny/src/main/resources/fishingrodofdestiny/rltiles/](FishingRodOfDestiny/src/main/resources/fishingrodofdestiny/rltiles/) are in public domain. You can find the original tileset at: [rltiles.sf.net](rltiles.sf.net).
* [The DawnLike tileset](https://opengameart.org/users/dragondeplatino) by [DragonDePlatino](https://opengameart.org/users/dragondeplatino) under [FishingRodOfDestiny/src/main/resources/fishingrodofdestiny/DawnLike/](FishingRodOfDestiny/src/main/resources/fishingrodofdestiny/DawnLike) are licensed under the [CC-BY-SA 4.0](https://creativecommons.org/licenses/by/4.0/) license.


## Commandline functionality

All commands are supposed to be run in the project directory *FishingRodOfDestiny/*.

## Running

The game can be run from commandline with the following command:
```mvn compile exec:java -Dexec.mainClass=fishingrodofdestiny.ui.FishingRodOfDestinyUi```


## Packaging

The game can be packaged as a *JAR* -file with the following command:
```mvn package```


### Testing

Tests can be run by:
```mvn test```

Test coverage can be generated with:
```mvn jacoco:report```

And the results can be seen by pointing browser to *target/site/jacoco/index.html*.


### Checkstyle

Checkstyle can be run by:
```mvn jxr:jxr checkstyle:checkstyle```

And the results can be seen by pointing browser to *target/site/checkstyle.html*.
