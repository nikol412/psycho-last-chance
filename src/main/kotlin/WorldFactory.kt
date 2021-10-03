import com.almasb.fxgl.dsl.entityBuilder
import com.almasb.fxgl.dsl.texture
import com.almasb.fxgl.entity.Entity
import com.almasb.fxgl.entity.EntityFactory
import com.almasb.fxgl.entity.SpawnData
import com.almasb.fxgl.entity.Spawns
import com.almasb.fxgl.physics.BoundingShape
import com.almasb.fxgl.physics.HitBox
import com.almasb.fxgl.physics.PhysicsComponent
import com.almasb.fxgl.physics.box2d.dynamics.BodyType
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef


class WorldFactory : EntityFactory {


    @Spawns("background")
    fun newBackground(data: SpawnData): Entity {
        return entityBuilder(data)
            .type(EntityType.BACKGROUND)
            .view("background_full.png")
            .zIndex(-1)
            .build()
    }

    @Spawns("background_doorlight")
    fun newBackgroundDoorlight(data: SpawnData): Entity {
        return entityBuilder(data)
            .type(EntityType.BACKGROUND)
            .view("background_doorlight_full.png")
            .zIndex(-1)
            .build()
    }

    @Spawns("d")
    fun newBackDown(data: SpawnData): Entity {
        val physicsComponent = PhysicsComponent()
        physicsComponent.setBodyType(BodyType.STATIC)
        physicsComponent.setFixtureDef(FixtureDef().friction(0.0f))

        return entityBuilder(data)
            .type(EntityType.BORDER)
            .with(physicsComponent)
            .view(texture("down.png"))
            .bbox(
                HitBox(
                    BoundingShape.box(
                        1024.0,
                        140.0
                    )
                )
            )
            .collidable()
            .build()
    }

    @Spawns("c")
    fun newRoomBlock(data: SpawnData): Entity {
        return entityBuilder(data)
            .type(EntityType.ROOM)
            .view(texture("backclean.png"))
            .bbox(
                HitBox(
                    BoundingShape.box(
                        1024.0,
                        625.0
                    )
                )
            )
            .zIndex(-1)
            .build()
    }

    @Spawns("u")
    fun newBackUp(data: SpawnData): Entity {
        val physicsComponent = PhysicsComponent()
        physicsComponent.setBodyType(BodyType.STATIC)
        physicsComponent.setFixtureDef(FixtureDef().friction(0.0f))

        return entityBuilder(data)
            .type(EntityType.BORDER)
            .with(physicsComponent)
            .view(texture("up.png"))
            .bbox(
                HitBox(
                    BoundingShape.box(
                        1024.0,
                        125.0
                    )
                )
            )
            .collidable()
            .zIndex(-1)
            .build()
    }
}

enum class EntityType {
    BACKGROUND, PLAYER, BORDER, ROOM
}