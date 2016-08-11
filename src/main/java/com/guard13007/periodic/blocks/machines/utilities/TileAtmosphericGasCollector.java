package com.guard13007.periodic.blocks.machines.utilities;

import com.guard13007.periodic.api.MachineIO;
import com.guard13007.periodic.blocks.machines.TileGenericMachine;
import com.guard13007.periodic.fluids.ModFluids;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;

import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

public class TileAtmosphericGasCollector extends TileGenericMachine implements IFluidHandler {
    protected FluidTank tank;
    private int tick;

    public TileAtmosphericGasCollector() {
        super();
        tank = new FluidTank(7000);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!worldObj.isRemote && worldObj.provider.dimensionId == 0) {
            if (tank.getFluid() == null)
                tank.setFluid(FluidRegistry.getFluidStack(ModFluids.genericAir.getName().toLowerCase(), 0));
            if (tank.getFluidAmount() < tank.getCapacity() && this.getEnergyStored(null) >= 20) {
                storage.extractEnergy(20, false);
                if (++tick >= 100) {
                    int airAmount = 0;
                    if (worldObj.isAirBlock(xCoord + 1, yCoord, zCoord))
                        airAmount += 100;
                    if (worldObj.isAirBlock(xCoord - 1, yCoord, zCoord))
                        airAmount += 100;
                    if (worldObj.isAirBlock(xCoord, yCoord, zCoord - 1))
                        airAmount += 100;
                    if (worldObj.isAirBlock(xCoord, yCoord, zCoord + 1))
                        airAmount += 100;
                    if (worldObj.isAirBlock(xCoord, yCoord + 1, zCoord))
                        airAmount += 100;
                    if (worldObj.isAirBlock(xCoord, yCoord - 1, zCoord))
                        airAmount += 100;
                    if (!worldObj.canBlockSeeTheSky(xCoord, yCoord, zCoord))
                        airAmount /= 2;

                    tank.fill(FluidRegistry.getFluidStack(ModFluids.genericAir.getName().toLowerCase(), airAmount), true);
                    tick = 0;
                }
            }
        }
    }

    @Override
    public boolean onBlockRightClicked(EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        if (!player.isSneaking()) {
            ItemStack stack = player.getCurrentEquippedItem();
            if (stack != null && stack.getItem() != null && stack.getItem() == Items.compass) {
                if (!worldObj.isRemote) {
                    player.addChatComponentMessage(new ChatComponentText("RF: " + this.getEnergyStored(null) + "/" + this.getMaxEnergyStored(null)));
                    player.addChatComponentMessage(new ChatComponentText("Air: " + tank.getFluidAmount() + "/" + tank.getCapacity()));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        if (resource == null || !resource.isFluidEqual(tank.getFluid()))
            return null;
        return drain(from, resource.amount, doDrain);
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        MachineIO io = this.getFaceIO(from);
        return io.outputs() ? tank.drain(maxDrain, doDrain) : null;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return false;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return true;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return new FluidTankInfo[]{tank.getInfo()};
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        tick = nbt.getInteger("tick");
        tank.readFromNBT(nbt);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("tick", tick);
        tank.writeToNBT(nbt);
    }
}
