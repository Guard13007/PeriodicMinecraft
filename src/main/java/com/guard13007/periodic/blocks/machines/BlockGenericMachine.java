package com.guard13007.periodic.blocks.machines;

import com.guard13007.periodic.Periodic;
import com.guard13007.periodic.api.*;
import com.guard13007.periodic.blocks.machines.generators.TileBasicGenerator;
import com.guard13007.periodic.blocks.machines.utilities.TileAtmosphericGasCollector;
import com.guard13007.periodic.core.ModVars;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.common.util.ForgeDirection;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockGenericMachine extends BlockContainer implements IIOBlock {
    public static final int numberOfMachines = 4;
    @SideOnly(Side.CLIENT)
    private IIcon[] machineIcons;
    @SideOnly(Side.CLIENT)
    private IIcon[] ioIcons;

    public BlockGenericMachine() {
        super(Material.iron);
        this.setBlockName(Periodic.MODID + ".machine");
        this.setHardness(1.4f);
        this.setResistance(10f);
        this.setCreativeTab(Periodic.modTab);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        TileEntity tile = world.getTileEntity(x, y, z);
        return (tile != null && tile instanceof IMachine) ? ((IMachine) tile).onBlockRightClicked(player, side, hitX, hitY, hitZ) : false;

    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIoIcon(IBlockAccess world, int x, int y, int z, int side) {
        IIcon out = ioIcons[0];
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null && tile instanceof IFacing) {
            if (side == ((IFacing) tile).getFrontFace())
                out = ioIcons[0];
            else
                out = ioIcons[((IFacing) tile).getFaceIO(side).ordinal()];
        }
        return out;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        // indexes 0 - 2 will be generic side/top/bottom textures respectively
        machineIcons = new IIcon[numberOfMachines + 3];
        ioIcons = new IIcon[4];
        for (int i = 0; i < machineIcons.length; i++) {
            if (i < 4)
                ioIcons[i] = iconRegister.registerIcon(Periodic.MODID + ":io/ioIcon_" + i);
            machineIcons[i] = iconRegister.registerIcon(Periodic.MODID + ":machines/faceIcon_" + i);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null && tile instanceof IFacing) {
            int index = ((IFacing) tile).faceIcon(side);
            if (index < machineIcons.length)
                return machineIcons[index];
        }
        return machineIcons[0];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        //TODO set this up properly so that machines render correctly in inventory
        int frontIcon;
        switch (meta) {

            default:
                frontIcon = 3;
                break;
        }
        return side == 3 ? machineIcons[frontIcon] : side == 0 ? machineIcons[2] : side == 1 ? machineIcons[1] : machineIcons[0];
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        for (int i = 0; i < numberOfMachines; i++) {
            list.add(new ItemStack(item, 1, i));
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        switch (meta) {
            case 1:
                return new TileAtmosphericGasCollector();
            case 3:
                return new TileBasicGenerator();
            default:
                return null;
        }
    }

    @Override
    public int damageDropped(int meta) {
        return meta;
    }

    @Override
    public int getDamageValue(World world, int x, int y, int z) {
        return this.damageDropped(world.getBlockMetadata(x, y, z));
    }

    /**
     * Generic code to make a tile drop it's inventory, if it has one.
     */
    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        TileEntity tile = world.getTileEntity(x, y, z);

        if (tile != null && tile instanceof IInventory) {
            boolean drop = true;
            if (tile instanceof IInventoryMachine)
                drop = ((IInventoryMachine) tile).getDropInventory();
            if (drop) {
                for (int i1 = 0; i1 < ((IInventory) tile).getSizeInventory(); ++i1) {
                    ItemStack itemstack = ((IInventory) tile).getStackInSlot(i1);

                    if (itemstack != null) {
                        float f = world.rand.nextFloat() * 0.8F + 0.1F;
                        float f1 = world.rand.nextFloat() * 0.8F + 0.1F;
                        EntityItem entityitem;

                        for (float f2 = world.rand.nextFloat() * 0.8F + 0.1F; itemstack.stackSize > 0; world.spawnEntityInWorld(entityitem)) {
                            int j1 = world.rand.nextInt(21) + 10;

                            if (j1 > itemstack.stackSize)
                                j1 = itemstack.stackSize;

                            itemstack.stackSize -= j1;
                            entityitem = new EntityItem(world, x + f, y + f1, z + f2, new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));
                            float f3 = 0.05F;
                            entityitem.motionX = (float) world.rand.nextGaussian() * f3;
                            entityitem.motionY = (float) world.rand.nextGaussian() * f3 + 0.2F;
                            entityitem.motionZ = (float) world.rand.nextGaussian() * f3;

                            if (itemstack.hasTagCompound())
                                entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
                        }
                    }
                }
                world.func_147453_f(x, y, z, block);
            }
        }

        super.breakBlock(world, x, y, z, block, meta);
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
        super.onBlockAdded(world, x, y, z);
        if (!world.isRemote) {
            Block block = world.getBlock(x, y, z - 1);
            Block block1 = world.getBlock(x, y, z + 1);
            Block block2 = world.getBlock(x - 1, y, z);
            Block block3 = world.getBlock(x + 1, y, z);
            byte b0 = 3;

            if (block.func_149730_j() && !block1.func_149730_j())
                b0 = 3;

            if (block1.func_149730_j() && !block.func_149730_j())
                b0 = 2;

            if (block2.func_149730_j() && !block3.func_149730_j())
                b0 = 5;

            if (block3.func_149730_j() && !block2.func_149730_j())
                b0 = 4;

            TileEntity tile = world.getTileEntity(x, y, z);
            if (tile != null && tile instanceof IFacing)
                ((IFacing) tile).setFrontFace(b0);
        }
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
        int i = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null && tile instanceof IFacing) {
            switch (i) {
                case 1:
                    ((IFacing) tile).setFrontFace(5);
                    break;
                case 2:
                    ((IFacing) tile).setFrontFace(3);
                    break;
                case 3:
                    ((IFacing) tile).setFrontFace(4);
                    break;
                default:
                    ((IFacing) tile).setFrontFace(2);
            }
        }
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("tileData")) {
            NBTTagCompound tag = stack.getTagCompound().getCompoundTag("tileData");
            tag.setInteger("x", tile.xCoord);
            tag.setInteger("y", tile.yCoord);
            tag.setInteger("z", tile.zCoord);
            tile.readFromNBT(tag);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderType() {
        return ModVars.ioRenderID;
    }

    @Override
    public boolean rotateBlock(World worldObj, int x, int y, int z, ForgeDirection axis) {
        TileEntity tile = worldObj.getTileEntity(x, y, z);
        if (tile != null && tile instanceof IFacing) {
            int front = ((IFacing) tile).getFrontFace();
            if (axis.ordinal() != front && axis.ordinal() > 1) {
                ((IFacing) tile).setFrontFace(axis.ordinal());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }

    @Override
    public int getComparatorInputOverride(World world, int x, int y, int z, int side) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null && tile instanceof IRedstoneHandler)
            return ((IRedstoneHandler) tile).emitsRedstonePower() ? ((IRedstoneHandler) tile).powerRedstoneStrength(side) : 0;
        return super.getComparatorInputOverride(world, x, y, z, side);
    }

    public static class ItemGenericMachine extends ItemBlock {
        public ItemGenericMachine(Block block) {
            super(block);
            this.setHasSubtypes(true);
            this.setCreativeTab(Periodic.modTab);
        }

        @Override
        public int getMetadata(int par1) {
            return par1;
        }

        @Override
        @SideOnly(Side.CLIENT)
        public void getSubItems(Item item, CreativeTabs tab, List list) {
            for (int i = 0; i < numberOfMachines; i++) {
                list.add(new ItemStack(item, 1, i));
            }
        }

        @Override
        @SideOnly(Side.CLIENT)
        public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean var) {
            if (stack.hasTagCompound() && stack.getTagCompound().hasKey("tileData"))
                list.add(StatCollector.translateToLocal("tile.periodic.machine.data"));
        }

        @Override
        public String getUnlocalizedName(ItemStack stack) {
            return this.getUnlocalizedName() + "." + stack.getItemDamage();
        }
    }
}
