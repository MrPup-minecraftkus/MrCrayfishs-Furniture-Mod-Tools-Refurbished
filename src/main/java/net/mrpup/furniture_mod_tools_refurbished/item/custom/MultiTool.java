package net.mrpup.furniture_mod_tools_refurbished.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.mrpup.furniture_mod_tools_refurbished.item.ModComponents;

import java.util.function.Consumer;

public class MultiTool extends Item {

    private static final String TARGET_MOD_ID = "refurbished_furniture";

    private static final int MODE_A_ROTATE = 0;
    private static final int MODE_B_BREAK = 1;

    public MultiTool(Properties properties) {
        super(properties);
    }

    public static int getCurrentMode(ItemStack stack) {
        return stack.getOrDefault(ModComponents.TOOL_MODE, MODE_A_ROTATE);
    }

    public static void setMode(ItemStack stack, int mode) {
        stack.set(ModComponents.TOOL_MODE, mode);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand usedHand) {

        ItemStack stack = player.getItemInHand(usedHand);

        if (player.isShiftKeyDown()) {
            if (!level.isClientSide()) {
                int currentMode = getCurrentMode(stack);
                int newMode = (currentMode == MODE_A_ROTATE) ? MODE_B_BREAK : MODE_A_ROTATE;
                setMode(stack, newMode);

                Component modeName = getModeName(newMode);

                player.displayClientMessage(
                        Component.literal("Mode changed to: ").withStyle(net.minecraft.ChatFormatting.GOLD)
                                .append(modeName), true);
            }
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }


    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        Player player = context.getPlayer();
        BlockPos pos = context.getClickedPos();
        ItemStack stack = context.getItemInHand();

        if (player == null) {
            return InteractionResult.PASS;
        }

        BlockState oldState = world.getBlockState(pos);

        if (!isBlockFromTargetMod(oldState)) {
            return InteractionResult.PASS;
        }

        int currentMode = getCurrentMode(stack);

        if (world.isClientSide()) {
            return InteractionResult.CONSUME;
        }

        if (currentMode == MODE_A_ROTATE) {

            BlockState newState = oldState.rotate(Rotation.CLOCKWISE_90); // Виправлено: Rotation

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

        } else if (currentMode == MODE_B_BREAK) {

            boolean removed = world.destroyBlock(pos, true);

            if (removed) {
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
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        int currentMode = getCurrentMode(stack);
        Component modeName = getModeName(currentMode);

        Component modeLine = Component.literal("Currently Mode: ")
                .withStyle(net.minecraft.ChatFormatting.GRAY)
                .append(modeName);

        tooltipAdder.accept(modeLine);

        Component instructionLine = Component.literal("Hold SHIFT + RIGHT CLICK to change the mode")
                .withStyle(net.minecraft.ChatFormatting.DARK_GRAY, net.minecraft.ChatFormatting.ITALIC);

        tooltipAdder.accept(instructionLine);
    }


    private Component getModeName(int mode) {
        if (mode == MODE_A_ROTATE) {
            return Component.literal("Rotation").withStyle(net.minecraft.ChatFormatting.AQUA);
        } else if (mode == MODE_B_BREAK) {
            return Component.literal("BREAK").withStyle(net.minecraft.ChatFormatting.RED);
        }
        return Component.literal("ERROR- NONE MODE").withStyle(net.minecraft.ChatFormatting.DARK_PURPLE);
    }

    private boolean isBlockFromTargetMod(BlockState state) {
        ResourceLocation blockId = BuiltInRegistries.BLOCK.getKey(state.getBlock());
        return blockId.getNamespace().equals(TARGET_MOD_ID);
    }
}