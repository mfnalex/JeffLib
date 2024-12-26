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

package com.jeff_media.jefflib.internal.nms.v1_21_4;

import com.jeff_media.jefflib.internal.nms.AbstractNMSMaterialHandler;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_21_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_21_R3.util.CraftMagicNumbers;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

class MaterialHandler implements AbstractNMSMaterialHandler {

    private static final Field field_Item_components;
    private static final Class<?> class_DataComponentMap_Builder_SimpleMap;
    private static final Field field_SimpleMap_map;
    private static final Field field_Material_maxStack;

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

        Field materialMaxStackField = null;
        try {

            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);

            materialMaxStackField = Material.class.getDeclaredField("maxStack");
            materialMaxStackField.setAccessible(true);

            modifiersField.setInt(materialMaxStackField, materialMaxStackField.getModifiers() & ~Modifier.FINAL);

        } catch (Throwable ignored) { }
        field_Material_maxStack = materialMaxStackField;
    }

    @Override
    public void setMaxStackSize(final Material material, final int maxStackSize) {
        Item item = CraftMagicNumbers.getItem(material);
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

        if(field_Material_maxStack != null) {
            try {
                field_Material_maxStack.set(material, maxStackSize);
            } catch (Exception ignored) { }
        }
    }
}
