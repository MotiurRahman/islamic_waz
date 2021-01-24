package morahman.bd.com.islamic_waz;

/**
 * Created by user on 21/5/18.
 */

public class ListWaz {

    private String name;
    private String title;
    private String urlName;

    public ListWaz(String name, String title, String urlName) {
        this.name = name;
        this.title = title;
        this.urlName = urlName;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getUrlName() {
        return urlName;
    }
}
