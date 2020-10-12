package com.github.simkuenzi.lone;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.*;

import java.util.List;
import java.util.Set;

class PlayerObjectType {
    private ShotObjectType shotObjectType;

    PlayerObjectType(ShotObjectType shotObjectType) {
        this.shotObjectType = shotObjectType;
    }

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
        PhysicsCategory.PLAYER.apply(fixtureDef);

        body.createFixture(fixtureDef);

        shape.dispose();

        return new GameObject() {
            @Override
            public void update(float deltaTime, List<GameObject> spawn, List<GameObject> despawn, Set<GameObject> collisions) {
                if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                    body.applyForce(0, 200, 0, 0, true);
                } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                    body.applyForce(0, -200, 0, 0, true);
                }

                if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                    body.applyForce(200, 0, 0, 0, true);
                } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                    body.applyForce(-200, 0, 0, 0, true);
                }

                if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                    spawn.add(shotObjectType.create(body.getPosition().x + .5f, body.getPosition().y, world));
                }
            }

            @Override
            public void render() {
                // Later ...
            }
        };
    }
}
