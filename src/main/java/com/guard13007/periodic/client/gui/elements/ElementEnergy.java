package com.guard13007.periodic.client.gui.elements;

import java.util.List;

import net.minecraft.util.ResourceLocation;

import net.lomeli.lomlib.client.ResourceUtil;
import net.lomeli.lomlib.client.gui.GuiLomLib;
import net.lomeli.lomlib.client.gui.element.ElementBase;

import cofh.api.energy.IEnergyHandler;

public class ElementEnergy extends ElementBase {
    private static final ResourceLocation defaultTexture = new ResourceLocation("lomlib:textures/elements/Energy.png");
    private IEnergyHandler energyHandler;

    public ElementEnergy(GuiLomLib gui, int posX, int posY, IEnergyHandler energyHandler) {
        super(gui, posX, posY);
        this.setSize(13, 42);
        this.texW = 32;
        this.texH = 64;
        this.energyHandler = energyHandler;
    }

    @Override
    public boolean handleMouseClicked(int x, int y, int mouseButton) {
        return false;
    }

    @Override
    public void draw() {
        ResourceUtil.bindTexture(defaultTexture);
        drawTexturedModalRect(posX, posY, 1, 0, sizeX, sizeY);
        if (energyHandler != null) {
            int size = energyHandler.getEnergyStored(null) * sizeY / energyHandler.getMaxEnergyStored(null);
            drawTexturedModalRect(posX, posY + sizeY - size, 17, sizeY - size, sizeX, size);
        }
    }

    @Override
    public void addTooltip(List<String> list) {
        if (energyHandler != null)
            list.add(energyHandler.getEnergyStored(null) + "/" + energyHandler.getMaxEnergyStored(null) + " RF");
    }
}
