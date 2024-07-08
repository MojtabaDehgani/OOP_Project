package user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserService {

    private static User currentUser;

    private static final Map<String, Integer> loginFailedTries = new HashMap<>();
    private static final Map<String, Long> loginFailedTime = new HashMap<>();

    public static User getCurrentUser() {
        if (currentUser == null) return null;
        else return copy(currentUser);
    }

    public static User checkPassword(String username, String password) {
        if (loginFailedTime.containsKey(username)) {
            int waitTime = (int) ((System.currentTimeMillis() - loginFailedTime.get(username)) / 1000);
            waitTime = 5 * loginFailedTries.get(username) - waitTime;
            if (waitTime > 0) {
                System.out.printf("Try again in %d seconds.\n", waitTime);
                return null;
            }
        }

        User user = getByUsername(username);

        if (user == null) {
            System.out.println("Username does not exist!");
            return null;

        } else if (!user.password.equals(password)) {
            if (!loginFailedTries.containsKey(username)) {
                loginFailedTries.put(username, 1);
                loginFailedTime.put(username, System.currentTimeMillis());
            } else {
                loginFailedTries.replace(username, loginFailedTries.get(username) + 1);
                loginFailedTime.computeIfPresent(username, (key, value) -> System.currentTimeMillis());
            }

            System.out.println("Password and username don't match!");
            return checkPassword(username, password);

        } else return user;
    }

    public static void login(String username, String password) {
        User user = checkPassword(username, password);
        if (user != null) {
            currentUser = getByUsername(username);
            loginFailedTries.remove(username);
            loginFailedTime.remove(username);
            System.out.println("User logged in successfully!");
        } else {
            currentUser = null;
        }
    }

    public static void logout() {
        currentUser = null;
        System.out.println("User logged out!");
    }

    public static boolean isPreliminaryValid(User user) {
        if (user.password == null || user.password.length() < 4) {
            System.out.println("The length of the password must be at least 4 characters.");
            return false;
        }

        if (user.email == null || user.email.equals("")) {
            System.out.println("The email cannot be empty.");
            return false;
        }

        if (user.nickname == null || user.nickname.length() < 4) {
            System.out.println("The length of the nickname must be at least 4 characters.");
            return false;
        }

        if (!user.password.matches("\\w+")) {
            System.out.println("Password can only contain letters and numbers and underline.");
            return false;
        }

//        if (!user.password.matches("(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*_)")) {
//            System.out.println("Password should have at least one small letter and one uppercase letter and one character other than numbers and letters.");
//            return false;
//        }

        if (!user.email.matches("^[\\w!#$%&'*+/=?^`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^`{|}~-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$")) {
            System.out.println("The email address is incorrect.");
            return false;
        }

        return true;
    }

    public static boolean isValid(User user) {
        if (!isPreliminaryValid(user)) return false;

        if (user.recoveryQuestion == null || user.recoveryQuestion.equals("")) {
            System.out.println("The password recovery question cannot be empty.");
            return false;
        }

        if (user.answer == null || user.answer.equals("")) {
            System.out.println("The answer cannot be empty.");
            return false;
        }

        return true;
    }

    public static User copy(User user) {
        User newUser = new User();
        newUser.id = user.id;
        newUser.username = user.username;
        newUser.password = user.password;
        newUser.email = user.email;
        newUser.nickname = user.nickname;
        newUser.recoveryQuestion = user.recoveryQuestion;
        newUser.answer = user.answer;
        return newUser;
    }

    public static void create(User user) {
        if (isValid(user)) UserRepository.insert(user);
    }

    public static User get(int id) {
        return UserRepository.get(id);
    }

    public static User getByUsername(String username) {
        return UserRepository.getByUsername(username);
    }

    public static List<User> getAll() {
        return UserRepository.getAll();
    }

    public static void update(User user) {
        if (isValid(user)) UserRepository.update(user);
        if (currentUser != null && currentUser.id.equals(user.id)) currentUser = user;
    }

    public static void delete(User user) {
        UserRepository.delete(user);
    }

}
