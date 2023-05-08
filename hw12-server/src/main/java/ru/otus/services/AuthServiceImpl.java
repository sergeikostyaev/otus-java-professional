package ru.otus.services;



public class AuthServiceImpl implements AuthService {



    public AuthServiceImpl() {

    }

    @Override
    public boolean authenticate(String login, String password) {
        return login.equals(AdminInfo.ADMIN_LOGIN) && password.equals(AdminInfo.ADMIN_PASSWORD);
    }

}
