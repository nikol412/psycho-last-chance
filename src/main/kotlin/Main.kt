import com.almasb.fxgl.app.GameApplication
import com.almasb.fxgl.app.GameSettings
import com.almasb.fxgl.dsl.FXGL
import com.almasb.fxgl.dsl.FXGL.Companion.getWorldProperties
import com.almasb.fxgl.dsl.getGameScene
import com.almasb.fxgl.dsl.getGameWorld
import com.almasb.fxgl.dsl.spawn
import com.almasb.fxgl.entity.Entity
import javafx.scene.input.KeyCode
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.scene.text.Text


class PsychoGameApp() : GameApplication() {
    companion object {
        const val WINDOW_WIDTH = 800.0
        const val WINDOW_HEIGHT = 800.0
    }
    private var player: Entity? = null

    override fun initSettings(settings: GameSettings) {
        settings.width = 600
        settings.height = 600
        settings.title = "Psycho: Last chance"
        settings.version = "0.1"
    }

    override fun initGame() {
        getGameWorld().addEntityFactory(BlockFactory())
        val bg = spawn("background")
        bg.yProperty().bind(getGameScene().viewport.yProperty())

        getGameScene().viewport.setBounds(0, 0, WINDOW_WIDTH.toInt(), WINDOW_HEIGHT.toInt())
        player = FXGL.entityBuilder()
            .at(300.0, 300.0)
            .view(Rectangle(25.0, 25.0, Color.BLUE))
            .buildAndAttach()
    }

    override fun initInput() {
        FXGL.onKey(KeyCode.D) {
            player!!.translateX(5.0) // move right 5 pixels
        }
        FXGL.onKey(KeyCode.A) {
            player!!.translateX(-5.0) // move left 5 pixels
        }
        FXGL.onKey(KeyCode.W) {
            player!!.translateY(-5.0) // move up 5 pixels
        }
        FXGL.onKey(KeyCode.S) {
            player!!.translateY(5.0) // move down 5 pixels
        }
    }

    override fun initUI() {
        val textPixels = Text()
        textPixels.setTranslateX(50.0) // x = 50
        textPixels.setTranslateY(100.0) // y = 100

        textPixels.textProperty().bind(getWorldProperties().intProperty("pixelsMoved").asString());

        FXGL.getGameScene().addUINode(textPixels) // add to the scene graph
    }

    override fun initGameVars(vars: MutableMap<String?, Any?>) {
        vars["pixelsMoved"] = 0
    }

}

fun main() {
    GameApplication.launch(PsychoGameApp::class.java, emptyArray())
}
