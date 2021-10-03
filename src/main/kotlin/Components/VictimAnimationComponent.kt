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

    private var idleImage = image("mobgirl.png")
    private var walkImage = image("mobgirl.png")

    private var animIdle: AnimationChannel =
        AnimationChannel(idleImage, 8, 3200/8, 300, Duration(500.0), 0, 0)
    private var animWalk: AnimationChannel =
        AnimationChannel(
            walkImage, 8, 3200/8, 300,
            Duration(500.0), 0, 7
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