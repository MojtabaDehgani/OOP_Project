package main;

import console.menu.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class Menus {

    public final static String Login = "Login";
    public final static String SignUp = "Sign Up";
    public final static String Back = "Back";
    public final static String Exit = "Exit";

    public final static String Sort = "Sort";
    public final static String NextPage = "Next Page";
    public final static String PrevPage = "Previous Page";
    public final static String PrevNo = "Page Number";

    public final static String ForgetPassword = "Forget password";

    public final static List<MenuItem> MainMenu = new ArrayList<>() {{
        add(new MenuItem(Login));
        add(new MenuItem(SignUp));
        add(new MenuItem(ForgetPassword));
        add(new MenuItem(Exit));
    }};

    public final static String FatherName = "What is your father's name?";
    public final static String FavouriteColor = "What is your favourite color?";
    public final static String FirstPetName = "What was the name of your first pet?";

    public final static List<MenuItem> PasswordRecoveryQuestionMenu = new ArrayList<>() {{
        add(new MenuItem(FatherName));
        add(new MenuItem(FavouriteColor));
        add(new MenuItem(FirstPetName));
    }};

    public final static String UserShowInfo = "Show my information";
    public final static String UserChangeUserName = "Change username";
    public final static String UserChangeNickname = "Change nickname";
    public final static String UserChangeEmail = "Change email";
    public final static String UserChangePassword = "Change password";

    public final static List<MenuItem> ProfileMenu = new ArrayList<>() {{
        add(new MenuItem(UserShowInfo));
        add(new MenuItem(UserChangeUserName));
        add(new MenuItem(UserChangeNickname));
        add(new MenuItem(UserChangeEmail));
        add(new MenuItem(UserChangePassword));
        add(new MenuItem(Back));
    }};

    public final static String StartGame = "Start Game";
    public final static String MyCardGames = "See My Card Games";
    public final static String GamesHistory = "Games History";
    public final static String Store = "Store";
    public final static String Profile = "Profile";
    public final static String Logout = "Logout";

    public final static List<MenuItem> UserMenu = new ArrayList<>() {{
        add(new MenuItem(StartGame));
        add(new MenuItem(MyCardGames));
        add(new MenuItem(GamesHistory));
        add(new MenuItem(Store));
        add(new MenuItem(Profile));
        add(new MenuItem(Logout));
    }};

    public final static String TwoPlayer = "TwoPlayer Game";
    public final static String Betting = "Betting Games";
    public final static String OnePlayer = "One Player History";
    public final static String Clan = "Clan";

    public final static List<MenuItem> GameMode = new ArrayList<>() {{
        add(new MenuItem(TwoPlayer));
        add(new MenuItem(Betting));
        add(new MenuItem(OnePlayer));
        add(new MenuItem(Clan));
        add(new MenuItem(Back));
    }};

    public final static List<MenuItem> GameHistoryMenu = new ArrayList<>() {{
        add(new MenuItem(Sort));
        add(new MenuItem(NextPage));
        add(new MenuItem(PrevPage));
        add(new MenuItem(PrevNo));
        add(new MenuItem(Back));
    }};

    public final static String CardAdd = "Add Card";
    public final static String CardEdit = "Edit Card";
    public final static String CardRemove = "Remove Card";
    public final static String SeeCards = "See Cards";
    public final static String SeePlayers = "See players";

    public final static List<MenuItem> AdminMenu = new ArrayList<>() {{
        add(new MenuItem(SeePlayers));
        add(new MenuItem(SeeCards));
        add(new MenuItem(CardAdd));
        add(new MenuItem(CardEdit));
        add(new MenuItem(CardRemove));
        add(new MenuItem(Logout));
    }};

    public final static String AvailableCards = "List available cards";
    public final static String BuyCard = "Buy card";

    public final static List<MenuItem> StoreMenu = new ArrayList<>() {{
        add(new MenuItem(AvailableCards));
        add(new MenuItem(BuyCard));
        add(new MenuItem(Back));
    }};

    public final static String PlayCard = "Play Card";
    public final static String ShowCard = "Show Card Information";

    public final static List<MenuItem> GamePlay = new ArrayList<>() {{
        add(new MenuItem(PlayCard));
        add(new MenuItem(ShowCard));
        add(new MenuItem(Exit));
    }};
}
