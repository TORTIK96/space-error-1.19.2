package org.tor_tik96.chronoline.hud.reputation;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.lwjgl.glfw.GLFW;
import org.tor_tik96.chronoline.Chronoline;
import org.tor_tik96.chronoline.Npcs.NPC;
import org.tor_tik96.chronoline.Npcs.NpcUtils;

import java.util.*;

import static org.tor_tik96.chronoline.Utils.Utils.HexToDecimal;
import static org.tor_tik96.chronoline.hud.RegisterHUD.*;

public class ReputatationScreen extends Screen {

    private int index = 0;

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Chronoline.MODID, "textures/gui/reputation.png");
    private static final ResourceLocation TEXTURE_DEAD = ResourceLocation.fromNamespaceAndPath(Chronoline.MODID, "textures/gui/dead_reputation.png");

    public ReputatationScreen() {
        super(Component.literal("Репутация"));
    }


    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        List<NPC> npcs = NpcUtils.getOpenNpc();
        NPC npc = npcs.get(index);
        this.clearWidgets();
        int width = this.width / 2;
        int height = this.height;
        this.renderBackground(poseStack);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        if (npc.isAlive()) {
            RenderSystem.setShaderTexture(0, TEXTURE);
        } else {
            RenderSystem.setShaderTexture(0, TEXTURE_DEAD);
        }
        blitSpec(poseStack, width - 78, height - 200, Icons.NAME_LINE.u1, Icons.NAME_LINE.v1, Icons.NAME_LINE.u2, Icons.NAME_LINE.v2, 231, 200);
        ImageButton left_arrow = imageButton(width - 75, height - 197, Icons.LEFT_ARROW.u2-Icons.LEFT_ARROW.u1, Icons.LEFT_ARROW.v2-Icons.LEFT_ARROW.v1, Icons.LEFT_ARROW.u1, Icons.LEFT_ARROW.v1, 0, TEXTURE, 231, 200, modifyValue(index - 1 , 0, npcs.size() - 1));
        ImageButton right_arrow = imageButton(width + 63, height - 197, Icons.RIGHT_ARROW.u2-Icons.RIGHT_ARROW.u1, Icons.RIGHT_ARROW.v2-Icons.RIGHT_ARROW.v1, Icons.RIGHT_ARROW.u1, Icons.RIGHT_ARROW.v1, 0, TEXTURE, 231, 200, modifyValue(index + 1, 0, npcs.size() - 1));
        this.addRenderableWidget(left_arrow);
        this.addRenderableWidget(right_arrow);
        blitSpec(poseStack, width - 29, height - 163, Icons.CHARACTER_PANEL.u1, Icons.CHARACTER_PANEL.v1, Icons.CHARACTER_PANEL.u2, Icons.CHARACTER_PANEL.v2, 231, 200);
        blitSpec(poseStack, width - 102, height - 178, Icons.POSITIVE_PROPERTIES.u1, Icons.POSITIVE_PROPERTIES.v1, Icons.POSITIVE_PROPERTIES.u2, Icons.POSITIVE_PROPERTIES.v2, 231, 200);
        blitSpec(poseStack, width + 32, height - 178, Icons.NEGATIVE_PROPERTIES.u1, Icons.NEGATIVE_PROPERTIES.v1, Icons.NEGATIVE_PROPERTIES.u2, Icons.NEGATIVE_PROPERTIES.v2, 231, 200);
        blitSpec(poseStack, width - 37, height - 65, Icons.REPUTATION_LINE.u1, Icons.REPUTATION_LINE.v1, Icons.REPUTATION_LINE.u2, Icons.REPUTATION_LINE.v2, 231, 200);
        int x1 = width - 35;

        for (int i = 1; i <= npc.getReputation() / 10; i++) {
            if (i <= 3) {
                blitSpec(poseStack, x1, height - 63, Icons.RED_CUBE.u1, Icons.RED_CUBE.v1, Icons.RED_CUBE.u2, Icons.RED_CUBE.v2, 231, 200);
            } else if (i <= 7) {
                blitSpec(poseStack, x1, height - 63, Icons.YELLOW_CUBE.u1, Icons.YELLOW_CUBE.v1, Icons.YELLOW_CUBE.u2, Icons.YELLOW_CUBE.v2, 231, 200);
            }  else {
                blitSpec(poseStack, x1, height - 63, Icons.GREEN_CUBE.u1, Icons.GREEN_CUBE.v1, Icons.GREEN_CUBE.u2, Icons.GREEN_CUBE.v2, 231, 200);
            }
            x1 += 7;
        }

        renderCharacter(poseStack, npc, npc.getReputation());

        for (Widget widget : this.renderables) {
            widget.render(poseStack, mouseX, mouseY, partialTicks);
        }
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE) { // Escape
            this.onClose();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    private void renderCharacter(PoseStack poseStack, NPC npc, int reputation) {
        int width = this.width / 2;
        int height = this.height;
        NpcReputations type = npc.getType();
        RenderSystem.setShaderTexture(0, type.getTexture());
        int texWidth = type.getU2() - type.getU1();
        int texHeight = type.getV2() - type.getV1();
        blitSpec(poseStack, width - (texWidth / 2), height - 159, type.getU1(), type.getV1(), type.getU2(), type.getV2(), texWidth, texHeight);
        drawScaledString(poseStack, type.getName(), width, height - 194, HexToDecimal("FFFFFF"), 0.9F);
        drawScaledString(poseStack, getReputationPercent(reputation), width, height - 45, HexToDecimal("FFFFFF"), 0.4F);
        if (npc.isAlive()) {
            List<String> positive = npc.getPositive();
            List<String> negative = npc.getNegative();
            int y1 = 160;
            for (String s1 : positive) {
                List<String> split = splitterMessage(s1, 19);
                for (String s : split) {
                    drawScaledNoCenteredString(poseStack, Component.literal(s), width - 94, height - y1, HexToDecimal("FFFFFF"), 0.5F);
                    y1 -= 4;
                }
                if (y1 <= 84) break;
            }
            y1 = 160;
            for (String s1 : negative) {
                List<String> split = splitterMessage(s1, 19);
                for (String s : split) {
                    drawScaledNoCenteredString(poseStack, Component.literal(s), width + 40, height - y1, HexToDecimal("FFFFFF"), 0.5F);
                    y1 -= 4;
                }
                if (y1 <= 84) break;
            }
        }
    }

    private Component getReputationPercent(int reputation) {
        String rep = "";
        if (reputation >= 0 && reputation < 10) {
            rep = "§4Враждебность";
        } else if (reputation >= 10 && reputation < 30) {
            rep = "§cНеприязнь";
        } else if (reputation >= 30 && reputation < 50) {
            rep = "§6Недоверие";
        } else if (reputation >= 50 && reputation < 61) {
            rep = "§eРавнодушие";
        } else if (reputation >= 61 && reputation < 76) {
            rep = "§2Доверие";
        } else if (reputation >= 76 && reputation <= 100) {
            rep = "§aДружба";
        }
        return Component.literal(rep + " " + reputation + "%");
    }

    private int modifyValue(int value, int min, int max) {
        if (value > max) {
            return min;
        } else if (value < min) {
            return max;
        } else {
            return value;
        }
    }

    public List<String> splitterMessage(String message, int oneLength) {
        List<String> mesg = new ArrayList<>();
        String new_mesg = "";

        if (message.length() > 1000) {
            return List.of("Сообщение не может быть выведено так как слишком длинное!");
        }

        List<String> words = Arrays.stream(message.split(" ")).toList();

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

    private ImageButton imageButton(int x, int y, int width, int height, int xStart, int yStart, int yDiff, ResourceLocation texture, int texWidth, int texHeight, int index) {
        return new ImageButton(x, y, width, height, xStart, yStart, yDiff, texture, texWidth, texHeight, button -> {
            this.index = index;
        });
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

    private void blitSpec(PoseStack poseStack, int x, int y, int u1, int v1, int u2, int v2, int width, int height) {
        blit(poseStack, x, y, u1, v1, u2-u1, v2-v1, width, height);
    }

    private enum Icons {
        LEFT_ARROW (34, 4, 45, 16),
        RIGHT_ARROW (46, 4, 57, 16),
        NAME_LINE (39, 30, 194, 48),
        CHARACTER_PANEL (88, 67, 145, 144),
        POSITIVE_PROPERTIES (15, 52, 84, 159),
        NEGATIVE_PROPERTIES (149, 52, 218, 159),
        REPUTATION_LINE (79, 161, 153, 189),
        RED_CUBE (80, 0, 87, 14),
        YELLOW_CUBE (72, 0, 79, 14),
        GREEN_CUBE (64, 0, 71, 14),
        RED_PART_CUBE (80, 14, 84, 28),
        YELLOW_PART_CUBE (72, 14, 76, 28),
        GREEN_PART_CUBE (64, 14, 68, 28),



        ;


        private final int u1;
        private final int v1;
        private final int u2;
        private final int v2;

        private Icons(int u1, int v1, int u2, int v2) {
            this.u1 = u1;
            this.v1 = v1;
            this.u2 = u2;
            this.v2 = v2;
        }

        public int getU1() {
            return u1;
        }

        public int getV1() {
            return v1;
        }

        public int getU2() {
            return u2;
        }

        public int getV2() {
            return v2;
        }
    }
}
