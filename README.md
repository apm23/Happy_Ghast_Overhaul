# Happy Ghast Overhaul

Minecraft Java 26.2 NeoForge mod focused on turning the Happy Ghast into a durable long-range flying mount.

## Planned gameplay

- Happy Ghast base max health: 250 HP.
- Base ridden flight speed: 1.25x vanilla.
- Heavy general durability while remaining damageable.
- Diamond Harness upgrade recipe: vanilla Harness in the center, Diamonds above/below/left/right.
- Diamond Harness bonus max health: +100 HP.
- Diamond Harness resistance: 90% reduction to melee, projectile, and explosion damage.
- Diamond Harness immunity to fire and lava.
- Snowball healing uses current max health: each snowball heals 50% of the Happy Ghast's actual max health.
- Diamond Harness staged boost controlled from the client:
  - normal: 1.0x upgraded base speed
  - Ctrl press 1: 1.5x
  - Ctrl press 2: 2.0x
  - Ctrl press 3: 3.0x
  - Ctrl press 4: normal
- Small action-bar speed indicator; no chat spam.
- Void/admin kill-style damage should not be blocked.

## Architecture

The mod is intended as one JAR with server-authoritative health/damage/healing logic and a small client component for the Ctrl boost input and ridden movement adjustment.

Target loader: NeoForge, Minecraft Java 26.2.
