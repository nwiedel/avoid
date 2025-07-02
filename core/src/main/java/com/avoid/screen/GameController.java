package com.avoid.screen;

import com.avoid.config.DifficultyLevel;
import com.avoid.config.GameConfig;
import com.avoid.entity.Obstacle;
import com.avoid.entity.Player;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;

public class GameController {

    // -- Konstanten --
    private static final Logger log = new Logger(GameController.class.getSimpleName(), Logger.DEBUG);

    // -- Attribute --
    private Player player;
    private Array<Obstacle> obstacles = new Array<>();
    private float obstacleTimer;private float scoreTimer;
    private int lives = GameConfig.LIVES_START;
    private int score;
    private int displayScore;
    private DifficultyLevel difficultyLevel = DifficultyLevel.MEDIUM;

    // -- Konstruktoren --
    public GameController(){
        init();
    }

    // -- init() --
    private void init(){
        // Spieler erstellen und positionieren
        player = new Player();
        float startPlayerX = GameConfig.WORLD_WIDTH / 2;
        float startPlayerY = 1;
        player.setPosition(startPlayerX, startPlayerY);
    }

    // -- öffentliche Methoden --
    public void update(float delta){
        if (isGameOver()){
            log.debug("GAME OVER!");
            return;
        }

        updatePlayer();
        updateObstacles(delta);
        updateScore(delta);
        updateDisplayScore(delta);

        if (isPlayerCollidingWithObstacle()){
            log.debug("Kollision erkannt!");
            lives--;
        }
    }

    public Player getPlayer() {
        return player;
    }

    public Array<Obstacle> getObstacles() {
        return obstacles;
    }

    public int getLives() {
        return lives;
    }

    public int getDisplayScore() {
        return displayScore;
    }

    // -- private Methoden --
    private boolean isGameOver(){
        return lives <= 0;
    }

    private boolean isPlayerCollidingWithObstacle(){
        for (Obstacle obstacle : obstacles){
            if (obstacle.isNotHit() && obstacle.isPlayerColliding(player)){
                return true;
            }
        }

        return false;
    }

    private void updatePlayer(){
        //log.debug("playerX = " + player.getX() + " playerY = " + player.getY());
        player.update();
        blockPlayerFromLeavingWorld();
    }

    private void blockPlayerFromLeavingWorld(){
        float playerX = MathUtils.clamp(player.getX(),
            player.getWidth() / 2,
            GameConfig.WORLD_WIDTH - player.getWidth() / 2);

        player.setPosition(playerX, player.getY());
    }

    private void updateObstacles(float delta){
        for (Obstacle obstacle : obstacles){
            obstacle.update();
        }
        createNewObstacle(delta);
    }

    private void createNewObstacle(float delta){
        obstacleTimer += delta;

        if (obstacleTimer >= GameConfig.OBSTACLE_SPAWN_TIME){
            float min = 0f;
            float max = GameConfig.WORLD_WIDTH;
            float obstacleX = MathUtils.random(min, max);
            float obstacleY = GameConfig.WORLD_HEIGHT;

            Obstacle obstacle = new Obstacle();
            obstacle.setYSpeed(difficultyLevel.getObstacleSpeed());
            obstacle.setPosition(obstacleX, obstacleY);
            obstacles.add(obstacle);
            obstacleTimer = 0f;
        }
    }

    private void updateScore(float delta){
        scoreTimer += delta;

        if (scoreTimer >= GameConfig.SCORE_MAX_TIME){
            score += MathUtils.random(1, 5);
            scoreTimer = 0.0f;
        }
    }

    private void updateDisplayScore(float delta){
        if(displayScore < score){
            displayScore = Math.min(
                score,
                displayScore + (int)(60 * delta));
        }
    }
}
