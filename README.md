# Happy Ghast Overhaul

Minecraft Java 26.2 NeoForge mod focused exclusively on the **Military Harness** upgrade for Happy Ghasts.

## Core rule

Happy Ghasts without the Military Harness remain 100% vanilla. The mod does not globally change their health, speed, damage handling, or healing.

## Military Harness

- Display name: `Military Harness`
- Internal id: `military_harness`
- Recipe: vanilla Harness in the center with Diamonds above, below, left, and right.
- All bonuses below are active only while the Military Harness is equipped.
- Removing/replacing the Military Harness removes its bonuses cleanly.

### Durability

- +250 max health as an attribute modifier on top of the Happy Ghast's actual base/current max-health system; do not hardcode a final total HP.
- 90% damage reduction against melee damage.
- 90% damage reduction against projectile damage.
- 90% damage reduction against explosion damage.
- Complete immunity to fire and lava damage.
- Do not protect against void/admin kill-style damage.

### Snowball healing

- Military Harness only: each valid snowball heal restores 50% of the Happy Ghast's actual current maximum health.
- Healing is capped at current maximum health.
- Do not hardcode the heal amount, so other compatible max-health changes remain respected.
- Without Military Harness, retain vanilla snowball behavior unchanged.

### Flight speed levels

Military Harness provides exactly four speed levels, cycled with Left Ctrl:

- Lv1: 1.0x vanilla speed
- Lv2: 1.5x vanilla speed
- Lv3: 2.0x vanilla speed
- Lv4: 3.0x vanilla speed
- Next Left Ctrl press cycles Lv4 back to Lv1.

Lv1 is the normal/vanilla speed; there is no separate `Normal` state. Speed modification must only apply while riding a Happy Ghast equipped with Military Harness. Show a small action-bar speed-level indicator without chat spam.

## Architecture

One NeoForge JAR for Minecraft Java 26.2. Health, damage protection, fire/lava immunity, harness state, and snowball healing should be server-authoritative. A small client component may capture Left Ctrl and communicate the requested speed-level change to the server. Avoid global Happy Ghast patches wherever a harness-gated implementation is possible.
