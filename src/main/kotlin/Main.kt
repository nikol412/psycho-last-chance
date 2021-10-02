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


class PsychoGameApp() : GameApplication() {
    private var player: Entity? = null
    private var playerComponent: PsychoComponent? = null

    override fun initSettings(settings: GameSettings) {
        settings.width = 600
        settings.height = 600
        settings.title = "Psycho: Last chance"
        settings.version = "0.1"
    }

    override fun initGame() {
        getGameWorld().addEntityFactory(CharactersEntityFactory())
        player = spawn("Psycho");
        playerComponent = player!!.getComponent(PsychoComponent::class.java)
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
