public class DeadLockMain {

    class A {

        synchronized void a1(B b) {
            System.out.println(Thread.currentThread().getName() + " entered in A.a1");
            try {
                Thread.sleep(1000);
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
                Thread.sleep(1000);
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

    class Thread1 extends Thread {

        A a;
        B b;

        Thread1(A a, B b, String name) {
            this.a = a;
            this.b = b;
            this.setName(name);
        }

        @Override
        public void run() {
            a.a1(b);
            System.out.println("Back to Thread2");
        }
    }

    class Thread2 extends Thread {
        A a;
        B b;

        Thread2(A a, B b, String name) {
            this.a = a;
            this.b = b;
            this.setName(name);
        }

        @Override
        public void run() {
            b.b1(a);
            System.out.println("Back to Thread1");
        }
    }

    private void deadLockExec() throws InterruptedException {
        final A a = new A();
        final B b = new B();
        Thread1 thread1 = new Thread1(a, b, "Thread1");
        Thread2 thread2 = new Thread2(a, b, "Thread2");
        thread1.start();
//        thread1.join(); //- uncomment to remove deadlock
        thread2.start();
    }

    public static void main(String[] args) throws InterruptedException {
        DeadLockMain deadLockMain = new DeadLockMain();
        deadLockMain.deadLockExec();
    }
}
