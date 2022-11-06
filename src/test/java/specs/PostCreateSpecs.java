package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomApiListener.withCustomTemplates;
import static io.restassured.RestAssured.with;

public class PostCreateSpecs {
    public static RequestSpecification postCreateRequestSpec = with()
            .filter(withCustomTemplates())
            .baseUri("https://reqres.in")
            .basePath("/api/users")
            .log().uri()
            .log().body()
            .contentType(ContentType.JSON);

    public static ResponseSpecification postCreateResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(201)
            .log(LogDetail.STATUS)
            .log(LogDetail.BODY)
            .build();


    public static RequestSpecification negativePostCreateRequestSpec = with()
            .filter(withCustomTemplates())
            .baseUri("https://reqres.in")
            .basePath("/api/users")
            .log().uri()
            .log().body();

    public static ResponseSpecification negativePostCreateResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(415)
            .log(LogDetail.STATUS)
            .log(LogDetail.BODY)
            .build();
}



