package com.guard13007.periodic.core;

import com.guard13007.periodic.items.ModItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

/**
 * Created by guard13007 on 10/12/14.
 */
public class CreativeTab extends CreativeTabs {
    @Override
    public Item getTabIconItem() {
        return ModItems.mid;
    }

    public CreativeTab(String label) {
        super(label);
    }
}
