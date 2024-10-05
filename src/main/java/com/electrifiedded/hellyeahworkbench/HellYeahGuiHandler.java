package com.electrifiedded.hellyeahworkbench;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class HellYeahGuiHandler implements IGuiHandler {

    public static final int CUSTOM_WORKBENCH_GUI_ID = 0;

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == CUSTOM_WORKBENCH_GUI_ID) {
            return new ContainerHellYeahWorkbench(player.inventory, world, new BlockPos(x, y, z));
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == CUSTOM_WORKBENCH_GUI_ID) {
            return new GuiHellYeahWorkbench(player.inventory, world);
        }
        return null;
    }
}
