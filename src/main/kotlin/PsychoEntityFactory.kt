import Components.CharactersType
import Components.PsychoComponent
import Components.VictimAnimationComponent
import com.almasb.fxgl.dsl.FXGL
import com.almasb.fxgl.entity.Entity
import com.almasb.fxgl.entity.EntityFactory
import com.almasb.fxgl.entity.SpawnData
import com.almasb.fxgl.entity.Spawns
import com.almasb.fxgl.entity.components.CollidableComponent
import com.almasb.fxgl.physics.BoundingShape
import com.almasb.fxgl.physics.HitBox
import com.almasb.fxgl.physics.PhysicsComponent
import com.almasb.fxgl.physics.box2d.dynamics.BodyType
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef
import javafx.geometry.Point2D


class CharactersEntityFactory : EntityFactory {

    companion object {
        const val BTS_TAG = "Victim"
    }

    @Spawns("Victim")
    fun newVictim(data: SpawnData): Entity {
        val physicsComponent = PhysicsComponent()
        physicsComponent.setBodyType(BodyType.DYNAMIC)
        physicsComponent.setFixtureDef(FixtureDef().friction(0.0f))
        return FXGL.entityBuilder(data)
            .type(CharactersType.Victim)
            .with(physicsComponent)
            .with(VictimAnimationComponent(physicsComponent))
            .bbox(BoundingShape.box(22.0, 33.0))
            .collidable()
            .build()
    }

    @Spawns("Psycho")
    fun newPsycho(data: SpawnData): Entity {
        val physics = PhysicsComponent()
        physics.setBodyType(BodyType.DYNAMIC)
        physics.addGroundSensor(HitBox("GROUND_SENSOR", Point2D(50.0, -50.0), BoundingShape.box(40.0, 40.0)))
        physics.setFixtureDef(FixtureDef().friction(0.0f))

        return FXGL.entityBuilder(data)
            .type(CharactersType.Psycho)
            .at(100.0, 450.0)
            .bbox(HitBox(BoundingShape.box(30.0, 30.0)))
            .with(physics)
            .with(PsychoComponent(physics))
            .with(CollidableComponent(true))
            .buildAndAttach()
    }

}