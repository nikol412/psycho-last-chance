package Components

import com.almasb.fxgl.entity.component.Component
import com.almasb.fxgl.physics.PhysicsComponent

data class PsychoComponent(var physicsComponent: PhysicsComponent) : Component() {

    fun moveUp() {
        if (physicsComponent.velocityX < 100) {
            physicsComponent.velocityY -= 20;
        }

    }

    fun moveRight() {
        if (physicsComponent.velocityX < 100) {
            physicsComponent.velocityX += 20
        }
    }

    fun moveLeft() {
        if (physicsComponent.velocityX < 100) {
            physicsComponent.velocityX -= 20
        }
    }

    fun moveDown() {
        if (physicsComponent.velocityY < 100) {
            physicsComponent.velocityY += 20;
        }


    }
}
