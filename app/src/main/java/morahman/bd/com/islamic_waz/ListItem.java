package morahman.bd.com.islamic_waz;

/**
 * Created by user on 19/5/18.
 */

public class ListItem {
    private String head;
    private String imageUrl;

    public ListItem(String head, String imageUrl) {
        this.head = head;

        this.imageUrl = imageUrl;
    }

    public String getHead() {
        return head;
    }
    

    public String getImageUrl() {
        return imageUrl;
    }
}
