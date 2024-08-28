package api.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class PetPojo {
    private Integer id;
    private String name;

    private Category category;
    private ArrayList<String> photoUrls;
    private ArrayList<Tag> tags;
    private PetStatus status;

}
