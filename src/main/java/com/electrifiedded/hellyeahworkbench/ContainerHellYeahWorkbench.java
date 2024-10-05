package com.electrifiedded.hellyeahworkbench;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ContainerHellYeahWorkbench extends Container {

    private final InventoryCrafting craftMatrix;
    private final InventoryCraftResult craftResult;
    private final World world;
    private final BlockPos pos;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private CompletableFuture<Void> slotsAdditionFuture;

    public ContainerHellYeahWorkbench(InventoryPlayer playerInventory, World worldIn, BlockPos posIn) {
        this.world = worldIn;
        this.pos = posIn;
        this.craftMatrix = new InventoryCrafting(this, 18, 18);
        this.craftResult = new InventoryCraftResult();

        // Добавление слота для результата
        this.addSlotToContainer(new SlotCrafting(playerInventory.player, this.craftMatrix, this.craftResult, 0, 324, 162));

        // Добавление слотов для инвентаря игрока
        for (int k = 0; k < 3; ++k) {
            for (int i1 = 0; i1 < 9; ++i1) {
                this.addSlotToContainer(new Slot(playerInventory, i1 + k * 9 + 9, 8 + i1 * 18, 36 + k * 18 + 18 * 18));
            }
        }

        for (int l = 0; l < 9; ++l) {
            this.addSlotToContainer(new Slot(playerInventory, l, 8 + l * 18, 94 + 18 * 18));
        }

        // Запуск добавления слотов для крафта в фоновом потоке
        slotsAdditionFuture = CompletableFuture.runAsync(this::addCraftingSlotsPartially, executorService)
                .thenRun(this::updateCraftingResult);
    }

    private void addCraftingSlotsPartially() {
        for (int i = 0; i < 324; ++i) {
            int row = i / 18;
            int col = i % 18;
            this.addSlotToContainer(new Slot(this.craftMatrix, col + row * 18, 8 + col * 18, 18 + row * 18));
        }
    }

    private void updateCraftingResult() {
        IRecipe recipe = CraftingManager.findMatchingRecipe(this.craftMatrix, this.world);
        if (recipe != null) {
            this.craftResult.setInventorySlotContents(0, recipe.getCraftingResult(this.craftMatrix));
        } else {
            this.craftResult.setInventorySlotContents(0, ItemStack.EMPTY);
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public void onContainerClosed(EntityPlayer playerIn) {
        super.onContainerClosed(playerIn);
        executorService.shutdown();
    }
}