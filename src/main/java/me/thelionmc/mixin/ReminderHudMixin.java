package me.thelionmc.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import me.thelionmc.ReminderOverlay;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class ReminderHudMixin {

    @Inject(method = "render", at = @At("TAIL"))
    private void renderReminderOverlay(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
    /*    if (ReminderOverlay.shouldRender()) {
            var client = net.minecraft.client.MinecraftClient.getInstance();
            var textRenderer = client.textRenderer;

            String msg = ReminderOverlay.getMessage();
            int screenWidth = context.getScaledWindowWidth();
            int screenHeight = context.getScaledWindowHeight();

            int x = screenWidth / 2;
            int y = screenHeight / 4;

            int padding = 6;
            int textWidth = textRenderer.getWidth(msg);
            int boxWidth = textWidth + padding * 2;
            int boxHeight = 20 + padding * 2;

            int boxX1 = x - boxWidth / 2;
            int boxY1 = y - boxHeight / 2;
            int boxX2 = x + boxWidth / 2;
            int boxY2 = y + boxHeight / 2;

            context.fill(boxX1, boxY1, boxX2, boxY2, 0xAA000000); // translucent black box
            context.drawCenteredTextWithShadow(textRenderer, Text.literal(msg), x, y, 0xFFFFFF);
        } */
    }
}
