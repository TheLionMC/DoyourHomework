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
        if (ReminderOverlay.shouldRender()) {
            var client = net.minecraft.client.MinecraftClient.getInstance();
            var textRenderer = client.textRenderer;

            String msg = ReminderOverlay.getMessage();
            int screenWidth = context.getScaledWindowWidth();
            int screenHeight = context.getScaledWindowHeight();

            context.drawCenteredTextWithShadow(
                    textRenderer,
                    Text.literal(msg),
                    screenWidth / 2,
                    screenHeight / 4,
                    0xFFFFFF
            );
        }
    }
}
