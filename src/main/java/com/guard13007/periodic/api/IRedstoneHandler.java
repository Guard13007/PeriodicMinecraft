package com.guard13007.periodic.api;

import net.minecraftforge.common.util.ForgeDirection;

public interface IRedstoneHandler {
    public boolean emitsRedstonePower();

    public void setEmits(boolean var);

    public boolean acceptsRedstonePower();

    public void setAcceptsRedstone(boolean var);

    public int powerRedstoneStrength(int side);

    public void onPowerAccepted(int strength);

    public RedstoneSetting getRedstoneSetting();

    public void setRedstoneSetting(RedstoneSetting var);

    public void setOutputSide(ForgeDirection side);

    public ForgeDirection getOutputSide();

    public void setInputSide(ForgeDirection side);

    public ForgeDirection getInputSide();

    public int getX();

    public int getY();

    public int getZ();
}
