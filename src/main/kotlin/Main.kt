import Components.PsychoComponent
import com.almasb.fxgl.app.ApplicationMode
import com.almasb.fxgl.app.GameApplication
import com.almasb.fxgl.app.GameSettings
import com.almasb.fxgl.dsl.getAssetLoader
import com.almasb.fxgl.dsl.getGameWorld
import com.almasb.fxgl.dsl.getInput
import com.almasb.fxgl.dsl.spawn
import com.almasb.fxgl.entity.Entity
import com.almasb.fxgl.entity.level.Level
import com.almasb.fxgl.entity.level.text.TextLevelLoader
import com.almasb.fxgl.input.UserAction
import javafx.scene.input.KeyCode


class PsychoGameApp : GameApplication() {
    companion object {
        const val GAME_NAME = "Psycho: Last chance"
        const val GAME_VERSION = "0.1"
        const val WINDOW_WIDTH = 800.0
        const val WINDOW_HEIGHT = 800.0
        const val PLAYER_Y = 500.0
        const val LEVEL_LENGTH = 3000
    }

    private var player: Entity? = null
    private var playerComponent: PsychoComponent? = null

    override fun initSettings(settings: GameSettings) {
        settings.width = WINDOW_WIDTH.toInt()
        settings.height = WINDOW_HEIGHT.toInt()
        settings.title = GAME_NAME
        settings.version = GAME_VERSION
        settings.applicationMode = ApplicationMode.DEBUG
    }

    override fun initGame() {
        getGameWorld().addEntityFactory(CharactersEntityFactory())
        getGameWorld().addEntityFactory(WorldFactory())

        val level: Level = getAssetLoader().loadLevel("0.txt", TextLevelLoader(50, 50, ' '))
        getGameWorld().setLevel(level)
        spawn("background")
        player = spawn("Psycho")
        playerComponent = player!!.getComponent(PsychoComponent::class.java)

        //val bg = spawn("background")
        //bg.yProperty().bind(getGameScene().viewport.yProperty())
    }

    override fun initInput() {
        getInput().addAction(object : UserAction("Move Up") {
            override fun onActionBegin() {
                playerComponent?.moveUp()
            }

        }, KeyCode.SPACE)
        getInput().addAction(object : UserAction("Move Right") {
            override fun onAction() {
                playerComponent?.moveRight()
            }

            override fun onActionEnd() {
                playerComponent?.stop()
            }
        }, KeyCode.D)
        getInput().addAction(object : UserAction("Move Left") {
            override fun onAction() {
                playerComponent?.moveLeft()
            }

            override fun onActionEnd() {
                playerComponent?.stop()
            }
        }, KeyCode.A)
    }

}

fun main() {
    GameApplication.launch(PsychoGameApp::class.java, emptyArray())
}
