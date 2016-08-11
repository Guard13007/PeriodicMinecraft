package com.guard13007.periodic.api;

public interface IInventoryMachine extends IMachine{
    public void setDropInventory (boolean val);

    public boolean getDropInventory();
}
