package org.tor_tik96.chronoline.Network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import org.tor_tik96.chronoline.Config;
import org.tor_tik96.chronoline.Upgrades.Strength.ClientStrength;

import java.util.function.Supplier;

public class SetCloseDoorPacketS2C {
    private final BlockPos pos;
    private final boolean add;

    public SetCloseDoorPacketS2C(BlockPos pos, boolean add) {
        this.pos = pos;
        this.add = add;
    }

    public static SetCloseDoorPacketS2C decode(FriendlyByteBuf buf) {
        BlockPos pos = buf.readBlockPos();
        boolean add = buf.readBoolean();
        return new SetCloseDoorPacketS2C(pos, add);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeBoolean(add);
    }

    public static void handle(SetCloseDoorPacketS2C packet, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            //ТУТ МЫ НА КЛИЕНТЕ
            if (packet.add) {
                Config.addCloseDoor(packet.pos);
            } else {
                Config.delCloseDoor(packet.pos);
            }
        });
        context.get().setPacketHandled(true);
    }
}
