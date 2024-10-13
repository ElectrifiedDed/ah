package com.electrifiedded.hellyeahworkbench;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


import javax.annotation.Nullable;

public class HellYeahTileBase extends TileEntity{


    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
        return oldState.getBlock() != newSate.getBlock();
    }

    public NBTTagCompound getUpdateTag() {
        return this.writeToNBT(new NBTTagCompound());
    }

    @Nullable
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(this.getPos(), 255, this.getUpdateTag());
    }

    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        this.readFromNBT(pkt.getNbtCompound());
    }

    public void markDirty() {
        super.markDirty();
        if (this.getWorld() != null) {
            IBlockState state = this.getWorld().getBlockState(this.pos);
            if (state != null) {
                state.getBlock().updateTick(this.getWorld(), this.getPos(), state, this.getWorld().rand);
                this.getWorld().notifyBlockUpdate(this.pos, state, state, 3);
            }
        }


    }}
