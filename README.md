# Minecraft Damage Indicator Plugin

A Minecraft plugin that displays damage dealt to entities in real-time using the action bar.

## Features

- Shows damage dealt to entities when you attack them
- Displays victim's remaining health and max health
- Works with both melee and projectile attacks
- Toggle damage display on/off with a simple command
- Action bar display for clean, non-intrusive feedback

## Building the Plugin

1. Make sure you have Maven installed
2. Run the following command:
   ```bash
   mvn clean package
   ```
3. The compiled plugin JAR will be in the `target` directory

## Installation

1. Build the plugin using Maven
2. Copy the generated JAR file from `target/damage-indicator-1.0.0.jar`
3. Place it in your server's `plugins` folder
4. Restart your server or reload plugins

## Usage

### Commands

- `/damageindicator` or `/di` - Toggle damage indicator on/off
- `/damageindicator on` - Enable damage indicator
- `/damageindicator off` - Disable damage indicator

### Permissions

- `damageindicator.use` - Allows players to see damage indicators (default: true)
- `damageindicator.toggle` - Allows players to toggle the feature (default: true)

## How It Works

When you deal damage to an entity, you'll see a message in your action bar showing:
- The entity's name
- The amount of damage dealt
- The remaining health / max health of the entity

Example: `Zombie -5.0 ♥15.0/20.0`

## Requirements

- Minecraft Server 1.20.4 (compatible with Spigot/Paper)
- Java 17 or higher

## Support

This plugin is enabled by default for all players. Players can toggle it on or off using the `/di` command.
