
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.documentationConfiguration

import com.jayway.restassured.builder.RequestSpecBuilder
import com.jayway.restassured.specification.RequestSpecification
import org.junit.Rule
import org.springframework.restdocs.JUnitRestDocumentation
import spock.lang.Specification

class BaseDocumentationSpec extends Specification {
    @Rule
    JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation('src/docs/generated-snippets')

    protected RequestSpecification documentationSpec

    void setup() {
        this.documentationSpec = new RequestSpecBuilder()
            .setBaseUri('http://jsonplaceholder.typicode.com')
            .addFilter(documentationConfiguration(restDocumentation))
            .build()
    }
}
