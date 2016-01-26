#DESIGN.md

## High-Level Design Concepts
I attempted to abstract out the concept of a level (GameWorld). I also realized that my splash screens and menu could be viewed as "levels" (they execute the game loop but don't really update anything). There was a LevelChanger controlling the flow of levels. The people and objects were modeled as Sprites and there was a SpriteManager.

## Adding new features
To add new features, I would first centralize the duplicated code. We could add health bars to each sprite by grouping a rectangle node to the sprite parent node and modifying this rectangle as we go along. We could add powerups that move down the screen just like drones. We could add different kind of enemies by creating different types of sprites.

## Design Tradeoffs
I honestly tried to follow the readings and links about game design. I think it more or less turned out to be along those lines.
