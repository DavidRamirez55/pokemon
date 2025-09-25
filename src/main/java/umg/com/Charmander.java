package umg.com;

public class Charmander extends Pokemon {
    public Charmander() {
        super("Charmander", 100);
        addAttack(new Attack("Arañazo", 0.9, () -> 10));
        addAttack(new Attack("Ascuas", 0.8, () -> 20));
    }

    public String getType() { return "Fuego"; }
}