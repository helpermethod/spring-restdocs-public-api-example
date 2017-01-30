import static com.jayway.restassured.RestAssured.given
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document
import static org.hamcrest.CoreMatchers.is
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.FieldDescriptor

class GenericDocumentationSpec extends BaseDocumentationSpec {
    void 'test and document list posts endpoint'() {
        given:
        def setup = given(this.documentationSpec)
            .param('userId', 1)
            .contentType('application/json')
            .accept('application/json')
                .filter(document('list-posts-example',
                preprocessResponse(prettyPrint()),
                responseFields(postList)))

        when:
        def result = setup
        .when()
        .get('/posts')

        then:
        result
            .then()
            .assertThat()
            .statusCode(is(200))
    }

    void 'test and document get single post'() {
        given:
        def setup = given(this.documentationSpec)
                .filter(document('get-post-by-id-example',
                preprocessResponse(prettyPrint()),
                responseFields(post)
            ))

        when:
        def result = setup
                .when()
                .get('/posts/1')

        then:
        result
                .then()
                .assertThat()
                .statusCode(is(200))
    }

    void 'test and document creating a new post'() {
        given:
        def setup = given(this.documentationSpec)
                .body('{"userId": 1, "id": 101, "title": "Test Example", "body": "Example Body"}')
                .contentType('application/json')
                .filter(document('create-new-post-example',
                preprocessResponse(prettyPrint()),
                requestFields(post)))

        when:
        def result = setup
                .when()
                .post('/posts')

        then:
        result
                .then()
                .assertThat()
                .statusCode(is(201))
    }

    void 'test and document error response'() {
        given:
        def setup = given(this.documentationSpec)
                .filter(document('error-example',
                preprocessResponse(prettyPrint())))

        when:
        def result = setup
                .when()
                .delete('/posts')

        then:
        result
                .then()
                .assertThat()
                .statusCode(is(404))
    }

    FieldDescriptor[] postList = new FieldDescriptor().with {
        [fieldWithPath('[].userId').type(JsonFieldType.NUMBER)
                .description('The userId of the user that made the post'),
        fieldWithPath('[].id').type(JsonFieldType.NUMBER).description('The id of the post'),
        fieldWithPath('[].title').type(JsonFieldType.STRING)
                .description('The title of the post'),
        fieldWithPath('[].body').type(JsonFieldType.STRING)
                .description('The body of the post')]
    }

    FieldDescriptor[] post = new FieldDescriptor().with {
        [fieldWithPath('userId').type(JsonFieldType.NUMBER)
                .description('The userId of the user that made the post'),
        fieldWithPath('id').type(JsonFieldType.NUMBER).description('The id of the post'),
        fieldWithPath('title').type(JsonFieldType.STRING)
                .description('The title of the post'),
        fieldWithPath('body').type(JsonFieldType.STRING)
                .description('The body of the post')]
    }
}
