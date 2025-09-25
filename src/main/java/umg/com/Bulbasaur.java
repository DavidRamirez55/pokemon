package umg.com;

public class Bulbasaur extends Pokemon {
    public Bulbasaur() {
        super("Bulbasaur", 105);
        addAttack(new Attack("Látigo Cepa", 0.85, () -> 15));
        addAttack(new Attack("Hoja Afilada", 0.75, () -> 22));
    }

 
    public String getType() { return "Planta"; }
}