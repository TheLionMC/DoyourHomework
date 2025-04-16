package me.thelionmc;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class LockoutScreen extends Screen {

    private ButtonWidget quitButton;

    public LockoutScreen() {
        super(Text.literal("You're Done For Today"));
    }

    @Override
    protected void init() {
        int buttonWidth = 200;
        int buttonHeight = 20;
        int x = (width - buttonWidth) / 2;
        int y = (height - buttonHeight) / 2 + 100;

        quitButton = ButtonWidget.builder(Text.literal("Go back to Focus"), button -> {
            MinecraftClient.getInstance().scheduleStop();
        }).dimensions(x, y, buttonWidth, buttonHeight).build();

        this.addDrawableChild(quitButton);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.getMatrices().push();
        context.getMatrices().translate(0, 0, 100);

        int boxWidth = 300;
        int boxHeight = 100;
        int x = (width - boxWidth) / 2;
        int y = (height - boxHeight) / 2;

        context.drawCenteredTextWithShadow(textRenderer, Text.literal("You wanted to do something else now, time to take a break!"), width / 2, y + 20, 0xFFFFFF);
        context.drawCenteredTextWithShadow(textRenderer, Text.literal("Remember: " + SessionManager.getPostSessionReason()), width / 2, y + 50, 0x66FF66);
        context.getMatrices().pop();

    }

}
