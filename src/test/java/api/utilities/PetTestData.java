package api.utilities;

import api.model.Category;
import api.model.PetPojo;
import api.model.PetStatus;
import api.model.Tag;
import com.github.javafaker.Faker;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class PetTestData {
    public PetPojo petPayload = new PetPojo();
    public Category category = new Category();
    public Tag tag = new Tag();
    public Faker faker = new Faker();

    public PetPojo petDataCreation(){
        ArrayList<String> photoUrlList = new ArrayList<String>();
        photoUrlList.add(faker.internet().image());

        tag.setId(faker.number().randomDigit());
        tag.setName(faker.animal().name());
        ArrayList<Tag> tags = new ArrayList<>();
        tags.add(tag);

        category.setId(faker.number().randomDigit());
        category.setName(faker.animal().name());

        petPayload.setId(faker.idNumber().hashCode());
        petPayload.setName(faker.animal().name());
        petPayload.setCategory(category);
        petPayload.setPhotoUrls(photoUrlList);
        petPayload.setTags(tags);
        petPayload.setStatus(PetStatus.available);

        return petPayload;
    }

    public File petImageDownloadAndSave() throws IOException {
        String imagePath = System.getProperty("user.dir") + "\\images";
        File imageFile = new File(imagePath);
        if(!imageFile.exists()){
            imageFile.mkdir();
        }
        URL url = new URL("https://upload.wikimedia.org/wikipedia/commons/4/43/Cute_dog.jpg");
        BufferedImage image = ImageIO.read(url);
        ImageIO.write(image, "png", new File(imagePath + "\\animal.png"));
        imageFile = new File(imagePath + "\\animal.png");

        return imageFile;
    }
}
