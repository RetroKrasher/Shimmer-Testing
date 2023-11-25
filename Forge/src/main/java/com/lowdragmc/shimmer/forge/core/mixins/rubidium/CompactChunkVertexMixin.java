package com.lowdragmc.shimmer.forge.core.mixins.rubidium;

import me.jellysquid.mods.sodium.client.render.chunk.terrain.material.Material;
import me.jellysquid.mods.sodium.client.render.chunk.vertex.format.ChunkVertexEncoder;
import me.jellysquid.mods.sodium.client.render.chunk.vertex.format.impl.CompactChunkVertex;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = CompactChunkVertex.class, remap = false)
public abstract class CompactChunkVertexMixin {
    @Shadow
    private static int encodeDrawParameters(Material par1, int par2) {
        throw new RuntimeException();
    }

    @Redirect(method = "lambda$getEncoder$0", at = @At(value = "INVOKE", target = "Lme/jellysquid/mods/sodium/client/render/chunk/vertex/format/impl/CompactChunkVertex;encodeDrawParameters(Lme/jellysquid/mods/sodium/client/render/chunk/terrain/material/Material;I)I"))
    private static int injectMaterialForBloom(Material material, int i, long ptr, Material material1, ChunkVertexEncoder.Vertex vertex, int sectionIndex) {
        var origin = encodeDrawParameters(material,i);
        if ((vertex.light & 0x100) != 0) {
            origin |= (0x01 << 4);
        }
        return origin;
    }
}
