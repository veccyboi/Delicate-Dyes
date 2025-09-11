package net.azurune.delicate_dyes.core.mixin;

import net.azurune.delicate_dyes.common.util.DDUtil;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.material.MapColor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.util.StringRepresentable;
import net.azurune.delicate_dyes.DelicateDyes;

import java.util.Arrays;

@Mixin(DyeColor.class)
public class DyeColorMixin {
    @Mutable @Shadow @Final private static DyeColor[] $VALUES;

    @Invoker("<init>")
    private static DyeColor delicateDyes$init(String dyeId, int ordinal, int id, String name, int entityColor, MapColor mapColor, int fireworkColor, int signColor) {
        throw new AssertionError();
    }

    @Inject(method = "<clinit>", at = @At(value = "FIELD", target = "Lnet/minecraft/world/item/DyeColor;$VALUES:[Lnet/minecraft/world/item/DyeColor;", shift = At.Shift.AFTER))
    private static void delicateDyes$addDyes(CallbackInfo ci) {
        int length = $VALUES.length;
        int getDyeCount = DDUtil.getDyeCount();

        DyeColor[] addDye = new DyeColor[length + 8];
        System.arraycopy($VALUES, 0, addDye, 0, length);

        addDye[length + 0] = delicateDyes$init("DELICATEDYES_CORAL", getDyeCount + 1, getDyeCount + 1, "delicatedyes_coral",
                0xf97474, MapColor.RAW_IRON, 0xff6565, 0xff6565);

        addDye[length + 1] = delicateDyes$init("CANARY", getDyeCount + 2, getDyeCount + 2, "canary",
                0xf6ee7d, MapColor.COLOR_YELLOW, 0xf3e37c, 0xf3e37c);

        addDye[length + 2] = delicateDyes$init("WASABI", getDyeCount + 3, getDyeCount + 3, "wasabi",
                0xbddc79, MapColor.TERRACOTTA_LIGHT_GREEN, 0x8ecc70, 0x8ecc70);

        addDye[length + 3] = delicateDyes$init("SACRAMENTO", getDyeCount + 4, getDyeCount + 4, "sacramento",
                0x287f66, MapColor.COLOR_CYAN, 0x437a6a, 0x437a6a);

        addDye[length + 4] = delicateDyes$init("SKY", getDyeCount + 5, getDyeCount + 5, "sky",
                0x4bfbff, MapColor.COLOR_LIGHT_BLUE, 0x97ebf2, 0x97ebf2);

        addDye[length + 5] = delicateDyes$init("BLURPLE", getDyeCount + 6, getDyeCount + 6, "blurple",
                0x6130c8, MapColor.TERRACOTTA_BLUE, 0x614495, 0x614495);

        addDye[length + 6] = delicateDyes$init("SANGRIA", getDyeCount + 7, getDyeCount + 7, "sangria",
                0x82125a, MapColor.TERRACOTTA_PURPLE, 0x821d5e, 0x821d5e);

        addDye[length + 7] = delicateDyes$init("DELICATEDYES_ROSE", getDyeCount + 8, getDyeCount + 8, "delicatedyes_rose",
                0xc81b4f, MapColor.CRIMSON_HYPHAE, 0xbe2e59, 0xbe2e59);

        $VALUES = addDye;
        DelicateDyes.LOGGER.info("all colors present after delicate dyes:" + Arrays.toString($VALUES));
    }
}
