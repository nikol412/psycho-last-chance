import Components.CharactersType
import Components.VictimComponent
import com.almasb.fxgl.dsl.FXGL
import com.almasb.fxgl.dsl.texture
import com.almasb.fxgl.entity.Entity
import com.almasb.fxgl.entity.EntityFactory
import com.almasb.fxgl.entity.SpawnData
import com.almasb.fxgl.entity.Spawns

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
}