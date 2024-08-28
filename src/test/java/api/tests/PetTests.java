package api.tests;

import api.TestBase;
import api.model.PetPojo;
import api.model.PetPojoResponse;
import api.model.PetStatus;
import api.utilities.PetTestData;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import controllers.PetModuleController;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

public class PetTests extends TestBase {
    PetPojo newPet;
    File imageToUpload;
    PetModuleController petModuleController = new PetModuleController();
    ExtentTest extentTest;

    @BeforeClass
    public void setupData() throws IOException {
        PetTestData petTestData = new PetTestData();
        newPet = petTestData.petDataCreation();
        imageToUpload = petTestData.petImageDownloadAndSave();
    }

    @BeforeMethod
    public void setup(ITestResult result) {
        extentTest = reportManager.setUpTest(result.getMethod().getMethodName());
    }

    /* Test case to Add new pet to Pet store.
    Expected :: If Pet added successfully Status code : 200 and
    pet name in response body matches the pet name provided in request
    * */
    @Test(priority = 1)
    public void testCreateNewPet() {
        //Create new Pet
        extentTest.log(Status.INFO, "Creating Add New Pet Request");
        Response addPetResponse = PetModuleController.addPet(newPet);
        addPetResponse.then().log().all();
        PetPojoResponse petResponse = addPetResponse.as(PetPojoResponse.class);

        extentTest.log(Status.INFO, "Asserting Response Status Code and Pet Name");
        //Assert status code and Pet Name
        Assert.assertEquals(addPetResponse.getStatusCode(), 200);
        Assert.assertEquals(petResponse.getName(), newPet.getName());
        extentTest.log(Status.PASS, "Response status code and Pet Name matches successfully.");
    }

    /* Test case to Find pet using PetID.
    Expected :: If Pet found successfully Status code : 200 else Status Code : 404
    * */
    @Test(priority = 2)
    public void testFindPetById() {
        //Create request with valid PetID
        extentTest.log(Status.INFO, "Creating Find Pet using Id Request");
        Response findPetByIdResponse = PetModuleController.getPetsById(newPet.getId());
        findPetByIdResponse.then().log().all();
        PetPojoResponse petResponse = findPetByIdResponse.as(PetPojoResponse.class);

        extentTest.log(Status.INFO, "Asserting Response Status Code and Pet Name");
        //Assert status code and Pet Name after creation
        Assert.assertEquals(findPetByIdResponse.getStatusCode(), 200);
        Assert.assertEquals(petResponse.getName(), newPet.getName());
        extentTest.log(Status.PASS, "Response status code and Pet Name matches with Pet Id.");

        //Create request with invalid PetID
        extentTest.log(Status.INFO, "Creating Find Pet using invalid Id Request");
        findPetByIdResponse = PetModuleController.getPetsById(200000);
        findPetByIdResponse.then().log().all();

        extentTest.log(Status.INFO, "Asserting Response Status Code and Response body message");
        //Assert status code and Response body
        Assert.assertEquals(findPetByIdResponse.getStatusCode(), 404);
        Assert.assertTrue(findPetByIdResponse.getBody().asString().contains("Pet not found"));
        extentTest.log(Status.PASS, "Response status code and Response body matches successfully.");
    }

    /* Test case to Find List of Pet using Status.
    Expected :: Body should contain the list of all pets according to PetStatus. Status Code :: 200
    * */
    @Test(priority = 3)
    public void testFindPetByStatus() {
        //Create request with valid PetStatus
        extentTest.log(Status.INFO, "Creating Find Pet By Status Request");
        Response findPetByStatusResponse = PetModuleController.getPetsByStatus(PetStatus.available);
        findPetByStatusResponse.then().log().all();

        //Assert status code and response body for correct status and not empty message body
        extentTest.log(Status.INFO, "Asserting Response Status Code and List of Pet according to Pet Status");
        Assert.assertEquals(findPetByStatusResponse.getStatusCode(), 200);
        Assert.assertFalse(findPetByStatusResponse.getBody().asString().contains(PetStatus.sold.toString()));
        Assert.assertFalse(findPetByStatusResponse.getBody().asString().contains(PetStatus.pending.toString()));
        Assert.assertNotNull(findPetByStatusResponse.getBody().jsonPath().getList("", PetPojoResponse.class));
        extentTest.log(Status.PASS, "Response status code and Response body matches successfully.");
    }

    /* Test case to update details of Pet.
    Expected :: Body should contain the updated Data of Pet. Status Code :: 200
    * */
    @Test(priority = 4)
    public void testUpdatePet() {
        //Create request with Pet Data to be updated
        extentTest.log(Status.INFO, "Creating Pet request with Pet data to update");
        newPet.setStatus(PetStatus.sold);
        newPet.setName("Updated Pet");
        Response updatePetResponse = PetModuleController.updatePet(newPet);
        updatePetResponse.then().log().all();
        PetPojoResponse petResponse = updatePetResponse.as(PetPojoResponse.class);

        //Assert response body contains the updated Pet data. Status Code :: 200
        extentTest.log(Status.INFO, "Asserting Response Status Code and Updated Pet records");
        Assert.assertEquals(updatePetResponse.getStatusCode(), 200);
        Assert.assertEquals(petResponse.getName(), "Updated Pet");
        Assert.assertEquals(petResponse.getStatus(), PetStatus.sold.toString());
        extentTest.log(Status.PASS, "Response status code and Response body matches successfully.");
    }

    /* Test case to update details of Pet using Form Data.
    Expected :: Body should contain the ID of the Pet updated and updated details. Status Code :: 200
    * */
    @Test(priority = 5)
    public void testUpdatePetUsingFormData() {
        //Create request with Pet Form Data to be updated
        extentTest.log(Status.INFO, "Creating Pet request with Pet Form values");
        Response updatePetUsingFormDataResponse = PetModuleController.updatePetUsingFormData
                (newPet.getId(), "UpdatePetFormData", "Pending");
        updatePetUsingFormDataResponse.then().log().all();

        //Assert response body contains the updated Pet ID. Status Code :: 200
        extentTest.log(Status.INFO, "Asserting Response Status Code and Updated Pet records");
        Assert.assertEquals(updatePetUsingFormDataResponse.getStatusCode(), 200);
        Assert.assertTrue(updatePetUsingFormDataResponse.getBody().asString().contains(newPet.getId().toString()));
        extentTest.log(Status.PASS, "Response status code and Response body matches successfully.");
    }

    /* Test case to upload Pet image to Pet ID.
   Expected :: Body should contain File uploaded of the Pet. Status Code :: 200
   * */
    @Test(priority = 6)
    public void testUploadPetImage() throws IOException {
        //Create request with image to be uploaded to Pet ID
        extentTest.log(Status.INFO, "Creating Pet request with Pet image to be uploaded");
        Response uploadPetImageResponse = PetModuleController.uploadPetImage
                (newPet.getId(), "Uploaded Pet Image", imageToUpload);
        uploadPetImageResponse.then().log().all();

        //Assert response body contains the uploaded Pet image. Status Code :: 200
        extentTest.log(Status.INFO, "Asserting Response Status Code and Updated Pet records");
        Assert.assertEquals(uploadPetImageResponse.getStatusCode(), 200);
        Assert.assertTrue(uploadPetImageResponse.getBody().asString().contains("File uploaded to ./animal.png"));
        extentTest.log(Status.PASS, "Response status code and Response body matches successfully.");
    }

    /* Test case to delete Pet according to PetId.
   Expected :: Body should contain Pet not found message when trying to get the deleted Pet of the Pet.
   Status Code :: 200 and 404 if Pet nod found
   * */
    @Test(priority = 7)
    public void testDeletePet() {
        //Create request to delete the Pet using Pet ID
        extentTest.log(Status.INFO, "Creating Pet deletion request with Pet ID");
        Response deletePetResponse = PetModuleController.deletePet(newPet.getId());
        deletePetResponse.then().log().all();

        //Assert Status code for deletion  response
        extentTest.log(Status.INFO, "Asserting Response code after Pet deletion");
        Assert.assertEquals(deletePetResponse.getStatusCode(), 200);
        extentTest.log(Status.PASS, "Response status code matches successfully.");

        //Get Pet using the deleted pet ID
        extentTest.log(Status.INFO, "Trying to Get Pet using the deleted PetID");
        Response verifyPetDeleted = PetModuleController.getPetsById(newPet.getId());
        Assert.assertEquals(verifyPetDeleted.getStatusCode(), 404);
        Assert.assertTrue(verifyPetDeleted.getBody().asString().contains("Pet not found"));
        extentTest.log(Status.PASS, "Response status code and Response body matches successfully.");

        //Deleting Pet with invalid PetID
        extentTest.log(Status.INFO, "Delete Pet using an invalid PetID");
        deletePetResponse = PetModuleController.deletePet(20000);
        deletePetResponse.then().log().all();

        Assert.assertEquals(deletePetResponse.getStatusCode(), 404);
        extentTest.log(Status.PASS, "Response status code matches successfully.");
    }
}