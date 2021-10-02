import Components.PsychoComponent
import com.almasb.fxgl.app.GameApplication
import com.almasb.fxgl.app.GameSettings
import com.almasb.fxgl.dsl.FXGL
import com.almasb.fxgl.dsl.FXGL.Companion.getWorldProperties
import com.almasb.fxgl.dsl.getGameWorld
import com.almasb.fxgl.dsl.getInput
import com.almasb.fxgl.dsl.spawn
import com.almasb.fxgl.entity.Entity
import com.almasb.fxgl.input.UserAction
import javafx.scene.input.KeyCode
import javafx.scene.text.Text
import com.almasb.fxgl.dsl.*
import com.almasb.fxgl.entity.Entity
import com.almasb.fxgl.entity.components.CollidableComponent
import com.almasb.fxgl.entity.level.Level
import com.almasb.fxgl.entity.level.text.TextLevelLoader
import com.almasb.fxgl.pathfinding.CellState
import com.almasb.fxgl.pathfinding.astar.AStarGrid
import javafx.scene.input.KeyCode
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle


class PsychoGameApp() : GameApplication() {
    companion object {
        const val WINDOW_WIDTH = 800.0
        const val WINDOW_HEIGHT = 800.0
        const val PLAYER_Y = 500.0
        const val LEVEL_LENGTH = 3000
    }

    private var player: Entity? = null
    private var playerComponent: PsychoComponent? = null

    override fun initSettings(settings: GameSettings) {
        settings.width = 800
        settings.height = 800
        settings.title = "Psycho: Last chance"
        settings.version = "0.1"
    }

    override fun initGame() {
        getGameWorld().addEntityFactory(CharactersEntityFactory())
        player = spawn("Psycho");
        playerComponent = player!!.getComponent(PsychoComponent::class.java)
        
        getGameWorld().addEntityFactory(WorldFactory())

        val level: Level = getAssetLoader().loadLevel("0.txt", TextLevelLoader(50, 50, ' '))
        getGameWorld().setLevel(level)

        val bg = spawn("background")
        bg.yProperty().bind(getGameScene().viewport.yProperty())
        // грид это сетка игры, тут можно реализовать границы через которые юзер не может проходить
        // проблема с гридом: не работает эта фича и игра из-за него грузится секунд 10

//        val grid = AStarGrid.fromWorld(getGameWorld(), LEVEL_LENGTH, WINDOW_HEIGHT.toInt(), 50, 50) { type ->
//            return@fromWorld when (type) {
//                EntityType.BORDER -> CellState.NOT_WALKABLE
//                else -> CellState.WALKABLE
//            }
//        }
//
//        set("grid", grid)


        player = FXGL.entityBuilder()
            .type(EntityType.PLAYER)
            .at(300.0, PLAYER_Y)
            .viewWithBBox(Rectangle(25.0, 25.0, Color.BLUE))
            .with(CollidableComponent(true))
            .buildAndAttach()

        with(getGameScene()) {
            viewport.setBounds(0, 0, LEVEL_LENGTH, WINDOW_HEIGHT.toInt())
            viewport.bindToEntity(player!!, 350.0, PLAYER_Y)
        }
    }

    override fun initInput() {
        getInput().addAction(object : UserAction("Move Up") {
            override fun onAction() {
                playerComponent?.moveUp();
            }

        }, KeyCode.SPACE)
        getInput().addAction(object : UserAction("Move Down") {
            override fun onAction() {
                playerComponent?.moveDown();
            }
        }, KeyCode.S)
        getInput().addAction(object : UserAction("Move Right") {
            override fun onAction() {
                playerComponent?.moveRight();
            }
        }, KeyCode.D)
        getInput().addAction(object : UserAction("Move Left") {
            override fun onAction() {
                playerComponent?.moveLeft();
            }
        }, KeyCode.A)
    }

}

fun main() {
    GameApplication.launch(PsychoGameApp::class.java, emptyArray())
}
