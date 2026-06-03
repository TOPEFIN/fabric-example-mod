package net.fabricmc.example.mixin;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public class BlockMixin {

    @Inject(method = "shouldRenderFace", at = @At("HEAD"), cancellable = true)
    private static void onShouldRenderFace(BlockState state, Object level, Object pos, Object face, Object blockPos, CallbackInfoReturnable<Boolean> cir) {
        // Haetaan palikan sisäinen nimi tekstinä (esim. "block.minecraft.diamond_ore")
        String palikanNimi = state.getBlock().getDescriptionId();

        // Määritetään ne tekstinpätkät, jotka halutaan säästää näkyvissä
        boolean onMalmi = palikanNimi.contains("ore") || 
                          palikanNimi.contains("chest") || 
                          palikanNimi.contains("spawner") || 
                          palikanNimi.contains("portal");

        if (onMalmi) {
            // Jos palikka on malmi tai arkku, se pakotetaan näkymään aina
            cir.setReturnValue(true);
        } else {
            // Kaikki muu (kivi, multa, hiekka) muuttuu näkymättömäksi
            cir.setReturnValue(false);
        }
    }
}
