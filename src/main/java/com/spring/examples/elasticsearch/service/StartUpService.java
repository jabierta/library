package com.spring.examples.elasticsearch.service;

import com.spring.examples.elasticsearch.domain.Library;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StartUpService {
  private final ResourceLoader resourceLoader;
  private final RestHighLevelClient elasticsearchClient;

  @PostConstruct
  private void initializeData() throws IOException, ParseException {
    BulkRequest userBulkRequest = new BulkRequest();

    // Delete existing user index and creates an empty user index
    if (!elasticsearchClient
        .indices()
        .exists(new GetIndexRequest("user"), RequestOptions.DEFAULT)) {
      elasticsearchClient.indices().create(new CreateIndexRequest("user"), RequestOptions.DEFAULT);
    } else {
      elasticsearchClient.deleteByQuery(
          new DeleteByQueryRequest("user").setQuery(QueryBuilders.matchAllQuery()),
          RequestOptions.DEFAULT);
    }

    userBulkRequest.add(createUserRequest("John", "Doe"));
    userBulkRequest.add(createUserRequest("Jane", "Doe"));
    userBulkRequest.add(createUserRequest("Mark", "Doe"));
    userBulkRequest.add(createUserRequest("Mills", "Doe"));
    userBulkRequest.add(createUserRequest("Ian", "Deer"));
    userBulkRequest.add(createUserRequest("Anna", "Deer"));
    userBulkRequest.add(createUserRequest("Anthony", "Deer"));
    userBulkRequest.add(createUserRequest("Josh", "Smith"));
    userBulkRequest.add(createUserRequest("Jenny", "Smith"));
    userBulkRequest.add(createUserRequest("Jack", "Mason"));
    userBulkRequest.add(createUserRequest("Alex", "Mason"));
    userBulkRequest.add(createUserRequest("Rafael", "Dore"));
    userBulkRequest.add(createUserRequest("Cinda", "Mallari"));
    userBulkRequest.add(createUserRequest("Dani", "Waits"));
    userBulkRequest.add(createUserRequest("Salley", "Holdridge"));
    userBulkRequest.add(createUserRequest("Gertrudis", "Rentschler"));
    userBulkRequest.add(createUserRequest("Marylin", "Giddings"));
    userBulkRequest.add(createUserRequest("Reita", "Beltrami"));
    userBulkRequest.add(createUserRequest("Carley", "Howton"));
    userBulkRequest.add(createUserRequest("Marco", "Serafini"));
    userBulkRequest.add(createUserRequest("Camilla", "Garbett"));
    userBulkRequest.add(createUserRequest("Hilaria", "Heather"));
    userBulkRequest.add(createUserRequest("Bruno", "Wainwright"));
    userBulkRequest.add(createUserRequest("Lane", "Fitzsimmons"));
    userBulkRequest.add(createUserRequest("Katrina", "Loudon"));
    userBulkRequest.add(createUserRequest("Sherie", "Alam"));
    userBulkRequest.add(createUserRequest("Percy", "Fujimoto"));
    userBulkRequest.add(createUserRequest("Alda", "Schroer"));
    userBulkRequest.add(createUserRequest("Onie", "Aponte"));
    userBulkRequest.add(createUserRequest("Maxima", "Figeroa"));
    userBulkRequest.add(createUserRequest("Caridad", "Maurin"));
    userBulkRequest.add(createUserRequest("Jaqueline", "Bellomy"));
    userBulkRequest.add(createUserRequest("Pansy", "Joplin"));
    userBulkRequest.add(createUserRequest("Verna", "Parish"));
    userBulkRequest.add(createUserRequest("Denver", "Gaeta"));
    userBulkRequest.add(createUserRequest("Hugh", "Mcfatridge"));
    userBulkRequest.add(createUserRequest("Shavon", "Herren"));
    userBulkRequest.add(createUserRequest("Dagny", "Predmore"));
    userBulkRequest.add(createUserRequest("Jalisa", "Madkins"));
    userBulkRequest.add(createUserRequest("Consuela", "Redondo"));
    userBulkRequest.add(createUserRequest("Bethanie", "Getman"));
    userBulkRequest.add(createUserRequest("Wilburn", "Santee"));
    userBulkRequest.add(createUserRequest("Stephan", "Mcabee"));
    userBulkRequest.add(createUserRequest("Alleen", "Faivre"));
    userBulkRequest.add(createUserRequest("Elsy", "Kovacs"));
    userBulkRequest.add(createUserRequest("Layla", "Ridgeway"));
    userBulkRequest.add(createUserRequest("Carmelia", "Balser"));
    userBulkRequest.add(createUserRequest("Rubin", "Marmon"));
    userBulkRequest.add(createUserRequest("Felecia", "Desch"));
    userBulkRequest.add(createUserRequest("Marty", "Stutes"));
    userBulkRequest.add(createUserRequest("Ulrike", "Kimmer"));
    userBulkRequest.add(createUserRequest("Gaye", "Seguin"));
    userBulkRequest.add(createUserRequest("Audria", "Doane"));
    userBulkRequest.add(createUserRequest("Jadwiga", "Northrop"));
    userBulkRequest.add(createUserRequest("Evelynn", "Feagins"));
    userBulkRequest.add(createUserRequest("Rosalie", "Higgin"));
    userBulkRequest.add(createUserRequest("Pamelia", "Youmans"));
    userBulkRequest.add(createUserRequest("Christinia", "Kleinman"));
    userBulkRequest.add(createUserRequest("Raphael", "Hipsher"));
    userBulkRequest.add(createUserRequest("Sherrill", "Melle"));

    elasticsearchClient.bulk(userBulkRequest, RequestOptions.DEFAULT);

    // Delete existing library index and creates an empty library index
    if (!elasticsearchClient
        .indices()
        .exists(new GetIndexRequest("library"), RequestOptions.DEFAULT)) {
      elasticsearchClient
          .indices()
          .create(new CreateIndexRequest("library"), RequestOptions.DEFAULT);
    } else {
      elasticsearchClient.deleteByQuery(
          new DeleteByQueryRequest("library").setQuery(QueryBuilders.matchAllQuery()),
          RequestOptions.DEFAULT);
    }

    elasticsearchClient.index(
        new IndexRequest("library")
            .source(
                XContentType.JSON,
                "name",
                "Abiertas Library",
                "street",
                "Awesome Street",
                "city",
                "Abiertas",
                "province",
                "AWE"),
        RequestOptions.DEFAULT);

    // Find the Abiertas Library and use the _id to save books into it.
    SearchSourceBuilder getLibrarySearchSourceBuilder = new SearchSourceBuilder();
    getLibrarySearchSourceBuilder
        .query(QueryBuilders.matchQuery("name.keyword", "Abiertas Library"))
        .size(1);

    SearchResponse getLibrarySearchResponse =
        elasticsearchClient.search(
            new SearchRequest().source(getLibrarySearchSourceBuilder), RequestOptions.DEFAULT);

    SearchHit searchHit = getLibrarySearchResponse.getHits().getHits()[0];
    Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();

    Library library = new Library();
    library.setId(searchHit.getId());
    library.setName((String) sourceAsMap.get("name"));
    library.setStreet((String) sourceAsMap.get("street"));
    library.setCity((String) sourceAsMap.get("city"));
    library.setProvince((String) sourceAsMap.get("province"));

    // Delete existing book index and creates an empty book index
    if (!elasticsearchClient
        .indices()
        .exists(new GetIndexRequest("book"), RequestOptions.DEFAULT)) {
      elasticsearchClient.indices().create(new CreateIndexRequest("book"), RequestOptions.DEFAULT);
    } else {
      elasticsearchClient.deleteByQuery(
          new DeleteByQueryRequest("book").setQuery(QueryBuilders.matchAllQuery()),
          RequestOptions.DEFAULT);
    }

    BulkRequest bookBulkRequest = new BulkRequest();

    // Getting the file from resource path
    Resource resource = resourceLoader.getResource("classpath:books.csv");
    BufferedReader csvReader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
    String line;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
    Map<Integer, Pair<List<String>, Boolean>> books = new HashMap<>();

    // Get current date
    Date date
    // Iterate through file and create books
    while ((line = csvReader.readLine()) != null) {
      if (!line.equals("Title,Author,Year\n")) {
        String[] data = line.split(",");
        int numBooks = new Random().nextInt(5) + 1;
        System.out.println(numBooks);
        while (numBooks >= 1) {
          bookBulkRequest.add(
              createBookRequest(
                  data[0], data[1], simpleDateFormat.parse(data[2]), true, null, library.getId()));
          numBooks--;
        }
      }
    }
    csvReader.close();

    elasticsearchClient.bulk(bookBulkRequest, RequestOptions.DEFAULT);

    //    // Itterate through year 2021
    //      for (day in year) {
    //        itterate through each user if there are any books that are required to be returned if
    // so add them to the array
    //        itterate through each user and see if any of there reserved book is available to
    // borrow add them to the array
    //          if they have full inventory then they can either return 1 of there current used
    // books or keep resrving
    //        if size of array is less than 30 then add more users to borrow books
    //      }

  }

  private IndexRequest createUserRequest(String firstName, String lastName) {
    return new IndexRequest("user")
        .source(XContentType.JSON, "firstName", firstName, "lastName", lastName);
  }

  private IndexRequest createBookRequest(
      String title,
      String author,
      Date year,
      Boolean isAvailableToBorrow,
      Date nextAvailability,
      String libraryId) {
    return new IndexRequest("book")
        .source(
            XContentType.JSON,
            "title",
            title,
            "author",
            author,
            "year",
            year,
            "isAvailableToBorrow",
            isAvailableToBorrow,
            "nextAvailability",
            nextAvailability,
            "libraryId",
            libraryId);
  }
}
