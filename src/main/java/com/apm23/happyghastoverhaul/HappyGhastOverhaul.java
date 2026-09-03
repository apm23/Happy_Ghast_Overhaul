package com.apm23.happyghastoverhaul;

import com.apm23.happyghastoverhaul.gameplay.MilitaryHarnessEffects;
import com.apm23.happyghastoverhaul.item.MilitaryHarnessItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(HappyGhastOverhaul.MOD_ID)
public final class HappyGhastOverhaul {
    public static final String MOD_ID = "happy_ghast_overhaul";

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);

    public static final DeferredItem<MilitaryHarnessItem> MILITARY_HARNESS = ITEMS.registerItem(
            "military_harness",
            MilitaryHarnessItem::new,
            MilitaryHarnessItem::properties
    );

    public HappyGhastOverhaul(IEventBus modEventBus, ModContainer modContainer) {
        ITEMS.register(modEventBus);
        modEventBus.addListener(this::addCreativeTabEntries);
        NeoForge.EVENT_BUS.register(new MilitaryHarnessEffects());
    }

    private void addCreativeTabEntries(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(MILITARY_HARNESS);
        }
    }
}
