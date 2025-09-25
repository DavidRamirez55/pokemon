package umg.com;

public class Squirtle extends Pokemon {
    public Squirtle() {
        super("Squirtle", 110);
        addAttack(new Attack("Placaje", 0.9, () -> 12));
        addAttack(new Attack("Pistola Agua", 0.8, () -> 18));
    }


    public String getType() { return "Agua"; }
}