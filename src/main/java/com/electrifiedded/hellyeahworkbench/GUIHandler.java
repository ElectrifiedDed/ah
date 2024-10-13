package com.electrifiedded.hellyeahworkbench;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GUIHandler implements IGuiHandler {

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity tile = world.getTileEntity(pos);
        switch (ID) {
            case 0:
                return new GUIHellYeahWorkbench(player.inventory, world, pos, ((TileHellYeahWorkbench) tile));
            default:
                return null;
        }
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity tile = world.getTileEntity(pos);
        switch (ID) {
            case 0:
                return new ContainerHellYeahWorkbench(player.inventory, world, pos, (TileHellYeahWorkbench) tile);
            default:
                return null;
        }
    }
}