# internetmarket
   
    В UserService добавить метод: User login(String login, String password);
    В UserDao добавить метод Optional<User> findByLogin(String login) и спользовать его в методе userService.login(login, password)
    Релизовать метод Optional<User> findByToken(String token)
    Реализовать авторизацию юзера, создать cookie и httpSession

