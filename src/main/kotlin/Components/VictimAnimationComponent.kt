package Components

import com.almasb.fxgl.dsl.getGameWorld
import com.almasb.fxgl.dsl.image
import com.almasb.fxgl.entity.component.Component
import com.almasb.fxgl.physics.PhysicsComponent
import com.almasb.fxgl.texture.AnimatedTexture
import com.almasb.fxgl.texture.AnimationChannel
import javafx.util.Duration

class VictimAnimationComponent(
    private var physicsComponent: PhysicsComponent
) : Component() {

    private val player = getGameWorld().getEntitiesByType(CharactersType.Psycho).first()

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

    override fun onAdded() {
        with(entity) {
            viewComponent.addChild(texture)
        }
        texture.loop()
    }

    override fun onUpdate(tpf: Double) {

        if (player.distanceBBox(entity) < 200) {
            if (entity.x < player.x) {
                moveRight()
            } else {
                moveLeft()
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
        physicsComponent.velocityX = 80.0
        entity.scaleX = 1.0
    }

    fun moveLeft() {
        physicsComponent.velocityX = -80.0
        entity.scaleX = -1.0
    }

}