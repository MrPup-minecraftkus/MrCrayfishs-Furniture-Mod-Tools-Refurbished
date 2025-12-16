package net.mrpup.furniture_mod_tools_refurbished.item;

import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.mrpup.furniture_mod_tools_refurbished.MrCrayfishsFurnitureModToolsRefurbished;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModComponents {
    public static final DeferredRegister.DataComponents REGISTRAR =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, MrCrayfishsFurnitureModToolsRefurbished.MOD_ID);

    private static final Codec<Integer> MODE_CODEC = Codec.INT;

    private static final StreamCodec<RegistryFriendlyByteBuf, Integer> MODE_STREAM_CODEC = ByteBufCodecs.INT.cast();

    public static final Supplier<DataComponentType<Integer>> TOOL_MODE = REGISTRAR.registerComponentType(
            "tool_mode",
            builder -> builder
                    .persistent(MODE_CODEC)
                    .networkSynchronized(MODE_STREAM_CODEC)
    );

    public static DataComponentType<Integer> getToolMode() {
        return TOOL_MODE.get();
    }

    public static void register(net.neoforged.bus.api.IEventBus eventBus) {
        REGISTRAR.register(eventBus);
    }
}
