package com.github.simkuenzi.lone;

import com.badlogic.gdx.physics.box2d.FixtureDef;

enum PhysicsCategory  {
    PLAYER((short) 0b0000_0000_0000_0001, (short) 0b0000_0000_0000_1110),
    BORDER((short) 0b0000_0000_0000_0010, (short) 0b0000_0000_0000_0001),
    BELOW_PLANE((short) 0b0000_0000_0000_0100, (short) 0b0000_0000_0000_0100),
    PLAYER_PLANE((short) 0b0000_0000_0000_1000, (short) 0b0000_0000_0001_0001),
    PLAYER_BULLET((short) 0b0000_0000_0001_0000, (short) 0b0000_0000_0000_1000);


    private final short categoryBits;
    private final short maskBits;

    PhysicsCategory(short categoryBits, short maskBits) {
        this.categoryBits = categoryBits;
        this.maskBits = maskBits;
    }

    void apply(FixtureDef fixtureDef) {
        fixtureDef.filter.categoryBits = categoryBits;
        fixtureDef.filter.maskBits = maskBits;
    }
}
