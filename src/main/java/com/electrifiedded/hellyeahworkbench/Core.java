package com.electrifiedded.hellyeahworkbench;

import morph.avaritia.Avaritia;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static com.electrifiedded.hellyeahworkbench.Tags.MODID;

@Mod(modid = MODID, version = Tags.VERSION, name = Tags.MODNAME, acceptedMinecraftVersions = "[1.12.2]")
public class Core {

    @Mod.Instance(MODID)
    public static Core instance;


    public static Block hellYeahWorkbench = new BlockHellYeahWorkbench();

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        hellYeahWorkbench = new BlockHellYeahWorkbench();
        GameRegistry.registerTileEntity(TileHellYeahWorkbench.class, new ResourceLocation(MODID, "hell_yeah_workbench"));
        NetworkRegistry.INSTANCE.registerGuiHandler(Avaritia.instance, new GUIHandler());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        // Здесь можно выполнить инициализацию, необходимую после загрузки мода
    }

    @Mod.EventBusSubscriber
    public static class RegistrationHandler {
        @SubscribeEvent
        public static void registerBlocks(RegistryEvent.Register<Block> event) {
            event.getRegistry().register(hellYeahWorkbench);
        }

        @SubscribeEvent
        public static void registerItems(RegistryEvent.Register<Item> event) {
            event.getRegistry().register(new ItemBlock(hellYeahWorkbench).setRegistryName(hellYeahWorkbench.getRegistryName()));
        }

        @SubscribeEvent
        @SideOnly(Side.CLIENT)
        public static void registerModels(ModelRegistryEvent event) {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(hellYeahWorkbench), 0, new ModelResourceLocation(hellYeahWorkbench.getRegistryName(), "inventory"));
        }
    }
}
