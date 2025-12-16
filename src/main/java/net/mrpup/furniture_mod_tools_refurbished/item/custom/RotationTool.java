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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;

public class RotationTool extends Item {

    private static final String TARGET_MOD_ID = "refurbished_furniture";

    public RotationTool(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();

        BlockState oldState = world.getBlockState(pos);

        if (!isBlockFromTargetMod(oldState)) {
            return InteractionResult.PASS;
        }

        if (world.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        BlockState newState = oldState.rotate(Rotation.CLOCKWISE_90);

        if (oldState != newState) {

            world.setBlock(pos, newState, Block.UPDATE_ALL);

            world.playSound(
                    null,
                    pos,
                    SoundEvents.WOOD_HIT,
                    SoundSource.BLOCKS,
                    1.0F,
                    0.8F
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
