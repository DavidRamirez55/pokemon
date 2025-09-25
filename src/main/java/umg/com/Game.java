package umg.com;

import java.util.*;
import java.util.stream.*;

public class Game {
    private static Scanner sc = new Scanner(System.in);
    private static List<String> log = new ArrayList<>();
    private static List<Integer> damageHistory = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Bienvenido al juego de Pokémon!");
        System.out.print("Ingresa tu nombre: ");
        String playerName = sc.nextLine();

        Pokemon player = choosePokemon();
        Pokemon cpu = randomPokemon(player.getClass());

        System.out.println(playerName + " eligió a " + player.getName());
        System.out.println("CPU eligió a " + cpu.getName());

        while (player.getCurrentHp() > 0 && cpu.getCurrentHp() > 0) {
            try {
                playerTurn(player, cpu);
                if (cpu.getCurrentHp() <= 0) break;
                cpuTurn(cpu, player);
            } catch (AttackMissedException | InvalidChoiceException e) {
                System.out.println(e.getMessage());
            }
            System.out.println(player.getName() + " HP: " + player.getCurrentHp() +
                    " | " + cpu.getName() + " HP: " + cpu.getCurrentHp());
        }

        String winner = (player.getCurrentHp() > 0) ? playerName : "CPU";
        System.out.println("El ganador es: " + winner);
        showSummary();
    }

    private static Pokemon choosePokemon() {
        List<Pokemon> pokemons = List.of(new Charmander(), new Squirtle(), new Bulbasaur(), new Pikachu());
        IntStream.range(0, pokemons.size())
                .forEach(i -> System.out.println((i+1) + ". " + pokemons.get(i).getName()));

        try {
            int choice = sc.nextInt(); sc.nextLine();
            if (choice < 1 || choice > pokemons.size()) throw new InvalidChoiceException("Selección inválida");
            return pokemons.get(choice - 1);
        } catch (Exception e) {
            throw new RuntimeException(new InvalidChoiceException("Entrada inválida"));
        }
    }

    private static Pokemon randomPokemon(Class<?> exclude) {
        List<Pokemon> pokemons = List.of(new Charmander(), new Squirtle(), new Bulbasaur(), new Pikachu());
        return pokemons.stream()
                .filter(p -> !p.getClass().equals(exclude))
                .skip(new Random().nextInt(pokemons.size() - 1))
                .findFirst().get();
    }

    private static void playerTurn(Pokemon player, Pokemon enemy) throws AttackMissedException, InvalidChoiceException {
        System.out.println("Elige un ataque:");
        List<Attack> attacks = player.getAttacks().stream()
                .sorted(Comparator.comparing(Attack::getName))
                .toList();
        IntStream.range(0, attacks.size())
                .forEach(i -> System.out.println((i+1) + ". " + attacks.get(i)));

        int choice = sc.nextInt(); sc.nextLine();
        if (choice < 1 || choice > attacks.size()) throw new InvalidChoiceException("Opción inválida");

        Attack atk = attacks.get(choice - 1);
        int dmg = atk.execute();
        enemy.setCurrentHp(enemy.getCurrentHp() - dmg);
        log.add(player.getName() + " usó " + atk.getName() + " causando " + dmg + " de daño");
        damageHistory.add(dmg);
    }

    private static void cpuTurn(Pokemon cpu, Pokemon player) throws AttackMissedException {
        Attack atk = cpu.getAttacks().get(new Random().nextInt(cpu.getAttacks().size()));
        int dmg = atk.execute();
        player.setCurrentHp(player.getCurrentHp() - dmg);
        log.add("CPU (" + cpu.getName() + ") usó " + atk.getName() + " causando " + dmg + " de daño");
        damageHistory.add(dmg);
    }

    private static void showSummary() {
        System.out.println("\n--- Resumen de la Batalla ---");
        log.forEach(System.out::println);

        long fallos = log.stream().filter(l -> l.contains("falló")).count();
        System.out.println("Fallos totales: " + fallos);

        System.out.println("Top 3 golpes más fuertes: " +
                damageHistory.stream().sorted(Comparator.reverseOrder()).limit(3).toList());

        System.out.println("Daño promedio: " +
                damageHistory.stream().mapToInt(i -> i).average().orElse(0));

        Map<String, Long> eventosPorActor = log.stream()
                .collect(Collectors.groupingBy(s -> s.contains("CPU") ? "CPU" : "Jugador", Collectors.counting()));
        System.out.println("Eventos por actor: " + eventosPorActor);
    }
}
