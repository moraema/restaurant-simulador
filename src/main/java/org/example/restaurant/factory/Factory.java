package org.example.restaurant.factory;


import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.FollowComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import org.example.restaurant.factory.Type.Type;

public class Factory implements EntityFactory {


    @Spawns("Cliente")
    public Entity newClient(SpawnData data) {
        int id = data.get("id");
        FollowComponent followComponent = new FollowComponent();
        followComponent.setSpeed(1);
        return FXGL.entityBuilder(data)
                .type(Type.CLIENT)
                .view("cliente.png")
                .with(followComponent)
                .buildAndAttach();
    }

    @Spawns("Mesero")
    public Entity newMesero(SpawnData data) {
        FollowComponent followComponent = new FollowComponent();
        followComponent.setSpeed(1);
        return FXGL.entityBuilder(data)
                .type(Type.MESERO)
                .view("mesero.png")
                .zIndex(1)
                .with(followComponent)
                .buildAndAttach();
    }


    @Spawns("Cocinero")
    public Entity newCocinero(SpawnData data) {
        FollowComponent followComponent = new FollowComponent();
        followComponent.setSpeed(1);
        return FXGL.entityBuilder(data)
                .type(Type.COCINERO)
                .view("cocinero.png")
                .with(followComponent)
                .build();
    }

    @Spawns("Recepcionista")
    public Entity newRecepcionista(SpawnData data) {
        PhysicsComponent physicsComponent = new PhysicsComponent();
        physicsComponent.setBodyType(BodyType.KINEMATIC);
        return FXGL.entityBuilder(data)
                .type(Type.RECEPCIONISTA)
                .view("recepcionista.png")
                .with(physicsComponent)
                .build();
    }

    @Spawns("Comida")
    public Entity newComida(SpawnData data) {
        FollowComponent followComponent = new FollowComponent();
        followComponent.setSpeed(1);
        return FXGL.entityBuilder(data)
                .type(Type.COMIDA)
                .view("comida.png")
                .with(followComponent)
                .buildAndAttach();
    }
}

