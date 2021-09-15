//package de.jeff_media.jefflib.internal.blackhole;
//
//import com.comphenix.protocol.PacketType;
//import com.comphenix.protocol.ProtocolLibrary;
//import com.comphenix.protocol.events.ListenerPriority;
//import com.comphenix.protocol.events.PacketAdapter;
//import com.comphenix.protocol.events.PacketEvent;
//import de.jeff_media.jefflib.JeffLib;
//import de.jeff_media.jefflib.internal.InternalOnly;
//import lombok.experimental.UtilityClass;
//import org.bukkit.entity.Player;
//import org.jetbrains.annotations.NotNull;
//
//import java.util.ArrayList;
//import java.util.Collection;
//
//@InternalOnly
//@Deprecated
//@UtilityClass
//public class ChatMuteHandler {
//
//    private static final Collection<Player> mutedPlayers = new ArrayList<>();
//
//    public static boolean isMuted(@NotNull final Player player) {
//        return mutedPlayers.contains(player);
//    }
//
//    public static void mute(@NotNull final Player player, final boolean muted) {
//        if(muted) {
//            mutedPlayers.add(player);
//        } else {
//            mutedPlayers.remove(player);
//        }
//    }
//
//    public static void init() {
//
//        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(JeffLib.getPlugin(), ListenerPriority.HIGHEST, PacketType.Play.Server.CHAT) {
//
//            @Override
//            public void onPacketSending(final PacketEvent event) {
//
//                if (event.getPacketType() != PacketType.Play.Server.CHAT) return;
//
//                /*System.out.println("Chat event: ");
//                for(WrappedChatComponent comp : event.getPacket().getChatComponents().getValues()) {
//                    System.out.println(comp.getJson());
//                }*/
//
//                if (mutedPlayers.contains(event.getPlayer())) {
//                    event.setCancelled(true);
//                    //System.out.println("  (Cancelled)");
//                }
//
//            }
//
//
//        });
//    }
//
//}
