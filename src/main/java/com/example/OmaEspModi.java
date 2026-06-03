package com.example;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class OmaEspModi implements ClientModInitializer {
    private static final boolean ESP_PAALLA = true;
    private static final int SATEY_VALI = 15; // Kuinka monta palikkaa ympäriltä etsitään

    @Override
    public void onInitializeClient() {
        // Rekisteröidään ESP piirtymään aina, kun peli renderöi maailmaa
        WorldRenderEvents.LAST.register(context -> {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player == null || mc.level == null || !ESP_PAALLA) return;

            // Haetaan pelaajan nykyinen sijainti pyöristettynä lohkoksi
            BlockPos pelaajaPos = mc.player.blockPosition();

            // Selaataan pelaajan ympärillä oleva alue (X, Y, Z -akselit)
            for (int x = -SATEY_VALI; x <= SATEY_VALI; x++) {
                for (int y = -SATEY_VALI; y <= SATEY_VALI; y++) {
                    for (int z = -SATEY_VALI; z <= SATEY_VALI; z++) {
                        
                        BlockPos tilaPos = pelaajaPos.offset(x, y, z);
                        BlockState tila = mc.level.getBlockState(tilaPos);

                        // Tarkistetaan, onko palikka Timantti vai Netherite (Ancient Debris)
                        boolean onKohde = tila.is(Blocks.DIAMOND_ORE) || 
                                          tila.is(Blocks.DEEPSLATE_DIAMOND_ORE) || 
                                          tila.is(Blocks.ANCIENT_DEBRIS);

                        if (onKohde) {
                            // Lasketaan 3D-laatikon paikka kameran sijaintiin nähden
                            double peliX = tilaPos.getX() - context.camera().getPosition().x;
                            double peliY = tilaPos.getY() - context.camera().getPosition().y;
                            double peliZ = tilaPos.getZ() - context.camera().getPosition().z;

                            // Piirretään punainen 3D-ääriviivalaatikko (ESP) palikan ympärille
                            LevelRenderer.renderLineBox(
                                context.matrixStack(),
                                context.consumers().getBuffer(net.minecraft.client.renderer.RenderType.lines()),
                                peliX, peliY, peliZ,
                                peliX + 1, peliY + 1, peliZ + 1,
                                1.0F, 0.0F, 0.0F, 1.0F // Punainen väri (R, G, B, Alpha)
                            );
                        }
                    }
                }
            }
        });
    }
}
