package com.guard13007.periodic.client.render;

import com.guard13007.periodic.Periodic;
import com.guard13007.periodic.api.IIOBlock;
import com.guard13007.periodic.core.ModVars;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

/**
 * Created by lomeli12 on 10/16/14.
 */
public class RenderIO implements ISimpleBlockRenderingHandler {

    public void drawBlockFaces(RenderBlocks renderer, Block block, IIcon i0, IIcon i1, IIcon i2, IIcon i3, IIcon i4, IIcon i5) {
        Tessellator tessellator = Tessellator.instance;
        GL11.glTranslatef(-0.5F, 0.0F, -0.5F);

        if (block != null) {
            tessellator.startDrawingQuads();
            tessellator.setNormal(0F, -1F, 0F);
            renderer.renderFaceYNeg(block, 0D, -0.5D, 0D, i0);
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(0F, 1F, 0F);
            renderer.renderFaceYPos(block, 0D, -0.5D, 0D, i1);
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(0F, 0F, -1F);
            renderer.renderFaceZNeg(block, 0D, -0.5D, 0D, i2);
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(0F, 0F, 1F);
            renderer.renderFaceZPos(block, 0D, -0.5D, 0D, i3);
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(-1F, 0F, 0F);
            renderer.renderFaceXNeg(block, 0D, -0.5D, 0D, i4);
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(1F, 0F, 0F);
            renderer.renderFaceXPos(block, 0D, -0.5D, 0D, i5);
            tessellator.draw();
            GL11.glTranslatef(0.5F, 0.0F, 0.5F);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        block.setBlockBoundsForItemRender();
        renderer.setRenderBoundsFromBlock(block);
        GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
        float f = 1.0F;
        float f1 = 1.0F;
        float f2 = 1.0F;

        if (EntityRenderer.anaglyphEnable) {
            float f3 = (f * 30.0F + f1 * 59.0F + f2 * 11.0F) / 100.0F;
            float f4 = (f * 30.0F + f1 * 70.0F) / 100.0F;
            float f5 = (f * 30.0F + f2 * 70.0F) / 100.0F;
            f = f3;
            f1 = f4;
            f2 = f5;
        }

        GL11.glColor4f(f, f1, f2, 1.0F);
        renderer.colorRedTopLeft *= f;
        renderer.colorRedTopRight *= f;
        renderer.colorRedBottomLeft *= f;
        renderer.colorRedBottomRight *= f;
        renderer.colorGreenTopLeft *= f1;
        renderer.colorGreenTopRight *= f1;
        renderer.colorGreenBottomLeft *= f1;
        renderer.colorGreenBottomRight *= f1;
        renderer.colorBlueTopLeft *= f2;
        renderer.colorBlueTopRight *= f2;
        renderer.colorBlueBottomLeft *= f2;
        renderer.colorBlueBottomRight *= f2;

        drawBlockFaces(renderer, block, block.getIcon(0, metadata), block.getIcon(1, metadata), block.getIcon(2, metadata), block.getIcon(3, metadata), block.getIcon(4, metadata),
                block.getIcon(5, metadata));
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        Tessellator tess = Tessellator.instance;
        renderer.enableAO = false;
        tess.setColorOpaque_F(1f, 1f, 1f);
        boolean flag = false;
        if (block instanceof IIOBlock) {
            for (int side = 0; side < 6; side++) {
                int brightness = block.getMixedBrightnessForBlock(world, x, y, z);
                IIcon base = block.getIcon(world, x, y, z, side);
                IIcon io = ((IIOBlock) block).getIoIcon(world, x, y, z, side);
                switch (side) {
                    case 3:
                        tess.setBrightness(renderer.renderMaxZ < 1.0D ? brightness : block.getMixedBrightnessForBlock(world, x, y, z + 1));
                        if (block.shouldSideBeRendered(world, x, y, z + 1, side) || renderer.renderAllFaces) {
                            flag = true;
                            if (base != null)
                                renderer.renderFaceZPos(block, x, y, z, base);
                            if (io != null && Periodic.proxy.canSeeIO)
                                renderer.renderFaceZPos(block, x, y, z, io);
                        }
                        break;
                    case 2:
                        tess.setBrightness(renderer.renderMinZ > 0.0D ? brightness : block.getMixedBrightnessForBlock(world, x, y, z - 1));
                        if (block.shouldSideBeRendered(world, x, y, z - 1, side) || renderer.renderAllFaces) {
                            flag = true;
                            if (base != null)
                                renderer.renderFaceZNeg(block, x, y, z, base);
                            if (io != null && Periodic.proxy.canSeeIO)
                                renderer.renderFaceZNeg(block, x, y, z, io);
                        }
                        break;
                    case 5:
                        tess.setBrightness(renderer.renderMaxX < 1.0D ? brightness : block.getMixedBrightnessForBlock(world, x + 1, y, z));
                        if (block.shouldSideBeRendered(world, x + 1, y, z, side) || renderer.renderAllFaces) {
                            flag = true;
                            if (base != null)
                                renderer.renderFaceXPos(block, x, y, z, base);
                            if (io != null && Periodic.proxy.canSeeIO)
                                renderer.renderFaceXPos(block, x, y, z, io);
                        }
                        break;
                    case 4:
                        tess.setBrightness(renderer.renderMinX > 0.0D ? brightness : block.getMixedBrightnessForBlock(world, x - 1, y, z));
                        if (block.shouldSideBeRendered(world, x - 1, y, z, side) || renderer.renderAllFaces) {
                            flag = true;
                            if (base != null)
                                renderer.renderFaceXNeg(block, x, y, z, base);
                            if (io != null && Periodic.proxy.canSeeIO)
                                renderer.renderFaceXNeg(block, x, y, z, io);
                        }
                        break;
                    default:
                        if (block.shouldSideBeRendered(world, x, y + (side == 0 ? -1 : 1), z, side) || renderer.renderAllFaces) {
                            flag = true;
                            if (side == 0) {
                                tess.setBrightness(renderer.renderMinY > 0.0D ? brightness : block.getMixedBrightnessForBlock(world, x, y - 1, z));
                                if (base != null)
                                    renderer.renderFaceYNeg(block, x, y, z, base);
                                if (io != null && Periodic.proxy.canSeeIO)
                                    renderer.renderFaceYNeg(block, x, y, z, io);
                            } else {
                                tess.setBrightness(renderer.renderMaxY < 1.0D ? brightness : block.getMixedBrightnessForBlock(world, x, y + 1, z));
                                if (base != null)
                                    renderer.renderFaceYPos(block, x, y, z, base);
                                if (io != null && Periodic.proxy.canSeeIO)
                                    renderer.renderFaceYPos(block, x, y, z, io);
                            }
                        }
                        break;
                }
            }
        } else
            return renderer.renderStandardBlock(block, x, y, z);
        return flag;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    @Override
    public int getRenderId() {
        return ModVars.ioRenderID;
    }
}
