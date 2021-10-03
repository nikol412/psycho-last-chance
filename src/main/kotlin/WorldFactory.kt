import com.almasb.fxgl.dsl.entityBuilder
import com.almasb.fxgl.entity.Entity
import com.almasb.fxgl.entity.EntityFactory
import com.almasb.fxgl.entity.SpawnData
import com.almasb.fxgl.entity.Spawns
import com.almasb.fxgl.physics.PhysicsComponent
import com.almasb.fxgl.physics.box2d.dynamics.BodyType
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle


class WorldFactory : EntityFactory {


    @Spawns("background")
    fun newBackground(data: SpawnData): Entity {
        return entityBuilder(data)
            .type(EntityType.BACKGROUND)
            .view("doorlight.png")
            .zIndex(-1)
            .build()
    }

    @Spawns("b")
    fun newBorder(data: SpawnData): Entity {
        val physicsComponent = PhysicsComponent()
        physicsComponent.setBodyType(BodyType.STATIC)
        physicsComponent.setFixtureDef(FixtureDef().friction(0.0f))
        val rectangle = Rectangle(50.0, 50.0, Color.BLACK)
        return entityBuilder(data)
            .type(EntityType.BORDER)
            .with(physicsComponent)
            .viewWithBBox(rectangle)
            .zIndex(-1)
            .collidable()
            .build()
    }

    @Spawns("r")
    fun newRoomBlock(data: SpawnData): Entity {
        return entityBuilder(data)
            .type(EntityType.ROOM)
            .zIndex(-2)
            .viewWithBBox(Rectangle(50.0, 50.0, Color.color(1.0, 0.0, 1.0)))
            .build()
    }
}

enum class EntityType {
    BACKGROUND, PLAYER, BORDER, ROOM
}