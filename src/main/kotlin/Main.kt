import CharactersEntityFactory.Companion.BTS_TAG
import Components.PsychoComponent
import com.almasb.fxgl.app.ApplicationMode
import com.almasb.fxgl.app.GameApplication
import com.almasb.fxgl.app.GameSettings
import com.almasb.fxgl.dsl.*
import com.almasb.fxgl.entity.Entity
import com.almasb.fxgl.entity.level.Level
import com.almasb.fxgl.entity.level.text.TextLevelLoader
import com.almasb.fxgl.input.UserAction
import javafx.scene.input.KeyCode


class PsychoGameApp : GameApplication() {
    companion object {
        const val GAME_NAME = "Psycho: Last chance"
        const val GAME_VERSION = "0.1"
        const val WINDOW_WIDTH = 1024.0
        const val WINDOW_HEIGHT = 900.0
        const val PLAYER_Y = 500.0
        const val LEVEL_LENGTH = 2000
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

        val level: Level = getAssetLoader().loadLevel("0.txt", TextLevelLoader(1024, 125, ' '))
        getGameWorld().setLevel(level)

        player = spawn("Psycho")
        playerComponent = player!!.getComponent(PsychoComponent::class.java)

        with(getGameScene()) {
            viewport.setBounds(0, 0, LEVEL_LENGTH, WINDOW_HEIGHT.toInt())
            viewport.bindToEntity(player!!, 350.0, PLAYER_Y)
        }

        generateWorld()
        generateBTSFans()
    }

    private fun generateWorld() {
        val background = spawn("background", 0.0, 0.0)
        val backgroundDoorlight = spawn("background_doorlight", 1024.0, 0.0)
        val backgroundSecond = spawn("background", 2048.0, 0.0)


    }

    private fun generateBTSFans() {
        val btsCount = random(2, 6)
        val startXPOsition = 200
        val endXPosition = LEVEL_LENGTH - 300
        val xPositions = List(btsCount) { id ->
            random(startXPOsition, endXPosition).toDouble()
        }
        for (i in xPositions.indices) {
            val btsNps = spawn(BTS_TAG, xPositions[i], PLAYER_Y)
        }
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
