package com.guard13007.periodic.items;

import com.guard13007.periodic.Periodic;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

/**
 * Created by guard13007 on 10/13/14.
 */
public class ItemElement extends Item {

    public static final int numSubTypes=120;

    public ItemElement(){
        super();
        setUnlocalizedName(Periodic.MODID+".element");
        setHasSubtypes(true);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list){
        for (int i=1;i<numSubTypes;i++){
            list.add(new ItemStack(item,1,i));
        }
    }

    @SideOnly(Side.CLIENT)
    private IIcon[] icons;

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister registry) { // Yay icons
        icons=new IIcon[numSubTypes];
        for (int i=1; i < numSubTypes; i++) {
            icons[i]=registry.registerIcon(Periodic.MODID+":elements/"+i);
        }
        /*icons[0]=registry.registerIcon(Periodic.MODID+":elements/elementium");
        icons[1]=registry.registerIcon(Periodic.MODID+":elements/hydrogen");
        icons[2]=registry.registerIcon(Periodic.MODID+":elements/helium");*/
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int metadata) { // Get icon based on damage value
        if (metadata > icons.length) return icons[0];
        return icons[metadata];
    }

    @Override
    public String getUnlocalizedName(ItemStack items) { // Get name of item based on damage value
        if (items.getItemDamage()>(numSubTypes-1)) return this.getUnlocalizedName()+".0";
        return this.getUnlocalizedName()+"."+items.getItemDamage();
    }
}
