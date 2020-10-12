package com.github.simkuenzi.lone;

import com.badlogic.gdx.physics.box2d.*;

import java.util.List;
import java.util.Set;

class EnemyObjectType {
    public GameObject create(float x, float y, World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
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
        PhysicsCategory.PLAYER_PLANE.apply(fixtureDef);

        GameObject gameObject = new GameObject() {
            @Override
            public void update(float deltaTime, List<GameObject> spawn, List<GameObject> despawn, Set<GameObject> collisions) {
                if (body.getPosition().x > -30) {
                    body.applyForce(-20, 0, 0, 0, true);
                }
            }

            @Override
            public void render() {
                // Later ...
            }
        };

        body.createFixture(fixtureDef).setUserData(gameObject);

        shape.dispose();
        return gameObject;
    }
}
