package com.apm23.happyghastoverhaul;

import com.apm23.happyghastoverhaul.item.MilitaryHarnessItem;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

public final class HappyGhastOverhaul implements ModInitializer {
    public static final String MOD_ID = "happy_ghast_overhaul";

    // Keep the original id as Sentinel Blue so existing worlds/items remain valid.
    public static final Identifier MILITARY_HARNESS_ID = Identifier.fromNamespaceAndPath(MOD_ID, "military_harness");
    public static final ResourceKey<Item> MILITARY_HARNESS_KEY = ResourceKey.create(Registries.ITEM, MILITARY_HARNESS_ID);
    public static final MilitaryHarnessItem MILITARY_HARNESS = new MilitaryHarnessItem(
            MilitaryHarnessItem.properties(MilitaryHarnessItem.SENTINEL_ASSET).setId(MILITARY_HARNESS_KEY)
    );

    public static final Identifier REAPER_HARNESS_ID = Identifier.fromNamespaceAndPath(MOD_ID, "reaper_military_harness");
    public static final ResourceKey<Item> REAPER_HARNESS_KEY = ResourceKey.create(Registries.ITEM, REAPER_HARNESS_ID);
    public static final MilitaryHarnessItem REAPER_HARNESS = new MilitaryHarnessItem(
            MilitaryHarnessItem.properties(MilitaryHarnessItem.REAPER_ASSET).setId(REAPER_HARNESS_KEY)
    );

    @Override
    public void onInitialize() {
        Registry.register(BuiltInRegistries.ITEM, MILITARY_HARNESS_KEY, MILITARY_HARNESS);
        Registry.register(BuiltInRegistries.ITEM, REAPER_HARNESS_KEY, REAPER_HARNESS);
    }
}
