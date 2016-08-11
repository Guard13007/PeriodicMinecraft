package com.guard13007.periodic.blocks.machines.generators;

import com.guard13007.periodic.Periodic;
import com.guard13007.periodic.api.IRedstoneHandler;
import com.guard13007.periodic.api.MachineRedstone;
import com.guard13007.periodic.api.RedstoneSetting;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;

import net.minecraftforge.common.util.ForgeDirection;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileBasicGenerator extends TileGeneratorInventory implements IRedstoneHandler {
    public int burnTime;
    public int currentBurnTime;
    protected MachineRedstone redstoneSettings;
    private boolean isPowered;

    public TileBasicGenerator() {
        super(1);
        redstoneSettings = new MachineRedstone();
    }

    @Override
    public boolean onBlockRightClicked(EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        if (player != null && !player.isSneaking()) {
            if (!worldObj.isRemote)
                player.openGui(Periodic.instance, 0, worldObj, xCoord, yCoord, zCoord);
            return true;
        }
        return false;
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!worldObj.isRemote) {
            if (storage.getEnergyStored() < storage.getMaxEnergyStored()) {
                if (currentBurnTime <= 0 && (redstoneSettings.acceptsRedstonePower() ? !isPowered : true)) {
                    if (inventory.getStackInSlot(0) != null) {
                        int time = TileEntityFurnace.getItemBurnTime(inventory.getStackInSlot(0));
                        if (time > 0) {
                            burnTime = time;
                            currentBurnTime = time;
                            ItemStack item = inventory.getStackInSlot(0);
                            --item.stackSize;
                            if (item.stackSize <= 0) {
                                item = item.getItem().getContainerItem(item);
                                inventory.setInventorySlotContents(0, item);
                            }
                        }
                    }
                } else {
                    if (--currentBurnTime >= 0)
                        storage.receiveEnergy(10, false);
                }
            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        burnTime = nbt.getInteger("burnTime");
        currentBurnTime = nbt.getInteger("currentBurnTime");
        redstoneSettings.readFromNBT(nbt);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("burnTime", burnTime);
        nbt.setInteger("currentBurnTime", currentBurnTime);
        redstoneSettings.writeToNBT(nbt);
    }

    @SideOnly(Side.CLIENT)
    public int getBurnTimeScaled(int var) {
        if (burnTime == 0)
            burnTime = 200;
        return currentBurnTime * var / burnTime;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        return new int[]{0};
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        return TileEntityFurnace.isItemFuel(stack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side) {
        return super.canExtractItem(slot, stack, side) && !TileEntityFurnace.isItemFuel(inventory.getStackInSlot(0));
    }

    @Override
    public boolean emitsRedstonePower() {
        return redstoneSettings.emitsRedstonePower();
    }

    @Override
    public boolean acceptsRedstonePower() {
        return redstoneSettings.acceptsRedstonePower();
    }

    @Override
    public int powerRedstoneStrength(int side) {
        return 0;
    }

    @Override
    public void onPowerAccepted(int strength) {

    }

    @Override
    public RedstoneSetting getRedstoneSetting() {
        return redstoneSettings.getRedstoneSetting();
    }

    @Override
    public void setRedstoneSetting(RedstoneSetting var) {
        redstoneSettings.setRedstoneSetting(var);
    }

    @Override
    public void setOutputSide(ForgeDirection side) {
        redstoneSettings.setOutputSide(side);
    }

    @Override
    public ForgeDirection getOutputSide() {
        return redstoneSettings.getOutputSide();
    }

    @Override
    public void setInputSide(ForgeDirection side) {
        redstoneSettings.setInputSide(side);
    }

    @Override
    public ForgeDirection getInputSide() {
        return redstoneSettings.getInputSide();
    }

    @Override
    public void setEmits(boolean var) {
        redstoneSettings.setEmits(var);
    }

    @Override
    public void setAcceptsRedstone(boolean var) {
        redstoneSettings.setAcceptsRedstone(var);
    }

    @Override
    public String getInventoryName() {
        return "tile.periodic.machine.2.name";
    }
}
