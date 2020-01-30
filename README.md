
# internet-market

![](https://lh5.googleusercontent.com/proxy/rkFKDCl1EJAR68_E1BuutdMKWpp6FbEkKQ4hsjQRd11S2a7hu7dsABzren1lEAwbkcAnbAn67C1T87oNMm6_8JKAijfSq6FBrCv16jV1vKX9v8vxNuLFSU-maIXll6_i4ao3ZqNDiWjYW8f4)

![](https://img.shields.io/github/languages/top/SergiyAgeev/internetmarket)
![](https://img.shields.io/github/languages/code-size/SergiyAgeev/internetmarket)
# Table of Contents
[Project purpose](#purpose)

[Project structure](#structure)

[For developer](#developer-start)

[Author](#author)


# <a name="purpose"></a>Project purpose
>This project is a prototype of an online market. 
Where its main functionality is realized.
<hr>

-   *Inject* - injection mock data.

-   *Registration* - registration of new users, password is hashed with **SHA-512** + *small amount of* **salt**.

-   *Login* - users authentication and authorization.

-   *Users* - displaying a list of all users application and able to remove some. Available for users with an **ADMIN** role only.

-   *Items* - displaying all items in stock. User with role **USER** can buy items. User with only role **ADMIN** can add new items and remove.

-   *Bucket* - displaying user’s bucket, user can add and remove items from his bucket. Available for users with a **USER** role only.

-   *Orders* - displaying user’s order history. If user is ready to buy items from bucket he is able to complete order. Available for users with a **USER** role only.

-   *Logout* - logging out from application.
<hr>

# <a name="structure"></a>Project Structure
- Java 11
- Maven 4.0.0
- javax.servlet 3.1.0
- jstl 1.2
- log4j 1.2.17
- maven-checkstyle-plugin
- mysql-connector-java 8.0.18
- packing *war*
<hr>

# <a name="developer-start"></a>For developer

Open the project in your IDE.

Add it as maven project.

Import dependencies.

Add sdk 11 in project structure.

Configure **Tomcat** local server:
> add artifact;
>
> add sdk 11;

<hr>
Create a schema in any SQL database.

Execute query from file **init_db.sql** to create all the tables required by this app.

    src                 
     └── main            
         └── resourses        
                └── init_db.sql 
     
<hr>

In **Factory** class input your **DB url**, **username** and **password** from your DB to create a connection.

    src                 
     └── main            
        └── java        
            └── mate
                └── academy
                    └── internetshop
                          └────factory
                                └── Factory.java
     
Change a path in **log4j.properties**. It has to reach your logFile.

    src                 
     └── main            
         └── resourses        
                └── log4j.properties 

Run the project.

input **/inject** in address bar in your browser to create 2 users :

###### role = ADMIN
>login = admin
>
>password = admin
>

###### role = USER
>login = user
>
>password = user

<hr>

# <a name="authors"></a>Author
 [SergiyAgeev](https://github.com/SergiyAgeev)
