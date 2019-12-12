package day12;

public class Moon {
	private static final int TIMES = 20;
	int posX;
	int posY;
	int posZ;

	int velX;
	int velY;
	int velZ;

	int initZ;
	int initY;
	int initX;
	private String name;
	private int bX = 0;
	private int bY = 0;
	private int bZ = 0;

	public Moon(int x, int y, int z, String name) {
		posX = x;
		posY = y;
		posZ = z;

		initX = x;
		initY = y;
		initZ = z;
		this.name = name;

		velX = 0;
		velY = 0;
		velZ = 0;
	}

	public void applyGravity(Moon otherMoon) {
		if (posX > otherMoon.posX) {
			velX--;
			otherMoon.velX++;
		} else if (posX < otherMoon.posX) {
			velX++;
			otherMoon.velX--;
		}

		if (posY > otherMoon.posY) {
			velY--;
			otherMoon.velY++;
		} else if (posY < otherMoon.posY) {
			velY++;
			otherMoon.velY--;
		}

		if (posZ > otherMoon.posZ) {
			velZ--;
			otherMoon.velZ++;
		} else if (posZ < otherMoon.posZ) {
			velZ++;
			otherMoon.velZ--;
		}
	}

	public void applyVelocity() {
		posX = posX + velX;
		posY = posY + velY;
		posZ = posZ + velZ;
	}

	public double getEnergy() {
		return (Math.abs(posX) + Math.abs(posY) + Math.abs(posZ)) * (Math.abs(velX) + Math.abs(velY) + Math.abs(velZ));
	}

	public String toString() {
		return "pos=<x=" + posX + ", y=" + posY + ", z=" + posZ + ">, vel=<x=" + velX + ", y=" + velY + ", z=" + velZ
				+ "> En=" + getEnergy();
	}

	public boolean isTheOriginalOnX() {
		return velX == 0 && posX == initX;
	}

	public boolean isTheOriginalOnY() {
		return velY == 0 && posY == initY;
	}

	public boolean isTheOriginalOnZ() {
		return velZ == 0 && posZ == initZ;
	}

	public boolean isTheOriginal(double numIter) {
		return (velX == 0 && velY == 0 && velZ == 0 && posX == initX && posY == initY && posZ == initZ);
	}
}
