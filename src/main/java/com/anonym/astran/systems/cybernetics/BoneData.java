package com.anonym.astran.systems.cybernetics;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.ArrayList;
import java.util.List;

public class BoneData {
    public static final Codec<BoneData> CODEC = RecordCodecBuilder.create(questInstance ->
            questInstance.group(
                    Codec.list(SocketData.CODEC).fieldOf("Sockets").forGetter(BoneData::getSockets),
                    CyberModule.LIMB_TYPE_CODEC.fieldOf("Type").forGetter(BoneData::getType)
            ).apply(questInstance, (sockets, limbType) -> {
                if (sockets == null) {
                    List<SocketData> list = new ArrayList<>();
                    for (int i = 1; i < 10; i++ ) {
                        list.add(new SocketData(1));
                    }

                    return new BoneData(list,limbType);
                }
                if (sockets.isEmpty()) {
                    List<SocketData> list = new ArrayList<>();
                    for (int i = 1; i < 10; i++ ) {
                        list.add(new SocketData(1));
                    }
                    return new BoneData(list,limbType);
                }
                return new BoneData(sockets,limbType);
            }));
    public static final StreamCodec<ByteBuf, BoneData> STREAM_CODEC = ByteBufCodecs.fromCodec(CODEC);


    private List<SocketData> sockets;
    private LimbType type;


    public BoneData(List<SocketData> sockets, LimbType type) {
        if (sockets.isEmpty()) {
            List<SocketData> list = new ArrayList<>();
            for (int i = 1; i < 10; i++ ) {
                list.add(new SocketData(1));
            }
            this.sockets = list;
        } else {
            this.sockets = sockets;
        }
        this.type = type;
    }

    public BoneData(BoneData data) {
        this(data.sockets,data.type);
    }


    public BoneData(LimbType type) {
        this(new ArrayList<>(),type);
    }

    public List<SocketData> getSockets() {
        return this.sockets;
    }

    public void setSockets(List<SocketData> sockets) {
        this.sockets = sockets;
    }

    public void addSocket(SocketData data) {
        List<SocketData> socketList = new ArrayList<>(this.sockets);
        socketList.add(data);
        this.sockets = socketList;
    }

    public LimbType getType() {
        return this.type;
    }

    public BoneData copy() {
        return new BoneData(new ArrayList<>(this.sockets),this.type);
    }
}
