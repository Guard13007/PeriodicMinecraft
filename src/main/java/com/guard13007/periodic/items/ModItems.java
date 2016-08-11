package com.guard13007.periodic.items;

import com.guard13007.periodic.items.tools.ItemMachineConfig;
import net.minecraft.item.Item;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModItems {
    public static Item itemHeliumGas;
    public static Item itemHeliumGasBottle;
    public static Item itemLithium;
    public static Item itemBeryllium;
    public static Item itemCarbon;
    public static Item itemNitrogenGas;
    public static Item itemNitrogenGasBottle;

    public static Item mid;

    public static void loadItems() {
        //TODO make generic item class instead of having one class for every item
        mid = new ItemMachineConfig();
        registerItem(mid, "mid");
        /*itemLithium = new ItemLithium();
        registerItem(itemLithium, "lithium");
        itemBeryllium = new ItemBeryllium();
        registerItem(itemBeryllium, "beryllium");
        itemCarbon = new ItemCarbon();
        registerItem(itemCarbon, "carbon");*/
    }

    private static void registerItem(Item item, String name) {
        GameRegistry.registerItem(item, name);
    }
}
