package org.tor_tik96.chronoline.hud;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;
import org.tor_tik96.chronoline.Chronoline;
import org.tor_tik96.chronoline.Upgrades.Stamina.ClientStamina;
import org.tor_tik96.chronoline.Utils.Timer;
import org.tor_tik96.chronoline.hud.Chat.Chat;

import static org.tor_tik96.chronoline.Utils.Utils.HexToDecimal;
import static org.tor_tik96.chronoline.hud.RegisterHUD.*;

public class generalHUD extends Gui {

    private final ResourceLocation HOTBAR = ResourceLocation.fromNamespaceAndPath(Chronoline.MODID, "textures/gui/hotbar.png");
    private final Chat chat;
    private int specTicktimer = 0;
    private int timerTextureY = 15;
    private int timerTextY = 19;
    //protected int tickCount;
    /*
    private final Minecraft minecraft;
    private final ItemRenderer itemRenderer;
    private final DebugScreenOverlay debugScreen;
    private final SpectatorGui spectatorGui;

    private final PlayerTabOverlay tabList;
    private final BossHealthOverlay bossOverlay;
    private final SubtitleOverlay subtitleOverlay;*/

    public generalHUD(Minecraft p_232355_, ItemRenderer p_232356) {
        super(p_232355_, p_232356);
        /*
        this.lastToolHighlight = ItemStack.EMPTY;
        this.minecraft = p_232355_;
        this.itemRenderer = p_232356_;
        this.debugScreen = new DebugScreenOverlay(p_232355_);
        this.spectatorGui = new SpectatorGui(p_232355_);
        this.chat = new ChatComponent(p_232355_);
        this.tabList = new PlayerTabOverlay(p_232355_, this);
        this.bossOverlay = new BossHealthOverlay(p_232355_);
        this.subtitleOverlay = new SubtitleOverlay(p_232355_);
        this.resetTitleTimes();*/
        //this.chat = new Chat(p_232355_);
        this.chat = new Chat();
    }

    public void render(PoseStack stack, float tick) {
        this.screenWidth = this.minecraft.getWindow().getGuiScaledWidth();
        this.screenHeight = this.minecraft.getWindow().getGuiScaledHeight();

        Font font = this.getFont();
        RenderSystem.enableBlend();
        if (Minecraft.useFancyGraphics()) {
            this.renderVignette(stack, this.getCameraPlayer());
        } else {
            RenderSystem.enableDepthTest();
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.defaultBlendFunc();
        }

        float f = this.minecraft.getDeltaFrameTime();
        this.scopeScale = Mth.lerp(0.5F * f, this.scopeScale, 1.125F);
        if (this.minecraft.options.getCameraType().isFirstPerson()) {
            if (this.minecraft.player.isScoping()) {
                this.renderSpyglassOverlay(this.scopeScale);
            } else {
                this.scopeScale = 0.5F;
                ItemStack itemstack = this.minecraft.player.getInventory().getArmor(3);
                if (itemstack.is(Blocks.CARVED_PUMPKIN.asItem())) {
                    this.renderTextureOverlay(PUMPKIN_BLUR_LOCATION, 1.0F);
                }
            }
        }

        if (this.minecraft.player.getTicksFrozen() > 0) {
            this.renderTextureOverlay(POWDER_SNOW_OUTLINE_LOCATION, this.minecraft.player.getPercentFrozen());
        }

        float f2 = Mth.lerp(tick, this.minecraft.player.oPortalTime, this.minecraft.player.portalTime);
        if (f2 > 0.0F && !this.minecraft.player.hasEffect(MobEffects.CONFUSION)) {
            this.renderPortalOverlay(f2);
        }

        if (this.minecraft.gameMode.getPlayerMode() == GameType.SPECTATOR) {
            this.spectatorGui.renderHotbar(stack);
        } else if (!this.minecraft.options.hideGui) {
            this.renderHotbar(tick, stack);
        }

        if (!this.minecraft.options.hideGui) {
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, GUI_ICONS_LOCATION);
            RenderSystem.enableBlend();
            this.renderCrosshair(stack);
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.defaultBlendFunc();
            this.minecraft.getProfiler().push("bossHealth");
            this.bossOverlay.render(stack);
            this.minecraft.getProfiler().pop();
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, HOTBAR);
            if (this.minecraft.gameMode.canHurtPlayer()) {
                this.renderPlayerHealth(stack);
            }

            if (this.minecraft.gameMode.hasExperience()) {
                if (this.isWaterBar()) {
                    this.renderAirSupplyBar(stack);
                } else {
                    this.renderExperienceBar(stack);
                }
            }

            RenderSystem.disableBlend();
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, GUI_ICONS_LOCATION);
            if (this.minecraft.options.heldItemTooltips && this.minecraft.gameMode.getPlayerMode() != GameType.SPECTATOR) {
                this.renderSelectedItemName(stack);
            } else if (this.minecraft.player.isSpectator()) {
                this.spectatorGui.renderTooltip(stack);
            }
        }

        if (this.minecraft.player.getSleepTimer() > 0) {
            this.minecraft.getProfiler().push("sleep");
            RenderSystem.disableDepthTest();
            float f3 = (float)this.minecraft.player.getSleepTimer();
            float f1 = f3 / 100.0F;
            if (f1 > 1.0F) {
                f1 = 1.0F - (f3 - 100.0F) / 10.0F;
            }

            int j = (int)(220.0F * f1) << 24 | 1052704;
            fill(stack, 0, 0, this.screenWidth, this.screenHeight, j);
            RenderSystem.enableDepthTest();
            this.minecraft.getProfiler().pop();
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        }

        if (this.minecraft.isDemo()) {
            this.renderDemoOverlay(stack);
        }

        this.renderEffects(stack);
        if (this.minecraft.options.renderDebug) {
            this.debugScreen.render(stack);
        }

        if (!this.minecraft.options.hideGui) {
            if (this.overlayMessageString != null && this.overlayMessageTime > 0) {
                this.minecraft.getProfiler().push("overlayMessage");
                float f4 = (float)this.overlayMessageTime - tick;
                int i1 = (int)(f4 * 255.0F / 20.0F);
                if (i1 > 255) {
                    i1 = 255;
                }

                if (i1 > 8) {
                    stack.pushPose();
                    stack.translate((double)(this.screenWidth / 2), (double)(this.screenHeight - 68), (double)0.0F);
                    RenderSystem.enableBlend();
                    RenderSystem.defaultBlendFunc();
                    int k1 = 16777215;
                    if (this.animateOverlayMessageColor) {
                        k1 = Mth.hsvToRgb(f4 / 50.0F, 0.7F, 0.6F) & 16777215;
                    }

                    int k = i1 << 24 & -16777216;
                    int l = font.width(this.overlayMessageString);
                    this.drawBackdrop(stack, font, -4, l, 16777215 | k);
                    font.drawShadow(stack, this.overlayMessageString, (float)(-l / 2), -4.0F, k1 | k);
                    RenderSystem.disableBlend();
                    stack.popPose();
                }

                this.minecraft.getProfiler().pop();
            }

            if (this.title != null && this.titleTime > 0) {
                this.minecraft.getProfiler().push("titleAndSubtitle");
                float f5 = (float)this.titleTime - tick;
                int j1 = 255;
                if (this.titleTime > this.titleFadeOutTime + this.titleStayTime) {
                    float f6 = (float)(this.titleFadeInTime + this.titleStayTime + this.titleFadeOutTime) - f5;
                    j1 = (int)(f6 * 255.0F / (float)this.titleFadeInTime);
                }

                if (this.titleTime <= this.titleFadeOutTime) {
                    j1 = (int)(f5 * 255.0F / (float)this.titleFadeOutTime);
                }

                j1 = Mth.clamp(j1, 0, 255);
                if (j1 > 8) {
                    stack.pushPose();
                    stack.translate((double)(this.screenWidth / 2), (double)(this.screenHeight / 2), (double)0.0F);
                    RenderSystem.enableBlend();
                    RenderSystem.defaultBlendFunc();
                    stack.pushPose();
                    stack.scale(4.0F, 4.0F, 4.0F);
                    int l1 = j1 << 24 & -16777216;
                    int i2 = font.width(this.title);
                    this.drawBackdrop(stack, font, -10, i2, 16777215 | l1);
                    font.drawShadow(stack, this.title, (float)(-i2 / 2), -10.0F, 16777215 | l1);
                    stack.popPose();
                    if (this.subtitle != null) {
                        stack.pushPose();
                        stack.scale(2.0F, 2.0F, 2.0F);
                        int k2 = font.width(this.subtitle);
                        this.drawBackdrop(stack, font, 5, k2, 16777215 | l1);
                        font.drawShadow(stack, this.subtitle, (float)(-k2 / 2), 5.0F, 16777215 | l1);
                        stack.popPose();
                    }

                    RenderSystem.disableBlend();
                    stack.popPose();
                }

                this.minecraft.getProfiler().pop();
            }

            this.subtitleOverlay.render(stack);
            Scoreboard scoreboard = this.minecraft.level.getScoreboard();
            Objective objective = null;
            PlayerTeam playerteam = scoreboard.getPlayersTeam(this.minecraft.player.getScoreboardName());
            if (playerteam != null) {
                int j2 = playerteam.getColor().getId();
                if (j2 >= 0) {
                    objective = scoreboard.getDisplayObjective(3 + j2);
                }
            }

            Objective objective1 = objective != null ? objective : scoreboard.getDisplayObjective(1);
            if (objective1 != null) {
                this.displayScoreboardSidebar(stack, objective1);
            }

            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            stack.pushPose();
            stack.translate((double)0.0F, (double)(this.screenHeight - 48), (double)0.0F);
            this.minecraft.getProfiler().push("chat");
            //this.chat.render(stack, this.tickCount);
            this.minecraft.getProfiler().pop();
            stack.popPose();
            objective1 = scoreboard.getDisplayObjective(0);
            if (this.minecraft.options.keyPlayerList.isDown() && (!this.minecraft.isLocalServer() || this.minecraft.player.connection.getOnlinePlayers().size() > 1 || objective1 != null)) {
                this.tabList.setVisible(true);
                this.tabList.render(stack, this.screenWidth, scoreboard, objective1);
            } else {
                this.tabList.setVisible(false);
            }

            this.renderSavingIndicator(stack);
            this.chat.render(stack, tickCount);
            if (Timer.tick > 0 || specTicktimer > 0) {
                this.renderTimer(stack);
            }
        }

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    private void renderTimer(PoseStack poseStack) {
        int color = HexToDecimal("FFFFFF");
        if (Timer.tick <= 100) color = HexToDecimal("FF5555");
        if (Timer.tick <= 2 && Timer.tick != 0) {
            specTicktimer = 60;
        }
        if (specTicktimer > 0) {
            if (specTicktimer <= 40) {
                timerTextureY--;
                timerTextY--;
            }
            specTicktimer--;
        } else {
            timerTextureY = 15;
            timerTextY = 19;
            specTicktimer = 0;
        }
        ResourceLocation timer = ResourceLocation.fromNamespaceAndPath(Chronoline.MODID, "textures/gui/timer.png");
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, timer);
        int height = screenHeight; //высота
        int width = screenWidth / 2; //ширина
        blit(poseStack, width - 34, timerTextureY, 0, 0, 67, 16, 67, 16);
        int totalTicks = Timer.tick;
        int totalMilliseconds = totalTicks * 50;
        int seconds = totalMilliseconds / 1000;
        int milliseconds = (totalMilliseconds % 1000) / 10;
        String timeString = String.format("%d:%01d", seconds, milliseconds);
        drawCenteredString(poseStack, minecraft.font, timeString, width, timerTextY, color);
    }

    private void renderPlayerHealth(PoseStack poseStack) {
        Player player = this.getCameraPlayer();
        int height = screenHeight; //высота
        int width = screenWidth / 2; //ширина
        if (player != null) {
            this.minecraft.getProfiler().popPush("health");
            blit(poseStack, width - 73, height - 37, 0, 27, 65, 15, 200, 100);
            renderHearts(poseStack, player);
            this.minecraft.getProfiler().pop();

            this.minecraft.getProfiler().popPush("food");
            blit(poseStack, width + 10, height - 37, 81, 27, 65, 15, 200, 100);
            renderFoods(poseStack, player);
            this.minecraft.getProfiler().pop();

            this.minecraft.getProfiler().popPush("stamina");
            blit(poseStack, width - 28, height - 69, 1, 0, 55, 25, 200, 100);
            renderStamina(poseStack, player);
            this.minecraft.getProfiler().pop();


        }

    }

    protected void renderHearts(PoseStack poseStack, Player player) {
        if (player != null) {
            int height = screenHeight; //высота
            int width = screenWidth / 2; //ширина
            int countHearts = (int) Math.ceil(player.getHealth());
            int cubes = countHearts / 2;
            int count = 0;
            int i1 = 72;
            for (int i = 72; count < cubes; i -= 6) {
                renderHeart(poseStack, HeartType.FULL, width - i, height - 36);
                count++;
                i1 = i;
            }
            if (countHearts % 2 > 0) {
                if (countHearts > 1) {
                    i1 -= 6;
                }
                renderHeart(poseStack, HeartType.PART, width - i1, height - 36);

            }
        }
    }

    private void renderHeart(PoseStack poseStack, HeartType type, int x, int y) {
        HeartModel model;
        if (type.equals(HeartType.FULL)) {
            if (isFlickering) {
                model = HeartModel.FLICKER_FULL;
            } else {
                model = HeartModel.FULL;
            }
        } else {
            if (isFlickering) {
                model = HeartModel.FLICKER_PART;
            } else {
                model = HeartModel.PART;
            }
        }
        blit(poseStack, x, y, model.getModelX(), model.getModelY(), model.getWidth(), model.getHeight(), 200, 100);
    }

    public enum HeartType {
        FULL,
        PART
    }

    public enum HeartModel {
        FULL (1, 44, 6, 10),
        FLICKER_FULL (8, 44, 6, 10),
        PART (15, 44, 4, 10),
        FLICKER_PART(20, 44, 4, 10)

        ;

        final int modelX;
        final int modelY;
        final int width;
        final int height;

        private HeartModel(int modelX, int modelY, int width, int height) {
            this.modelX = modelX;
            this.modelY = modelY;
            this.width = width;
            this.height = height;
        }

        public int getModelX() {
            return modelX;
        }

        public int getModelY() {
            return modelY;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }
    }

    protected void renderFoods(PoseStack poseStack, Player player) {
        if (player != null) {
            int height = screenHeight; //высота
            int width = screenWidth / 2; //ширина
            int countFoods = player.getFoodData().getFoodLevel();
            int cubes = countFoods / 2;
            int count = 0;
            int i1 = 65;
            for (int i = 65; count < cubes; i -= 6) {
                blit(poseStack, width + i, height - 36, 1, 54, 6, 10, 200, 100);
                count++;
                i1 = i;
            }
            if (countFoods % 2 > 0) {
                if (countFoods > 1) {
                    i1 -= 4;
                } else {
                    i1 = 67;
                }
                blit(poseStack, width + i1, height - 36, 8, 54, 4, 10, 200, 100);
            }
        }
    }

    private void renderExperienceBar(PoseStack poseStack) {
        Player player = this.getCameraPlayer();
        if (player != null) {
            this.minecraft.getProfiler().push("expBar");
            int height = screenHeight; //высота
            int width = screenWidth / 2; //ширина
            RenderSystem.setShaderTexture(0, HOTBAR);
            blit(poseStack, width - 8, height - 42, 64, 40, 15, 17, 200, 100);
            this.minecraft.getProfiler().pop();

            int expLevel = player.experienceLevel;
            this.minecraft.getProfiler().push("expLevel");
            int x = width - 1;
            int y = height - 32;
            int length = String.valueOf(expLevel).length();
            if (length == 1) {
                x = width - 1;
                y = height - 32;
            } else if (length == 2) {
                x = width - 3;
                y = height - 32;
            } else if (length == 3) {
                x = width - 4;
                y = height - 32;
            } else if (length == 4) {
                x = width - 6;
                y = height - 32;
            }
            drawScaledString(poseStack, String.valueOf(expLevel), x, y, 0xFFFFFF, 0.5F);
            this.minecraft.getProfiler().pop();
        }
    }

    private void renderAirSupplyBar(PoseStack poseStack) {
        this.minecraft.getProfiler().push("air");
        Player player = this.getCameraPlayer();
        if (player != null) {
            RenderSystem.setShaderTexture(0, HOTBAR);
            int height = screenHeight; //высота
            int width = screenWidth / 2; //ширина
            int maxAirSupply = player.getMaxAirSupply();
            int playerAirSupply = player.getAirSupply();
            int airPercentage = (playerAirSupply * 100) / maxAirSupply;
            if (airPercentage < 0) {
                airPercentage = 0;
            }
            String message = airPercentage + "%";
            blit(poseStack, width - 8, height - 41, 64, 23, 15, 17, 200, 100);
            int x = width - 1;
            int y = height - 32;
            int length = message.length();
            if (length == 2) {
                x = width - 3;
                y = height - 32;
            } else if (length == 3) {
                x = width - 4;
                y = height - 32;
            } else if (length == 4) {
                x = width - 6;
                y = height - 32;
            }
            drawScaledString(poseStack, message, x, y, 0xFFFFFF, 0.5F);
        }
        this.minecraft.getProfiler().pop();
    }

    private boolean isWaterBar() {
        Player player = this.getCameraPlayer();
        if (player != null) {
            int maxAirSupply = player.getMaxAirSupply();
            int playerAirSupply = Math.min(player.getAirSupply(), maxAirSupply);
            return player.isEyeInFluidType(Fluids.WATER.getFluidType()) || playerAirSupply < maxAirSupply;
        }
        return false;
    }

    public void renderStamina(PoseStack poseStack, Player player) {
        if (player != null) {
            int height = screenHeight; //высота
            int width = screenWidth / 2; //ширина
            String stamina = String.valueOf((ClientStamina.getStamina() * 100) / ClientStamina.getMaxStamina()) + "%";
            int w = 0;
            if (stamina.length() == 2) {
                w = 3;
            } else if (stamina.length() == 3) {
                w = 4;
            } else if (stamina.length() == 4) {
                w = 5;
            }
            drawScaledString(poseStack, stamina, width - w, height - 50, 0xFFFFFF, 0.4f);
            RenderSystem.setShaderTexture(0, HOTBAR);
            int cubes = ClientStamina.getStamina() / (ClientStamina.getMaxStamina() / 10);
            int count = 0;
            int a = 26;
            int b = -19;
            for (int i = 0; count < cubes; i++) {
                if (isFlickeringStamina) {
                    if (i % 2 > 0) {
                        blit(poseStack, width - a, height - 59, 6, 65, 5, 6, 200, 100);
                        a -= 5;
                    } else {
                        blit(poseStack, width - b, height - 59, 6, 65, 5, 6, 200, 100);
                        b += 5;
                    }
                } else {
                    if (i % 2 > 0) {
                        blit(poseStack, width - a, height - 59, 1, 65, 5, 6, 200, 100);
                        a -= 5;
                    } else {
                        blit(poseStack, width - b, height - 59, 1, 65, 5, 6, 200, 100);
                        b += 5;
                    }
                }
                count ++;
            }
        }
    }

    public void renderVignette(PoseStack poseStack, Entity entity) {

        WorldBorder worldborder = this.minecraft.level.getWorldBorder();
        float f = (float)worldborder.getDistanceToBorder(entity);
        double d0 = Math.min(worldborder.getLerpSpeed() * (double)worldborder.getWarningTime() * (double)1000.0F, Math.abs(worldborder.getLerpTarget() - worldborder.getSize()));
        double d1 = Math.max((double)worldborder.getWarningBlocks(), d0);
        if ((double)f < d1) {
            f = 1.0F - (float)((double)f / d1);
        } else {
            f = 0.0F;
        }

        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        if (f > 0.0F) {
            f = Mth.clamp(f, 0.0F, 1.0F);
            RenderSystem.setShaderColor(0.0F, f, f, 1.0F);
        } else {
            float f1 = this.vignetteBrightness;
            f1 = Mth.clamp(f1, 0.0F, 1.0F);
            RenderSystem.setShaderColor(f1, f1, f1, 1.0F);
        }
        RenderSystem.setShaderTexture(0, VIGNETTE_LOCATION);
        blit(poseStack, 0, 0, -90, 0.0F, 0.0F, this.screenWidth, this.screenHeight, this.screenWidth, this.screenHeight);
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.defaultBlendFunc();
    }

    private void renderSavingIndicator(PoseStack p_193835_) {
        if ((Boolean)this.minecraft.options.showAutosaveIndicator().get() && (this.autosaveIndicatorValue > 0.0F || this.lastAutosaveIndicatorValue > 0.0F)) {
            int i = Mth.floor(255.0F * Mth.clamp(Mth.lerp(this.minecraft.getFrameTime(), this.lastAutosaveIndicatorValue, this.autosaveIndicatorValue), 0.0F, 1.0F));
            if (i > 8) {
                Font font = this.getFont();
                int j = font.width(SAVING_TEXT);
                int k = 16777215 | i << 24 & -16777216;
                font.drawShadow(p_193835_, SAVING_TEXT, (float)(this.screenWidth - j - 10), (float)(this.screenHeight - 15), k);
            }
        }

    }

    private Player getCameraPlayer() {
        return !(this.minecraft.getCameraEntity() instanceof Player) ? null : (Player)this.minecraft.getCameraEntity();
    }

    public void drawScaledString(PoseStack poseStack, String text, int x, int y, int color, float scale) {
        poseStack.pushPose();
        poseStack.translate(x, y, 0);
        poseStack.scale(scale, scale, 1.0f); // Увеличение или уменьшение масштаба
        Minecraft.getInstance().font.draw(poseStack, text, 0, 0, color);
        poseStack.popPose();
    }

    private void updateVignetteBrightness(Entity p_93021_) {
        if (p_93021_ != null) {
            BlockPos blockpos = new BlockPos(p_93021_.getX(), p_93021_.getEyeY(), p_93021_.getZ());
            float f = LightTexture.getBrightness(p_93021_.level.dimensionType(), p_93021_.level.getMaxLocalRawBrightness(blockpos));
            float f1 = Mth.clamp(1.0F - f, 0.0F, 1.0F);
            this.vignetteBrightness += (f1 - this.vignetteBrightness) * 0.01F;
        }

    }

    public void tick() {
        this.tickAutosaveIndicator();
        if (this.overlayMessageTime > 0) {
            --this.overlayMessageTime;
        }

        if (this.titleTime > 0) {
            --this.titleTime;
            if (this.titleTime <= 0) {
                this.title = null;
                this.subtitle = null;
            }
        }

        ++this.tickCount;
        Entity entity = this.minecraft.getCameraEntity();
        if (entity != null) {
            this.updateVignetteBrightness(entity);
        }

        if (this.minecraft.player != null) {
            ItemStack itemstack = this.minecraft.player.getInventory().getSelected();
            if (itemstack.isEmpty()) {
                this.toolHighlightTimer = 0;
            } else if (!this.lastToolHighlight.isEmpty() && itemstack.getItem() == this.lastToolHighlight.getItem() && itemstack.getHoverName().equals(this.lastToolHighlight.getHoverName()) && itemstack.getHighlightTip(itemstack.getHoverName()).equals(this.lastToolHighlight.getHighlightTip(this.lastToolHighlight.getHoverName()))) {
                if (this.toolHighlightTimer > 0) {
                    --this.toolHighlightTimer;
                }
            } else {
                this.toolHighlightTimer = 40;
            }

            this.lastToolHighlight = itemstack;
        }

    }

    private void tickAutosaveIndicator() {
        MinecraftServer minecraftserver = this.minecraft.getSingleplayerServer();
        boolean flag = minecraftserver != null && minecraftserver.isCurrentlySaving();
        this.lastAutosaveIndicatorValue = this.autosaveIndicatorValue;
        this.autosaveIndicatorValue = Mth.lerp(0.2F, this.autosaveIndicatorValue, flag ? 1.0F : 0.0F);
    }

    public Chat getMyChat() {
        return this.chat;
    }
}
