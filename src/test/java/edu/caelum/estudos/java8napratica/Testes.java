package edu.caelum.estudos.java8napratica;

import edu.caelum.estudos.java8napratica.consumers.Mostrador;
import edu.caelum.estudos.java8napratica.functionalInterfaces.Validator;
import edu.caelum.estudos.java8napratica.model.DataPreLoaded;
import edu.caelum.estudos.java8napratica.model.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Testes {

    @Test
    public void loadListOldScenario(){
        for (User u: getUsers().stream().collect(Collectors.toList()))
            System.out.println(u);
    }

    @Test
    public void testForEach(){

        Mostrador mostrador = new Mostrador();
        getUsers().stream().forEach(mostrador);

        getUsers().stream().forEach(new Consumer<User>() {
            @Override
            public void accept(User user) {
                System.out.println(user.getNome());
            }
        });
    }

    @Test
    public void testSimpleLambda(){
        getUsers().stream().forEach(u -> System.out.println(u.getNome()));

        new Thread(() -> {for (int i = 0; i < 10; i++){System.out.println(i);}}).start();
    }


    @Test
    public void testSimpleLambdaValidator() {
        Validator<String> validator = valor -> {
            return valor.equals("Teste");
        };

        System.out.println(validator.valida("Teste"));

        Validator<Integer> validatorNumber = number -> {
            return number > 10;
        };

        System.out.println(validatorNumber.valida(21));
    }


    @Test
    public void testConsumer() {
        Consumer<User> mostraSufixo = c -> System.out.println("Testando o lambda");

        Consumer<User> mostraNome = c -> System.out.println(c.getNome());

        getUsers().stream().forEach(mostraSufixo.andThen(mostraNome));
    }


    @Test
    public void testRemoveIf(){
        List<User> users = getUsers();
        Predicate<User> predicate = u -> u.getNome().length() < 5;
        users.removeIf(predicate);
        users.forEach(user -> System.out.println(user));
    }

    @Test
    public void orderingList(){
        getUsers()
                .stream()
                .sorted((o1, o2) -> o1.getPontos().compareTo(o2.getPontos()))
                .forEach(System.out::println);
    }

    @Test
    public void orderingListUsingComparing(){

        /**
         * First case
         */
        Function<User, String> getNome = user -> user.getNome();
        getUsers().stream()
                .sorted(Comparator.comparing(getNome))
                .forEach(System.out::println);

        /**
         * Reduced case
         */
        Function<User, String> getNomeReduced = User::getNome;
        getUsers().stream()
                .sorted(Comparator.comparing(getNomeReduced))
                .forEach(System.out::println);


        /**
         * Legible case
         */
        getUsers().stream()
                .sorted(Comparator.comparing(User::getNome))
                .forEach(System.out::println);
    }

    @Test
    public void changeAllToModerators(){
        /**
         * Case one
         */
        getUsers().stream()
                .forEach(u -> {
                    u.setModerador(true);
                    System.out.println(u);
                });

        /**
         * Legible case
         */
        getUsers().stream().forEach(User::toModerator);
    }

    @Test
    public void composingComparators(){
        getUsers()
                .stream()
                .sorted(Comparator
                        .comparingInt(User::getPontos)
                        .thenComparing(User::getNome))
                .forEach(System.out::println);
    }

    @Test
    public void reversingComparators(){
        getUsers()
                .stream()
                .sorted(Comparator
                        .comparingInt(User::getPontos)
                        .thenComparing(User::getNome)
                        .reversed())
                .forEach(System.out::println);
    }

    @Test
    public void lambdaConstructing(){
        BiFunction<String, Integer, User>  userCreator = User::new;
        User felipe = userCreator.apply("Felipe", 12);
        User matheus = userCreator.apply("Matheus", 12);
    }

    @Test
    public void moderadoresComMaisPontos(){
        List<User> users = getUsers();

        users.sort(Comparator.comparing(User::getPontos));

        users
            .subList(0, 10)
                        .forEach(u -> {
                            u.toModerator();
                            System.out.println(u);
                        });
    }

    @Test
    public void moderadoresComMaisDe100(){
        getUsers().stream().filter(u -> u.getPontos() > 100).collect(Collectors.toList()).forEach(System.out::println);
    }

    @Test
    public void listandoApenasPontos(){
        getUsers().stream().map(User::getPontos).forEach(System.out::println);
    }

    @Test
    public void streamsPrimitivas(){
        IntStream intStream =  getUsers().stream().mapToInt(User::getPontos);
        System.out.println(intStream.count());
        System.out.println(intStream.average());
    }

    private static List<User> getUsers(){
        return new ArrayList<User>(DataPreLoaded.users);
    }

}
