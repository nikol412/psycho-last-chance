package Components

import com.almasb.fxgl.dsl.getGameWorld
import com.almasb.fxgl.dsl.image
import com.almasb.fxgl.entity.component.Component
import com.almasb.fxgl.physics.PhysicsComponent
import com.almasb.fxgl.texture.AnimatedTexture
import com.almasb.fxgl.texture.AnimationChannel
import javafx.util.Duration
import kotlin.math.absoluteValue

class VictimAnimationComponent(
    private var physicsComponent: PhysicsComponent
) : Component() {

    private var speed: Int = 0

    private var idleImage = image("bts_fun.png")
    private var walkImage = image("bts_fun_walk.png")

    private var animIdle: AnimationChannel =
        AnimationChannel(idleImage, 11, 22, 33, Duration(500.0), 1, 11)
    private var animWalk: AnimationChannel =
        AnimationChannel(
            walkImage, 13, 24, 32,
            Duration(500.0), 1, 13
        )

    private var texture: AnimatedTexture = AnimatedTexture(animIdle)

    init {

    }

    override fun onAdded() {
        with(entity) {
            viewComponent.addChild(texture)
        }
        texture.loop()
    }

    override fun onUpdate(tpf: Double) {
        val player = getGameWorld().getEntitiesByType(CharactersType.Psycho).first()

        if (player.distanceBBox(entity) < 200) {
            if (entity.x < player.x) {
                moveLeft()
            } else {
                moveRight()
            }
        }

        if (physicsComponent.isMoving) {
            texture.loopNoOverride(animWalk)
        }

        if (physicsComponent.isMoving.not()) {
            texture.loopNoOverride(animIdle)
        }
    }


    fun moveRight() {
        if (physicsComponent.velocityX.absoluteValue < 100) {
            physicsComponent.velocityX += 20
        }
        speed = 100
        entity.scaleX = 1.0
    }

    fun moveLeft() {
        if (physicsComponent.velocityX.absoluteValue < 100) {
            physicsComponent.velocityX -= 20
        }

        entity.scaleX = -1.0
    }

}