package umg.com;

public class Pikachu extends Pokemon {
    public Pikachu() {
        super("Pikachu", 95);
        addAttack(new Attack("Impactrueno", 0.9, () -> 15));
        addAttack(new Attack("Rayo", 0.75, () -> 25));
    }


    public String getType() { return "Eléctrico"; }
}