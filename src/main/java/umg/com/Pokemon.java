package umg.com;

import java.util.ArrayList;
import java.util.List;

public abstract class Pokemon {
    private String name;
    private int maxHp;
    private int currentHp;
    private List<Attack> attacks;

    public Pokemon(String name, int maxHp) {
        this.name = name;
        this.maxHp = maxHp;
        this.currentHp = maxHp;
        this.attacks = new ArrayList<>();
    }

    public String getName() { return name; }
    public int getMaxHp() { return maxHp; }
    public int getCurrentHp() { return currentHp; }

    public void setCurrentHp(int hp) {
        this.currentHp = Math.max(0, Math.min(hp, maxHp));
    }

    public List<Attack> getAttacks() { return attacks; }

    public void addAttack(Attack attack) {
        attacks.add(attack);
    }

    public abstract String getType();
}