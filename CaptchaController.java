package GUI.controller;

import general.General;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class CaptchaController implements Initializable {

    @FXML
    private TextField code;

    @FXML
    private TextField entered_code;

    @FXML
    private Label status;

    private boolean verified;
    private String captchaQuestion;
    private String captchaAnswer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generateCode();
    }

    @FXML
    void verify(MouseEvent event) {
        try{
            int input = Integer.parseInt(entered_code.getText());
            if(input == Integer.parseInt(captchaAnswer)){
                verified = true;
                Stage stage = (Stage) status.getScene().getWindow();
                stage.close();
            }else{
                status.setText("Wrong Answer.Try Again!");
                generateCode();
            }
        }catch (NumberFormatException e){
            status.setText("Please Enter A Number");
        }
    }
    private void generateCode(){
        String[] captcha = General.createCaptcha();
        captchaQuestion = captcha[0];
        captchaAnswer = captcha[1];
        code.setText(captchaQuestion);
    }
    public boolean isVerified(){
        return verified;
    }
}
