package com.guard13007.periodic.fluids;

import net.minecraft.init.Blocks;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class ModFluids {
    public static Fluid hydrogenGas;
    public static Fluid genericAir;

    public static void loadFluids() {
        hydrogenGas = new Fluid("hydorgenGas").setDensity(70).setGaseous(true).setViscosity(200);
        genericAir = new Fluid("air").setDensity(1).setGaseous(true).setTemperature(288).setViscosity(1790).setBlock(Blocks.air).setUnlocalizedName("fluid.periodic.genericAir");
        FluidRegistry.registerFluid(hydrogenGas);
        FluidRegistry.registerFluid(genericAir);
    }
}
