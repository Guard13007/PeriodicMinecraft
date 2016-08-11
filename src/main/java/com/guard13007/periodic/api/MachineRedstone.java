package com.guard13007.periodic.api;

import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.common.util.ForgeDirection;

public class MachineRedstone implements IRedstoneHandler {
    private RedstoneSetting redstoneSetting;
    private boolean emit, acceptIn;
    private ForgeDirection input, output;

    public MachineRedstone() {
        redstoneSetting = RedstoneSetting.NULL;
        input = ForgeDirection.UNKNOWN;
        output = ForgeDirection.UNKNOWN;
    }

    @Override
    public boolean emitsRedstonePower() {
        return emit;
    }

    @Override
    public void setEmits(boolean var) {
        emit = var;
    }

    @Override
    public boolean acceptsRedstonePower() {
        return acceptIn;
    }

    @Override
    public void setAcceptsRedstone(boolean var) {
        acceptIn = var;
    }

    /**
     * DO NOT USE. Implement this in tile entity
     * @param side
     * @return
     */
    @Override
    public int powerRedstoneStrength(int side) {
        return 0;
    }

    /**
     * DO NOT USE. Implement this in tile entity
     * @param strength
     * @return
     */
    @Override
    public void onPowerAccepted(int strength) {

    }

    @Override
    public RedstoneSetting getRedstoneSetting() {
        return redstoneSetting;
    }

    @Override
    public void setRedstoneSetting(RedstoneSetting var) {
        redstoneSetting = var;
    }

    @Override
    public void setOutputSide(ForgeDirection side) {
        output = side;
    }

    @Override
    public ForgeDirection getOutputSide() {
        return output;
    }

    @Override
    public void setInputSide(ForgeDirection side) {
        input = side;
    }

    @Override
    public ForgeDirection getInputSide() {
        return input;
    }

    public void readFromNBT(NBTTagCompound nbt) {
        redstoneSetting = RedstoneSetting.getSetting(nbt.getInteger("redstoneSetting"));
        input = ForgeDirection.getOrientation(nbt.getInteger("redstoneInput"));
        output = ForgeDirection.getOrientation(nbt.getInteger("redstoneOutput"));
        emit = nbt.getBoolean("redstoneEmit");
        acceptIn = nbt.getBoolean("redstoneAccept");
    }

    public void writeToNBT(NBTTagCompound nbt) {
        nbt.setInteger("redstoneSetting", redstoneSetting.ordinal());
        nbt.setInteger("redstoneInput", input.ordinal());
        nbt.setInteger("redstoneOutput", output.ordinal());
        nbt.setBoolean("redstoneEmit", emit);
        nbt.setBoolean("redstoneAccept", acceptIn);
    }

    @Override
    public int getX() {
        return 0;
    }

    @Override
    public int getY() {
        return 0;
    }

    @Override
    public int getZ() {
        return 0;
    }
}
