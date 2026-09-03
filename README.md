# Happy Ghast Overhaul

Minecraft Java 26.2 NeoForge mod focused entirely on a custom upgraded Happy Ghast harness.

## Design rule

Vanilla Happy Ghasts must remain completely vanilla when the custom harness is not equipped. The mod must not globally change Happy Ghast health, normal flight speed, durability, healing, or damage behavior.

## Planned gameplay

- Happy Ghast without the custom harness: fully vanilla behavior and stats.
- Custom Diamond Harness recipe: vanilla Harness in the center, Diamonds above/below/left/right.
- Equipping the Custom Diamond Harness applies all upgrades.
- Custom Harness bonus max health: +250 HP above the Happy Ghast's vanilla max health.
- Removing/replacing the Custom Harness removes its bonus max health and all other custom effects safely.
- Normal ridden flight speed with the Custom Harness: vanilla speed (no passive speed increase).
- Custom Harness resistance: 90% reduction to melee, projectile, and explosion damage.
- Custom Harness immunity to fire and lava.
- While the Custom Harness is equipped, snowball healing uses current actual max health: each snowball heals 50% of that max health.
- Custom Harness staged boost controlled from the client:
  - normal: 1.0x vanilla ridden speed
  - Ctrl press 1: 1.5x
  - Ctrl press 2: 2.0x
  - Ctrl press 3: 3.0x
  - Ctrl press 4: back to 1.0x
- Small action-bar speed indicator; no chat spam.
- Void/admin kill-style damage should not be blocked.

## Effective health target

If the vanilla Happy Ghast max health is 100 HP in the target Minecraft version, equipping the Custom Diamond Harness results in 350 HP total. The implementation should apply a +250 HP modifier rather than hardcoding total health, so the harness remains compatible with another mod or future vanilla change that alters Happy Ghast base max health.

## Architecture

One JAR with server-authoritative harness detection, max-health modifier, damage resistance, immunity, and snowball healing. A small client component handles Ctrl boost input and ridden movement adjustment. All custom gameplay behavior must be gated behind the Custom Diamond Harness being equipped.

Target loader: NeoForge, Minecraft Java 26.2.
