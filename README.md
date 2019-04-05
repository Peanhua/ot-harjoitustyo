# The Fishing Rod of Destiny

The software is a [roguelike game](https://en.wikipedia.org/wiki/Roguelike) in which the player descends through a set of dungeon levels to find certain item, and then ascends back to the surface. The player will face dangers such as enemies and traps. The player character can be levelled up, and the player can find tools (mainly armor and weapons) to help in the quest.

## Documentation

* [Timesheet](documentation/timesheet.md)
* [Requirements Specification](documentation/requirements_specification.md)


## Commandline functionality

All commands are supposed to be run in the project directory *FishingRodOfDestiny/*.

## Running

The game can be run from commandline with the following command:
```mvn compile exec:java -Dexec.mainClass=fishingrodofdestiny.ui.FishingRodOfDestinyUi```


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
