package com.github.simkuenzi.lone;

import com.badlogic.gdx.physics.box2d.*;

import java.util.List;
import java.util.Set;

class BorderObjectType {
    public GameObject create(float x, float y, float x2, float y2, World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.linearDamping = 7;
        bodyDef.fixedRotation = true;

        final Body body = world.createBody(bodyDef);

        EdgeShape shape = new EdgeShape();
        shape.set(x, y, x2, y2);


        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.restitution = .1f;
        PhysicsCategory.BORDER.apply(fixtureDef);

        body.createFixture(fixtureDef);

        shape.dispose();

        return new GameObject() {
            @Override
            public void update(float deltaTime, List<GameObject> spawn, List<GameObject> despawn, Set<GameObject> collisions) {
            }

            @Override
            public void render() {
                // Later ...
            }
        };
    }
}
