package com.github.simkuenzi.lone;

import com.badlogic.gdx.physics.box2d.*;

import java.util.List;
import java.util.Set;

class StructureObjectType {
    public GameObject create(float x, float y, World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x, y);
        bodyDef.linearDamping = 7;
        bodyDef.fixedRotation = true;

        final Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(.5f, .5f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.restitution = .1f;
        PhysicsCategory.BELOW_PLANE.apply(fixtureDef);

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
