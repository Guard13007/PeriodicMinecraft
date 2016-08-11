package com.guard13007.periodic.network;

import com.guard13007.periodic.Periodic;

import net.minecraft.entity.player.EntityPlayerMP;

import net.lomeli.lomlib.network.AbstractPacket;
import net.lomeli.lomlib.network.PacketHandler;

public class ModPacketHandler {
    private static PacketHandler handler;

    public static void init() {
        Class<? extends AbstractPacket>[] packetTypes = new Class[]{
            PacketRedstoneIn.class
        };
        handler = new PacketHandler(Periodic.MODID, packetTypes);
    }

    public static void sendTo(AbstractPacket message, EntityPlayerMP player) {
        handler.sendTo(message, player);
    }

    public static void sendToAll(AbstractPacket message) {
        handler.sendToAll(message);
    }

    public static void sendToServer(AbstractPacket message) {
        handler.sendToServer(message);
    }

    public static void sendToDimension(AbstractPacket message, int dimID) {
        handler.sendToDimension(message, dimID);
    }
}
