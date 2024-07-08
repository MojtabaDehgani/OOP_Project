package main.consoleUI;

import console.Console;
import general.General;
import console.menu.MenuManager;
import main.Menus;
import console.parser.ParsedString;
import console.parser.Parser;
import user.User;
import user.UserService;

public class UserConsoleUI {

    public static User checkPassword() {
        System.out.println("user login -u <username> -p <password>");
        General.copyToClipboard("user login -u sara -p 1234");
        String inputString = Console.scanner.nextLine();

        ParsedString parsedString = Parser.Parse(inputString);

        if (!parsedString.getFunction().equals("user login")) {
            System.out.println("Wrong function!");
            return null;
        }

        String username = parsedString.getArgument("u");
        String password = parsedString.getArgument("p");

        return UserService.checkPassword(username, password);
    }

    public static void login() {
        System.out.println("user login -u <username> -p <password>");
        General.copyToClipboard("user login -u hamed -p 1234");
        String inputString = Console.scanner.nextLine();

        ParsedString parsedString = Parser.Parse(inputString);

        if (!parsedString.getFunction().equals("user login")) {
            System.out.println("Wrong function!");
            return;
        }

        String username = parsedString.getArgument("u");
        String password = parsedString.getArgument("p");

        UserService.login(username, password);

        if (UserService.getCurrentUser() != null)
            if (UserService.getCurrentUser().username.equals("admin")) MenuManager.menu = Menus.AdminMenu;
            else MenuManager.menu = Menus.UserMenu;
    }

    public static void logout() {
        UserService.logout();
        MenuManager.menu = Menus.MainMenu;
    }

    public static void create() {
        User user = new User();

        System.out.println("user create -u <username> -p <password> <password-confirm> -email <email> -n <nickname>");
        General.copyToClipboard("user create -u reza -p 1234 1234 -email reza@domain.com -n reza");
        String inputString = Console.scanner.nextLine();
        ParsedString parsedString = Parser.Parse(inputString);

        if (!parsedString.getFunction().equals("user create")) {
            System.out.println("Wrong function!");
            return;
        }

        String password = parsedString.getArgument("p", 0);
        String passwordConfirm = parsedString.getArgument("p", 1);

        // random option for password
        if (password.equals("random")) {
            password = General.generateRandomString(8);
            System.out.printf("Confirm this password: %s\n", password);
            passwordConfirm = Console.scanner.nextLine();
        }

        // checking matching password and confirm
        if (!password.equals(passwordConfirm)) {
            System.out.println("Passwords do not match!");
            return;
        }

        user.username = parsedString.getArgument("u");
        user.password = password;
        user.email = parsedString.getArgument("email");
        user.nickname = parsedString.getArgument("n");

        if (UserService.isPreliminaryValid(user)) {
            MenuManager.printMenu(Menus.PasswordRecoveryQuestionMenu);

            System.out.println("question pick -q <question-number> -a <answer> -c <answer-confirm>");
            General.copyToClipboard("question pick -q 2 -a white -c white");
            inputString = Console.scanner.nextLine();
            parsedString = Parser.Parse(inputString);

            if (!parsedString.getFunction().equals("question pick")) {
                System.out.println("Wrong function!");
                return;
            }

            // checking matching answer and confirm
            String answer = parsedString.getArgument("a");
            String answerConfirm = parsedString.getArgument("c");
            if (!answer.equals(answerConfirm)) {
                System.out.println("Answers do not match!");
                return;
            }

            int q = Integer.parseInt(parsedString.getArgument("q")) - 1;
            user.recoveryQuestion = Menus.PasswordRecoveryQuestionMenu.get(q).text;
            user.answer = answer;

            // captcha with 3 try
            for (int i = 0; i < 3; i++) {
                String[] captcha = General.createCaptcha();
                System.out.println("Enter the captcha:");
                System.out.println(captcha[0]);
                answer = Console.scanner.nextLine();

                if (answer.equals(captcha[1])) break;
                else System.out.println("Wrong answer!");
            }

            UserService.create(user);
        }
    }

    public static void showCurrentUserInfo() {
        System.out.println(UserService.getCurrentUser());
    }

    public static void changeUsername() {
        System.out.println("profile change -u <username>");
        String inputString = Console.scanner.nextLine();
        ParsedString parsedString = Parser.Parse(inputString);

        if (!parsedString.getFunction().equals("profile change")) {
            System.out.println("Wrong function!");
            return;
        }

        User user = UserService.getCurrentUser();
        if (user != null) {
            user.username = parsedString.getArgument("u");
            UserService.update(user);
        }
    }

    public static void changeNickname() {
        System.out.println("\nprofile change -n <nickname>");
        String inputString = Console.scanner.nextLine();
        ParsedString parsedString = Parser.Parse(inputString);

        if (!parsedString.getFunction().equals("profile change")) {
            System.out.println("Wrong function!");
            return;
        }

        User user = UserService.getCurrentUser();
        if (user != null) {
            user.nickname = parsedString.getArgument("n");
            UserService.update(user);
        }
    }

    public static void changePassword() {
        System.out.println("profile change password -o <old-password> -n <new-password>");
        String inputString = Console.scanner.nextLine();
        ParsedString parsedString = Parser.Parse(inputString);

        if (!parsedString.getFunction().equals("profile change password")) {
            System.out.println("Wrong function!");
            return;
        }

        String oldPassword = parsedString.getArgument("o");
        String newPassword = parsedString.getArgument("n");

        User user = UserService.getCurrentUser();
        if (user != null) {
            if (!UserService.getCurrentUser().password.equals(oldPassword)) {
                System.out.println("Old password does not match!");
                return;
            }

            user.password = newPassword;
            UserService.update(user);
        }
    }

    public static void changeEmail() {
        System.out.println("\nprofile change -e <email>");
        String inputString = Console.scanner.nextLine();
        ParsedString parsedString = Parser.Parse(inputString);

        if (!parsedString.getFunction().equals("profile change")) {
            System.out.println("Wrong function!");
            return;
        }

        User user = UserService.getCurrentUser();
        if (user != null) {
            user.email = parsedString.getArgument("e");
            UserService.update(user);
        }
    }

    public static void forgetPassword() {
        System.out.println("forgot my password -u <username>");
        General.copyToClipboard("forgot my password -u hamed");
        String inputString = Console.scanner.nextLine();
        ParsedString parsedString = Parser.Parse(inputString);

        if (!parsedString.getFunction().equals("forgot my password")) {
            System.out.println("Wrong function!");
            return;
        }

        String username = parsedString.getArgument("u");
        User user = UserService.getByUsername(username);

        System.out.println(user.recoveryQuestion);
        String answer = Console.scanner.nextLine();
        if (!answer.equals(user.answer)) {
            System.out.println("The answer is wrong!");
            return;
        }

        System.out.println("change password -p <password> <password-confirm>");
        General.copyToClipboard("change password -p 4321 4321");
        inputString = Console.scanner.nextLine();
        parsedString = Parser.Parse(inputString);

        if (!parsedString.getFunction().equals("change password")) {
            System.out.println("Wrong function!");
            return;
        }

        String password = parsedString.getArgument("p", 0);
        String passwordConfirm = parsedString.getArgument("p", 1);

        if (!password.equals(passwordConfirm)) {
            System.out.println("Confirmation password does not match!");
            return;
        }

        user.password = password;
        UserService.update(user);

        System.out.println("The password is updated.");
    }

}
