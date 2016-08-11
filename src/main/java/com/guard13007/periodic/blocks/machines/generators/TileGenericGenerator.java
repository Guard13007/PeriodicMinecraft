package com.guard13007.periodic.blocks.machines.generators;

import com.guard13007.periodic.api.MachineIO;
import com.guard13007.periodic.blocks.machines.TileGenericMachine;

import net.minecraftforge.common.util.ForgeDirection;

public class TileGenericGenerator extends TileGenericMachine {

    public TileGenericGenerator(int capacity) {
        super(capacity);
    }

    public TileGenericGenerator() {
        super();
    }

    @Override
    public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
        MachineIO io = this.getFaceIO(from);
        if (io.outputs())
            return storage.extractEnergy(maxExtract, simulate);
        return 0;
    }

    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
        return 0;
    }
}
