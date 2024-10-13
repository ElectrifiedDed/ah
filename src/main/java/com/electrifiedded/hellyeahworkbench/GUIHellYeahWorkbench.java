package com.electrifiedded.hellyeahworkbench;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GUIHellYeahWorkbench extends GuiContainer {

    private static final ResourceLocation GUI_TEX = new ResourceLocation("avaritia", "textures/gui/hell_yeah_workbench_gui.png");

    public GUIHellYeahWorkbench(InventoryPlayer par1InventoryPlayer, World par2World, BlockPos pos, TileHellYeahWorkbench table) {
        super(new ContainerHellYeahWorkbench(par1InventoryPlayer, par2World, pos, table));
        ySize = 512;
        xSize = 512;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int i, int j) {
        // Дополнительные элементы интерфейса могут быть добавлены здесь
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(GUI_TEX);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
    }
}
