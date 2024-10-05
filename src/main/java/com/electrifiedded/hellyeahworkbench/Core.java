package com.electrifiedded.hellyeahworkbench;

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

@Mod(modid = Tags.MODID, version = Tags.VERSION, name = Tags.MODNAME, acceptedMinecraftVersions = "[1.12.2]")
public class Core {

    @Mod.Instance(Tags.MODID)
    public static Core instance;

    @GameRegistry.ObjectHolder("hellyeahworkbench:BlockHellYeahWorkbench")
    public static BlockHellYeahWorkbench customWorkbench;

    @Mod.EventBusSubscriber
    public static class RegistrationHandler {

        @SubscribeEvent
        public static void registerBlocks(RegistryEvent.Register<Block> event) {
            event.getRegistry().register(new BlockHellYeahWorkbench().setRegistryName(new ResourceLocation(Tags.MODID, "BlockHellYeahWorkbench")));
            GameRegistry.registerTileEntity(TileEntityHellYeahWorkbench.class, "TileEntityHellYeahWorkbench");
        }

        @SubscribeEvent
        public static void registerItems(RegistryEvent.Register<Item> event) {
            event.getRegistry().register(new ItemBlock(customWorkbench).setRegistryName(customWorkbench.getRegistryName()));
        }

        @SubscribeEvent
        @SideOnly(Side.CLIENT)
        public static void registerModels(ModelRegistryEvent event) {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(customWorkbench), 0, new ModelResourceLocation(customWorkbench.getRegistryName(), "inventory"));
        }
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        // Регистрация блока и предмета уже выполняется в RegistrationHandler
    }
    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        event.getRegistry().register(new RecipeHellYeahCrafting().setRegistryName("hell_yeah_crafting"));
    }
    @EventHandler
    public void init(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new HellYeahGuiHandler());
    }

}
