package net.gartersnake.carbcraft.screen.renderer;

import com.google.common.base.Preconditions;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRendering;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.gartersnake.carbcraft.util.FluidStack;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.render.*;
import net.minecraft.client.texture.Sprite;
import net.minecraft.fluid.Fluids;
import net.minecraft.registry.Registries;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.joml.Matrix4f;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

// CREDIT: https://github.com/mezz/JustEnoughItems by mezz (Forge Version)
// HIGHLY EDITED VERSION FOR FABRIC by Kaupenjoe
// Under MIT-License: https://github.com/mezz/JustEnoughItems/blob/1.18/LICENSE.txt
public class FluidStackRenderer implements IIngredientRenderer<FluidStack> {
    private static final NumberFormat nf = NumberFormat.getIntegerInstance();
    public final long capacity;
    private final TooltipMode tooltipMode;
    private final int width;
    private final int height;

    enum TooltipMode {
        SHOW_AMOUNT,
        SHOW_AMOUNT_AND_CAPACITY,
    }

    public FluidStackRenderer(long capacity, boolean showCapacity, int width, int height) {
        this(capacity, showCapacity ? TooltipMode.SHOW_AMOUNT_AND_CAPACITY : TooltipMode.SHOW_AMOUNT, width, height);
    }

    private FluidStackRenderer(long capacity, TooltipMode tooltipMode, int width, int height) {
        Preconditions.checkArgument(capacity > 0, "capacity must be > 0");
        Preconditions.checkArgument(width > 0, "width must be > 0");
        Preconditions.checkArgument(height > 0, "height must be > 0");
        this.capacity = capacity;
        this.tooltipMode = tooltipMode;
        this.width = width;
        this.height = height;
    }
    /*
    public void drawFluid(MatrixStack matrixStack, FluidStack fluid, int x, int y, int width, int height, long maxCapacity) {
        if (fluid.getFluidVariant().getFluid() == Fluids.EMPTY) {
            return;
        }
        RenderSystem.setShaderTexture(0, PlayerScreenHandler.BLOCK_ATLAS_TEXTURE);
        y += height;
        final Sprite sprite = FluidVariantRendering.getSprite(fluid.getFluidVariant());
        int color = FluidVariantRendering.getColor(fluid.getFluidVariant());

        final int drawHeight = (int) (fluid.getAmount() / (maxCapacity * 1F) * height);
        final int iconHeight = sprite.getY();
        int offsetHeight = drawHeight;

        RenderSystem.setShaderColor((color >> 16 & 255) / 255.0F, (float) (color >> 8 & 255) / 255.0F, (float) (color & 255) / 255.0F, 1F);

        int iteration = 0;
        while (offsetHeight != 0) {
            final int curHeight = offsetHeight < iconHeight ? offsetHeight : iconHeight;

            DrawableHelper.drawSprite(matrixStack, x, y - offsetHeight, 0, width, curHeight, sprite);
            offsetHeight -= curHeight;
            iteration++;
            if (iteration > 50) {
                break;
            }
        }
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);

        RenderSystem.setShaderTexture(0, FluidRenderHandlerRegistry.INSTANCE.get(fluid.getFluidVariant().getFluid())
                .getFluidSprites(MinecraftClient.getInstance().world, null, fluid.getFluidVariant().getFluid().getDefaultState())[0].getAtlasId());
    }
    */

    // Method taken from https://github.com/Tiviacz1337/Travelers-Backpack/blob/1.20.1/src/main/java/com/tiviacz/travelersbackpack/util/RenderUtils.java#L34
    public void renderInsulinTank(DrawContext context, FluidStack fluid, long capacity, long amount, double x, double y, double z, double height, double width)
    {
        if (fluid.getFluidVariant().getFluid() == Fluids.EMPTY) {
            return;
        }

        Sprite sprite = FluidVariantRendering.getSprite(fluid.getFluidVariant());

        int renderAmount = (int) Math.max(Math.min(height, amount * height / capacity), 1);
        int posY = (int) (y + height - renderAmount);

        int color = FluidVariantRendering.getColor(fluid.getFluidVariant());

        context.getMatrices().push();

        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor((color >> 16 & 255) / 255.0F, (float) (color >> 8 & 255) / 255.0F, (float) (color & 255) / 255.0F, 1F);
        //RenderSystem.setShaderColor((color >> 16 & 0xFF) / 255f, (color >> 8 & 0xFF) / 255f, (color & 0xFF) / 255f, 1);
        RenderSystem.setShaderTexture(0, PlayerScreenHandler.BLOCK_ATLAS_TEXTURE);
        RenderSystem.disableBlend();

        for(int i = 0; i < width; i += 16)
        {
            for(int j = 0; j < renderAmount; j += 16)
            {
                int drawWidth = (int) Math.min(width - i, 16);
                int drawHeight = Math.min(renderAmount - j, 16);

                int drawX = (int) (x + i);
                int drawY = posY + j;

                float minU;
                float minV;

                minU = sprite.getMinU();
                minV = sprite.getMinV();

                float maxU = sprite.getMaxU();
                float maxV = sprite.getMaxV();

                Matrix4f matrix4f = context.getMatrices().peek().getPositionMatrix();
                BufferBuilder builder = Tessellator.getInstance().getBuffer();
                builder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
                builder.vertex(matrix4f, drawX, drawY + drawHeight, (float)z).texture(minU, minV + (maxV - minV) * (float)drawHeight / 16F).next();
                builder.vertex(matrix4f, drawX + drawWidth, drawY + drawHeight, (float)z).texture(minU + (maxU - minU) * (float)drawWidth / 16F, minV + (maxV - minV) * drawHeight / 16F).next();
                builder.vertex(matrix4f, drawX + drawWidth, drawY, (float)z).texture(minU + (maxU - minU) * drawWidth / 16F, minV).next();
                builder.vertex(matrix4f, drawX, drawY, (float)z).texture(minU, minV).next();
                BufferRenderer.drawWithGlobalProgram(builder.end());
            }
        }
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
        context.getMatrices().pop();
    }

    @Override
    public List<Text> getTooltip(FluidStack fluidStack, TooltipContext tooltipFlag) {
        List<Text> tooltip = new ArrayList<>();
        FluidVariant fluidType = fluidStack.getFluidVariant();
        if (fluidType == null) {
            return tooltip;
        }

        MutableText displayName = Text.translatable("block." + Registries.FLUID.getId(fluidStack.fluidVariant.getFluid()).toTranslationKey());
        tooltip.add(displayName);

        long amount = fluidStack.getAmount();
        if (tooltipMode == TooltipMode.SHOW_AMOUNT_AND_CAPACITY) {
            MutableText amountString = Text.translatable("carbcraft.tooltip.insulin_amount_with_capacity", nf.format(amount), nf.format(capacity));
            tooltip.add(amountString.fillStyle(Style.EMPTY.withColor(Formatting.DARK_GRAY)));
        } else if (tooltipMode == TooltipMode.SHOW_AMOUNT) {
            MutableText amountString = Text.translatable("carbcraft.tooltip.insulin_amount", nf.format(amount));
            tooltip.add(amountString.fillStyle(Style.EMPTY.withColor(Formatting.DARK_GRAY)));
        }

        return tooltip;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
}