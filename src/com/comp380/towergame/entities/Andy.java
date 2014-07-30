package com.comp380.towergame.entities;

import com.comp380.towergame.physics.Speed;

import android.graphics.Bitmap;

public class Andy extends BaseEntity {
	int score;

	public Andy(EntityManager manager, Bitmap bitmap) {
		super(manager, bitmap, 25, 25);
		this.speed = Speed.PLAYER;
		this.health = 20;
		this.score = 0;
	}
	
	public int getX() {
		return this.point.x;
	}
	
	public int getY() {
		return this.point.y;
	}

	public int getScore() {
		return score;
		
	}
	
	public void setScore(int x) {
		score = x;
	}
	
	public enum MoveDirection {
		UP, DOWN, LEFT, RIGHT, JUMP, DIAGONAL_UP_RIGHT, DIAGONAL_DOWN_LEFT, DIAGONAL_UP_LEFT, DIAGONAL_DOWN_RIGHT;
	}
	
	/** Player input is accounted for here **/
	public void onMoveEvent(MoveDirection direction) {
		switch(direction) {
		case UP:
		case JUMP:
			super.moveJump();
			break;
		case DOWN:
			break;
		case LEFT:
			this.facingRight = false;
			super.moveWalk(-1);
			break;
		case RIGHT:
			this.facingRight = true;
			super.moveWalk(1);
			break;
		default:
			break;
		}
	}
}
