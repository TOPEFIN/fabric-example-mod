package com.example.mixin;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.core.Direction;
import net.core.BlockPos;
import net.world.level.BlockGetter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public class BlockMixin {
    private static boolean xrayAktiivinen = true;

    @Inject(method = "shouldRenderFace", at = @At("HEAD"), cancellable = true)
    private static void onShouldRenderFace(BlockState state, BlockGetter level, BlockPos pos, Direction face, BlockPos blockPos, CallbackInfoReturnable<Boolean> cir) {
        if (!xrayAktiivinen) return;

        Block block = state.getBlock();

        boolean onArvokas = block == Blocks.DIAMOND_ORE || 
                            block == Blocks.DEEPSLATE_DIAMOND_ORE ||
                            block == Blocks.GOLD_ORE || 
                            block == Blocks.DEEPSLATE_GOLD_ORE ||
                            block == Blocks.IRON_ORE || 
                            block == Blocks.DEEPSLATE_IRON_ORE ||
                            block == Blocks.CHEST ||
                            block == Blocks.SPAWNER;

        if (onArvokas) {
            cir.setReturnValue(true);
        } else {
            cir.setReturnValue(false);
        }
    }
}
