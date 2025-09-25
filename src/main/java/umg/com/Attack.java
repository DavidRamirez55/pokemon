package umg.com;

public class Attack {
    private String name;
    private double accuracy;
    private DamageRule rule;

    public Attack(String name, double accuracy, DamageRule rule) {
        this.name = name;
        this.accuracy = accuracy;
        this.rule = rule;
    }

    public String getName() { return name; }

    public int execute() throws AttackMissedException {
        if (Math.random() > accuracy) {
            throw new AttackMissedException(name + " falló!");
        }
        return rule.calculateDamage();
    }

    public String toString() {
        return name + " (Precisión: " + (int)(accuracy * 100) + "%)";
    }
}