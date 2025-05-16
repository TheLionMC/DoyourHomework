package me.thelionmc;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

import java.util.Random;

public class MathChallengeScreen extends Screen {

    private final Screen parent;
    private final String[] questions = new String[3];
    private final int[] answers = new int[3];
    private final TextFieldWidget[] inputs = new TextFieldWidget[3];
    private ButtonWidget submitButton;

    public MathChallengeScreen(Screen parent) {
        super(Text.literal("Solve to Exit"));
        this.parent = parent;

        Random rand = new Random();
        for (int i = 0; i < 3; i++) {
            int a = rand.nextInt(50) + 50;
            int b = rand.nextInt(50) + 50;
            int op = rand.nextInt(3);
            String q;
            int result;
            switch (op) {
                case 0 -> { q = a + " + " + b; result = a + b; }
                case 1 -> { q = a + " * " + b; result = a * b; }
                default -> { q = a + " - " + b; result = a - b; }
            }
            questions[i] = q;
            answers[i] = result;
        }
    }

    @Override
    protected void init() {
        int x = (width - 250) / 2;
        Random rand = new Random();

        for (int i = 0; i < 3; i++) {
            int a = rand.nextInt(50) + 50;
            int b = rand.nextInt(50) + 50;
            int op = rand.nextInt(3);
            String q;
            int result;
            switch (op) {
                case 0 -> { q = a + " + " + b; result = a + b; }
                case 1 -> { q = a + " * " + b; result = a * b; }
                default -> { q = a + " - " + b; result = a - b; }
            }
            questions[i] = q;
            answers[i] = result;

            int y = 50 + i * 40;
            inputs[i] = new TextFieldWidget(textRenderer, x + 130, y, 80, 20, Text.literal("Answer"));
            inputs[i].setMaxLength(10);
            this.addDrawableChild(inputs[i]);
        }

        submitButton = ButtonWidget.builder(Text.literal("Submit"), button -> {
            for (int i = 0; i < 3; i++) {
                try {
                    int userInput = Integer.parseInt(inputs[i].getText().trim());
                    if (userInput != answers[i]) return;
                } catch (NumberFormatException e) {
                    return;
                }
            }
            SessionManager.finishUnlockChallenge();
            MinecraftClient.getInstance().setScreen(new StartUpPromptScreen());
        }).dimensions(width / 2 - 50, height - 40, 100, 20).build();

        this.addDrawableChild(submitButton);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(textRenderer, Text.literal("Solve all 3 to continue"), width / 2, 20, 0xFFFFFF);

        int x = (width - 250) / 2;
        for (int i = 0; i < 3; i++) {
            int y = 55 + i * 40;
            context.drawTextWithShadow(textRenderer, Text.literal(questions[i]), x, y, 0xFFFFFF);
        }
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }
}
