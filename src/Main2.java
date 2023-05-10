public class Main2 {
    public static void main(String[] args) {
        Cat barsik = new Cat("Барсик", "Дворняга", 4);
        Bird kesha = new Bird("Кеша", "Волнистый", 6);
        barsik.run();
        kesha.fly();
        barsik.fly();
    }
}

class Animal{
    String nick;
    String breed;
    int age;

    public Animal(String nick, String breed, int age) {
        this.nick = nick;
        this.breed = breed;
        this.age = age;
    }
    public void run(){
        System.out.println(this.nick+" не может бегать");
    }
    public void fly(){
        System.out.println(this.nick+" не может летать");
    }
}

class Cat extends Animal{
    public Cat(String nick, String breed, int age) {
        super(nick, breed, age);
    }
    @Override
    public void run(){
        System.out.println(this.nick+" побежал");
    }
}

class Bird extends Animal{
    public Bird(String nick, String breed, int age) {
        super(nick, breed, age);
    }
    @Override
    public void fly(){
        System.out.println(this.nick+" полетел");
    }
}