package com.electrifiedded.hellyeahworkbench;

import morph.avaritia.recipe.AvaritiaRecipeManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static com.electrifiedded.hellyeahworkbench.CommonProxy.blockHellYeahWorkbench;

public class ContainerHellYeahWorkbench extends Container {

    public InventoryCrafting craftMatrix;
    public IInventory craftResult;
    protected World worldObj;
    protected BlockPos pos;

    public ContainerHellYeahWorkbench(InventoryPlayer player, World world, BlockPos pos, TileHellYeahWorkbench table) {
        worldObj = world;
        this.pos = pos;
        craftMatrix = new InventoryCrafting(this, 18, 18);
        craftResult = new InventoryCraftResult();
        addSlotToContainer(new Slot(craftResult, 0, 324, 80));

        for (int wy = 0; wy < 18; ++wy) {
            for (int ex = 0; ex < 18; ++ex) {
                addSlotToContainer(new Slot(craftMatrix, ex + wy * 18, 12 + ex * 18, 8 + wy * 18));
            }
        }

        for (int wy = 0; wy < 3; ++wy) {
            for (int ex = 0; ex < 9; ++ex) {
                addSlotToContainer(new Slot(player, ex + wy * 9 + 9, 39 + ex * 18, 338 + wy * 18));
            }
        }

        for (int ex = 0; ex < 9; ++ex) {
            addSlotToContainer(new Slot(player, ex, 39 + ex * 18, 496));
        }

        onCraftMatrixChanged(craftMatrix);
    }

    @Override
    public void onCraftMatrixChanged(IInventory matrix) {
        craftResult.setInventorySlotContents(0, AvaritiaRecipeManager.getExtremeCraftingResult(craftMatrix, worldObj));
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return worldObj.getBlockState(pos) == CommonProxy.blockHellYeahWorkbench.getDefaultState() && player.getDistanceSq(pos) <= 64.0D;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotNumber) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(slotNumber);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (slotNumber == 0) {
                if (!mergeItemStack(itemstack1, 325, 562, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(itemstack1, itemstack);
            } else if (slotNumber >= 325 && slotNumber < 562) {
                if (!mergeItemStack(itemstack1, 562, 651, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (slotNumber >= 562 && slotNumber < 651) {
                if (!mergeItemStack(itemstack1, 325, 562, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!mergeItemStack(itemstack1, 325, 651, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemstack1);
        }

        return itemstack;
    }
}
