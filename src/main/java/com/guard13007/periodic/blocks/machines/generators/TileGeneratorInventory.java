package com.guard13007.periodic.blocks.machines.generators;

import com.guard13007.periodic.api.IInventoryMachine;
import com.guard13007.periodic.api.MachineIO;
import com.guard13007.periodic.core.SimpleInventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileGeneratorInventory extends TileGenericGenerator implements ISidedInventory, IInventoryMachine {
    protected SimpleInventory inventory;
    private boolean dropInventory;

    public TileGeneratorInventory(int capacity, int size) {
        super(capacity);
        inventory = new SimpleInventory(size);
        dropInventory = false;
    }

    public TileGeneratorInventory(int size) {
        super();
        inventory = new SimpleInventory(size);
        dropInventory = false;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        return new int[0];
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side) {
        MachineIO io = this.getFaceIO(side);
        return io.acceptsInput() && isItemValidForSlot(slot, stack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side) {
        MachineIO io = this.getFaceIO(side);
        return io.outputs();
    }

    @Override
    public int getSizeInventory() {
        return inventory.getSizeInventory();
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return inventory.getStackInSlot(slot);
    }

    @Override
    public ItemStack decrStackSize(int slot, int num) {
        return inventory.decrStackSize(slot, num);
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        return inventory.getStackInSlotOnClosing(slot);
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        inventory.setInventorySlotContents(slot, stack);
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
        return inventory.getInventoryStackLimit();
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return inventory.isUseableByPlayer(player);
    }

    @Override
    public void openInventory() {
    }

    @Override
    public void closeInventory() {
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        return inventory.isItemValidForSlot(slot, stack);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        inventory.readFromNBT(nbt);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        inventory.writeToNBT(nbt);
    }

    @Override
    public void setDropInventory(boolean val) {
        dropInventory = val;
    }

    @Override
    public boolean getDropInventory() {
        return dropInventory;
    }
}
