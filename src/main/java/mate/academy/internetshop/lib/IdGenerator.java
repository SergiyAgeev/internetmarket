package mate.academy.internetshop.lib;

public class IdGenerator {
    private static Long userId = 0L;
    private static Long itemId = 0L;
    private static Long bucketId = 0L;
    private static Long orderId = 0L;

    public static Long generateNewUserId() {
        userId++;
        return userId;
    }

    public static Long generateNewItemId() {
        itemId++;
        return itemId;
    }

    public static Long generateNewBucketId() {
        bucketId++;
        return bucketId;
    }

    public static Long generateNewOrderId() {
        orderId++;
        return orderId;
    }
}
