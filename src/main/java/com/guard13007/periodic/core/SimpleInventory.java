package com.guard13007.periodic.core;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class SimpleInventory implements IInventory {
    private ItemStack[] inventory;

    public SimpleInventory(int size) {
        inventory = new ItemStack[size];
    }

    /**
     * Use sparingly, as you can delete items
     * @param size
     */
    public void setInventorySize(int size) {
        ItemStack[] newInv = new ItemStack[size];
        for (int i = 0; i < size; i++){
            if (i < inventory.length)
                inventory[i] = newInv[i];
        }
        inventory = newInv;
    }

    @Override
    public int getSizeInventory() {
        return inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return inventory[slot];
    }

    @Override
    public ItemStack decrStackSize(int slot, int num) {
        if (this.inventory[slot] != null) {
            ItemStack itemstack;

            if (this.inventory[slot].stackSize <= num) {
                itemstack = this.inventory[slot];
                this.inventory[slot] = null;
                this.markDirty();
                return itemstack;
            } else {
                itemstack = this.inventory[slot].splitStack(num);

                if (this.inventory[slot].stackSize == 0) {
                    this.inventory[slot] = null;
                }

                this.markDirty();
                return itemstack;
            }
        } else
            return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        ItemStack stack = this.getStackInSlot(slot);
        inventory[slot] = null;
        this.markDirty();
        return stack;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        inventory[slot] = stack;
        this.markDirty();
    }

    @Override
    public String getInventoryName() {
        return null;
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void markDirty() {
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return true;
    }

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        return true;
    }

    public void readFromNBT(NBTTagCompound nbt) {
        NBTTagList nbttaglist = nbt.getTagList("Items", 10);
        for (int i = 0; i < nbttaglist.tagCount(); ++i) {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            int j = nbttagcompound1.getByte("Slot") & 255;

            if (j >= 0 && j < this.inventory.length)
                this.inventory[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
        }
    }

    public void writeToNBT(NBTTagCompound nbt) {
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.inventory.length; ++i) {
            if (this.inventory[i] != null) {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte) i);
                this.inventory[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }
        nbt.setTag("Items", nbttaglist);
    }
}
