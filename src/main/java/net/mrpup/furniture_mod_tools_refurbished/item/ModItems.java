package net.mrpup.furniture_mod_tools_refurbished.item;

import net.minecraft.world.item.Item;
import net.mrpup.furniture_mod_tools_refurbished.MrCrayfishsFurnitureModToolsRefurbished;
import net.mrpup.furniture_mod_tools_refurbished.item.custom.BreakTool;
import net.mrpup.furniture_mod_tools_refurbished.item.custom.MultiTool;
import net.mrpup.furniture_mod_tools_refurbished.item.custom.RotationTool;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MrCrayfishsFurnitureModToolsRefurbished.MOD_ID);

    public static final DeferredItem<Item> ROTATION_TOOL = ITEMS.registerItem("rotation_tool",
            properties  -> new RotationTool(properties.stacksTo(1)));

    public static final DeferredItem<Item> BREAK_TOOL = ITEMS.registerItem("break_tool",
            properties  -> new BreakTool(properties.stacksTo(1)));

    public static final DeferredItem<Item> MULTI_TOOL = ITEMS.registerItem("multi_tool",
            properties  -> new MultiTool(properties.stacksTo(1)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
