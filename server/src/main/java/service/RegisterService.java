package service;

import model.UserData;
import dataAccess.MemoryUserAccess;

public class RegisterService {
    MemoryUserAccess users = new MemoryUserAccess();
    private boolean validate(UserData user) {
        String username = user.username();
    }
}
