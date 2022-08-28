/*
 *     Copyright (c) 2022. JEFF Media GbR / mfnalex et al.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package com.jeff_media.jefflib.data;

import com.jeff_media.jefflib.JeffLib;
import com.jeff_media.jefflib.PDCUtils;
import com.jeff_media.jefflib.Tasks;
import com.jeff_media.jefflib.internal.annotations.NMS;
import com.jeff_media.jefflib.internal.cherokee.Validate;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.Advancement;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


/**
 * Represents a toast message (a message that is displayed at the top right of the screen). They are dynamically generated advancements, whose .json files
 * will be deleted after sending the message
 */
@NMS
public class ToastMessage {

    private static final String CRITERIA = "display";
    private static final String EMPTY_STRING = "";
    private final String title;
    private final String description;
    private final boolean announceToChat;
    private final FrameType frameType;
    private final Material icon;
    private final NamespacedKey key = PDCUtils.getRandomKey();

    public ToastMessage(@Nonnull final String title, @Nullable final String description, @Nonnull final Material icon, @Nonnull final FrameType frameType, boolean announceToChat) {
        this.title = title;
        this.frameType = frameType;
        this.icon = icon;
        this.announceToChat = announceToChat;
        this.description = description == null ? EMPTY_STRING : description;
        Validate.notNull(title, "title cannot be null");
        Validate.notNull(icon, "icon cannot be null");
        Validate.isTrue(icon.isItem(), "icon must be an item");
        Validate.notNull(frameType, "frameType cannot be null");
    }

    public void send(@Nonnull final Player player) {
        send(new Player[]{player});
    }

    private Advancement registerMyself(final String title, final String description, final boolean announceToChat, final Material icon, final FrameType frameType) {
        return JeffLib.getNMSHandler().loadVolatileAdvancement(key, "{\"display\":{\"icon\": {\"item\": \"" + icon.getKey() + "\"},\"frame\": \"" + frameType.getJsonName() + "\", \"title\": {\"translate\":\"" + title + "\"},\"description\": {\"translate\":\"" + description + "\"},\"announce_to_chat\": \"" + announceToChat + "\"},\"criteria\": {\"display\": {\"trigger\": \"minecraft:impossible\"}}}");
    }

    public void send(final Player... players) {
        Tasks.nextTickAsync(() -> {
            final Advancement adv = registerMyself(title, description, announceToChat, icon, frameType);
            Tasks.later(() -> {
                for (final Player player : players) {
                    if (player.isOnline()) {
                        player.getAdvancementProgress(adv).awardCriteria(CRITERIA);
                    }
                }
            }, 1);
            Tasks.later(() -> {
                for (final Player player : players) {
                    if(player.isOnline()) {
                        player.getAdvancementProgress(adv).revokeCriteria(CRITERIA);
                    }
                }
            }, 2);
        });
    }

    /**
     * Represents the type of announcement message that should be made
     */
    public enum FrameType {
        /**
         * Purple, titled with "Challenge"
         */
        CHALLENGE("challenge"),
        /**
         * Yellow, titled with "Goal"
         */
        GOAL("goal"),
        /**
         * Yellow, titled with "Advancement"
         */
        ADVANCEMENT("task");

        private final String name;

        FrameType(@Nonnull final String name) {
            this.name = name;
        }

        public String getJsonName() {
            return this.name;
        }
    }
}
