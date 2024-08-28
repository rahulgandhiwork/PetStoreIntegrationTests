package api.endpoints;

// Class contains all the URL's for the operations performed in PetStore

public class UrlRepository {

    //Base URL
    public static final String BASEURL = "https://petstore.swagger.io/v2";

    //Pet Module
    public static String addPet = BASEURL + "/pet";
    public static String updatePet = BASEURL + "/pet";
    public static String findPetByStatus = BASEURL + "/pet/findByStatus";
    public static String findPetById = BASEURL + "/pet/{petId}";
    public static String deletePet = BASEURL + "/pet/{petId}";
    public static String updatePetForm = BASEURL + "/pet/{petId}";
    public static String uploadPetImage = BASEURL + "/pet/{petId}/uploadImage";

    //Store Module
    public static String getPetInventory = BASEURL + "/store/inventory";
    public static String placeOrder = BASEURL + "/store/order";
    public static String getOrder = BASEURL + "/store/order/{orderId}";
    public static String deleteOrder = BASEURL + "/store/order/{orderId}";

    //User Module
    public static String createUserWithList = BASEURL + "/user/createWithList";
    public static String createUser = BASEURL + "/user";
    public static String getUser = BASEURL + "/user/{username}";
    public static String deleteUser = BASEURL + "/user/{username}";
    public static String updateUser = BASEURL + "/user/{username}";
    public static String logInUser = BASEURL + "/user/login";
    public static String logOutUser = BASEURL + "/user/logout";
    public static String createUserWithArray = BASEURL + "/user/createWithArray";

}
