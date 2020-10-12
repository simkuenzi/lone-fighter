package com.github.simkuenzi.lone;

import com.badlogic.gdx.physics.box2d.*;

import java.util.List;
import java.util.Set;

class ShotObjectType {
    private final float despawnAt;

    ShotObjectType(float despawnAt) {
        this.despawnAt = despawnAt;
    }

    public GameObject create(float x, float y, World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        bodyDef.fixedRotation = true;

        final Body body = world.createBody(bodyDef);
        body.setBullet(true);

        CircleShape shape = new CircleShape();
        shape.setRadius(.1f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 2f;
        PhysicsCategory.PLAYER_BULLET.apply(fixtureDef);

        GameObject gameObject = new GameObject() {
            @Override
            public void update(float deltaTime, List<GameObject> spawn, List<GameObject> despawn, Set<GameObject> collisions) {
                if (body.getPosition().x > despawnAt || !collisions.isEmpty()) {
                    world.destroyBody(body);
                    despawn.add(this);
                }
            }

            @Override
            public void render() {
                // Later ...
            }
        };

        body.createFixture(fixtureDef).setUserData(gameObject);
        shape.dispose();
        body.applyForce(70, 0, 0, 0, true);
        return gameObject;
    }
}
