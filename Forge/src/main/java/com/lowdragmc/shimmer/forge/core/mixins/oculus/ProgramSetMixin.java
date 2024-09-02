package com.lowdragmc.shimmer.forge.core.mixins.oculus;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.lowdragmc.shimmer.comp.iris.ShaderpackInjection;
import net.irisshaders.iris.shaderpack.include.AbsolutePackPath;
import net.irisshaders.iris.shaderpack.programs.ProgramSet;
import net.irisshaders.iris.shaderpack.properties.ShaderProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.Function;

@Mixin(ProgramSet.class)
public abstract class ProgramSetMixin {
    @ModifyExpressionValue(
            method = "readProgramSource(Lnet/irisshaders/iris/shaderpack/include/AbsolutePackPath;Ljava/util/function/Function;Ljava/lang/String;Lnet/irisshaders/iris/shaderpack/programs/ProgramSet;Lnet/irisshaders/iris/shaderpack/properties/ShaderProperties;Lnet/irisshaders/iris/gl/blending/BlendModeOverride;Z)Lnet/irisshaders/iris/shaderpack/programs/ProgramSource;",
            at = @At(value = "INVOKE",
                    target = "Ljava/util/function/Function;apply(Ljava/lang/Object;)Ljava/lang/Object;",
                    ordinal = 0)
    , remap = false)
    private static Object injectShaderpackVsh(Object value, AbsolutePackPath directory, Function<AbsolutePackPath, String> sourceProvider, String program, ProgramSet programSet, ShaderProperties properties){
        if (program.equals("gbuffers_terrain") && value instanceof String vsh) {
            return ShaderpackInjection.TERRAIN.injectTerrainVsh(vsh);
        }
        return value;
    }
}
