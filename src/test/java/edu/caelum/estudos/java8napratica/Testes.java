package edu.caelum.estudos.java8napratica;

import edu.caelum.estudos.java8napratica.calculator.Fibonacci;
import edu.caelum.estudos.java8napratica.consumers.Mostrador;
import edu.caelum.estudos.java8napratica.functionalInterfaces.Validator;
import edu.caelum.estudos.java8napratica.model.DataPreLoaded;
import edu.caelum.estudos.java8napratica.model.User;
import org.junit.Test;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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
        System.out.println(getUsers().stream().mapToInt(User::getPontos).count());

        System.out.println(getUsers().stream().mapToDouble(User::getPontos).average().getAsDouble());

        OptionalDouble average = getUsers().stream().mapToDouble(User::getPontos).average();
        System.out.println(average.orElse(0));

        average.orElseThrow(IllegalAccessError::new);
    }

    @Test
    public void ordenandoEFiltrando(){
        getUsers().stream()
                        .filter(u -> u.getPontos() > 100)
                        .sorted(Comparator.comparing(User::getNome))
                        .forEach(System.out::println);
    }

    @Test
    public void testStreamPipeline(){
        getUsers().stream()
                        .filter(u -> u.getPontos() > 100)
                        .peek(System.out::println)
                        .findAny();

        getUsers().stream()
                        .sorted(Comparator.comparing(User::getNome))
                        .peek(System.out::println)
                        .findAny();
    }

    @Test
    public void testReduction(){
        Optional<User> user = getUsers().stream().max(Comparator.comparing(User::getPontos));
        System.out.println("Using max: "+user.get());

        System.out.println("Using sum: "+ getUsers().stream().mapToInt(User::getPontos).sum());

        System.out.println("Using reduce: "+ getUsers().stream().mapToInt(User::getPontos).reduce(0, (n1,n2) -> n1 + n2));
    }

    @Test
    public void usingIteratorOnStream(){
        getUsers().stream()
                        .iterator()
                        .forEachRemaining(System.out::println);
    }

    @Test
    public void testingPredicates(){
        System.out.println(getUsers().stream() .anyMatch(User::isModerador));
    }

    @Test
    public void testBoxing(){
        Random random = new Random();
        IntStream stream = IntStream.generate(random::nextInt);

        List<Integer> list = stream
                            .limit(100)
                            .boxed()
                            .collect(Collectors.toList());

        list.forEach(System.out::println);
    }

    @Test
    public void testFibonacci(){
        Integer num = IntStream
                        .generate(new Fibonacci())
                        .filter(f -> f > 10)
                        .findFirst()
                        .getAsInt();

        System.out.println(num);
    }

    @Test
    public void testCircuitEnd(){
        IntStream.iterate(0, x -> x + 1).limit(10).forEach(System.out::println);
    }

    @Test
    public void testNio(){
        try {
            Files.list(Paths.get("/home/fpelichero/Downloads/OneDrive-2017-09-21"))
                            .filter(p -> p.toString().endsWith(".pdf"))
                            .forEach(System.out::println);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Test
    public void testCapsulingOwnMethods(){
        try {
            Files.list(Paths.get("/home/fpelichero/Downloads"))
                            .filter(p -> p.toString().endsWith(".txt"))
                            .map(p -> lines(p))
                            .forEach(System.out::println);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Test
    public void testFlatMap(){
        try{
            Files.list(Paths.get("/home/fpelichero/Downloads"))
                            .filter(p -> p.toString().endsWith(".txt"))
                            .flatMap(Testes::lines)
                            .forEach(System.out::println);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private static Stream<String> lines(Path p){
        try{
            return Files.lines(p);
        }catch (IOException ex){
            throw new UncheckedIOException(ex);
        }
    }

    private static List<User> getUsers(){
        return new ArrayList<User>(DataPreLoaded.users);
    }

}
