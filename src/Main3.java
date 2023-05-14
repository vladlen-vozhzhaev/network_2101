public class Main3 {
    public static void main(String[] args) {
        int n = 13; // 32 байта
        Person person1 = new Person("Иван", "Иванов");
        Person person2 = new Person("Иван", "Иванов");
        System.out.println(person1);
        System.out.println(person2);
        if(person1 == person2){
            System.out.println("Два одинаковых персоны");
        }else{
            System.out.println("Разные пресоны");
        }
        person2 = person1; // Мы скопировали ссылку из person1 в person2
        // с этого момента, эти две переменные ссылаются в одну и ту же область ОЗУ
        person1.lastname = "Петров";
        System.out.println(person2.lastname);
    }
}
class Person{
    String name;
    String lastname;

    public Person(String name, String lastname) {
        this.name = name;
        this.lastname = lastname;
    }
}