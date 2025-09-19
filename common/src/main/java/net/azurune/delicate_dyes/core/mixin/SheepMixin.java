package net.azurune.delicate_dyes.core.mixin;
import net.azurune.delicate_dyes.DelicateDyes;
import net.azurune.delicate_dyes.common.util.DDUtil;
import net.azurune.delicate_dyes.core.registry.DDBlocks;
import net.azurune.delicate_dyes.common.util.DDDyeValues;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Shearable;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(Sheep.class)
public abstract class SheepMixin extends Animal implements Shearable {
    protected SheepMixin(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    @Shadow @Final private static EntityDataAccessor<Byte> DATA_WOOL_ID;
    @Shadow @Final private static Map<DyeColor, ItemLike> ITEM_BY_DYE;

    @Inject(method = "getDefaultLootTable", at = @At("HEAD"), cancellable = true)
    private void delicateDyes$getDefaultLootTable(CallbackInfoReturnable<ResourceLocation> cir) {
        if (!this.isSheared() && (getColor().getId() > 690 || getColor().getId() < 697)) {
            if (this.getColor() == DDDyeValues.CORAL) {
                cir.setReturnValue(new ResourceLocation(DelicateDyes.MOD_ID,"entities/sheep/coral"));
            }
            if (this.getColor() == DDDyeValues.CANARY) {
                cir.setReturnValue(new ResourceLocation(DelicateDyes.MOD_ID,"entities/sheep/canary"));
            }
            if (this.getColor() == DDDyeValues.WASABI) {
                cir.setReturnValue(new ResourceLocation(DelicateDyes.MOD_ID,"entities/sheep/wasabi"));
            }
            if (this.getColor() == DDDyeValues.SACRAMENTO) {
                cir.setReturnValue(new ResourceLocation(DelicateDyes.MOD_ID,"entities/sheep/sacramento"));
            }
            if (this.getColor() == DDDyeValues.SKY) {
                cir.setReturnValue(new ResourceLocation(DelicateDyes.MOD_ID,"entities/sheep/sky"));
            }
            if (this.getColor() == DDDyeValues.BLURPLE) {
                cir.setReturnValue(new ResourceLocation(DelicateDyes.MOD_ID,"entities/sheep/blurple"));
            }
            if (this.getColor() == DDDyeValues.SANGRIA) {
                cir.setReturnValue(new ResourceLocation(DelicateDyes.MOD_ID,"entities/sheep/sangria"));
            }
            if (this.getColor() == DDDyeValues.ROSE) {
                cir.setReturnValue(new ResourceLocation(DelicateDyes.MOD_ID,"entities/sheep/rose"));
            }
        }
    }

    static {
        ITEM_BY_DYE.put(DDDyeValues.CORAL, DDBlocks.CORAL_WOOL.get());
        ITEM_BY_DYE.put(DDDyeValues.CANARY, DDBlocks.CANARY_WOOL.get());
        ITEM_BY_DYE.put(DDDyeValues.WASABI, DDBlocks.WASABI_WOOL.get());
        ITEM_BY_DYE.put(DDDyeValues.SACRAMENTO, DDBlocks.SACRAMENTO_WOOL.get());
        ITEM_BY_DYE.put(DDDyeValues.SKY, DDBlocks.SKY_WOOL.get());
        ITEM_BY_DYE.put(DDDyeValues.BLURPLE, DDBlocks.BLURPLE_WOOL.get());
        ITEM_BY_DYE.put(DDDyeValues.SANGRIA, DDBlocks.SANGRIA_WOOL.get());
        ITEM_BY_DYE.put(DDDyeValues.ROSE, DDBlocks.ROSE_WOOL.get());
    }

    @Inject(method = "getRandomSheepColor", at = @At("HEAD"), cancellable = true)
    private static void delicateDyes$getRandomSheepColor(RandomSource random, CallbackInfoReturnable<DyeColor> cir) {
        if (random.nextInt(777) == 0) {
            cir.setReturnValue(DDDyeValues.ROSE);
        }
    }

//    /**
//     * @reason Allowing >16 unique dye colors (128)
//     * @author ADudeCalledLeo
//     */
    @Overwrite
    public DyeColor getColor() {
        byte b = entityData.get(DATA_WOOL_ID);
        return DyeColor.byId(b & 0x7F);
    }

//    /**
//     * @reason Allowing >16 unique dye colors (128)
//     * @author ADudeCalledLeo
//     */
    @Overwrite
    public void setColor(DyeColor color) {
        byte b = entityData.get(DATA_WOOL_ID);
        DelicateDyes.LOGGER.info("DATA_WOOL_ID before dyeing: " + b);
        DelicateDyes.LOGGER.info("b & 0x80 = " + (b & 0x80) + " (" + Integer.toBinaryString(b & 0x80) + ")");
        DelicateDyes.LOGGER.info("color.getId() % 0x7F = " + (color.getId() % 0x7F) + " (" + Integer.toBinaryString(color.getId() % 0x7F) + ")");
        entityData.set(DATA_WOOL_ID, (byte) ((b & 0x80) | color.getId() % 0x7F));
        DelicateDyes.LOGGER.info("((b & 0x80) | color.getId() % 0x7F)) = " + ((b & 0x80) | color.getId() % 0x7F) + " (" + Integer.toBinaryString((b & 0x80) | color.getId() % 0x7F) + ")");
        DelicateDyes.LOGGER.info("dyed sheep to " + color + " with id " + color.getId() + " and DATA_WOOL_ID " + b);
    }

//    /**
//     * @reason Allowing >16 unique dye colors (128)
//     * @author ADudeCalledLeo
//     */
    @Overwrite
    public boolean isSheared() {
        return (entityData.get(DATA_WOOL_ID) & 0x80) != 0;
    }

//    /**
//     * @reason Allowing >16 unique dye colors (128)
//     * @author ADudeCalledLeo
//     */
    @Overwrite
    public void setSheared(boolean sheared) {
        byte b = entityData.get(DATA_WOOL_ID);
        DelicateDyes.LOGGER.info("method sheared: DATA_WOOL_ID before shearing: " + b + " (in binary: " + Integer.toBinaryString(b) + ")");
        entityData.set(DATA_WOOL_ID, (byte) ((b & 0x7F) | (sheared ? 0x80 : 0)));
        DelicateDyes.LOGGER.info("method sheared: sheep with color " + this.getColor() + " with id " + this.getColor().getId() + " is now sheared and DATA_WOOL_ID " + b + " (in binary: " + Integer.toBinaryString(b) + ")");
        DelicateDyes.LOGGER.info("method sheared: sheared sheep with color " + this.getColor() + " with id " + this.getColor().getId());
        DelicateDyes.LOGGER.info("method sheared: wool from sheep with color " + this.getColor() + " dropped. ITEM_BY_DYE: " + ITEM_BY_DYE.get(this.getColor()));
        int i = 1 + this.getRandom().nextInt(3);

        for (int j = 0; j < i; ++j) {
            ItemEntity itemEntity = this.spawnAtLocation(ITEM_BY_DYE.get(this.getColor()), 1);
            if (itemEntity == null) continue;
            itemEntity.setDeltaMovement(itemEntity.getDeltaMovement().add((this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.1F, (this.getRandom().nextFloat() * 0.05F), ((this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.1F)));
        }
    }
}
