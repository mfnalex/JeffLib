/*
 * Copyright (c) 2023. JEFF Media GbR / mfnalex et al.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.jeff_media.jefflib.internal.nms.v1_20_5;

import com.jeff_media.jefflib.internal.nms.AbstractNMSMaterialHandler;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_20_R4.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_20_R4.util.CraftMagicNumbers;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

class MaterialHandler implements AbstractNMSMaterialHandler {

    private static final Field field_Item_components;
    private static final Class<?> class_DataComponentMap_Builder_SimpleMap;
    private static final Field field_SimpleMap_map;

    static {
        Field foundComponentsField = null;
        for (Field field : Item.class.getDeclaredFields()) {
            if (field.getType().equals(DataComponentMap.class)) {
                field.setAccessible(true);
                foundComponentsField = field;
                break;
            }
        }
        if (foundComponentsField == null) {
            throw new RuntimeException("Could not find components field in Item class");
        } else {
            field_Item_components = foundComponentsField;
        }

        Class<?> foundSimpleMapClass = null;

        for (Class<?> clazz : DataComponentMap.Builder.class.getDeclaredClasses()) {
            //System.out.println("Found declared class: " + clazz.getName());
            if (DataComponentMap.class.isAssignableFrom(clazz)) {
                foundSimpleMapClass = clazz;
                break;
            }
        }
        if (foundSimpleMapClass == null) {
            throw new RuntimeException("Could not find SimpleMap class in DataComponentMap.Builder: " + Arrays.stream(DataComponentMap.Builder.class.getDeclaredClasses()).map(Class::getName).collect(Collectors.joining(", ")));
        } else {
            class_DataComponentMap_Builder_SimpleMap = foundSimpleMapClass;
        }

        Field foundMapField = null;
        for (Field field : class_DataComponentMap_Builder_SimpleMap.getDeclaredFields()) {
            if (Map.class.isAssignableFrom(field.getType())) {
                field.setAccessible(true);
                foundMapField = field;
                break;
            }
        }
        if (foundMapField == null) {
            throw new RuntimeException("Could not find map field in SimpleMap class");
        } else {
            field_SimpleMap_map = foundMapField;
        }
    }

    @Override
    public void setMaxStackSize(final Material material, final int maxStackSize) {
        Item item = CraftMagicNumbers.getItem(material);
        int defaultMax = item.getDefaultMaxStackSize();
        //if (defaultMax == maxStackSize) return;
        DataComponentMap map = item.components();

        Integer boxed = maxStackSize;

        if (map instanceof Map<?, ?>) {
            Map<DataComponentType<?>, Object> map1 = (Map<DataComponentType<?>, Object>) map;
            map1.put(DataComponents.MAX_STACK_SIZE, boxed);
        } else if (class_DataComponentMap_Builder_SimpleMap.isInstance(map)) {
            try {
                Map<DataComponentType<?>, Object> map1 = (Map<DataComponentType<?>, Object>) field_SimpleMap_map.get(map);
                map1.put(DataComponents.MAX_STACK_SIZE, boxed);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            throw new RuntimeException("Could not set max stack size of " + material.name() + " to " + maxStackSize + " - map is not an instance of Map<?,?> or SimpleMap, but " + map.getClass().getName());
        }

        ItemStack bukkitStack = new ItemStack(material);

        net.minecraft.world.item.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(bukkitStack);

        //int bukkitMetaMaxStack = bukkitMeta.getMaxStackSize();
        int bukkitItemStackMaxStack = bukkitStack.getMaxStackSize();
        int nmsItemStackMaxStack = nmsItemStack.getMaxStackSize();

        int bukkitMaterialMaxStack = material.getMaxStackSize();
        int nmsItemDefaultMaxStack = item.getDefaultMaxStackSize();


//        if(bukkitMetaMaxStack != maxStackSize) {
//            throw new RuntimeException("Could not set max stack size of " + material.name() + " to " + maxStackSize + " - #1 bukkitMetaMaxStack is " + bukkitMetaMaxStack);
//        }

        if(bukkitItemStackMaxStack != maxStackSize) {
            throw new RuntimeException("Could not set max stack size of " + material.name() + " to " + maxStackSize + " - #2 bukkitItemStackMaxStack is " + bukkitItemStackMaxStack);
        }

        if(nmsItemStackMaxStack != maxStackSize) {
            throw new RuntimeException("Could not set max stack size of " + material.name() + " to " + maxStackSize + " - #3 nmsItemStackMaxStack is " + nmsItemStackMaxStack);
        }

        if(bukkitMaterialMaxStack != maxStackSize) {
            throw new RuntimeException("Could not set max stack size of " + material.name() + " to " + maxStackSize + " - #4 bukkitMaterialMaxStack is " + bukkitMaterialMaxStack);
        }

        if(nmsItemDefaultMaxStack != maxStackSize) {
            throw new RuntimeException("Could not set max stack size of " + material.name() + " to " + maxStackSize + " - #5 nmsItemDefaultMaxStack is " + nmsItemDefaultMaxStack);
        }

        Integer componentStackSize = item.components().get(DataComponents.MAX_STACK_SIZE);
        if(componentStackSize == null) {
            throw new RuntimeException("Could not set max stack size of " + material.name() + " to " + maxStackSize + " - componentStackSize is null");
        }
        if(componentStackSize != maxStackSize) {
            throw new RuntimeException("Could not set max stack size of " + material.name() + " to " + maxStackSize + " - componentStackSize is " + componentStackSize);
        }

        System.out.println("Successfully set max stack size of " + material.name() + " to " + maxStackSize);



    }
}
