package mate.academy.internetshop.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import mate.academy.internetshop.dao.BucketDao;
import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.dao.UserDao;

import mate.academy.internetshop.dao.impl.BucketDaoImpl;
import mate.academy.internetshop.dao.impl.OrderDaoImpl;
import mate.academy.internetshop.dao.impl.UserDaoImpl;
import mate.academy.internetshop.dao.jdbc.JdbcItemDaoImpl;

import mate.academy.internetshop.service.BucketService;
import mate.academy.internetshop.service.ItemService;
import mate.academy.internetshop.service.OrderService;
import mate.academy.internetshop.service.UserService;

import mate.academy.internetshop.service.impl.BucketServiceImpl;
import mate.academy.internetshop.service.impl.ItemServiceImpl;
import mate.academy.internetshop.service.impl.OrderServiceImpl;
import mate.academy.internetshop.service.impl.UserServiceImpl;

import org.apache.log4j.Logger;

public class Factory {
    private static Logger logger = Logger.getLogger(Factory.class);
    private static Connection connection;

    private static BucketDao bucketDaoInstance;
    private static ItemDao itemDaoInstance;
    private static OrderDao orderDaoInstance;
    private static UserDao userDaoInstance;

    private static BucketService bucketServiceInstance;
    private static ItemService itemServiceInstance;
    private static OrderService orderServiceInstance;
    private static UserService userServiceInstance;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/internet_market?"
                            + "user=root&password=MyNewPass5!"
            );
        } catch (ClassNotFoundException | SQLException e) {
            logger.warn("Can't establish connection to our DB" + e);
        }
    }

    public static BucketDao getBucketDao() {
        if (bucketDaoInstance == null) {
            bucketDaoInstance = new BucketDaoImpl();
        }
        return bucketDaoInstance;
    }

    public static BucketService getBucketService() {
        if (bucketServiceInstance == null) {
            bucketServiceInstance = new BucketServiceImpl();
        }
        return bucketServiceInstance;
    }

    public static ItemDao getItemDao() {
        if (itemDaoInstance == null) {
            itemDaoInstance = new JdbcItemDaoImpl(connection);
        }
        return itemDaoInstance;
    }

    public static ItemService getItemService() {
        if (itemServiceInstance == null) {
            itemServiceInstance = new ItemServiceImpl();
        }
        return itemServiceInstance;
    }

    public static OrderDao getOrderDao() {
        if (orderDaoInstance == null) {
            orderDaoInstance = new OrderDaoImpl();
        }
        return orderDaoInstance;
    }

    public static OrderService getOrderService() {
        if (orderServiceInstance == null) {
            orderServiceInstance = new OrderServiceImpl();
        }
        return orderServiceInstance;
    }

    public static UserDao getUserDao() {
        if (userDaoInstance == null) {
            userDaoInstance = new UserDaoImpl();
        }
        return userDaoInstance;
    }

    public static UserService getUserService() {
        if (userServiceInstance == null) {
            userServiceInstance = new UserServiceImpl();
        }
        return userServiceInstance;
    }
}
