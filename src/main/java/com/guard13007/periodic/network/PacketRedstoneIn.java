package com.guard13007.periodic.network;

import com.guard13007.periodic.api.IRedstoneHandler;
import io.netty.buffer.ByteBuf;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;

import net.lomeli.lomlib.network.AbstractPacket;

public class PacketRedstoneIn extends AbstractPacket {
    private int x, y, z;
    private boolean value;

    public PacketRedstoneIn() {
    }

    public PacketRedstoneIn(int x, int y, int z, boolean value) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.value = value;
    }

    @Override
    public void encodeInto(ByteBuf buffer) {
        buffer.writeInt(x);
        buffer.writeInt(y);
        buffer.writeInt(z);
        buffer.writeBoolean(value);
    }

    @Override
    public void decodeInto(ByteBuf buffer) {
        x = buffer.readInt();
        y = buffer.readInt();
        z = buffer.readInt();
        value = buffer.readBoolean();
    }

    @Override
    public void handleClientSide(EntityPlayer player) {
    }

    @Override
    public void handleServerSide() {
        TileEntity tile = MinecraftServer.getServer().getEntityWorld().getTileEntity(x, y, z);
        if (tile != null && tile instanceof IRedstoneHandler)
            ((IRedstoneHandler) tile).setAcceptsRedstone(value);
    }
}
