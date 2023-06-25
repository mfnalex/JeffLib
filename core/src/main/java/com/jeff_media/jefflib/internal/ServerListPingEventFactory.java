/*
 * Copyright (c) 2023. JEFF Media GbR / mfnalex et al.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.jeff_media.jefflib.internal;

import com.jeff_media.jefflib.ReflUtils;
import com.jeff_media.jefflib.internal.annotations.Internal;
import java.lang.reflect.Constructor;
import java.net.InetAddress;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.server.ServerListPingEvent;
import org.jetbrains.annotations.Contract;

/**
 * Factory for {@link ServerListPingEvent}, which is required for versions using chat preview
 *
 * @internal
 */
@SuppressWarnings("unchecked")
@Internal
public class ServerListPingEventFactory {

    private static final ServerListPingEventConstructorInvoker CONSTRUCTOR_INVOKER;

    static {
        Constructor<ServerListPingEvent> constructor;

        findConstructor:
        {
            // Try the proper constructor
            constructor = getProperConstructor();
            if (constructor != null) {
                CONSTRUCTOR_INVOKER = new ProperServerListPingEventConstructorInvoker(constructor);
                break findConstructor;
            }

            // Try the corrupted constructor
            constructor = getCorruptedConstructor();
            if (constructor != null) {
                CONSTRUCTOR_INVOKER = new CorruptedServerListPingEventConstructorInvoker(constructor);
                break findConstructor;
            }

            // Couldn't find any constructor
            throw new RuntimeException("Couldn't find any constructor for ServerListPingEvent");
        }
    }

    /**
     * Creates a new ServerListPingEvent
     *
     * @param hostname               The hostname
     * @param address                The address
     * @param motd                   The MOTD
     * @param shouldSendChatPreviews Whether chat previews should be sent
     * @param numPlayers             The number of online players
     * @param maxPlayers             The maximum number of players
     * @return The new ServerListPingEvent
     */
    @Contract("_, _, _, _, _, _ -> new")
    public static ServerListPingEvent createServerListPingEvent(String hostname, InetAddress address, String motd, boolean shouldSendChatPreviews, int numPlayers, int maxPlayers) {
        return CONSTRUCTOR_INVOKER.create(hostname, address, motd, shouldSendChatPreviews, numPlayers, maxPlayers);

    }

    /**
     * Gets the proper constructor for ServerListPingEvent
     *
     * @return The proper constructor
     */
    private static Constructor<ServerListPingEvent> getProperConstructor() {
        return (Constructor<ServerListPingEvent>) ReflUtils.getConstructor(ServerListPingEvent.class, String.class, InetAddress.class, String.class, int.class, int.class);
    }

    /**
     * Gets the corrupted constructor for ServerListPingEvent
     *
     * @return The corrupted constructor
     */
    private static Constructor<ServerListPingEvent> getCorruptedConstructor() {
        return (Constructor<ServerListPingEvent>) ReflUtils.getConstructor(ServerListPingEvent.class, String.class, InetAddress.class, String.class, boolean.class, int.class, int.class);
    }

    private interface ServerListPingEventConstructorInvoker {
        ServerListPingEvent create(String hostname, InetAddress address, String motd, boolean shouldSendChatPreviews, int numPlayers, int maxPlayers);
    }

    @RequiredArgsConstructor
    private static class ProperServerListPingEventConstructorInvoker implements ServerListPingEventConstructorInvoker {

        private final Constructor<ServerListPingEvent> constructor;

        @Override
        public ServerListPingEvent create(String hostname, InetAddress address, String motd, boolean shouldSendChatPreviews, int numPlayers, int maxPlayers) {
            try {
                return constructor.newInstance(hostname, address, motd, numPlayers, maxPlayers);
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @RequiredArgsConstructor
    private static class CorruptedServerListPingEventConstructorInvoker implements ServerListPingEventConstructorInvoker {

        private final Constructor<ServerListPingEvent> constructor;

        @Override
        public ServerListPingEvent create(String hostname, InetAddress address, String motd, boolean shouldSendChatPreviews, int numPlayers, int maxPlayers) {
            try {
                return constructor.newInstance(hostname, address, motd, shouldSendChatPreviews, numPlayers, maxPlayers);
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
