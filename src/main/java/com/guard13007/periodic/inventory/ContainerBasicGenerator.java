package com.guard13007.periodic.inventory;

import com.guard13007.periodic.blocks.machines.generators.TileBasicGenerator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerBasicGenerator extends Container {
    private World world;
    private int burnTime;
    private int currentBurnTime;
    private TileBasicGenerator tile;

    public ContainerBasicGenerator(TileBasicGenerator tile, InventoryPlayer player, World world) {
        this.world = world;
        this.tile = tile;

        this.addSlotToContainer(new Slot(tile, 0, 56, 53));

        for (int l = 0; l < 3; ++l) {
            for (int i1 = 0; i1 < 9; ++i1) {
                this.addSlotToContainer(new Slot(player, i1 + l * 9 + 9, 8 + i1 * 18, 97 + l * 18));
            }
        }

        for (int l = 0; l < 9; ++l) {
            this.addSlotToContainer(new Slot(player, l, 8 + l * 18, 155));
        }
    }

    @Override
    public void addCraftingToCrafters(ICrafting crafting) {
        super.addCraftingToCrafters(crafting);
        crafting.sendProgressBarUpdate(this, 0, this.tile.burnTime);
        crafting.sendProgressBarUpdate(this, 1, this.tile.currentBurnTime);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (int i = 0; i < this.crafters.size(); ++i) {
            ICrafting crafting = (ICrafting) this.crafters.get(i);
            if (this.burnTime != this.tile.burnTime)
                crafting.sendProgressBarUpdate(this, 0, this.tile.burnTime);
            if (this.currentBurnTime != this.tile.currentBurnTime)
                crafting.sendProgressBarUpdate(this, 1, this.tile.currentBurnTime);
        }
        this.burnTime = this.tile.burnTime;
        this.currentBurnTime = this.tile.currentBurnTime;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int type, int value) {
        if (type == 0)
            this.tile.burnTime = value;
        if (type == 1)
            this.tile.currentBurnTime = value;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return player.getDistanceSq(this.tile.xCoord + 0.5D, this.tile.yCoord + 0.5D, this.tile.zCoord + 0.5D) <= 64.0D;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.inventorySlots.get(par2);

        /*
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (TileEntityFurnace.isItemFuel(itemstack1)) {
                if (this.mergeItemStack(itemstack1, 0, 1, false))
                    return null;
            }
        }*/
        return itemstack;
    }
}
