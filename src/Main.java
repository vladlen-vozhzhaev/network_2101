public class Main {
    public static void main(String[] args) {
        /*
        Первый способ
        MyThread thread1 = new MyThread("Мыть посуду");
        MyThread thread2 = new MyThread("Пылесосить");
        MyThread thread3 = new MyThread("Стирать бельё");
        thread1.start();
        thread2.start();
        thread3.start();*/
        /*
        Второй способ
        Thread thread1 = new Thread(new MyRunnableClass("Мыть посуду"));
        Thread thread2 = new Thread(new MyRunnableClass("Пылесосить"));
        Thread thread3 = new Thread(new MyRunnableClass("Стирать бельё"));
        thread1.start();
        thread2.start();
        thread3.start();*/
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    System.out.println(i+"%) - Мыть посуду");
                }
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    System.out.println(i+"%) - Пылесосить");
                }
            }
        });
        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    System.out.println(i+"%) - Стирать бельё");
                }
            }
        });
        thread1.start();
        thread2.start();
        thread3.start();
    }
}

class MyRunnableClass implements Runnable{
    String task;

    public MyRunnableClass(String task) {
        this.task = task;
    }
    @Override
    public void run(){
        for (int i = 0; i < 100; i++) {
            System.out.println(i+"%) -  "+this.task);
        }
    }
}

class MyThread extends Thread{
    String task;

    public MyThread(String task) {
        this.task = task;
    }

    @Override
    public void run(){
        for (int i = 0; i < 100; i++) {
            System.out.println(i+"%) -  "+this.task);
        }
    }
}