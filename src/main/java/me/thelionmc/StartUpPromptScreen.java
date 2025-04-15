package me.thelionmc;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

public class StartUpPromptScreen extends Screen {
    private SessionSlider slider;
    private TextFieldWidget textBox;


    public StartUpPromptScreen() {
        super(Text.literal("Set Your Session"));
    }

    @Override
    protected void init() {
        int centerX = width / 2;
        int centerY = height / 2;

        slider = new SessionSlider(centerX - 100, centerY - 30, 200, 20);
        addDrawableChild(slider);

        textBox = new TextFieldWidget(textRenderer, centerX - 100, centerY, 200, 20, Text.literal("What’s next?"));
        textBox.setPlaceholder(Text.literal("Type what you’ll do after playing"));
        addDrawableChild(textBox);

        addDrawableChild(ButtonWidget.builder(Text.literal("Start"), button -> {
            int mins = slider.getSelectedMinutes();
            String reason = textBox.getText();
            SessionManager.setPostSessionReason(reason);
            SessionManager.startSession(mins, reason);

            close();
        }).dimensions(centerX - 50, centerY + 40, 100, 20).build());
    }


    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }


    private static class SessionSlider extends SliderWidget {
        public SessionSlider(int x, int y, int width, int height) {
            super(x, y, width, height, Text.literal(""), 0.1); // Default value
            updateMessage();
        }

        @Override
        protected void updateMessage() {
            int minutes = (int) (value * 600); // 0.1–1.0 maps to 60–600 mins
            setMessage(Text.literal("Session: " + minutes + " mins"));
        }

        @Override
        protected void applyValue() {
            updateMessage();
        }

        public int getSelectedMinutes() {
            return (int) (value * 600);
        }

        public void setSelectedMinutes(int minutes) {
            this.value = Math.max(0.1, Math.min(1.0, minutes / 600.0));
            updateMessage();
        }
    }
}
