package com.guard13007.periodic.core.handler;

import com.guard13007.periodic.blocks.machines.generators.TileBasicGenerator;
import com.guard13007.periodic.client.gui.generator.GuiBasicGenerator;
import com.guard13007.periodic.inventory.ContainerBasicGenerator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import cpw.mods.fml.common.network.IGuiHandler;

public class PeriodicGuiHandler implements IGuiHandler {
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null) {
            if (tile instanceof TileBasicGenerator)
                return new ContainerBasicGenerator((TileBasicGenerator) tile, player.inventory, world);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null) {
            if (tile instanceof TileBasicGenerator)
                return new GuiBasicGenerator((TileBasicGenerator) tile, player.inventory, world);
        }
        return null;
    }
}
