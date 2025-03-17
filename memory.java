public class Main {
    public static void main(String[] args) {
        String str = new String("Hello");
        str = null; // Eligible for garbage collection
        System.gc(); // Suggest garbage collection
    }
}
