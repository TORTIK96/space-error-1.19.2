package org.tor_tik96.chronoline.hud.Chat;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import org.tor_tik96.chronoline.Config;
import org.tor_tik96.chronoline.hud.Abilities.AbilityTypes;
import org.tor_tik96.chronoline.hud.RegisterHUD;

import java.util.List;
import java.util.Map;

public class MyChatScreen extends Screen {
    private EditBox inputField;
    private int scrollOffset = 0;
    int visibleLines = 10;

    public MyChatScreen() {
        super(Component.literal("Мой чат"));
    }

    @Override
    protected void init() {
        // Создаем строку ввода
        this.inputField = new EditBox(this.font, this.width / 2 - 100, this.height - 30, 200, 20, Component.literal("Введите сообщение"));
        this.inputField.setMaxLength(Integer.MAX_VALUE);
        this.addWidget(this.inputField);
        this.setFocused(this.inputField);
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTicks);
        this.inputField.render(poseStack, mouseX, mouseY, partialTicks);
        if (RegisterHUD.generalHUD != null && inputField != null) {
            final int lineHeight = 10; // высота строки
            Chat chat = RegisterHUD.generalHUD.getMyChat();
            if (chat != null) {
                int inputFieldY = this.height - this.inputField.getHeight() - 5;
                int messagesYStart = inputFieldY - 25; // или больше, в зависимости от дизайна
                int messagesX = this.inputField.x;

                List<Map<String, List<String>>> msgs = chat.getMessages();

                int yOffset = messagesYStart; // начинаем отображать с нижней части области
                int maxVisibleLines = (this.height - inputField.getHeight() - 20) / lineHeight;

                // Общий счетчик линий
                int usedLines = 0;

                // Итерация по сообщениям с конца
                for (int i = msgs.size() - 1 - scrollOffset; i >= 0; i--) {
                    if (usedLines >= maxVisibleLines) break;

                    Map<String, List<String>> message = msgs.get(i);
                    for (String sender : message.keySet()) {
                        String mesg = "";
                        for (String s : message.get(sender)) {
                            mesg += s + " ";
                        }
                        List<String> new_mesg = chat.splitterMessage(mesg, 45);
                        for (int i1 = new_mesg.size()-1; i1 >= 0; i1--) {
                            String s = new_mesg.get(i1);
                            if (usedLines >= maxVisibleLines) break;
                            if (new_mesg.indexOf(s) == 0) {
                                String s1 = "[" + sender + "] ";
                                chat.drawScaledNoCenteredString(poseStack, Component.literal(s1), messagesX, yOffset, chat.getSenderColor(i), 0.7F);
                                chat.drawScaledNoCenteredString(poseStack, Component.literal(s), messagesX + (int) (Minecraft.getInstance().font.width(s1) / 1.5), yOffset, 0xFFFFFF, 0.7F);
                            } else {
                                chat.drawScaledNoCenteredString(poseStack, Component.literal(s), messagesX, yOffset, 0xFFFFFF, 0.7F);
                            }
                            yOffset -= lineHeight; // двигаемся вниз
                            usedLines++;
                        }
                    }
                }

            }
        }
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (this.inputField.isFocused()) {
            if (keyCode == 256) { // Escape
                this.onClose();
                return true;
            } else if (keyCode == 257 || keyCode == 335) { // Enter
                String message = this.inputField.getValue();
                if (!message.isEmpty() && Minecraft.getInstance().player != null && RegisterHUD.generalHUD != null) {
                    Player player = Minecraft.getInstance().player;
                    RegisterHUD.generalHUD.getMyChat().addMessage(player.getDisplayName().getString(), message, "FFFFFF");
                    if (message.equals("Pocket Abyss") && Config.isOpenAbilityDim() && Config.isActiveAbility(AbilityTypes.DIMENSION) && !Config.isAbilityDim(player.getOnPos())) {
                        BlockPos pos = Config.getAbilityDimTeleportPos();
                        RegisterHUD.oldPos = new BlockPos(player.getOnPos().getX(), player.getOnPos().getY() + 1, player.getOnPos().getZ());
                        Minecraft.getInstance().player.teleportTo(pos.getX(), pos.getY(), pos.getZ());
                    } else if (message.equals("Abyss Out") && Config.isOpenAbilityDim() && Config.isActiveAbility(AbilityTypes.DIMENSION) && Config.isAbilityDim(player.getOnPos())) {
                        if (RegisterHUD.oldPos == null) {
                            BlockPos pos = player.getLevel().getSharedSpawnPos();
                            if (player.getSleepingPos().isPresent()) {
                                pos = player.getSleepingPos().get();
                            }
                            Minecraft.getInstance().player.teleportTo(pos.getX(), pos.getY(), pos.getZ());
                        } else {
                            BlockPos pos = RegisterHUD.oldPos;
                            Minecraft.getInstance().player.teleportTo(pos.getX(), pos.getY(), pos.getZ());
                        }
                        RegisterHUD.oldPos = null;
                    } else if (message.equals("EasterLand") && Config.isOpenPDim() && !Config.isPDim(player.getOnPos())) {
                        BlockPos pos = Config.getPDimTeleportPos();
                        RegisterHUD.oldPos = new BlockPos(player.getOnPos().getX(), player.getOnPos().getY() + 1, player.getOnPos().getZ());
                        Minecraft.getInstance().player.teleportTo(pos.getX(), pos.getY(), pos.getZ());
                    } else if (message.equals("EasterOut") && Config.isOpenPDim() && Config.isPDim(player.getOnPos())) {
                        if (RegisterHUD.oldPos == null) {
                            BlockPos pos = player.getLevel().getSharedSpawnPos();
                            if (player.getSleepingPos().isPresent()) {
                                pos = player.getSleepingPos().get();
                            }
                            Minecraft.getInstance().player.teleportTo(pos.getX(), pos.getY(), pos.getZ());
                        } else {
                            BlockPos pos = RegisterHUD.oldPos;
                            Minecraft.getInstance().player.teleportTo(pos.getX(), pos.getY(), pos.getZ());
                        }
                        RegisterHUD.oldPos = null;
                    }
                }
                this.inputField.setValue("");
                return true;
            }
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void onClose() {
        this.minecraft.setScreen(null);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        if (RegisterHUD.generalHUD != null) {
            Chat chat = RegisterHUD.generalHUD.getMyChat();
            // delta > 0 — колесо вверх, delta < 0 — колесо вниз
            int scrollSpeed = 1; // на сколько строк прокрутка за одно событие
            this.scrollOffset += (int) (delta * scrollSpeed);
            // Ограничьте прокрутку по диапазону
            int maxScroll = Math.max(0, chat.getMessages().size() - visibleLines);
            if (this.scrollOffset < 0) this.scrollOffset = 0;
            if (this.scrollOffset > maxScroll) this.scrollOffset = maxScroll;
        }
        return true;
    }
}