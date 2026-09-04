package com.apm23.happyghastoverhaul;

import com.apm23.happyghastoverhaul.item.MilitaryHarnessItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;

public final class HappyGhastOverhaul implements ModInitializer {
    public static final String MOD_ID = "happy_ghast_overhaul";
    public static final Identifier MILITARY_HARNESS_ID = Identifier.fromNamespaceAndPath(MOD_ID, "military_harness");
    public static final ResourceKey<Item> MILITARY_HARNESS_KEY = ResourceKey.create(Registries.ITEM, MILITARY_HARNESS_ID);
    public static final MilitaryHarnessItem MILITARY_HARNESS = new MilitaryHarnessItem(
            MilitaryHarnessItem.properties().setId(MILITARY_HARNESS_KEY)
    );

    @Override
    public void onInitialize() {
        Registry.register(BuiltInRegistries.ITEM, MILITARY_HARNESS_KEY, MILITARY_HARNESS);
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES)
                .register(entries -> entries.accept(MILITARY_HARNESS));
    }
}
