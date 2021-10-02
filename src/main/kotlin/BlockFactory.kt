import PsychoGameApp.Companion.WINDOW_HEIGHT
import PsychoGameApp.Companion.WINDOW_WIDTH
import com.almasb.fxgl.dsl.EntityBuilder
import com.almasb.fxgl.dsl.entityBuilder
import com.almasb.fxgl.entity.Entity
import com.almasb.fxgl.entity.EntityFactory
import com.almasb.fxgl.entity.SpawnData
import com.almasb.fxgl.entity.Spawns
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle


class BlockFactory: EntityFactory {
    @Spawns("background")
    fun newBackground(data: SpawnData): Entity {
        return entityBuilder()
            .type(EntityType.BACKGROUND)
            .view(Rectangle(WINDOW_WIDTH, WINDOW_HEIGHT, Color.color(0.5, 0.4, 0.1)))
            .zIndex(-5)
            .build()
    }
}

enum class EntityType {
    BACKGROUND
}