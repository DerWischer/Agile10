package adp.group10.roomates.backend.model;

import java.io.Serializable;

/**
 * Created by calle on 2017-04-23.
 */

public class AvailableItem implements Serializable {

    private String Name;

    public AvailableItem(){
        // Required for Firebase
    }

    public AvailableItem(String name){
        this.Name = name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getName() {
        return Name;
    }



}
