package com.electrifiedded.hellyeahworkbench;

import morph.avaritia.api.registration.IModelRegister;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class BlockHellYeahWorkbench extends BlockContainer implements IModelRegister {

    public BlockHellYeahWorkbench() {
        super(Material.IRON);
        setHardness(50F);
        setResistance(2000F);
        setTranslationKey("hell_yeah_workbench");
        setHarvestLevel("pickaxe", 3);
        setSoundType(SoundType.GLASS);
        setRegistryName("hell_yeah_workbench");
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (world.isRemote) {
            return true;
        } else {
            player.openGui(Core.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
            return true;
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileHellYeahWorkbench();
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerModels() {
        ModelResourceLocation loc = new ModelResourceLocation("avaritia:crafting", "type=extreme");
        ModelLoader.setCustomStateMapper(this, new StateMapperBase() {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
                return loc;
            }
        });
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, loc);
    }

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, @Nullable ItemStack stack) {
        player.addStat(StatList.getBlockStats(this));
        player.addExhaustion(0.025F);
        stack = getItemBlockWithNBT(te);
        spawnAsEntity(worldIn, pos, stack);
    }

    private ItemStack getItemBlockWithNBT(@Nullable TileEntity te) {
        ItemStack stack = new ItemStack(this);
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        if (te != null) {
            te.writeToNBT(nbttagcompound);
            stack.setTagInfo("BlockEntityTag", nbttagcompound);
        }
        return stack;
    }
}