package net.azurune.delicate_dyes.common.util;

import net.azurune.delicate_dyes.core.platform.Services;

import java.lang.reflect.InvocationTargetException;

public class DDUtil {
    //This gets the amount of dyes currently in the game (starting at 15 because vanilla has 16)
    public static int getDyeCount() {
        boolean isforge;
        int dyeCount = 15;
        //ForgePlatformHelper fph = new ForgePlatformHelper();

        try {
            Class.forName("net.minecraftforge.fml.loading.FMLEnvironment");
            isforge = true;
        } catch (ClassNotFoundException e) {
            isforge = false;
        }

        if (!isforge && Services.PLATFORM.isModLoaded("mint")) {
            dyeCount = dyeCount + 20;
        }
        try {
            Class.forName("com.ninni.dye_depot.DyeDepot");
            dyeCount = dyeCount + 16;
        }
        catch (ClassNotFoundException e) {
        }
        return dyeCount;
    }

//    public static <T> IntFunction<T> createIdToValueFunctionButBetterLmao(ToIntFunction<T> valueToIdFunction, T[] values, ByIdMap.OutOfBoundsStrategy outOfBoundsHandling) {
//        T[] objects = (T[]) validateButBetterLmao(valueToIdFunction, values);
//        int i = objects.length;
//        return switch (outOfBoundsHandling) {
//            case ZERO -> {
//                T object = objects[0];
//                yield index -> {
//                    for(int j=0; j<values.length; j++) {
//                        if (((DyeColor)Arrays.stream(values).toList().get(j)).getId() == index) return Arrays.stream(values).toList().get(j);
//                    }
//                    return object;
//                };
//            }
//            case WRAP -> index -> objects[Mth.floorDiv(index, i)];
//            case CLAMP -> index -> objects[Mth.clamp(index, 0, i - 1)];
//        };
//    }

//    private static <T> T[] validateButBetterLmao(ToIntFunction<T> valueToIndexFunction, T[] values) {
//        int i = values.length;
//        if (i == 0) {
//            throw new IllegalArgumentException("Empty value list");
//        } else {
//            T[] objects = (T[]) values.clone();
//            Arrays.fill(objects, null);
//            int count = 0;
//            for (T object : values) {
//                T object2 = objects[count];
//                if (object2 != null) {
//                    throw new IllegalArgumentException("Duplicate entry on id " + count + ": current=" + object + ", previous=" + object2);
//                }
//
//                objects[count] = object;
//                count++;
//            }
//
//            for (int k = 0; k < i; k++) {
//                if (objects[k] == null) {
//                    throw new IllegalArgumentException("Missing value at index: " + k);
//                }
//            }
//            return objects;
//        }
//    }
}
