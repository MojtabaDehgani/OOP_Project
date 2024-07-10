package GUI;

import game.Block;
import game.card.Card;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;



public class BlockGUI extends VBox {
    public boolean isEmpty = true;
    public boolean isDestroyed = false;
    public Block block = new Block();
    public Label dmg_lbl;
    public Separator separator;
    public Label acc_lbl;
    public BlockGUI() {
        this.separator = new Separator(Orientation.HORIZONTAL);
        this.separator.setMaxWidth(45);
        this.separator.setMinWidth(45);
        this.separator.setVisible(false);
        this.dmg_lbl = new Label();
        this.acc_lbl = new Label();

        super.prefWidth(45);
        super.prefHeight(85);
        super.setStyle("-fx-background-color: #ffffff");
        super.getChildren().addAll(acc_lbl,separator, dmg_lbl);
    }
    public void fillBlock(Card card){
        Tooltip tooltip = new Tooltip(card.name);
        Tooltip.install(this,tooltip);
        this.isEmpty = false;
        this.separator.setVisible(true);
        this.dmg_lbl.setText(block.dmg+"");
        this.acc_lbl.setText(block.acc+"");
        super.setStyle("-fx-background-color: #c4e1eb");
    }
    public void destroy(){
        this.isDestroyed = true;
        this.isEmpty = false;
        super.setStyle("-fx-background-color: #000000");
    }

}
