package tests;

import models.lombok.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static specs.DeleteSpecs.deleteRequestSpec;
import static specs.DeleteSpecs.deleteResponseSpec;
import static specs.GetSingleSpecs.getSingleRequestSpec;
import static specs.GetSingleSpecs.getSingleResponseSpec;
import static specs.PostCreateSpecs.*;
import static specs.PutUpdateSpecs.putUpdateRequestSpec;
import static specs.PutUpdateSpecs.putUpdateResponseSpec;
import static specs.RegisterSpecs.registerRequestSpec;
import static specs.RegisterSpecs.registerResponseSpec;

public class ReqresInTests {

    @Test
    @DisplayName("Позитивная проверка регистрации пользователя")
    void checkRegisterSuccessful() {
        RegisterBodyLombokModel body = new RegisterBodyLombokModel();
        body.setEmail("eve.holt@reqres.in");
        body.setPassword("pistol");

        RegisterResponseLombokModel response = given()
                .spec(registerRequestSpec)
                .body(body)
                .when()
                .post()
                .then()
                .spec(registerResponseSpec)
                .extract()
                .as(RegisterResponseLombokModel.class);

        assertThat(response.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");
        assertThat(response.getId()).isEqualTo(4);
    }

    @Test
    @DisplayName("Проверка получения информации по пользователю")
    void checkGetSingleUserTest() {
        GetSingleResponseLombokModel response = given()
                .spec(getSingleRequestSpec)
                .when()
                .get()
                .then()
                .spec(getSingleResponseSpec)
                .extract()
                .as(GetSingleResponseLombokModel.class);

        assertThat(response.getData().getId()).isEqualTo(2);
        assertThat(response.getData().getEmail()).isEqualTo("janet.weaver@reqres.in");
        assertThat(response.getData().getFirstName()).isEqualTo("Janet");
        assertThat(response.getData().getLastName()).isEqualTo("Weaver");
        assertThat(response.getData().getAvatar()).isEqualTo("https://reqres.in/img/faces/2-image.jpg");
        assertThat(response.getSupport().getUrl()).isEqualTo("https://reqres.in/#support-heading");
        assertThat(response.getSupport().getText()).isEqualTo("To keep ReqRes free, contributions towards server costs are appreciated!");
    }

    @Test
    @DisplayName("Тест на создание имени и работы пользователя")
    void checkPostCreateTest() {
        PostCreateBodyLombokModel body = new PostCreateBodyLombokModel();
        body.setName("Cat");
        body.setJob("walk around the house");

        PostCreateResponseLombokModel response = given()
                .spec(postCreateRequestSpec)
                .body(body)
                .when()
                .post()
                .then()
                .spec(postCreateResponseSpec)
                .extract()
                .as(PostCreateResponseLombokModel.class);

        assertThat(response.getName()).isEqualTo("Cat");
        assertThat(response.getJob()).isEqualTo("walk around the house");
    }

    @Test
    @DisplayName("Негативная проверка создания имени и работы пользователя")
    void checkNegativePostCreateTest() {
        given()
                .spec(negativePostCreateRequestSpec)
                .post()
                .then()
                .spec(negativePostCreateResponseSpec);
    }

    @Test
    @DisplayName("Тест на редактирование имени и работы пользователя")
    void checkPutUpdateTest() {
        PutUpdateBodyLombokModel body = new PutUpdateBodyLombokModel();
        body.setName("Kitty");
        body.setJob("sleep all day");

        PutUpdateResponseLombokModel response = given()
                .spec(putUpdateRequestSpec)
                .body(body)
                .when()
                .put()
                .then()
                .spec(putUpdateResponseSpec)
                .extract()
                .as(PutUpdateResponseLombokModel.class);

        assertThat(response.getName()).isEqualTo("Kitty");
        assertThat(response.getJob()).isEqualTo("sleep all day");
    }

    @Test
    @DisplayName("Тест на удаление пользователя")
    void checkDeleteTest() {
        given()
                .spec(deleteRequestSpec)
                .when()
                .delete()
                .then()
                .spec(deleteResponseSpec);
    }

    @Test
    @DisplayName("Проверка почты в списке пользователей с Groovy")
    public void checkEmailWithGroovy() {
        given()
                .spec(postCreateRequestSpec)
                .when()
                .get()
                .then()
                .spec(putUpdateResponseSpec)
                .body("data.findAll{it.email =~/.*?@reqres.in/}.email.flatten()",
                        hasItem("janet.weaver@reqres.in"));
    }

    @Test
    @DisplayName("Проверка имени в списке пользователей с Groovy")
    public void checkFirstNameWithGroovy() {
        given()
                .spec(postCreateRequestSpec)
                .when()
                .get()
                .then()
                .spec(putUpdateResponseSpec)
                .body("data.findAll{it.first_name}.first_name.flatten()",
                        hasItem("Charles"));
    }
}

