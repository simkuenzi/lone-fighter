package com.github.simkuenzi.lone;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LevelScreen extends ScreenAdapter {

    LoneFighterGame game;
    Camera camera;
    Viewport viewPort;
    World world;
    Box2DDebugRenderer box2dDebugRenderer;

    List<GameObject> gameObjects = new ArrayList<>();

    public LevelScreen(LoneFighterGame game) {
        this.game = game;
    }

    @Override
    public void show(){
        float worldHeight = 20;
        float worldWidth = worldHeight * (16f/10f);
        camera = new OrthographicCamera(worldWidth, worldHeight);
        viewPort = new FitViewport(worldWidth, worldHeight, camera);
        world = new World(new Vector2(0, 0), true);

        gameObjects.add(new BorderObjectType().create(-(worldWidth/2), 10, 8, 10, world));
        gameObjects.add(new BorderObjectType().create(-(worldWidth/2), -10, 8, -10, world));
        gameObjects.add(new BorderObjectType().create(-(worldWidth/2), 10, -(worldWidth/2), -10, world));
        gameObjects.add(new BorderObjectType().create(8, 10, 8, -10, world));
        gameObjects.add(new PlayerObjectType(new ShotObjectType(worldWidth / 2f)).create(-8, 0, world));
        gameObjects.add(new StructureObjectType().create(1, -4, world));
        gameObjects.add(new StructureObjectType().create(1, -3, world));
        gameObjects.add(new StructureObjectType().create(2, -4, world));
        gameObjects.add(new StructureObjectType().create(3, -4, world));
        gameObjects.add(new EnemyObjectType().create(9, 3, world));

        box2dDebugRenderer = new Box2DDebugRenderer();
        box2dDebugRenderer.SHAPE_AWAKE.set(Color.RED);
    }

    @Override
    public void resize(int width, int height) {
        viewPort.update(width, height);
    }

    @Override
    public void render(float delta) {

        final List<GameObject> spawnAll = new ArrayList<>();
        final List<GameObject> despawnAll = new ArrayList<>();


        gameObjects.forEach(o -> {
            List<GameObject> spawn = new ArrayList<>();
            List<GameObject> despawn = new ArrayList<>();
            Set<GameObject> contacts = new HashSet<>();
            contacts.addAll(extractContacts(Contact::getFixtureA, Contact::getFixtureB));
            contacts.addAll(extractContacts(Contact::getFixtureB, Contact::getFixtureA));

            o.update(delta, spawn, despawn, Collections.unmodifiableSet(contacts));
            spawnAll.addAll(spawn);
            despawnAll.addAll(despawn);
        });

        world.step(delta, 6, 2);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.shapeRenderer.setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor(.25f, .25f, .25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        game.shapeRenderer.setColor(0, 0, 0, 1);
        game.shapeRenderer.rect(viewPort.getWorldWidth() / -2f, viewPort.getWorldHeight() / -2f,
                viewPort.getWorldWidth(), viewPort.getWorldHeight());
        game.shapeRenderer.setColor(1, 1, 1, 1);

        gameObjects.forEach(GameObject::render);

        game.shapeRenderer.end();

        box2dDebugRenderer.render(world, camera.combined);

        gameObjects.removeAll(despawnAll);
        gameObjects.addAll(spawnAll);
    }

    @Override
    public void hide(){
        box2dDebugRenderer.dispose();
        world.dispose();
        Gdx.input.setInputProcessor(null);
    }

    private List<GameObject> extractContacts(Function<Contact, Fixture> me, Function<Contact, Fixture> other) {
        return IntStream.range(0, world.getContactCount())
                .mapToObj(i -> world.getContactList().get(i))
                .filter(c -> me.apply(c).getUserData() != null)
                .filter(c -> other.apply(c).getUserData() != null)
                .filter(c -> me.apply(c).getUserData() instanceof GameObject)
                .filter(c -> other.apply(c).getUserData() instanceof GameObject)
                .map(c -> (GameObject) me.apply(c).getUserData())
                .collect(Collectors.toList());
    }
}
