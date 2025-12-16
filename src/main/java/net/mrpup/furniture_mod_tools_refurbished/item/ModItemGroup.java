package net.mrpup.furniture_mod_tools_refurbished.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.mrpup.furniture_mod_tools_refurbished.MrCrayfishsFurnitureModToolsRefurbished;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModItemGroup {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MrCrayfishsFurnitureModToolsRefurbished.MOD_ID);


    public static final Supplier<CreativeModeTab> FURNITURE_TOOLS_MOD = CREATIVE_MODE_TAB.register("furniture_mod_tools_refurbished",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.ROTATION_TOOL.get()))
                    .title(Component.translatable("itemGroup.furniture_mod_tools_refurbished.furniture_mod_tools_refurbished"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.ROTATION_TOOL);
                        output.accept(ModItems.BREAK_TOOL);
                        output.accept(ModItems.MULTI_TOOL);

                    }).build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
