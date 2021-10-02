package Components

import com.almasb.fxgl.core.math.Vec2
import com.almasb.fxgl.entity.component.Component
import com.almasb.fxgl.logging.Logger
import com.almasb.fxgl.physics.PhysicsComponent
import com.almasb.fxgl.texture.AnimatedTexture
import com.almasb.fxgl.texture.AnimationChannel
import javafx.geometry.Point2D

data class PsychoComponent(
    val physicsComponent: PhysicsComponent,
    val animatedTexture: AnimatedTexture?,
    val animationIdle: AnimationChannel?,
    val animationMoving: AnimationChannel?
) : Component() {

    private val logger = Logger.get("PsychoComponent")

    override fun onAdded() {
        //entity.transformComponent.scaleOrigin = Point2D(16.0, 21.0)
        entity.viewComponent.addChild(animatedTexture!!)
        animatedTexture.loop()
    }

    override fun onUpdate(tpf: Double) {
        if (physicsComponent.isMovingX) {
            if (animatedTexture?.animationChannel != animationMoving) {
                animatedTexture?.loopAnimationChannel(animationMoving!!)
            }
        } else {
            if (animatedTexture?.animationChannel != animationIdle) {
                animatedTexture?.loopAnimationChannel(animationIdle!!)
            }
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
        entity.scaleX = 1.0
        physicsComponent.applyBodyForceToCenter(Vec2(20.0, 0.0))
    }

    fun moveLeft() {
        logger.debug(physicsComponent.velocityX.toString())
        entity.scaleX = -1.0
        physicsComponent.applyBodyForceToCenter(Vec2(-20.0, 0.0))
    }

    fun stop() {
        physicsComponent.applyBodyForceToCenter(
            Vec2(
                if (entity.scaleX > 0)
                    -physicsComponent.velocityX + 20 else
                    physicsComponent.velocityX - 20, 0.0
            )
        )
    }

}
