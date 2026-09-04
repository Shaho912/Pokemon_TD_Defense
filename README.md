# Pokemon TD Defense

A Java tower-defense game built with Swing, where you place Pokémon along a path to fend off waves of enemies before they reach your base.

## Gameplay

Place Charizard, Blastoise, and Pikachu towers along the enemy path to attack incoming Pokémon like Zubat, Houndoom, and Mewtwo Y. Towers gain XP as they land hits, leveling up and eventually mega-evolving into stronger forms with upgraded stats and attacks. Survive each wave, manage your resources, and stop enemies from breaking through.

## Features

- **Multiple tower types** — Charizard (Fire Blast), Blastoise (Hydro Pump), and Pikachu (Electro Ball), each with unique damage, range, and attack speed
- **Leveling & mega-evolution** — towers gain XP from kills and level up, with mega-evolved forms unlocking stronger stats
- **Wave-based enemy spawning** — enemies (Zubat, Houndoom, Mewtwo Y) spawn in waves with increasing difficulty
- **Custom sprites & animations** — GIF-based sprite animations for towers, attacks, and enemies
- **Collision & pathing logic** — enemies follow a defined path with hitbox-based collision detection for attacks

## Tech Stack

- **Language:** Java
- **UI:** Java Swing
- **Build tool:** Maven
- **Testing:** JUnit

## Project Structure
```
src/main/java/org/cis1200/pokemon/
├── GameObj.java          # Base class for all drawable/movable objects
├── Enemy.java            # Enemy base class
│   ├── Zubat.java
│   ├── Houndoom.java
│   └── MewtwoY.java
├── Tower.java            # Tower base class
│   ├── Charizard.java
│   ├── Blastoise.java
│   └── Pikachu.java
├── Attack.java           # Attack base class
│   ├── FireBlast.java
│   ├── HydroPump.java
│   └── ElectroBall.java
├── GameCourt.java        # Core game loop, spawning, collision, scoring
├── FloatingTowerWindow.java
├── Direction.java
├── PathEnd.java
└── RunPokemonTD.java     # Entry point
```


## Running the Game

```bash
mvn compile
mvn exec:java -Dexec.mainClass="org.cis1200.pokemon.RunPokemonTD"
```

## Running Tests

```bash
mvn test
```

## Core Design Concepts

- **Collections** — Lists manage enemies, towers, attacks, and path waypoints; waves are loaded and iterated dynamically at runtime.
- **Inheritance & subtyping** — `GameObj` is the shared parent for all drawable/movable objects, with `Enemy`, `Tower`, and `Attack` subclasses defining unique behavior per Pokémon.
- **Unit testing** — Isolated JUnit tests cover position/movement/collision logic, attack targeting, tower leveling, and game-court behavior (damage, placement validity, spawning).
- **File I/O** — Sprites and GIFs are loaded from the `files/` directory at runtime, with error handling for missing assets.

## Author

Built by Shaho as a project for CIS 1200 (Programming Languages and Techniques) at the University of Pennsylvania.
