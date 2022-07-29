package com.jeff_media.jefflib.ai.navigation;

import com.jeff_media.jefflib.EntityUtils;
import com.jeff_media.jefflib.internal.annotations.NMS;
import lombok.Getter;
import org.bukkit.entity.Mob;

import javax.annotation.Nonnull;

/**
 * Represents an entity's navigation, movement, jump and look controls
 */
public class Controls {

    @Getter private final MoveController moveController;
    @Getter private final JumpController jumpController;
    @Getter private final LookController lookController;
    @Getter private final PathNavigation navigation;

    private Controls(@Nonnull final Mob mob) {
        this.moveController = EntityUtils.getMoveController(mob);
        this.jumpController = EntityUtils.getJumpController(mob);
        this.lookController = EntityUtils.getLookController(mob);
        this.navigation = EntityUtils.getNavigation(mob);
    }

    private Controls(MoveController moveController, JumpController jumpController, LookController lookController, PathNavigation navigation) {
        this.moveController = moveController;
        this.jumpController = jumpController;
        this.lookController = lookController;
        this.navigation = navigation;
    }

    /**
     * Gets this mob's controls
     */
    @NMS
    @Nonnull public static Controls of(@Nonnull final Mob mob) {
        return new Controls(mob);
    }

    public static Controls of(@Nonnull final MoveController moveController, @Nonnull final JumpController jumpController, @Nonnull final LookController lookController, @Nonnull final PathNavigation navigation) {
        return new Controls(moveController, jumpController, lookController, navigation);
    }

}
