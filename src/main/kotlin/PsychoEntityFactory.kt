import Components.CharactersType
import Components.PsychoComponent
import Components.VictimComponent
import com.almasb.fxgl.dsl.FXGL
import com.almasb.fxgl.dsl.texture
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
import com.almasb.fxgl.texture.AnimatedTexture
import com.almasb.fxgl.texture.AnimationChannel
import javafx.geometry.Point2D
import javafx.util.Duration


class CharactersEntityFactory : EntityFactory {

    @Spawns("Victim")
    fun newVictim(data: SpawnData): Entity {
        return FXGL.entityBuilder(data)
            .type(CharactersType.Victim)
            .with(VictimComponent())
            .viewWithBBox(texture("victim.png", 40.0, 40.0))
            .collidable()
            .build()
    }

    @Spawns("Psycho")
    fun newPsycho(data: SpawnData): Entity {
        val physics = PhysicsComponent()
        physics.setBodyType(BodyType.DYNAMIC)
        physics.addGroundSensor(HitBox("GROUND_SENSOR", Point2D(16.0, 38.0), BoundingShape.box(40.0, 40.0)))

        physics.setFixtureDef(FixtureDef().friction(0.0f))

        val animationIdle = AnimationChannel(
            FXGL.image("thingIdle.png"), 9, 166, 180,
            Duration(2000.0), 0, 8
        )

        val animationMoving = AnimationChannel(
            FXGL.image("thingIdle.png"), 9, 150, 150,
            Duration(500.0), 0, 8
        )

        return FXGL.entityBuilder(data)
            .type(CharactersType.Psycho)
            .at(100.0, 450.0)
            .bbox(HitBox(BoundingShape.box(30.0, 30.0)))
            .with(physics)
            .with(PsychoComponent(physics, AnimatedTexture(animationIdle), animationIdle, animationMoving))
            .with(CollidableComponent(true))
            .buildAndAttach()
    }

}