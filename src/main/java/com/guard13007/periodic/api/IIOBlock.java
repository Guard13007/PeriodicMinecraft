package com.guard13007.periodic.api;

import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

/**
 * Created by lomeli12 on 10/16/14.
 */
public interface IIOBlock {
    public IIcon getIoIcon(IBlockAccess world, int x, int y, int z, int side);
}
