# Happy Ghast Overhaul

Minecraft Java 26.2 **Fabric** mod focused on the **Military Harness** upgrade for Happy Ghasts.

## Core rule

Happy Ghasts without the Military Harness remain vanilla. The mod does not globally change their health, flight behavior, damage handling, or healing.

## Military Harness

- Display name: `Military Harness`
- Internal id: `happy_ghast_overhaul:military_harness`
- Recipe: vanilla Harness in the center with Diamonds above, below, left, and right.
- All bonuses below are active only while the Military Harness is equipped.
- Removing/replacing the Military Harness removes its bonuses cleanly.

### Durability

- +250 max health as an attribute modifier on top of the Happy Ghast's actual max health.
- 90% damage reduction against melee damage.
- 90% damage reduction against projectile damage.
- 90% damage reduction against explosion damage.
- Complete immunity to fire and lava damage.
- Void/admin-style absolute damage remains untouched.

### Snowball healing

- Military Harness only: each valid snowball hit heals 50% of the Happy Ghast's current maximum health.
- Healing is capped at current maximum health.
- Without Military Harness, vanilla snowball behavior remains unchanged.

### Flight controls

The Military Harness replaces ridden flight handling with a responsive hover controller while the harness is equipped:

- WASD: horizontal movement
- Space: ascend
- Caps Lock: descend by default
- R: cycle speed level by default
- Left Shift: vanilla dismount
- Descend and Cycle Speed are normal Minecraft keybinds and can be changed under Options -> Controls -> Key Binds.
- Looking up/down does not change altitude.
- With no vertical input, the Happy Ghast holds altitude instead of slowly sinking.

### Flight speed levels

- Lv1: 4x vanilla speed
- Lv2: 8x vanilla speed
- Lv3: 12x vanilla speed
- Lv4: 20x vanilla speed
- Cycling from Lv4 returns to Lv1.

A small action-bar indicator shows the active speed level without chat spam.

## Platform

- Minecraft Java 26.2
- Fabric Loader 0.19.3
- Fabric API 0.159.0+26.2
- Java 25
- Fabric Loom 1.17

## Compile from source

Install Java 25 and Gradle 9.5.1, clone this repository, then run:

```bash
gradle clean build
```

The remapped production JAR is created in:

```text
build/libs/
```

For runtime smoke testing from source:

```bash
gradle runServer
gradle runClient
```

GitHub Actions performs the same production build plus packaged-JAR verification, dedicated Fabric server boot smoke, and Fabric client boot smoke.
