package com.guard13007.periodic.items.tools;

import com.guard13007.periodic.Periodic;
import com.guard13007.periodic.api.IFacing;
import com.guard13007.periodic.api.IInventoryMachine;
import com.guard13007.periodic.api.IMachine;
import com.guard13007.periodic.api.MachineIO;
import org.lwjgl.input.Keyboard;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import net.minecraftforge.common.util.ForgeDirection;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Created by lomeli12 on 10/16/14.
 */
public class ItemMachineConfig extends Item {
    @SideOnly(Side.CLIENT)
    private IIcon[] iconArray;

    public ItemMachineConfig() {
        super();
        this.setCreativeTab(Periodic.modTab);
        this.setMaxStackSize(1);
        this.setUnlocalizedName(Periodic.MODID + ".machineConfig");
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (!world.isRemote && player.isSneaking()) {
            if (stack.getItemDamage() == 0)
                stack.setItemDamage(1);
            else
                stack.setItemDamage(0);
            player.addChatComponentMessage(new ChatComponentText(stack.getDisplayName() + ": " + StatCollector.translateToLocal("item.periodic.machineConfig.type" + stack.getItemDamage())));
        }
        return stack;
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        boolean flag = false;
        if (!world.isRemote) {
            Block block = world.getBlock(x, y, z);
            int meta = world.getBlockMetadata(x, y, z);
            TileEntity tile = world.getTileEntity(x, y, z);
            if (!player.isSneaking()) {
                if (stack.getItemDamage() == 0) {
                    if (tile != null && tile instanceof IMachine) {
                        if (tile instanceof IFacing && side != ((IFacing) tile).getFrontFace()) {
                            player.swingItem();
                            int type = ((IFacing) tile).getFaceIO(side).ordinal();
                            if (++type >= MachineIO.VALID_IO_SETTINGS.length)
                                type = 0;
                            ((IFacing) tile).setFaceIO(side, MachineIO.VALID_IO_SETTINGS[type]);
                            world.markBlockForUpdate(x, y, z);
                            flag = true;
                        } else {
                            ItemStack item = new ItemStack(block, 1, meta);
                            item.stackTagCompound = new NBTTagCompound();
                            NBTTagCompound tag = new NBTTagCompound();
                            tile.writeToNBT(tag);
                            tag.removeTag("x");
                            tag.removeTag("y");
                            tag.removeTag("z");
                            item.getTagCompound().setTag("tileData", tag);
                            EntityItem entity = new EntityItem(world, x, y, z, item);
                            if (tile instanceof IInventoryMachine)
                                ((IInventoryMachine) tile).setDropInventory(false);
                            world.setBlockToAir(x, y, z);
                            world.spawnEntityInWorld(entity);
                            flag = true;
                        }
                    }
                } else {
                    block.rotateBlock(world, x, y, z, ForgeDirection.getOrientation(side));
                    flag = true;
                }
            }
        }
        if (flag) player.swingItem();
        return flag;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean var) {
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            list.add(StatCollector.translateToLocal("item.periodic.machineConfig.sub"));
            list.add(StatCollector.translateToLocal("item.periodic.machineConfig.type" + stack.getItemDamage()));
        } else
            list.add(StatCollector.translateToLocal("msg.shift"));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        iconArray = new IIcon[2];
        iconArray[0] = register.registerIcon(Periodic.MODID + ":io_default");
        iconArray[1] = register.registerIcon(Periodic.MODID + ":io_rotate");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int meta) {
        return meta < 2 ? iconArray[meta] : iconArray[0];
    }
}
