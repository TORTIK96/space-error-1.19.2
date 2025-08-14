package org.tor_tik96.chronoline.hud.Abilities;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;
import org.lwjgl.glfw.GLFW;
import org.tor_tik96.chronoline.Chronoline;
import org.tor_tik96.chronoline.Config;
import org.tor_tik96.chronoline.Upgrades.DistortedFragments.ClientDistortedFragments;
import org.tor_tik96.chronoline.Upgrades.Ephemeron.ClientEphemerons;
import org.tor_tik96.chronoline.Upgrades.Ephemeron.Ephemerons;
import org.tor_tik96.chronoline.hud.reputation.NpcReputations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.tor_tik96.chronoline.Utils.Utils.HexToDecimal;
import static org.tor_tik96.chronoline.hud.RegisterHUD.*;

public class AbilityScreen extends Screen {
    private final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Chronoline.MODID, "textures/gui/abilities2.png");
    private final ResourceLocation ICONS = ResourceLocation.fromNamespaceAndPath(Chronoline.MODID, "textures/gui/ability_icons.png");

    public static final List<AbilityTypes> types = List.of(AbilityTypes.DIGGING, AbilityTypes.UNDERWATER, AbilityTypes.MASTER_CHEF, AbilityTypes.AXE, AbilityTypes.NIGHT_VISION, AbilityTypes.DIMENSION, AbilityTypes.STUNNING, AbilityTypes.NO_CLOTHES, AbilityTypes.CRAFT_FRAGMENT);
    private final List<Icons> textures = List.of(Icons.DIGGING, Icons.UNDERWATER, Icons.MASTER_CHEF, Icons.AXE, Icons.NIGHT_VISION, Icons.DIMENSION, Icons.STUNNING, Icons.NO_CLOTHES, Icons.CRAFT_FRAGMENT);

    private int upIndex = 0;
    private int s = 0;
    private int s1 = 0;
    private boolean r = true;

    public AbilityScreen() {
        super(Component.literal("Способности"));
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        this.clearWidgets();
        int width = this.width / 2;
        int height = this.height;
        this.renderBackground(poseStack);
        RenderSystem.setShaderTexture(0, TEXTURE);
        blitSpec(poseStack, width - 85, height - 180, Icons.MENU.u1, Icons.MENU.v1, Icons.MENU.u2, Icons.MENU.v2, 219, 321);
        blitSpec(poseStack, width - 29 - s, height - 168, Icons.LEFT_ARROW.u1, Icons.LEFT_ARROW.v1, Icons.LEFT_ARROW.u2, Icons.LEFT_ARROW.v2, 219, 321);
        blitSpec(poseStack, width + 18 + s, height - 168, Icons.RIGHT_ARROW.u1, Icons.RIGHT_ARROW.v1, Icons.RIGHT_ARROW.u2, Icons.RIGHT_ARROW.v2, 219, 321);
        blitSpec(poseStack, width - 30, height - 140, Icons.NAME_LINE.u1, Icons.NAME_LINE.v1, Icons.NAME_LINE.u2, Icons.NAME_LINE.v2, 219, 321);
        blitSpec(poseStack, width - 78, height - 126, Icons.DESCRIPTION_LINE.u1, Icons.DESCRIPTION_LINE.v1, Icons.DESCRIPTION_LINE.u2, Icons.DESCRIPTION_LINE.v2, 219, 321);

        renderAbility(poseStack, width, height);

        drawScaledString(poseStack, Component.literal(String.valueOf(ClientEphemerons.getEphemerons())), width + 109, height - 103, HexToDecimal("FFFFFF"), 0.9F);

        s1++;
        if (s1 >= 18) {
            s1 = 0;
            if (r && s < 3) {
                s++;
            } else if (r) {
                r = false;
                s--;
            } else if (s > 0) {
                s--;
            } else {
                r = true;
                s++;
            }
        }


        for (Widget widget : this.renderables) {
            widget.render(poseStack, mouseX, mouseY, partialTicks);
        }
    }

    public void renderAbility(PoseStack poseStack, int width, int height) {
        RenderSystem.setShaderTexture(0, ICONS);
        Icons icon = textures.get(upIndex);
        int texWidth = icon.u2 - icon.u1;
        int texHeight = icon.v2 - icon.v1;
        int iconWidth = width - (texWidth / 2);
        int iconHeight = height - 168;
        if (icon.equals(Icons.DIGGING)) {
            iconWidth += 1;
        } else if (icon.equals(Icons.AXE)) {
            iconHeight -= 1;
        } else if (icon.equals(Icons.CRAFT_FRAGMENT)) {
            iconHeight -= 1;
        } else if (icon.equals(Icons.NIGHT_VISION)) {
            iconHeight += 2;
        }  else if (icon.equals(Icons.STUNNING)) {
            iconHeight += 1;
        }
        blitSpec(poseStack,  iconWidth, iconHeight, icon.u1, icon.v1, icon.u2, icon.v2, 56, 75);
        AbilityTypes type = types.get(upIndex);
        int y1 = 136;
        List<String> name = splitterMessage(type.getName(), 18);
        if (name.size() > 1) {
            for (String s : name) {
                drawScaledString(poseStack, Component.literal(s), width + 1, height - y1, HexToDecimal("FFFFFF"), 0.4F);
                y1 -= 4;
            }
        } else {
            drawScaledString(poseStack, Component.literal(name.get(0)), width + 1, height - 134, HexToDecimal("FFFFFF"), 0.4F);
        }
        List<String> mesg = splitterMessage(type.getDescription(), 47);
        int y = 122;
        for (String s : mesg) {
            drawScaledNoCenteredString(poseStack, Component.literal(s), width - 74, height - y, HexToDecimal("FFFFFF"), 0.4F);
            y -= 4;
        }
        int configAbility = Config.getAbilities().get(upIndex);
        if (configAbility == 0) {
            drawScaledNoCenteredString(poseStack, Component.literal("§bСтоимость: §r" + type.getCost() + " " + getEphemeronWord(type.getCost())), width - 74, height - y + 4, HexToDecimal("FFFFFF"), 0.4F);
            RenderSystem.setShaderTexture(0, TEXTURE);
            if (ClientEphemerons.getEphemerons() >= type.getCost()) {
                ImageButton button = imageButton(width + 38, height - 125, Icons.OPEN_BUTTONS.u2 - Icons.OPEN_BUTTONS.u1, 42, Icons.OPEN_BUTTONS.u1, Icons.OPEN_BUTTONS.v1, 42, TEXTURE, 219, 321, Operation.OPEN, type);
                this.addRenderableWidget(button);
            } else {
                ImageButton button = imageButton(width + 38, height - 125, Icons.UNACTIVE_OPEN_BUTTON.u2 - Icons.UNACTIVE_OPEN_BUTTON.u1, 42, Icons.UNACTIVE_OPEN_BUTTON.u1, Icons.UNACTIVE_OPEN_BUTTON.v1, 0, TEXTURE, 219, 321, Operation.NONE, type);
                this.addRenderableWidget(button);
            }
        } else if (configAbility == 1) {
            RenderSystem.setShaderTexture(0, TEXTURE);
            ImageButton button = imageButton(width + 38, height - 125, Icons.OFF_BUTTON.u2 - Icons.OFF_BUTTON.u1, 42, Icons.OFF_BUTTON.u1, Icons.OFF_BUTTON.v1, 0, TEXTURE, 219, 321, Operation.ON, type);
            this.addRenderableWidget(button);
        }  else if (configAbility == 2) {
            RenderSystem.setShaderTexture(0, TEXTURE);
            Operation operation = Operation.OFF;
            if (!type.isButton()) {
                operation = Operation.NONE;
            }
            ImageButton button = imageButton(width + 38, height - 125, Icons.ON_BUTTON.u2 - Icons.ON_BUTTON.u1, 42, Icons.ON_BUTTON.u1, Icons.ON_BUTTON.v1, 0, TEXTURE, 219, 321, operation, type);
            this.addRenderableWidget(button);
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

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        if (delta > 0) {
            //Вверх
            if (upIndex + 1 > 8) {
                upIndex = 0;
            } else upIndex += 1;
        } else if (delta < 0) {
            //Вниз
            if (upIndex - 1 < 0) {
                upIndex = 8;
            } else upIndex -= 1;
        }

        return true;
    }

    public String getEphemeronWord(int count) {
        List<Integer> twoToFour = List.of(2, 3, 4);
        int end = count % 10;
        String answer;
        if (count == 1 || (end == 1 && count != 11)) {
            answer = "эфемерон";
        } else if (twoToFour.contains(end)) {
            answer = "эфемерона";
        } else {
            answer = "эфемеронов";
        }

        return answer;
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
                    new_mesg = "";                    new_mesg += s + " ";
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

    private ImageButton imageButton(int x, int y, int width, int height, int xStart, int yStart, int yDiff, ResourceLocation texture, int texWidth, int texHeight, Operation operation, AbilityTypes type) {
        return new ImageButton(x, y, width, height, xStart, yStart, yDiff, texture, texWidth, texHeight, button -> {
            if (operation.equals(Operation.OPEN)) {
                Config.getAbilities().set(upIndex, 2);
                Ephemerons.subEphemerons(type.getCost());
            } else if (operation.equals(Operation.ON)) {
                Config.getAbilities().set(upIndex, 2);
            } else if (operation.equals(Operation.OFF)) {
                Config.getAbilities().set(upIndex, 1);
            }
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

    private enum Operation {
        NONE,
        OPEN,
        ON,
        OFF
    }

    private enum Icons {
        MENU (0, 0, 209, 102),
        NAME_LINE (49, 108, 109, 122),
        DESCRIPTION_LINE (1, 123, 116, 167),
        OPEN_BUTTONS (118, 109, 157, 193), //42
        UNACTIVE_OPEN_BUTTON (119, 193, 157, 235),
        ON_BUTTON (118, 235, 157, 277),
        OFF_BUTTON (118, 277, 157, 319),
        LEFT_ARROW (31, 169, 42, 187),
        RIGHT_ARROW (44, 169, 54, 187),

        DIGGING (0, 0, 18, 18),
        UNDERWATER (18, 0, 37, 18),
        MASTER_CHEF (38, 0, 55, 16),
        AXE (0, 18, 16, 37),
        NIGHT_VISION (19, 21, 36, 34),
        DIMENSION (38, 19, 55, 36),
        STUNNING (1, 39, 16, 54),
        NO_CLOTHES (18, 38, 37, 55),
        CRAFT_FRAGMENT (0, 56, 17, 75)



        ;

        private final int u1;
        private final int v1;
        private final int u2;
        private final int v2;

        Icons(int u1, int v1, int u2, int v2) {
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
