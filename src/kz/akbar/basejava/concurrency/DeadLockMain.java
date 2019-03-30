package kz.akbar.basejava.concurrency;

public class DeadLockMain {

    class A {

        synchronized void a1(B b) {
            System.out.println(Thread.currentThread().getName() + " entered in A.a1");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("Class A is interrupted!");
            }
            System.out.println(Thread.currentThread().getName() + " trying to call B.b2");
            b.b2();
        }

        synchronized void a2() {
            System.out.println(Thread.currentThread().getName() + " in A.a2");
        }
    }

    class B {

        synchronized void b1(A a) {
            System.out.println(Thread.currentThread().getName() + " entered in B.b1");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("Class B is interrupted!");
            }
            System.out.println(Thread.currentThread().getName() + " trying to call A.a2");
            a.a2();
        }

        synchronized void b2() {
            System.out.println(Thread.currentThread().getName() + " in B.b2");
        }
    }

    private void deadLockExec() {
        final A a = new A();
        final B b = new B();
        Thread thread1 = new Thread(() -> {
            a.a1(b);
            System.out.println("Back to Thread2");
        }, "Thread1");
        Thread thread2 = new Thread(() -> {
            b.b1(a);
            System.out.println("Back to Thread1");
        }, "Thread2");
        thread1.start();
//        thread1.join(); //- uncomment to remove deadlock
        thread2.start();
    }

    public static void main(String[] args) {
        DeadLockMain deadLockMain = new DeadLockMain();
        deadLockMain.deadLockExec();
    }
}
