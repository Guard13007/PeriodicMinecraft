package com.guard13007.periodic.blocks.machines;

import com.guard13007.periodic.api.IFacing;
import com.guard13007.periodic.api.IMachine;
import com.guard13007.periodic.api.MachineIO;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;

import net.minecraftforge.common.util.ForgeDirection;

import cpw.mods.fml.common.Optional;

import ic2.api.tile.IWrenchable;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;

/**
 * Generic machine class
 */
@Optional.Interface(modid = "IC2", iface = "ic2.api.tile.IWrenchable")
public class TileGenericMachine extends TileEntity implements IEnergyHandler, IFacing, IMachine, IWrenchable {
    protected EnergyStorage storage;
    private int faceSide;
    private MachineIO[] faceSetting;

    public TileGenericMachine(int capactiy) {
        storage = new EnergyStorage(capactiy);
        faceSetting = new MachineIO[6];
        for (int i = 0; i < faceSetting.length; i++)
            faceSetting[i] = MachineIO.ACCEPT_ALL;
    }

    public TileGenericMachine() {
        this(32000);
    }

    @Override
    public boolean onBlockRightClicked(EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        //Generic debugging stuff, to be replaced with something useful in the future.
        if (!player.isSneaking()) {
            ItemStack stack = player.getCurrentEquippedItem();
            if (stack != null && stack.getItem() != null && stack.getItem() == Items.compass) {
                if (!worldObj.isRemote) {
                    player.addChatComponentMessage(new ChatComponentText("RF: " + this.getEnergyStored(null) + "/" + this.getMaxEnergyStored(null)));
                    player.addChatComponentMessage(new ChatComponentText("Front Side: " + this.getFrontFace()));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        storage.readFromNBT(nbt);
        faceSide = nbt.getInteger("faceSide");
        NBTTagCompound faceIO = nbt.getCompoundTag("faceSettings");
        for (int i = 0; i < faceSetting.length; i++)
            this.setFaceIO(i, MachineIO.VALID_IO_SETTINGS[faceIO.getInteger("side_" + i)]);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        storage.writeToNBT(nbt);
        nbt.setInteger("faceSide", faceSide);
        NBTTagCompound faceIO = new NBTTagCompound();
        for (int i = 0; i < faceSetting.length; i++)
            faceIO.setInteger("side_" + i, faceSetting[i].ordinal());

        nbt.setTag("faceSettings", faceIO);
    }

    @Override
    public Packet getDescriptionPacket() {
        S35PacketUpdateTileEntity packet = (S35PacketUpdateTileEntity) super.getDescriptionPacket();
        NBTTagCompound dataTag = packet != null ? packet.func_148857_g() : new NBTTagCompound();
        writeToNBT(dataTag);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, dataTag);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        NBTTagCompound tag = pkt != null ? pkt.func_148857_g() : new NBTTagCompound();
        readFromNBT(tag);
    }

    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
        return storage.receiveEnergy(maxReceive, simulate);
    }

    @Override
    public int getEnergyStored(ForgeDirection from) {
        return storage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection from) {
        return storage.getMaxEnergyStored();
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from) {
        return true;
    }

    @Override
    public int getFrontFace() {
        return faceSide;
    }

    @Override
    public void setFrontFace(int var) {
        faceSide = var;
    }

    @Override
    public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
        return 0;
    }

    @Override
    public int faceIcon(int side) {
        return side == faceSide ? 3 : side == 0 ? 2 : side == 1 ? 1 : 0;
    }

    @Override
    public MachineIO getFaceIO(int side) {
        return faceSetting[side];
    }

    @Override
    public MachineIO getFaceIO(ForgeDirection dir) {
        return getFaceIO(dir.ordinal());
    }

    @Override
    public void setFaceIO(int side, MachineIO setting) {
        faceSetting[side] = setting;
    }

    @Override
    public boolean wrenchCanSetFacing(EntityPlayer entityPlayer, int side) {
        return false;
    }

    @Override
    public short getFacing() {
        return (short) faceSide;
    }

    @Override
    public void setFacing(short facing) {
        this.setFrontFace(facing);
    }

    @Override
    public int getX() {
        return xCoord;
    }

    @Override
    public int getY() {
        return yCoord;
    }

    @Override
    public int getZ() {
        return zCoord;
    }

    @Override
    public boolean wrenchCanRemove(EntityPlayer entityPlayer) {
        return true;
    }

    @Override
    public float getWrenchDropRate() {
        return 1f;
    }

    @Override
    public ItemStack getWrenchDrop(EntityPlayer entityPlayer) {
        ItemStack item = new ItemStack(worldObj.getBlock(xCoord, yCoord, zCoord), 1, worldObj.getBlockMetadata(xCoord, yCoord, zCoord));
        item.stackTagCompound = new NBTTagCompound();
        NBTTagCompound tag = new NBTTagCompound();
        writeToNBT(tag);
        item.getTagCompound().setTag("tileData", tag);
        return item;
    }
}
