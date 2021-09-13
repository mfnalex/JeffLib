package de.jeff_media.jefflib.internal.blackhole;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import de.jeff_media.jefflib.JeffLib;
import de.jeff_media.jefflib.internal.InternalOnly;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@InternalOnly
@Deprecated
public class ChatMuteHandler {

    private static final List<Player> mutedPlayers = new ArrayList<>();

    public static boolean isMuted(@NotNull final Player player) {
        return mutedPlayers.contains(player);
    }

    public static void mute(@NotNull final Player player, boolean muted) {
        if(muted) {
            mutedPlayers.add(player);
        } else {
            mutedPlayers.remove(player);
        }
    }

    public static void init() {

        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(JeffLib.getPlugin(), ListenerPriority.HIGHEST, PacketType.Play.Server.CHAT) {

            @Override
            public void onPacketSending(PacketEvent event) {

                if (event.getPacketType() != PacketType.Play.Server.CHAT) return;

                if (mutedPlayers.contains(event.getPlayer())) {
                    event.setCancelled(true);
                }

            }


        });
    }

}
