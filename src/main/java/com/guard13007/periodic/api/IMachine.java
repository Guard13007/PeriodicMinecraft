package com.guard13007.periodic.api;

import net.minecraft.entity.player.EntityPlayer;

public interface IMachine {
    public boolean onBlockRightClicked(EntityPlayer player, int side, float hitX, float hitY, float hitZ);

    public int getX();

    public int getY();

    public int getZ();
}
