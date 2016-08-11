package com.guard13007.periodic.api;

import net.minecraftforge.common.util.ForgeDirection;

/** interface for getting icons for specific face, as well as get which side is the front face */
public interface IFacing {
    /**
     * Get the front face of the block
     * @return
     */
    public int getFrontFace();

    /**
     * Set the front face of the block
     * @param side
     */
    public void setFrontFace(int side);

    /**
     * Get the index of the icon that corresponds with the side
     * @param side
     * @return
     */
    public int faceIcon(int side);

    /**
     * Check if face IO settings
     * @param side
     * @return
     */
    public MachineIO getFaceIO(int side);

    public MachineIO getFaceIO(ForgeDirection dir);

    /**
     * Set the io setting of a face
     * @param side
     * @param setting
     */
    public void setFaceIO(int side, MachineIO setting);
}
