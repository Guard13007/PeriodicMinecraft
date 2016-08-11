package com.guard13007.periodic.client.gui.elements;

import com.guard13007.periodic.api.IRedstoneHandler;
import com.guard13007.periodic.client.gui.GuiBase;
import com.guard13007.periodic.network.ModPacketHandler;
import com.guard13007.periodic.network.PacketRedstoneIn;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.util.List;

import net.minecraft.util.StatCollector;

import net.lomeli.lomlib.client.ResourceUtil;
import net.lomeli.lomlib.client.gui.GuiLomLib;
import net.lomeli.lomlib.client.gui.element.TabBase;
import net.lomeli.lomlib.client.render.RenderUtils;

public class TabRedsone extends TabBase {
    int headerColor = 14797103;
    int subheaderColor = 11186104;
    int textColor = 16777215;
    IRedstoneHandler redstoneHandler;
    RedstoneBtn redStoneInBtn, redStoneOutBtn;

    public TabRedsone(GuiLomLib gui, IRedstoneHandler redstoneHandler) {
        super(gui);
        this.maxHeight += 50;
        this.maxWidth += 18;
        this.redstoneHandler = redstoneHandler;
        this.redStoneInBtn = new RedStoneIn(this, this.redstoneHandler);
        this.redStoneOutBtn = new RedStoneOut(this, this.redstoneHandler);
    }

    @Override
    public boolean handleMouseClicked(int x, int y, int mouseButton) {
        int xPos1 = 0;

        if (side == 0)
            xPos1 = this.posX - this.currentWidth + 22;
        else
            xPos1 = this.posX + this.currentWidth - 120;
        if (mouseButton == 0) {
            if ((x >= xPos1 && x <= xPos1 + 16) && (y >= this.posY + 10 && y <= this.posY + 26))
                redStoneInBtn.onClicked();
            else if ((x >= xPos1 && x <= xPos1 + 16) && (y >= this.posY + 25 && y <= this.posY + 41))
                redStoneOutBtn.onClicked();
        }
        return super.handleMouseClicked(x, y, mouseButton);
    }

    @Override
    public void draw() {
        drawBackground();
        drawTabIcon("Icon_Redstone");
        if (!isFullyOpened())
            return;

        int xPos1 = 0, xPos2;

        if (side == 0) {
            xPos1 = this.posX - this.currentWidth + 22;
            xPos2 = this.posX + 8 - this.currentWidth;
        } else {
            xPos1 = this.posX + this.currentWidth - 120;
            xPos2 = this.posX + 128 - this.currentWidth;
        }

        elementFontRenderer.drawStringWithShadow(StatCollector.translateToLocal("tab.periodic.redstone"), xPos1, this.posY + 6, this.headerColor);


        this.redStoneInBtn.draw(xPos1 + 40, this.posY + 20);
        this.redStoneOutBtn.draw(xPos1 + 40, this.posY + 50);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public void addTooltip(List<String> list) {
        if (!isFullyOpened())
            list.add(StatCollector.translateToLocal("tab.periodic.redstone"));
    }

    private static abstract class RedstoneBtn {
        public abstract boolean onClicked();

        public abstract void draw(int x, int y);
    }

    public static class RedStoneIn extends RedstoneBtn {
        IRedstoneHandler redstoneHandler;
        TabRedsone parent;

        public RedStoneIn(TabRedsone base, IRedstoneHandler redstoneHandler) {
            this.redstoneHandler = redstoneHandler;
            this.parent = base;
        }

        public boolean onClicked() {
            if (redstoneHandler != null)
                ModPacketHandler.sendToServer(new PacketRedstoneIn(redstoneHandler.getX(), redstoneHandler.getY(), redstoneHandler.getZ(), !redstoneHandler.acceptsRedstonePower()));
            return true;
        }

        public void draw(int x, int y) {
            GL11.glPushMatrix();
            RenderUtils.resetColor();
            ResourceUtil.bindTexture(GuiBase.guiTexture);
            parent.drawTexturedModalRect(x, y, 66, 197, 16, 16);
            int mouseX = parent.gui.getMouseX(), mouseY = parent.gui.getMouseY();
            if ((mouseX >= x && mouseX <= x + 16) && (mouseY >= y && mouseY <= y + 16)) {
                parent.drawTexturedModalRect(x, y, 50, 197, 16, 16);
                if (Mouse.isButtonDown(0))
                    parent.drawTexturedModalRect(x, y, 34, 197, 16, 16);
            }
            int texX = 18, texY = 181;
            if (redstoneHandler != null && !redstoneHandler.acceptsRedstonePower())
                texX += 16;
            parent.drawTexturedModalRect(x, y, texX, texY, 16, 16);
            GL11.glPopMatrix();
        }
    }

    public static class RedStoneOut extends RedstoneBtn {
        IRedstoneHandler redstoneHandler;
        TabRedsone parent;

        public RedStoneOut(TabRedsone base, IRedstoneHandler redstoneHandler) {
            this.redstoneHandler = redstoneHandler;
            this.parent = base;
        }

        public boolean onClicked() {

            return true;
        }

        public void draw(int x, int y) {
            GL11.glPushMatrix();
            RenderUtils.resetColor();
            ResourceUtil.bindTexture(GuiBase.guiTexture);
            parent.drawTexturedModalRect(x, y, 66, 197, 16, 16);
            int mouseX = parent.gui.getMouseX(), mouseY = parent.gui.getMouseY();
            if ((mouseX >= x && mouseX <= x + 16) && (mouseY >= y && mouseY <= y + 16)) {
                parent.drawTexturedModalRect(x, y, 50, 197, 16, 16);
                if (Mouse.isButtonDown(0))
                    parent.drawTexturedModalRect(x, y, 34, 197, 16, 16);
            }
            int texX = 18, texY = 181;
            //if (redstoneHandler != null && !redstoneHandler.acceptsRedstonePower())
            //    texX += 16;
            //parent.drawTexturedModalRect(x, y, texX, texY, 16, 16);
            GL11.glPopMatrix();
        }
    }
}
