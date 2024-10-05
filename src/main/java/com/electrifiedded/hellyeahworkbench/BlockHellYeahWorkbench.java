package com.electrifiedded.hellyeahworkbench;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockHellYeahWorkbench extends Block implements ITileEntityProvider {

    public BlockHellYeahWorkbench() {
        super(Material.WOOD);

    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityHellYeahWorkbench();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            TileEntityHellYeahWorkbench tileEntity = (TileEntityHellYeahWorkbench) worldIn.getTileEntity(pos);
            if (tileEntity != null) {
                playerIn.openGui(Core.instance, HellYeahGuiHandler.CUSTOM_WORKBENCH_GUI_ID, worldIn, pos.getX(), pos.getY(), pos.getZ());
            }
        }
        return true;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntityHellYeahWorkbench tileEntity = (TileEntityHellYeahWorkbench) worldIn.getTileEntity(pos);
        if (tileEntity != null) {
            InventoryHellYeahWorkbench inventory = tileEntity.getInventory();
            for (int i = 0; i < inventory.getSizeInventory(); i++) {
                Block.spawnAsEntity(worldIn, pos, inventory.getStackInSlot(i));
            }
        }
        super.breakBlock(worldIn, pos, state);
    }
}