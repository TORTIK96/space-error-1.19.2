package org.tor_tik96.chronoline.hud.Chat;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.Component;
import org.tor_tik96.chronoline.Utils.Utils;

import java.util.*;


public class Chat extends GuiComponent {
    private Map<String, Map<Integer, List<String>>> activeMessages = new HashMap<>();
    private List<Map<String, List<String>>> messages = new ArrayList<>();
    private Map<String, Integer> lengthMes = new HashMap<>();
    private List<Integer> senderColors = new ArrayList<>();
    private final int maxTotalLength = 1000;
    private int tick = 0;

    public void render(PoseStack poseStack, int tick) {
        this.tick = tick;
        int screenWidth = Minecraft.getInstance().getWindow().getGuiScaledWidth();
        int height = Minecraft.getInstance().getWindow().getGuiScaledHeight(); //высота
        int width = screenWidth / 2; //ширина
        if (activeMessages.size() > 1) {
            String sender = activeMessages.keySet().stream().toList().get(activeMessages.size() - 1);
            Map<Integer, List<String>> message = activeMessages.get(sender);
            activeMessages.clear();
            activeMessages.put(sender, message);
        }
        for (String sender : activeMessages.keySet().stream().toList()) {
            Map<Integer, List<String>> message = activeMessages.get(sender);
            for (int endTick : message.keySet().stream().toList()) {
                if (endTick > tick) {
                    drawMessage(poseStack, width, height, sender, message.get(endTick), messages.size()-1);
                } else {
                    activeMessages.remove(sender);
                }
            }
        }

    }

    private void drawMessage(PoseStack poseStack, int width, int height, String sender, List<String> message, int index) {
        int maxLength = 0;
        for (String i : message) {
            if (i.length() > maxLength) {
                maxLength = i.length();
            }
        }
        int lines = message.size();
        int x;
        int y;
        int xS = 0;
        int yS = 0;
        if (!sender.isEmpty()) {
            sender = "[" + sender + "]";
            int len = sender.length();
            if (maxLength > len) {
                x = (int) (maxLength * 1.5); //92
                y = 80 + (lines * 5); //85
                xS = (int) (len * 2);
                yS = y + 10;
            } else {
                x = (int) (len * 2); //92
                y = 80 + (lines * 5); //85
                xS = x;
                yS = y + 10;
            }
        } else {
            x = (int) (maxLength * 1.5); //92
            y = 80 + (lines * 5); //85
        }
        int rectHeight = (int) (lines * 5.5);
        int x2 = x;
        int y2 = y - rectHeight - 5;
        if (!sender.isEmpty()) {
            int yS2 = yS - 8;
            fill(poseStack, width - xS, height - yS, width + xS, height - yS2, 0xA0000000);
            drawScaledString(poseStack, Component.literal(sender), width, height - (yS - 2), senderColors.get(index), 0.5F);
        }
        fill(poseStack, width - x, height - y, width + x2, height - y2, 0xA0000000);
        int x1 = width + 1;
        int y1 = height - (y - 3);
        for (String string : message) {
            drawScaledString(poseStack, Component.literal(string), x1, y1, 0xFFFFFF, 0.5F);
            y1 += 4;
        }
    }

    public List<String> splitterMessage(String message) {
        List<String> mesg = new ArrayList<>();
        String new_mesg = "";

        int oneLength = 70;

        if (message.length() > maxTotalLength) {
            return List.of("Сообщение не может быть выведено так как слишком длинное!");
        }

        List<String> words = Arrays.stream(message.split(" ")).toList();
        lengthMes.put(message, words.size());

        for (String s : words) {
            if (!new_mesg.isEmpty()) {
                String s1 = new_mesg;
                s1 += s;
                if (s1.length() < oneLength) {
                    new_mesg += s + " ";
                } else if (s1.length() > oneLength) {
                    mesg.add(new_mesg);
                    new_mesg = "";
                    new_mesg += s + " ";
                } else {
                    new_mesg += s;
                    mesg.add(new_mesg);
                    new_mesg = "";
                }
            } else {
                new_mesg += s + " ";
            }
        }

        if (!new_mesg.isEmpty() && new_mesg.length() <= oneLength) {
            mesg.add(new_mesg);
            new_mesg = "";
        }

        return mesg;
    }

    public List<String> splitterMessage(String message, int oneLength) {
        List<String> mesg = new ArrayList<>();
        String new_mesg = "";

        if (message.length() > maxTotalLength) {
            return List.of("Сообщение не может быть выведено так как слишком длинное!");
        }

        List<String> words = Arrays.stream(message.split(" ")).toList();
        lengthMes.put(message, words.size());

        for (String s : words) {
            if (!new_mesg.isEmpty()) {
                String s1 = new_mesg;
                s1 += s;
                if (s1.length() < oneLength) {
                    new_mesg += s + " ";
                } else if (s1.length() > oneLength) {
                    mesg.add(new_mesg);
                    new_mesg = "";
                    new_mesg += s + " ";
                } else {
                    new_mesg += s;
                    mesg.add(new_mesg);
                    new_mesg = "";
                }
            } else {
                new_mesg += s + " ";
            }
        }

        if (!new_mesg.isEmpty() && new_mesg.length() <= oneLength) {
            mesg.add(new_mesg);
            new_mesg = "";
        }

        return mesg;
    }

    public void drawScaledString(PoseStack poseStack, Component text, int x, int y, int color, float scale) {
        poseStack.pushPose();
        poseStack.translate(x, y, 0);
        poseStack.scale(scale, scale, 1.0f); // Увеличение или уменьшение масштаба
        drawCenteredString(poseStack, Minecraft.getInstance().font, text, 0, 0, color);
        poseStack.popPose();
    }

    public void drawScaledNoCenteredString(PoseStack poseStack, Component text, int x, int y, int color, float scale) {
        poseStack.pushPose();
        poseStack.translate(x, y, 0);
        poseStack.scale(scale, scale, 1.0f); // Увеличение или уменьшение масштаба
        drawString(poseStack, Minecraft.getInstance().font, text, 0, 0, color);
        poseStack.popPose();
    }



    public void addMessage(String sender, String message, String senderColor) {
        if (message.length() <= maxTotalLength) {
            List<String> mesg = splitterMessage(message);
            int endTick = this.tick + (40 * lengthMes.get(message));
            lengthMes.remove(message);
            activeMessages.put(sender, Map.of(endTick, mesg));
            messages.add(Map.of(sender, mesg));
            senderColors.add(Utils.HexToDecimal(senderColor));
        }
    }

    public void addOneTimeMessage(String message) {
        if (message.length() <= maxTotalLength) {
            List<String> mesg = splitterMessage(message);
            int endTick = this.tick + (40 * lengthMes.get(message));
            lengthMes.remove(message);
            activeMessages.put("", Map.of(endTick, mesg));
        }
    }

    public List<Map<String, List<String>>> getMessages() {
        return messages;
    }

    public void clearChat() {
        activeMessages.clear();
        messages.clear();
        senderColors.clear();
        lengthMes.clear();
    }

    public int getSenderColor(int index) {
        return senderColors.get(index);
    }
}
