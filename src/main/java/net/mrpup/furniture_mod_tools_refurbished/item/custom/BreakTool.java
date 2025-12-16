package net.mrpup.furniture_mod_tools_refurbished.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class BreakTool extends Item {

    private static final String TARGET_MOD_ID = "refurbished_furniture";

    public BreakTool(Properties properties) {
        super(properties);
    }


    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();

        if (world.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        BlockState blockState = world.getBlockState(pos);

        if (!isBlockFromTargetMod(blockState)) {
            return InteractionResult.SUCCESS;
        }

        boolean broken = world.destroyBlock(pos, true);

        if (broken) {
            world.playSound(
                    null,
                    pos,
                    SoundEvents.TUFF_BREAK,
                    SoundSource.BLOCKS,
                    1.0F,
                    1.0F
            );

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.SUCCESS;
    }

    private boolean isBlockFromTargetMod(BlockState state) {
        ResourceLocation blockId = BuiltInRegistries.BLOCK.getKey(state.getBlock());
        return blockId.getNamespace().equals(TARGET_MOD_ID);
    }
}
