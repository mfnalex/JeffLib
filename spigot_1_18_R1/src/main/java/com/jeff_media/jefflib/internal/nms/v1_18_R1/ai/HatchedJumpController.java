package com.jeff_media.jefflib.internal.nms.v1_18_R1.ai;

import com.jeff_media.jefflib.ai.navigation.JumpController;
import net.minecraft.world.entity.ai.control.JumpControl;

public class HatchedJumpController implements JumpController {

    private final JumpControl jumpControl;

    public HatchedJumpController(final JumpControl jumpControl) {
        this.jumpControl = jumpControl;
    }

    @Override
    public void jump() {
        jumpControl.jump();
    }
}
