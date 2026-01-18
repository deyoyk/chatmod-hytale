# ChatMod

A powerful chat moderation plugin for Hytale servers that filters offensive language and creates a cleaner chat environment.

## Features

- **Automatic Chat Filtering**: Automatically detects and blocks offensive language in player chat
- **Banned Words List**: Comes pre-configured with 50+ common offensive words - fully customizable
- **Flexible Filtering Options**: Choose between censoring words (replacing with *****) or blocking entire messages
- **Customizable Warning Messages**: Set your own warning message to display to players
- **Color-Coded Warnings**: Choose from 8 different colors for warning messages (RED, GREEN, BLUE, YELLOW, WHITE, BLACK, ORANGE, GRAY)
- **Live Config Reload**: Reload configuration without restarting the server

## Installation

1. Download the latest `ChatMod.jar` from the releases
2. Place the jar file in your server's `plugins` folder
3. Start your server
4. The plugin will automatically generate a `words.yml` configuration file

## Configuration

The plugin creates a `words.yml` file in your plugin data folder with the following options:

```yaml
# Custom warning message shown to players
message: Hey, you cannot say that!

# Warning message color (RED, GREEN, BLUE, YELLOW, WHITE, BLACK, ORANGE, GRAY)
color: RED

# If true, replaces banned words with *****
# If false, blocks the entire message
censorwords: false

# Add your banned words below
banned_words:
  - fuck
  - shit
  - ass
  - bitch
  # ... add as many as you need
```

### Configuration Options

- **message**: The warning message shown to players when they use banned language
- **color**: The color of the warning message (RED, GREEN, BLUE, YELLOW, WHITE, BLACK, ORANGE, GRAY)
- **censorwords**: 
  - `true`: Replaces banned words with asterisks (*****)
  - `false`: Blocks the entire message and shows the warning
- **banned_words**: List of words to filter (case-insensitive)

## Commands

| Command | Description | Permission |
|---------|-------------|------------|
| `/reloadchat` | Reloads the configuration file | Adventure+ |

## Usage

1. **Edit the Configuration**: Open `words.yml` and customize the settings to your preference
2. **Add/Remove Words**: Modify the `banned_words` list to fit your server's needs
3. **Reload Changes**: Use `/reloadchat` in-game to apply changes without restarting
4. **Test the Filter**: Try sending messages with banned words to verify the filter works

## Support & Updates

- **Discord**: https://discord.gg/phAeHvbgcH
- Join our Discord server for support, updates, and feature requests

## Credits

**Made by deyo**

Version: 0.1.0
