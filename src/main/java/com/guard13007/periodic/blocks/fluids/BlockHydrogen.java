package com.guard13007.periodic.blocks.fluids;

import com.guard13007.periodic.Periodic;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidFinite;
import net.minecraftforge.fluids.Fluid;

/**
 * Created by guard13007 on 10/12/14.
 */
public class BlockHydrogen extends BlockFluidFinite {
    @SideOnly(Side.CLIENT)
    protected IIcon stillIcon;
    @SideOnly(Side.CLIENT)
    protected IIcon flowingIcon;

    public BlockHydrogen(Fluid fluid, Material material) {
        super(fluid, material);
        setCreativeTab(Periodic.modTab);
    }

    @Override
    public IIcon getIcon(int side, int meta){
        return (side == 0 || side == 1)? stillIcon : flowingIcon;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister registry) {
        stillIcon = registry.registerIcon(Periodic.MODID+":hydrogenStill");
        flowingIcon = registry.registerIcon(Periodic.MODID+":hydrogenFlowing");
    }

    @Override
    public boolean canDisplace(IBlockAccess world, int x, int y, int z){
        if (world.getBlock(x,y,z).getMaterial().isLiquid()) return false;
        return super.canDisplace(world,x,y,z);
    }

    @Override
    public boolean displaceIfPossible(World world, int x, int y, int z){
        if (world.getBlock(x,y,z).getMaterial().isLiquid()) return false;
        return super.displaceIfPossible(world,x,y,z);
    }
}
