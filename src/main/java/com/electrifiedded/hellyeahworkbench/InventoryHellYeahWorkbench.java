package com.electrifiedded.hellyeahworkbench;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

public class InventoryHellYeahWorkbench {

    private ItemStack[] inventory;

    public InventoryHellYeahWorkbench(int size) {
        this.inventory = new ItemStack[size];
        for (int i = 0; i < size; i++) {
            this.inventory[i] = ItemStack.EMPTY;
        }
    }

    public void readFromNBT(NBTTagCompound compound) {
        NBTTagList tagList = compound.getTagList("Items", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < tagList.tagCount(); i++) {
            NBTTagCompound itemTag = tagList.getCompoundTagAt(i);
            int slot = itemTag.getByte("Slot") & 255;
            if (slot >= 0 && slot < this.inventory.length) {
                this.inventory[slot] = new ItemStack(itemTag);
            }
        }
    }

    public void writeToNBT(NBTTagCompound compound) {
        NBTTagList tagList = new NBTTagList();
        for (int i = 0; i < this.inventory.length; i++) {
            if (!this.inventory[i].isEmpty()) {
                NBTTagCompound itemTag = new NBTTagCompound();
                itemTag.setByte("Slot", (byte) i);
                this.inventory[i].writeToNBT(itemTag);
                tagList.appendTag(itemTag);
            }
        }
        compound.setTag("Items", tagList);
    }

    public ItemStack getStackInSlot(int slot) {
        return inventory[slot];
    }

    public void setInventorySlotContents(int slot, ItemStack stack) {
        inventory[slot] = stack;
        if (!stack.isEmpty() && stack.getCount() > getInventoryStackLimit()) {
            stack.setCount(getInventoryStackLimit());
        }
    }

    public int getInventoryStackLimit() {
        return 64;
    }

    public int getSizeInventory() {
        return inventory.length;
    }
}