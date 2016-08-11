package com.guard13007.periodic.client.gui;

import com.guard13007.periodic.Periodic;

import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import net.lomeli.lomlib.client.ResourceUtil;
import net.lomeli.lomlib.client.gui.GuiLomLib;

public class GuiBase extends GuiLomLib {
    public static ResourceLocation guiTexture = new ResourceLocation(Periodic.MODID.toLowerCase() + ":textures/gui/genericMachine.png");
    private String title;

    public GuiBase(Container par1Container, String title) {
        super(par1Container);
        this.texture = guiTexture;
        this.ySize = 178;
        this.title = title;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY) {
        this.drawGui();
        super.drawGuiContainerBackgroundLayer(f, mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
        super.drawGuiContainerForegroundLayer(x, y);
        this.fontRendererObj.drawString(StatCollector.translateToLocal(title), this.xSize / 2 - this.fontRendererObj.getStringWidth(title) / 2, 6, 4210752);
        this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 4210752);
    }

    public void drawItemSlot(int x, int y) {
        ResourceUtil.bindTexture(guiTexture);
        this.drawTexturedModalRect(this.getGuiLeft() + x, this.getGuiTop() + y, 0, 179, 18, 18);
    }
}
