package managering.instruments;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CollectionInfo {
    private final String type;
    private final Date startDate;
    private final int size;

    public CollectionInfo(String type, Date startDate, int size) {
        this.type = type;
        this.startDate = startDate;
        this.size = size;
    }

    public String getCollectionInfo() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String strDate = dateFormat.format(startDate);
        return "Collection type: " + type + "\nInitialized: " + strDate + "\nNumber of elements: " + size;
    }
}
