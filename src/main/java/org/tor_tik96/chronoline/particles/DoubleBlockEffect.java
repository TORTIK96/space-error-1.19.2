package org.tor_tik96.chronoline.particles;

import com.lowdragmc.photon.client.emitter.IParticleEmitter;
import com.lowdragmc.photon.client.fx.BlockEffect;
import com.lowdragmc.photon.client.fx.FX;
import com.lowdragmc.photon.client.fx.FXEffect;
import com.mojang.math.Vector3d;
import com.mojang.math.Vector3f;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.*;

public class DoubleBlockEffect extends FXEffect {
    public static Map<BlockPos, List<DoubleBlockEffect>> CACHE = new HashMap();
    public final Vec3 pos;
    public final BlockPos blockPos;
    private boolean checkState;
    private BlockState lastState;

    public DoubleBlockEffect(FX fx, Level level, Vec3 pos) {
        super(fx, level);
        this.pos = pos;
        this.blockPos = new BlockPos(pos);
    }

    public boolean updateEmitter(IParticleEmitter emitter) {
        if (this.level.isLoaded(this.blockPos) && this.lastState.getBlock() == this.level.getBlockState(this.blockPos).getBlock() && (!this.checkState || this.level.getBlockState(this.blockPos) == this.lastState)) {
            return false;
        } else {
            emitter.remove(this.forcedDeath);
            return this.forcedDeath;
        }
    }

    public void start() {
        this.emitters.clear();
        this.emitters.addAll(this.fx.generateEmitters());
        if (!this.emitters.isEmpty()) {
            if (!this.allowMulti) {
                List effects = CACHE.computeIfAbsent(this.blockPos, (p) -> new ArrayList<>());
                Iterator iter = effects.iterator();

                while(iter.hasNext()) {
                    DoubleBlockEffect effect = (DoubleBlockEffect)iter.next();
                    boolean removed = false;
                    if (effect.emitters.stream().noneMatch((e) -> e.self().isAlive())) {
                        iter.remove();
                        removed = true;
                    }

                    if (effect.fx.equals(this.fx) && !removed) {
                        return;
                    }
                }

                effects.add(this);
            }

            Vec3 realPos = (new Vec3(new Vector3f(this.pos))).add(this.xOffset + (double)0.5F, this.yOffset + (double)0.5F, this.zOffset + (double)0.5F);

            for(IParticleEmitter emitter : this.emitters) {
                if (!emitter.isSubEmitter()) {
                    emitter.reset();
                    emitter.self().setDelay(this.delay);
                    emitter.emmitToLevel(this, this.level, realPos.x, realPos.y, realPos.z, this.xRotation, this.yRotation, this.zRotation);
                }
            }

            this.lastState = this.level.getBlockState(this.blockPos);
        }
    }

    public void setCheckState(boolean checkState) {
        this.checkState = checkState;
    }
}