package com.guard13007.periodic.blocks;

import com.guard13007.periodic.Periodic;
import com.guard13007.periodic.blocks.fluids.BlockHydrogen;
import com.guard13007.periodic.blocks.machines.BlockGenericMachine;
import com.guard13007.periodic.blocks.machines.generators.TileBasicGenerator;
import com.guard13007.periodic.blocks.machines.utilities.TileAtmosphericGasCollector;
import com.guard13007.periodic.blocks.machines.TileGenericMachine;
import com.guard13007.periodic.fluids.ModFluids;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;

import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Created by Lomeli12
 */
public class ModBlocks {
    //Element Blocks
    public static Block blockBoron;

    //Fluid blocks
    public static Block hydrogenGasBlock;

    // Machine Blocks
    public static Block genericMachine;

    public static void loadBlocks() {
        //TODO make generic block class instead of having one class for every block
        blockBoron = new BlockBoron();
        hydrogenGasBlock = new BlockHydrogen(ModFluids.hydrogenGas, Material.water).setBlockName(Periodic.MODID + ".hydorgenGas");
        genericMachine = new BlockGenericMachine();

        registerBlock(blockBoron, "boron");
        registerBlock(hydrogenGasBlock, "hydrogenGasBlock");
        registerBlock(genericMachine, BlockGenericMachine.ItemGenericMachine.class, "machine");

        registerTileEntities();
    }

    private static void assignBlocksToFluid() {
        ModFluids.hydrogenGas.setUnlocalizedName(hydrogenGasBlock.getUnlocalizedName());
    }

    private static void registerTileEntities() {
        registerTile(TileGenericMachine.class, "genericMachine");
        registerTile(TileAtmosphericGasCollector.class, "atmoshericGasCollector");
        registerTile(TileBasicGenerator.class, "basicGenerator");
    }

    private static void registerTile(Class<? extends TileEntity> tileClass, String name) {
        GameRegistry.registerTileEntity(tileClass, name);
    }

    private static void registerBlock(Block bk, String name) {
        GameRegistry.registerBlock(bk, name);
    }

    private static void registerBlock(Block bk, Class<? extends ItemBlock> itemBlockClass, String name) {
        GameRegistry.registerBlock(bk, itemBlockClass, name);
    }
}
