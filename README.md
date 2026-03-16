# AngelCards
The plugin that saves your stats as if it's like an insurance.

https://www.spigotmc.org/resources/angelcards.64831/

## Features

- Lightweight
- Highly configurable
- Flexible system design
- Command-based management
- Event-based logic
- Angel Card system
- Payment support
- Data persistence (card data saved)

---

## How It Works

The concept is simple.

When a player dies, the plugin checks whether the player possesses an **Angel Card**.

If an Angel Card is available:
- The player's **inventory**
- **Level**
- **Experience**

will be preserved.

Think of Angel Cards as a type of **insurance system**.  
If a player holds a card, they can recover their progress after death.

---

## Commands

### `/angelcards`

| Command | Description | Permission |
|--------|-------------|-----------|
| `/angelcards check` | Check your Angel Card balance | `angelcards.check` |
| `/angelcards check <player>` | Check another player's cards | `angelcards.check.others` |
| `/angelcards give <player> <amount>` | Give Angel Cards to a player | `angelcards.pay` |
| `/angelcards pay <player> <amount>` | Pay Angel Cards to another player | `angelcards.pay` |
| `/angelcards add <player> <amount>` | Add cards to a player | `angelcards.admin` |
| `/angelcards take <player> <amount>` | Remove cards from a player | `angelcards.admin` |
| `/angelcards set <player> <amount>` | Set a player's card amount | `angelcards.admin` |
| `/angelcards reload` | Reload the plugin configuration | `angelcards.admin` |

---
