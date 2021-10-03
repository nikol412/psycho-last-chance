package Components

import com.almasb.fxgl.core.math.Vec2
import com.almasb.fxgl.dsl.FXGL
import com.almasb.fxgl.entity.component.Component
import com.almasb.fxgl.logging.Logger
import com.almasb.fxgl.physics.PhysicsComponent
import com.almasb.fxgl.texture.AnimatedTexture
import com.almasb.fxgl.texture.AnimationChannel
import javafx.geometry.Point2D
import javafx.util.Duration

data class PsychoComponent(
    val physicsComponent: PhysicsComponent,
) : Component() {

    private val logger = Logger.get("PsychoComponent")

    private val animationIdle = AnimationChannel(
        FXGL.image("girl-running.png"), 1, 1692 / 4, 441,
        Duration(2000.0), 0, 0
    )

    private val animationMoving = AnimationChannel(
        FXGL.image("girl-running.png"), 4, 1692 / 4, 441,
        Duration(500.0), 0, 3
    )

    private val animatedTexture = AnimatedTexture(animationIdle)

    override fun onAdded() {
        entity.scaleX = 0.5
        entity.scaleY = 0.5
        entity.viewComponent.addChild(animatedTexture)
        entity.transformComponent.scaleOrigin = Point2D(200.0, 225.0)
        animatedTexture.loop()
    }

    override fun onUpdate(tpf: Double) {
        if (physicsComponent.isMovingX) {
            animatedTexture.loopNoOverride(animationMoving)
        } else {
            animatedTexture.loopNoOverride(animationIdle)
        }
    }

    fun moveUp() {
        logger.debug(physicsComponent.velocityY.toString())
        if (physicsComponent.isOnGround) {
            physicsComponent.applyBodyForceToCenter(Vec2(0.0, 300.0))
        }

    }

    fun moveRight() {
        logger.debug(physicsComponent.velocityX.toString())
        entity.scaleX = 0.5
        if (physicsComponent.velocityX < 200) {
            physicsComponent.velocityX += 50
        } else {
            physicsComponent.velocityX = 200.0
        }

    }

    fun moveLeft() {
        logger.debug(physicsComponent.velocityX.toString())
        entity.scaleX = -0.5
        if (physicsComponent.velocityX > -200) {
            physicsComponent.velocityX -= 50
        } else {
            physicsComponent.velocityX = -200.0
        }
    }

    fun stop() {
        physicsComponent.applyLinearImpulse(
            Point2D(
                if (entity.scaleX > 0)
                    -physicsComponent.velocityX else
                    physicsComponent.velocityX, 0.0
            ), Point2D(entity.center.x, entity.center.y), true
        )
    }

}
