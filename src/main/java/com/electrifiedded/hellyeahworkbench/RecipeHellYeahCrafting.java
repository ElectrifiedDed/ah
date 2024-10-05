package com.electrifiedded.hellyeahworkbench;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class RecipeHellYeahCrafting extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

    public RecipeHellYeahCrafting() {
        setRegistryName(new ResourceLocation("hellyeahworkbench", "custom_crafting"));
    }

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (stack.getItem() != Item.getItemFromBlock(Blocks.DIRT)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        return new ItemStack(Items.DIAMOND);
    }

    @Override
    public boolean canFit(int width, int height) {
        return width * height >= 1;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return new ItemStack(Items.DIAMOND);
    }

    @Override
    public boolean isDynamic() {
        return true;
    }
}
