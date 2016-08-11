package com.guard13007.periodic.blocks;

import com.guard13007.periodic.Periodic;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;

/**
 * Created by guard13007 on 10/7/14.
 */
public class BlockBoron extends Block {
    public BlockBoron(){
        super(Material.rock);
        setBlockName(Periodic.MODID+".boron");
        setBlockTextureName(Periodic.MODID+":boron");
    }
}
