package com.cescristorey.naves;

import com.cescristorey.naves.entities.World;
import com.cescristorey.naves.entities.Missile;
import com.cescristorey.naves.entities.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class InputHandler {

    private Player player;
    private World entityManager;

    private String platform = Gdx.app.getType().toString();

    private int stop = 0, left = 1, right = 2;

    private long lastFire;
    private int movementSpeed;

    public InputHandler(Player player, World entityManager) {
        this.player = player;
        this.entityManager = entityManager;

        this.movementSpeed = player.movementSpeed;
    }

    public void listenForInput() {
        if (platform == "Android" || platform == "iOS") {
            touchControlls();
        }
        if (platform == "Desktop" || platform == "HeadlessDesktop" || platform == "WebGL"
                || platform == "Applet") {
            desktopControlls();
        }
    }

    private void touchControlls() {
        if (Gdx.input.isTouched()) {
            if (Gdx.input.getX() < Naves.WIDTH / 2) {
                move(left);
            } else {
                move(right);
            }
        } // Else stop movement
        else {
            move(stop);
        }
        // Fire missile
        if (!World.gameOver) {
            fireMissile();
        }
    }

    private void desktopControlls() {
        // Left input listener
        if (Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.LEFT)) {
            move(left);
        } // Right input listener
        else if (Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.RIGHT)) {
            move(right);
        } // Else stop movement
        else {
            move(stop);
        }
        // Fire missile
        if (Gdx.input.isKeyPressed(Keys.SPACE) && !World.gameOver) {
            fireMissile();
        }
    }

    private void move(int direction) {
        if ((direction == left) && (player.getPlayerPostion().x > 0)) {
            // Move left
            player.setDirection(-movementSpeed, 0);
        } else if ((direction == right)
                && (player.getPlayerPostion().x < Naves.WIDTH
                - TextureManager.PLAYER.getWidth())) {
            // Move right ------------
            player.setDirection(movementSpeed, 0);
        } else {
            // Stop moving -----------
            player.setDirection(0, 0);
        }
    }

    private void fireMissile() {
        if (System.currentTimeMillis() - lastFire >= 250) {
            entityManager.addEntity(new Missile(player.getPlayerPostion().cpy()
                    .add((25), TextureManager.PLAYER.getHeight())));
            lastFire = System.currentTimeMillis();
        }
    }
}
