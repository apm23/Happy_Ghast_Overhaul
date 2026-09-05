package com.apm23.happyghastoverhaul.item;

import com.apm23.happyghastoverhaul.HappyGhastOverhaul;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.minecraft.world.item.equipment.Equippable;

public final class MilitaryHarnessItem extends Item {
    public static final ResourceKey<EquipmentAsset> SENTINEL_ASSET = ResourceKey.create(
            EquipmentAssets.ROOT_ID,
            Identifier.fromNamespaceAndPath(HappyGhastOverhaul.MOD_ID, "military_harness")
    );
    public static final ResourceKey<EquipmentAsset> REAPER_ASSET = ResourceKey.create(
            EquipmentAssets.ROOT_ID,
            Identifier.fromNamespaceAndPath(HappyGhastOverhaul.MOD_ID, "reaper_military_harness")
    );

    public MilitaryHarnessItem(Properties properties) {
        super(properties);
    }

    public static Properties properties(ResourceKey<EquipmentAsset> asset) {
        Equippable equippable = Equippable.builder(EquipmentSlot.BODY)
                .setEquipSound(SoundEvents.HARNESS_EQUIP)
                .setAllowedEntities(EntityTypes.HAPPY_GHAST)
                .setEquipOnInteract(true)
                .setDamageOnHurt(false)
                .setAsset(asset)
                .build();

        return new Properties()
                .stacksTo(1)
                .rarity(Rarity.RARE)
                .component(DataComponents.EQUIPPABLE, equippable);
    }
}
