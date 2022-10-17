package com.flightright.monitor;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MonitorServiceApplicationTests {
	@LocalServerPort
	private Integer port;

	private String fileName;
	private String largeFileName;
	@BeforeAll
	public void setup() {

		RestAssured.port = port;
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

		fileName = "sample_100.csv";
		/*createCsvFile(fileName, 100);

		largeFileName = "sample_gig.csv";
		createCsvFile(largeFileName, 10000000);*/
	}

	@Test
	public void getReportFromCsvReturns400ForNoFileName() {
		RestAssured.given()
				.contentType(ContentType.JSON)
				.when()
				.queryParam("filename", "")
				.get("/monitor/csv-report")
				.then()
				.assertThat()
				.statusCode(400);
	}

	@Test
	public void getReportFromCsvReturns404ForInvalidFile() {
		RestAssured.given()
				.contentType(ContentType.JSON)
				.when()
				.queryParam("filename", "non-existing-file")
				.get("/monitor/csv-report")
				.then()
				.assertThat()
				.statusCode(404);
	}

	@Test
	public void getReportFromCsvReturnsCountForValidFile() {
		RestAssured.given()
				.contentType(ContentType.JSON)
				.when()
				.queryParam("filename", fileName)
				.get("/monitor/csv-report")
				.then()
				.assertThat()
				.statusCode(200)
				.body("uniqueHits", Matchers.equalTo(100))
				.time(Matchers.lessThan(1000L));
	}
/*
	@Test
	public void getReportFromCsvReturnsCountForValidFileForLargeFile() {

		RestAssured.given()
				.contentType(ContentType.JSON)
				.when()
				.queryParam("filename", largeFileName)
				.get("/monitor/csv-report")
				.then()
				.assertThat()
				.statusCode(200)
				.body("uniqueHits", Matchers.equalTo(10000000))
				.time(Matchers.lessThan(10000L));
	}


	private void createCsvFile(String fileName, int count) {
		Path path = Paths.get("src/main/resources/files/" + fileName);
		try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
			for (int i = 0; i < count; i++) {
				writer.write(i + "test@test.com," + Math.random() * 1000000000 + ",www.google.com\n");
			}
			writer.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}

		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}*/

}
