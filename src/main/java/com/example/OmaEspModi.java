package com.example;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class OmaEspModi implements ModInitializer, ClientModInitializer {
    private static final int ETSI_SADE = 10;

    @Override
    public void onInitialize() {
        // Pääalustus valmis
    }

    @Override
    public void onInitializeClient() {
        // Kuunnellaan pelin tickejä renderöinnin sijaan (toimii kaikissa versioissa)
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null || client.level == null) return;

            // Ajetaan etsintä vain 5 sekunnin (100 tickin) välein, jotta peli ei lagi
            if (client.player.tickCount % 100 == 0) {
                BlockPos pPos = client.player.blockPosition();

                for (int x = -ETSI_SADE; x <= ETSI_SADE; x++) {
                    for (int y = -ETSI_SADE; y <= ETSI_SADE; y++) {
                        for (int z = -ETSI_SADE; z <= ETSI_SADE; z++) {
                            
                            BlockPos tilaPos = pPos.offset(x, y, z);
                            BlockState tila = client.level.getBlockState(tilaPos);

                            if (tila.is(Blocks.DIAMOND_ORE) || tila.is(Blocks.DEEPSLATE_DIAMOND_ORE)) {
                                // Tulostetaan timantin paikka suoraan lokitietoihin
                                System.out.println("[OmaXray] TIMANTTI LÖYDETTY: X:" + tilaPos.getX() + " Y:" + tilaPos.getY() + " Z:" + tilaPos.getZ());
                            }
                        }
                    }
                }
            }
        });
    }
}
