package com.jeff_media.jefflib.internal.nms.v1_16_R2.ai;

import com.jeff_media.jefflib.ai.navigation.JumpController;
import net.minecraft.server.v1_16_R2.ControllerJump;

public class HatchedJumpController implements JumpController {

    private final ControllerJump jumpControl;

    public HatchedJumpController(final ControllerJump jumpControl) {
        this.jumpControl = jumpControl;
    }

    @Override
    public void jump() {
        jumpControl.jump();
    }
}
