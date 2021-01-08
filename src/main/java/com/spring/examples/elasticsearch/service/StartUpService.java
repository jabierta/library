package com.spring.examples.elasticsearch.service;

import com.spring.examples.elasticsearch.domain.BookRecord;
import com.spring.examples.elasticsearch.domain.Library;
import com.spring.examples.elasticsearch.domain.User;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
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
import org.elasticsearch.action.update.UpdateRequest;
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
// Create conditional property to run or not
public class StartUpService {
  private final ResourceLoader resourceLoader;
  private final RestHighLevelClient elasticsearchClient;

  /*This method is not optimised and Big O notation is O(n^c)*/
  @PostConstruct
  private void initializeData() throws IOException, ParseException {
    BulkRequest createUserBulkRequest = new BulkRequest();

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

    createUserBulkRequest.add(createUserRequest("John", "Doe"));
    createUserBulkRequest.add(createUserRequest("Jane", "Doe"));
    createUserBulkRequest.add(createUserRequest("Mark", "Doe"));
    createUserBulkRequest.add(createUserRequest("Mills", "Doe"));
    createUserBulkRequest.add(createUserRequest("Ian", "Deer"));
    createUserBulkRequest.add(createUserRequest("Anna", "Deer"));
    createUserBulkRequest.add(createUserRequest("Anthony", "Deer"));
    createUserBulkRequest.add(createUserRequest("Josh", "Smith"));
    createUserBulkRequest.add(createUserRequest("Jenny", "Smith"));
    createUserBulkRequest.add(createUserRequest("Jack", "Mason"));
    createUserBulkRequest.add(createUserRequest("Alex", "Mason"));
    createUserBulkRequest.add(createUserRequest("Rafael", "Dore"));
    createUserBulkRequest.add(createUserRequest("Cinda", "Mallari"));
    createUserBulkRequest.add(createUserRequest("Dani", "Waits"));
    createUserBulkRequest.add(createUserRequest("Salley", "Holdridge"));
    createUserBulkRequest.add(createUserRequest("Gertrudis", "Rentschler"));
    createUserBulkRequest.add(createUserRequest("Marylin", "Giddings"));
    createUserBulkRequest.add(createUserRequest("Reita", "Beltrami"));
    createUserBulkRequest.add(createUserRequest("Carley", "Howton"));
    createUserBulkRequest.add(createUserRequest("Marco", "Serafini"));
    createUserBulkRequest.add(createUserRequest("Camilla", "Garbett"));
    createUserBulkRequest.add(createUserRequest("Hilaria", "Heather"));
    createUserBulkRequest.add(createUserRequest("Bruno", "Wainwright"));
    createUserBulkRequest.add(createUserRequest("Lane", "Fitzsimmons"));
    createUserBulkRequest.add(createUserRequest("Katrina", "Loudon"));
    createUserBulkRequest.add(createUserRequest("Sherie", "Alam"));
    createUserBulkRequest.add(createUserRequest("Percy", "Fujimoto"));
    createUserBulkRequest.add(createUserRequest("Alda", "Schroer"));
    createUserBulkRequest.add(createUserRequest("Onie", "Aponte"));
    createUserBulkRequest.add(createUserRequest("Maxima", "Figeroa"));
    createUserBulkRequest.add(createUserRequest("Caridad", "Maurin"));
    createUserBulkRequest.add(createUserRequest("Jaqueline", "Bellomy"));
    createUserBulkRequest.add(createUserRequest("Pansy", "Joplin"));
    createUserBulkRequest.add(createUserRequest("Verna", "Parish"));
    createUserBulkRequest.add(createUserRequest("Denver", "Gaeta"));
    createUserBulkRequest.add(createUserRequest("Hugh", "Mcfatridge"));
    createUserBulkRequest.add(createUserRequest("Shavon", "Herren"));
    createUserBulkRequest.add(createUserRequest("Dagny", "Predmore"));
    createUserBulkRequest.add(createUserRequest("Jalisa", "Madkins"));
    createUserBulkRequest.add(createUserRequest("Consuela", "Redondo"));
    createUserBulkRequest.add(createUserRequest("Bethanie", "Getman"));
    createUserBulkRequest.add(createUserRequest("Wilburn", "Santee"));
    createUserBulkRequest.add(createUserRequest("Stephan", "Mcabee"));
    createUserBulkRequest.add(createUserRequest("Alleen", "Faivre"));
    createUserBulkRequest.add(createUserRequest("Elsy", "Kovacs"));
    createUserBulkRequest.add(createUserRequest("Layla", "Ridgeway"));
    createUserBulkRequest.add(createUserRequest("Carmelia", "Balser"));
    createUserBulkRequest.add(createUserRequest("Rubin", "Marmon"));
    createUserBulkRequest.add(createUserRequest("Felecia", "Desch"));
    createUserBulkRequest.add(createUserRequest("Marty", "Stutes"));
    createUserBulkRequest.add(createUserRequest("Ulrike", "Kimmer"));
    createUserBulkRequest.add(createUserRequest("Gaye", "Seguin"));
    createUserBulkRequest.add(createUserRequest("Audria", "Doane"));
    createUserBulkRequest.add(createUserRequest("Jadwiga", "Northrop"));
    createUserBulkRequest.add(createUserRequest("Evelynn", "Feagins"));
    createUserBulkRequest.add(createUserRequest("Rosalie", "Higgin"));
    createUserBulkRequest.add(createUserRequest("Pamelia", "Youmans"));
    createUserBulkRequest.add(createUserRequest("Christinia", "Kleinman"));
    createUserBulkRequest.add(createUserRequest("Raphael", "Hipsher"));
    createUserBulkRequest.add(createUserRequest("Sherrill", "Melle"));

    elasticsearchClient.bulk(createUserBulkRequest, RequestOptions.DEFAULT);

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
    LocalDate localDate = LocalDate.now();
    // how we going to create date
    Date date = Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    // Iterate through file and create books
    while ((line = csvReader.readLine()) != null) {
      if (!line.equals("Title,Author,Year\n")) {
        String[] data = line.split(",");
        int numBooks = new Random().nextInt(5) + 1;
        while (numBooks >= 1) {
          bookBulkRequest.add(
              createBookRequest(
                  data[0], data[1], simpleDateFormat.parse(data[2]), true, date, library.getId()));
          numBooks--;
        }
      }
    }
    csvReader.close();

    elasticsearchClient.bulk(bookBulkRequest, RequestOptions.DEFAULT);

    Date dateToday = date;
    for (int i = 365; i >= 0; i--) {
      BulkRequest bookAndActivityBulkRequest = new BulkRequest();
      BulkRequest userBulkRequest = new BulkRequest();

      List<User> usersToday =
          new ArrayList<>(); // if size is less than 20 then pick 20 random users
      SearchRequest userSearchRequest = new SearchRequest("user");
      SearchSourceBuilder userSearchSourceBuilder = new SearchSourceBuilder();
      userSearchSourceBuilder.query(QueryBuilders.matchAllQuery());
      userSearchSourceBuilder.size(1000);
      userSearchRequest.source(userSearchSourceBuilder);

      SearchResponse searchResponse =
          elasticsearchClient.search(userSearchRequest, RequestOptions.DEFAULT);
      List<User> userList = new ArrayList<>();

      for (SearchHit userHit : searchResponse.getHits().getHits()) {
        Map<String, Object> userHitSourceAsMap = userHit.getSourceAsMap();
        User user = new User();
        user.setId(userHit.getId());
        user.setFirstName((String) userHitSourceAsMap.get("firstName"));
        user.setLastName((String) userHitSourceAsMap.get("lastName"));
        userList.add(
            new User(
                userHit.getId(),
                (String) userHitSourceAsMap.get("firstName"),
                (String) userHitSourceAsMap.get("lastName"),
                (List<BookRecord>) userHitSourceAsMap.get("currentlyBorrowedBooks."),
                (List<BookRecord>) userHitSourceAsMap.get("idsOfCurrentlyReservedBooks")));
      }

      // Randomize userList. I know Collection.shuffle would be a better implementation.
      for (int currentUserIndex = 0; currentUserIndex <= userList.size(); currentUserIndex++) {
        int swapLocation = new Random().nextInt(userList.size()) + 1;
        User currentUser = userList.get(i);
        User swappedUser = userList.get(swapLocation);
        userList.set(i, swappedUser);
        userList.set(swapLocation, currentUser);
      }

      // Check if user has expired books
      for (User user : userList) {
        boolean hasToReturnBook = false;
        for (BookRecord bookRecord : user.getCurrentlyBorrowedBooks()) {
          if (bookRecord.getReturnDate().equals(dateToday)) {
           this.createActivityRequest();
           this.updateBookRequest();
            hasToReturnBook = true;
          }
        }

        if (hasToReturnBook) {
          usersToday.add(user);
        }
      }

      //      for (User user : userList) {
      //        if (!(user.getIdsOfCurrentlyBorrowedBooks().size() > 5)) {
      //          usersToday.add(user);
      //        }
      //      }

      //      itterate through each user if there are any books that are required to be returned if
      // so add them to the array
      //      ittterate through each user and see if any of there reserved book is available to
      //      borrow add them to the array
      //      if they have full inventory then they can either return 1 of there current used
      //      books or keep resrving
      //
      //      if size of array is less than 30 then add more users to borrow books

      // UPSERT FOR EACH USER in userList
      localDate = localDate.plusDays(1);
      dateToday = Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

      elasticsearchClient.bulk(bookAndActivityBulkRequest, RequestOptions.DEFAULT);
    }
  }

  private IndexRequest createUserRequest(String firstName, String lastName) {
    return new IndexRequest("user")
        .source(
            XContentType.JSON,
            "firstName",
            firstName,
            "lastName",
            lastName,
            "currentlyBorrowedBooks",
            new ArrayList<BookRecord>(),
            "currentlyReservedBooks",
            new ArrayList<BookRecord>());
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

//TODO: Create activity update!
  private IndexRequest createActivityRequest(){return null;}

  private UpdateRequest updateBookRequest() {return null; }
}
