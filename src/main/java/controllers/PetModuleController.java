package controllers;

import api.endpoints.UrlRepository;
import api.model.PetPojo;
import api.model.PetStatus;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.File;

import static io.restassured.RestAssured.given;

public class PetModuleController {
    private static RequestSpecification requestSpecification;

    public PetModuleController() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri(UrlRepository.BASEURL);
        requestSpecBuilder.setContentType(ContentType.JSON);
        requestSpecBuilder.log(LogDetail.ALL);
        requestSpecification = requestSpecBuilder.build();
    }

    //Request to add new Pet
    public static Response addPet(PetPojo payload) {
        return given(requestSpecification)
                .accept(ContentType.JSON)
                .body(payload)
                .when()
                .post(UrlRepository.addPet);
    }

    //Request to update an existing Pet
    public static Response updatePet(PetPojo payload) {
        return given(requestSpecification)
                .accept(ContentType.JSON)
                .body(payload)
                .when()
                .put(UrlRepository.updatePet);
    }

    //Request to get Pet List by Status
    public static Response getPetsByStatus(PetStatus status) {
        return given(requestSpecification)
                .queryParam("status", status)
                .when()
                .get(UrlRepository.findPetByStatus);
    }

    // Request to get the Pet by its ID
    public static Response getPetsById(Integer petId) {
        return given(requestSpecification)
                .pathParam("petId", petId)
                .when()
                .get(UrlRepository.findPetById);
    }

    // Request to delete the pet
    public static Response deletePet(Integer petId) {
        return given(requestSpecification)
                .pathParam("petId", petId)
                .when()
                .delete(UrlRepository.deletePet);
    }

//    public void verifyPetDeleted(Pet pet) {
//        given(requestSpecification)
//                .pathParam("petId", pet.getId())
//                .get(PET_ENDPOINT + "/{petId}")
//                .then()
//                .body(containsString("Pet not found"));
//    }


    //Request to update the Pet using the Form Data
    public static Response updatePetUsingFormData(Integer petId, String name, String status) {
        return given()
                .contentType(ContentType.URLENC.withCharset("UTF-8"))
                .accept(ContentType.JSON)
                .pathParam("petId", petId)
                .formParam("name", name)
                .formParam("status", status)
                .when()
                .post(UrlRepository.updatePetForm);
    }

    public static Response uploadPetImage(Integer petId, String additionalMetadata, File file){
        return given()
                .accept(ContentType.JSON)
                .pathParam("petId", petId)
                .multiPart("file", file)
                .formParam("additionalMetadata", additionalMetadata)
                .when()
                .post(UrlRepository.uploadPetImage);
    }

}
