import Components.CharactersType
import Components.PsychoComponent
import Components.VictimComponent
import com.almasb.fxgl.dsl.FXGL
import com.almasb.fxgl.dsl.texture
import com.almasb.fxgl.entity.Entity
import com.almasb.fxgl.entity.EntityFactory
import com.almasb.fxgl.entity.SpawnData
import com.almasb.fxgl.entity.Spawns
import com.almasb.fxgl.physics.PhysicsComponent
import com.almasb.fxgl.physics.box2d.dynamics.BodyType

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
        var physics = PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC)

        return FXGL.entityBuilder(data)
            .type(CharactersType.Psycho)
            .with(physics)
            .with(PsychoComponent(physics))
            .viewWithBBox(texture("victim.png", 100.0, 100.0))
            .collidable()
            .buildAndAttach()
    }

}