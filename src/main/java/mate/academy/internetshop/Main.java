package mate.academy.internetshop;

import mate.academy.internetshop.db.Storage;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.BucketService;
import mate.academy.internetshop.service.ItemService;
import mate.academy.internetshop.service.OrderService;
import mate.academy.internetshop.service.UserService;

public class Main {
    @Inject
    private static BucketService bucketService;

    @Inject
    private static ItemService itemService;

    @Inject
    private static OrderService orderService;

    @Inject
    private static UserService userService;

    static {
        try {
            Injector.injectDependency();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        Item one = new Item("juice", 100.0);
        Item two = new Item("apple", 120.0);
        Item three = new Item("hot-dog", 999.99);

        itemService.create(one);
        itemService.create(two);
        itemService.create(three);

        System.out.println(itemService.getAllItems());
        two.setName("new item2");
        System.out.println(itemService.update(two).getName());

        itemService.delete(three);
        itemService.deleteById(1L);
        System.out.println(itemService.getAllItems());

        User user1 = new User("kolia", 15);
        User user2 = new User("vasia", 20);
        User user3 = new User("petia", 30);
        userService.create(user1);
        userService.create(user2);
        userService.create(user3);

        System.out.println(Storage.users);

        user2.setName("updated name");
        System.out.println(Storage.users);

        System.out.println(userService.delete(user1));
        System.out.println(userService.deleteById(user3.getId()));
        System.out.println(Storage.users);

        Bucket bucket1 = new Bucket(user1);
        Bucket bucket2 = new Bucket(user2);
        Bucket bucket3 = new Bucket(user3);
        bucketService.create(bucket1);
        bucketService.create(bucket2);
        bucketService.create(bucket3);

        bucketService.addItem(bucket1, one);
        bucketService.addItem(bucket1, two);
        bucketService.addItem(bucket2, one);
        bucketService.addItem(bucket2, three);
        bucketService.addItem(bucket3, three);
        bucketService.addItem(bucket3, two);

        System.out.println(Storage.buckets);

        System.out.println(bucketService.get(bucket2.getId()));
        bucket2.getItems().add(three);
        bucket2.getItems().add(one);
        bucketService.update(bucket2);
        System.out.println(bucketService.get(bucket2.getId()));

        bucketService.delete(bucket1);
        bucketService.deleteById(bucket3.getId());
        System.out.println(Storage.buckets);

        Order order1 = orderService.completeOrder(bucketService.getAllItems(bucket1), user1);
        Order order3 = orderService.completeOrder(bucketService.getAllItems(bucket3), user3);

        orderService.delete(order3);
        orderService.delete(order1);
        System.out.println(Storage.orders);
    }
}
