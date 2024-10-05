package com.electrifiedded.hellyeahworkbench;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.util.Constants;

public class TileEntityHellYeahWorkbench extends TileEntity implements IInventory {
    private ItemStack result = ItemStack.EMPTY;
    private ItemStack[] matrix = new ItemStack[324];

    public TileEntityHellYeahWorkbench() {
        for (int i = 0; i < matrix.length; i++) {
            matrix[i] = ItemStack.EMPTY;
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        result = new ItemStack(tag.getCompoundTag("Result"));
        NBTTagList itemList = tag.getTagList("CraftMatrix", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < itemList.tagCount(); i++) {
            NBTTagCompound itemTag = itemList.getCompoundTagAt(i);
            int slot = itemTag.getByte("Slot") & 255;
            if (slot >= 0 && slot < matrix.length) {
                matrix[slot] = new ItemStack(itemTag);
            }
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        if (!result.isEmpty()) {
            NBTTagCompound produce = new NBTTagCompound();
            result.writeToNBT(produce);
            tag.setTag("Result", produce);
        } else {
            tag.removeTag("Result");
        }

        NBTTagList itemList = new NBTTagList();
        for (int i = 0; i < matrix.length; i++) {
            if (!matrix[i].isEmpty()) {
                NBTTagCompound itemTag = new NBTTagCompound();
                itemTag.setByte("Slot", (byte) i);
                matrix[i].writeToNBT(itemTag);
                itemList.appendTag(itemTag);
            }
        }
        tag.setTag("CraftMatrix", itemList);

        return super.writeToNBT(tag);
    }

    @Override
    public int getSizeInventory() {
        return 325;
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack stack : matrix) {
            if (!stack.isEmpty()) {
                return false;
            }
        }
        return result.isEmpty();
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        if (slot == 0) {
            return result;
        } else if (slot <= matrix.length) {
            return matrix[slot - 1];
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public ItemStack decrStackSize(int slot, int decrement) {
        if (slot == 0) {
            if (!result.isEmpty()) {
                for (int x = 1; x <= matrix.length; x++) {
                    decrStackSize(x, 1);
                }
                if (result.getCount() <= decrement) {
                    ItemStack craft = result;
                    result = ItemStack.EMPTY;
                    return craft;
                }
                ItemStack split = result.splitStack(decrement);
                if (result.getCount() <= 0) {
                    result = ItemStack.EMPTY;
                }
                return split;
            } else {
                return ItemStack.EMPTY;
            }
        } else if (slot <= matrix.length) {
            if (!matrix[slot - 1].isEmpty()) {
                if (matrix[slot - 1].getCount() <= decrement) {
                    ItemStack ingredient = matrix[slot - 1];
                    matrix[slot - 1] = ItemStack.EMPTY;
                    return ingredient;
                }
                ItemStack split = matrix[slot - 1].splitStack(decrement);
                if (matrix[slot - 1].getCount() <= 0) {
                    matrix[slot - 1] = ItemStack.EMPTY;
                }
                return split;
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeStackFromSlot(int slot) {
        if (slot == 0) {
            if (!result.isEmpty()) {
                for (int x = 1; x <= matrix.length; x++) {
                    decrStackSize(x, 1);
                }
                ItemStack craft = result;
                result = ItemStack.EMPTY;
                return craft;
            } else {
                return ItemStack.EMPTY;
            }
        } else if (slot <= matrix.length) {
            if (!matrix[slot - 1].isEmpty()) {
                ItemStack ingredient = matrix[slot - 1];
                matrix[slot - 1] = ItemStack.EMPTY;
                return ingredient;
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public void openInventory(EntityPlayer player) {
    }

    @Override
    public void closeInventory(EntityPlayer player) {
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        return true;
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        return true;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        if (slot == 0) {
            result = stack;
        } else if (slot <= matrix.length) {
            matrix[slot - 1] = stack;
        }
    }

    @Override
    public String getName() {
        return "container.dire";
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public ITextComponent getDisplayName() {
        if (hasCustomName()) {
            return new TextComponentString(getName());
        }
        return new TextComponentTranslation(getName());
    }


    public int[] getSlotsForFace(EnumFacing face) {
        return new int[0];
    }


    public boolean canInsertItem(int slot, ItemStack item, EnumFacing face) {
        return true;
    }

    public boolean canExtractItem(int slot, ItemStack item, EnumFacing face) {
        return true;
    }
    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void setField(int id, int value) {
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void clear() {
        result = ItemStack.EMPTY;
        for (int x = 0; x < matrix.length; x++) {
            matrix[x] = ItemStack.EMPTY;
        }
    }
}