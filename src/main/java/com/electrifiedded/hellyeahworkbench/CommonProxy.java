package com.electrifiedded.hellyeahworkbench;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public abstract class CommonProxy {

    @GameRegistry.ObjectHolder("modtut:firstblock")
    public static BlockHellYeahWorkbench blockHellYeahWorkbench;

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(new BlockHellYeahWorkbench());

    }
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {

    }

    public abstract void preInit(FMLPreInitializationEvent event);
}
