package org.tor_tik96.chronoline.hud.upgrades;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.glfw.GLFW;
import org.tor_tik96.chronoline.Chronoline;
import org.tor_tik96.chronoline.Upgrades.Craft.ClientCraft;
import org.tor_tik96.chronoline.Upgrades.Craft.Craft;
import org.tor_tik96.chronoline.Upgrades.DistortedFragments.ClientDistortedFragments;
import org.tor_tik96.chronoline.Upgrades.DistortedFragments.DistortedFragments;
import org.tor_tik96.chronoline.Upgrades.Magic.ClientMagic;
import org.tor_tik96.chronoline.Upgrades.Magic.Magic;
import org.tor_tik96.chronoline.Upgrades.Rhetoric.ClientRhetoric;
import org.tor_tik96.chronoline.Upgrades.Rhetoric.Rhetoric;
import org.tor_tik96.chronoline.Upgrades.Stamina.ClientStamina;
import org.tor_tik96.chronoline.Upgrades.Stamina.Stamina;
import org.tor_tik96.chronoline.Upgrades.Strength.ClientStrength;
import org.tor_tik96.chronoline.Upgrades.Strength.Strength;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.tor_tik96.chronoline.Utils.Utils.HexToDecimal;

public class UpgradesScreen extends Screen {
    private final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Chronoline.MODID, "textures/gui/upgrades.png");
    private final ResourceLocation FRAGMENT = ResourceLocation.fromNamespaceAndPath(Chronoline.MODID, "textures/gui/fragment.png");
    private int changeTick = 0;
    private final List<Icons> ups = List.of(Icons.STRENGTH, Icons.STAMINA, Icons.RHETORIC, Icons.MAGIC, Icons.CRAFT);
    List<List<Integer>> allIndexes = List.of(
            List.of(0, 1, 2, 3, 4),
            List.of(1, 2, 3, 4, 0),
            List.of(2, 3, 4, 0, 1),
            List.of(3, 4, 0, 1, 2),
            List.of(4, 0, 1, 2, 3)
    );
    private final List<Integer> xS = List.of(-54, -37, 53, 70, 9);
    private final List<Integer> yS = List.of(142, 95, 95, 142, 198);
    private int upIndex = 0;
    private int changeX = 92;


    public UpgradesScreen() {
        super(Component.literal("Прокачка"));
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(poseStack);
        int width = this.width / 2;
        int height = this.height;
        renderMenu(poseStack, width, height);
        for (Widget widget : this.renderables) {
            widget.render(poseStack, mouseX, mouseY, partialTicks);
        }
    }


    private void renderMenu(PoseStack poseStack, int width, int height) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        this.clearWidgets();
        RenderSystem.setShaderTexture(0, TEXTURE);
        if (changeTick == 0) {
            blitSpec(poseStack, width - 92, height - 225, Icons.MENU.getU1(), Icons.MENU.getV1(), Icons.MENU.getU2(), Icons.MENU.getV2(), 690, 195);
            int index = upIndex+1;
            if (index > 4) {
                index = 0;
            }
            for (int i = 0; i <= 4; i++) {
                int x = width - xS.get(i);
                int y = height - yS.get(i);
                Icons type = ups.get(index);

                if (type.equals(Icons.RHETORIC)) {
                    y += 1;
                } else if (type.equals(Icons.MAGIC)) {
                    x += 2;
                } else if (type.equals(Icons.CRAFT)) {
                    y -= 1;
                    x += 1;
                }

                blitSpec(poseStack, x, y, type.u1, type.v1, type.u2, type.v2, 690, 195);
                if (index + 1 > 4) {
                    index = 0;
                } else {
                    index++;
                }
            }
            drawScaledString(poseStack, Component.literal(String.valueOf(ClientDistortedFragments.getDistortedFragments())), width - (-8), height - 58, HexToDecimal("FFFFFF"), 0.9F);
            String name = "";
            String level = "";
            String newLevel = "Стоимость нового уровня: §k???";
            int nextLevel = 0;
            Icons type = ups.get(upIndex);
            if (type.equals(Icons.STRENGTH)) {
                name = "§9Сила";
                level = "Уровень: " + ClientStrength.getStrength();
                nextLevel = getNextLevelCost(ClientStrength.getStrength() + 1);
                if (nextLevel != 0) {
                    newLevel = "Стоимость нового уровня: " + nextLevel;
                }
            } else if (type.equals(Icons.STAMINA)) {
                name = "§bВыносливость";
                level = "Уровень: " + ((ClientStamina.getMaxStamina() - 100) / 2);
                nextLevel = getNextLevelCost(((ClientStamina.getMaxStamina() - 100) / 2) + 1);
                if (nextLevel != 0) {
                    newLevel = "Стоимость нового уровня: " + nextLevel;
                }
            } else if (type.equals(Icons.RHETORIC)) {
                name = "§7Риторика";
                level = "Уровень: " + ClientRhetoric.getRhetoric();
                nextLevel = getNextLevelCost(ClientRhetoric.getRhetoric() + 1);
                if (nextLevel != 0) {
                    newLevel = "Стоимость нового уровня: " + nextLevel;
                }
            } else if (type.equals(Icons.MAGIC)) {
                name = "§5Магия";
                level = "Уровень: " + ClientMagic.getMagic();
                nextLevel = getNextLevelCost(ClientMagic.getMagic() + 1);
                if (nextLevel != 0) {
                    newLevel = "Стоимость нового уровня: " + nextLevel;
                }
            } else if (type.equals(Icons.CRAFT)) {
                name = "§6Ремесло";
                level = "Уровень: " + ClientCraft.getCraft();
                nextLevel = getNextLevelCost(ClientCraft.getCraft() + 1);
                if (nextLevel != 0) {
                    newLevel = "Стоимость нового уровня: " + nextLevel;
                }
            }
            drawScaledString(poseStack, Component.literal(name), width, height - 167, HexToDecimal("FFFFFF"), 0.9F);
            drawScaledNoCenteredString(poseStack, Component.literal(level), width - 33, height - 157, HexToDecimal("FFFFFF"), 0.9F);
            List<String> split = splitterMessage(newLevel, 15);
            int y = 147;
            for (String s : split) {
                if (nextLevel != 0) {
                    drawScaledNoCenteredString(poseStack, Component.literal(s), width - 33, height - y, HexToDecimal("FFFFFF"), 0.8F);
                } else {
                    drawScaledNoCenteredString(poseStack, Component.literal(s), width - 33, height - y, HexToDecimal("FFD700"), 0.8F);
                }
                y -= 10;
            }
            RenderSystem.setShaderTexture(0, FRAGMENT);
            blitSpec(poseStack, width - 33 + ((int) (Minecraft.getInstance().font.width(split.get(split.size()-1)) / 1.1)) - 2, height - (y + 12), 1, 0, 10, 10, 11, 11);
            RenderSystem.setShaderTexture(0, TEXTURE);
            if (nextLevel != 0 && ClientDistortedFragments.getDistortedFragments() >= nextLevel) {
                ImageButton button = upgradeButtonSpec(width - 36, height - 115, 72, 17, Icons.ACTIVE_BUTTONS.u1, Icons.ACTIVE_BUTTONS.v1, 17, TEXTURE, 690, 195, type, nextLevel);
                this.addRenderableWidget(button);
            } else if (nextLevel != 0) {
                blitSpec(poseStack, width - 36, height - 115, Icons.UNACTIVE_BUTTON.u1, Icons.UNACTIVE_BUTTON.v1, Icons.UNACTIVE_BUTTON.u2, Icons.UNACTIVE_BUTTON.v2, 690, 195);
            } else {
                blitSpec(poseStack, width - 34, height - 115, Icons.MAX_BUTTON.u1, Icons.MAX_BUTTON.v1, Icons.MAX_BUTTON.u2, Icons.MAX_BUTTON.v2, 690, 195);
            }
        } else {
            blitSpec(poseStack, width - changeX, height - 225, Icons.CHANGE_MENU.getU1(), Icons.CHANGE_MENU.getV1(), Icons.CHANGE_MENU.getU2(), Icons.CHANGE_MENU.getV2(), 690, 195);
            if (changeTick > 15) {
                changeX += 1;
            } else if (changeTick > 5) {
                changeX -= 1;
            } else {
                changeX += 1;
            }
            changeTick--;
            if (changeTick < 0) {
                changeTick = 0;
            }
        }
    }

    public static int getNextLevelCost(int level) {
        if (level <= 500 && level >= 1) {
            int cost = 0;
            for (int i = 0; i < level; i++) {
                int factor = getFactor(level);
                cost += factor;
            }
            return cost;

        } else {
            return 0;
        }
    }

    public static int getFactor(int level) {
        int category = (level - 1) / 10 + 1;
        int factor = 20;
        for (int i = 0; i < category - 1; i++) {
            factor++;
        }
        return factor;
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


    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE) { // Escape
            this.onClose();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
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

    private ImageButton upgradeButtonSpec(int x, int y, int width, int height, int xStart, int yStart, int yDiff, ResourceLocation texture, int texWidth, int texHeight, Icons type, int cost) {
        return new ImageButton(x, y, width, height, xStart, yStart, yDiff, texture, texWidth, texHeight, button -> {
            if (ClientDistortedFragments.getDistortedFragments() >= cost) {
                DistortedFragments.subDistortedFragments(cost);
                if (type.equals(Icons.STRENGTH)) {
                    Strength.addStrength(1);
                } else if (type.equals(Icons.STAMINA)) {
                    Stamina.setMaxStamina(ClientStamina.getMaxStamina() + 2);
                } else if (type.equals(Icons.RHETORIC)) {
                    Rhetoric.addRhetoric(1);
                } else if (type.equals(Icons.MAGIC)) {
                    Magic.addMagic(1);
                } else if (type.equals(Icons.CRAFT)) {
                    Craft.addCraft(1);
                }
            }
        });
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        if (changeTick == 0) {
            if (delta > 0) {
                //Вверх
                if (upIndex + 1 > 4) {
                    upIndex = 0;
                } else upIndex += 1;
            } else if (delta < 0) {
                //Вниз
                if (upIndex - 1 < 0) {
                    upIndex = 4;
                } else upIndex -= 1;
            }
            changeTick = 20;
        }

        return true;
    }

    private enum Icons {
        MENU (271, 7, 454, 190),
        CHANGE_MENU (495, 7, 678, 190),
        STRENGTH (230, 82, 247, 98),
        STAMINA (230, 114, 247, 129),
        RHETORIC (230, 130, 246, 144),
        MAGIC (230, 98, 243, 113),
        CRAFT (230, 66, 246, 82),
        ACTIVE_BUTTONS (61, 11, 72, 34),
        UNACTIVE_BUTTON (61, 46, 133, 63),
        MAX_BUTTON (63, 64, 131, 81)

        //Для всех u2 и v2 это вторая координата, а у кнопок ширина и высота



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
