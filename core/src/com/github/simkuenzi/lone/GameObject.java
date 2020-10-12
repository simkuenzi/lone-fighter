package com.github.simkuenzi.lone;

import java.util.List;
import java.util.Set;

interface GameObject {
    void update(float deltaTime, List<GameObject> spawn, List<GameObject> despawn, Set<GameObject> collisions);
    void render();
}
