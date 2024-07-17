import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ConcurrentModificationExceptionDemo {
    public static void main(String[] args) {
        // Create a list and add some elements
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        // Using an iterator to iterate through the list
        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
            Integer number = iterator.next();
            System.out.println("Number: " + number);

            // Modifying the list while iterating
            // if (number == 2) {
            // list.add(4); // This will throw ConcurrentModificationException
            // }
        }
    }
}
