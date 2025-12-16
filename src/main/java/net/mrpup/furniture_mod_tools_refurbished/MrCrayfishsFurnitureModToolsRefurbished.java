package net.mrpup.furniture_mod_tools_refurbished;

import net.mrpup.furniture_mod_tools_refurbished.item.ModComponents;
import net.mrpup.furniture_mod_tools_refurbished.item.ModItemGroup;
import net.mrpup.furniture_mod_tools_refurbished.item.ModItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@Mod(MrCrayfishsFurnitureModToolsRefurbished.MOD_ID)
public class MrCrayfishsFurnitureModToolsRefurbished {
    public static final String MOD_ID = "furniture_mod_tools_refurbished";

    public MrCrayfishsFurnitureModToolsRefurbished(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        ModComponents.register(modEventBus);

        ModItems.register(modEventBus);

        ModItemGroup.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);
    }

    private void commonSetup(FMLCommonSetupEvent event) {

    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }
}
