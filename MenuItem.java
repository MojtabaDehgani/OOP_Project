package console.menu;

public class MenuItem {
    public String text;
    public boolean isActive;

    public MenuItem(String text, boolean isActive) {
        this.text = text;
        this.isActive = isActive;
    }

    public MenuItem(String text) {
        this.text = text;
        this.isActive = true;
    }

}
