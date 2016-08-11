package com.guard13007.periodic;

import com.guard13007.periodic.blocks.ModBlocks;
import com.guard13007.periodic.core.CreativeTab;
import com.guard13007.periodic.core.ModRecipes;
import com.guard13007.periodic.core.Proxy;
import com.guard13007.periodic.fluids.ModFluids;
import com.guard13007.periodic.items.ModItems;
import com.guard13007.periodic.network.ModPacketHandler;

import net.minecraft.creativetab.CreativeTabs;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;

@Mod(modid = Periodic.MODID, version = Periodic.VERSION)
public class Periodic {
    public static final String MODID = "periodic";
    public static final String VERSION = "0.1";

    public static final CreativeTabs modTab = new CreativeTab(Periodic.MODID);

    @SidedProxy(clientSide = "com.guard13007.periodic.client.ClientProxy", serverSide = "com.guard13007.periodic.core.Proxy")
    public static Proxy proxy;

    @Mod.Instance(Periodic.MODID)
    public static Periodic instance;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());

        config.load();

        //generateConflictingOres = config.get("Category", "generateConflictingOres", true, "this is a comment about that thing").getBoolean(true);

        config.save();

        ModPacketHandler.init();
        ModFluids.loadFluids();
        ModItems.loadItems();
        ModBlocks.loadBlocks();

        //itemHydrogenGas=new ItemHydrogenGas();
        //GameRegistry.registerItem(itemHydrogenGas,"hydrogengas");
        //itemHydrogenGasBottle=new ItemHydrogenGasBottle();
        //GameRegistry.registerItem(itemHydrogenGasBottle,"hydrogengasbottle");

        //GameRegistry.addShapelessRecipe(new ItemStack(itemHydrogenGasBottle), itemHydrogenGas, Items.glass_bottle);
        proxy.preInit();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        //TODO create and register required events
        proxy.init();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        ModRecipes.registerRecipes();
        //TODO Mod compatibility
        proxy.postInit();
    }

}
