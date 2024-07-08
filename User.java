package user;

public class User {
    public Integer id;
    public String username;
    public String password;
    public String nickname;
    public String email;
    public String recoveryQuestion;
    public String answer;

    public User() {
    }

    public User(String username, String password, String email, String nickname, String recoveryQuestion, String answer) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.recoveryQuestion = recoveryQuestion;
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "id: " + id +
                ", username: " + username +
                ", password: " + password +
                ", nickname: " + nickname +
                ", email: " + email +
                ", recoveryQuestion: " + recoveryQuestion +
                ", answer: " + answer
                ;
    }
}
