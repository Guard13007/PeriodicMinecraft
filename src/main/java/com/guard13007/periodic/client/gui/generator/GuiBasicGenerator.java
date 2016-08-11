package com.guard13007.periodic.client.gui.generator;

import com.guard13007.periodic.blocks.machines.generators.TileBasicGenerator;
import com.guard13007.periodic.client.gui.GuiBase;
import com.guard13007.periodic.client.gui.elements.ElementEnergy;
import com.guard13007.periodic.client.gui.elements.TabRedsone;
import com.guard13007.periodic.inventory.ContainerBasicGenerator;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.world.World;

public class GuiBasicGenerator extends GuiBase {
    private TileBasicGenerator tile;

    public GuiBasicGenerator(TileBasicGenerator tile, InventoryPlayer inventory, World world) {
        super(new ContainerBasicGenerator(tile, inventory, world), tile.getInventoryName());
        this.tile = tile;
    }

    @Override
    public void initGui() {
        super.initGui();
        this.addTab(new TabRedsone(this, tile));
        this.addElement(new ElementEnergy(this, 150, 25, tile));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(f, mouseX, mouseY);
        this.drawItemSlot(55, 52);
        this.drawTexturedModalRect(getGuiLeft() + 56, getGuiTop() + 36, 191, 0, 14, 14);
        if (tile.currentBurnTime > 0) {
            int i1 = tile.getBurnTimeScaled(13);
            this.drawTexturedModalRect(getGuiLeft() + 56, getGuiTop() + 36 + 12 - i1, 176, 12 - i1, 14, i1 + 1);
        }
    }
}
