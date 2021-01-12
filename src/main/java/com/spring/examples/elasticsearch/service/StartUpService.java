package com.spring.examples.elasticsearch.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.examples.elasticsearch.domain.Action;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.admin.indices.refresh.RefreshRequest;
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
// This is a Frankenstein
// TODO: Update and create a better approach
public class StartUpService {
  private final ResourceLoader resourceLoader;
  private final RestHighLevelClient elasticsearchClient;
  private final UserService userService;

  @PostConstruct
  /*This method is not optimised and Big O notation is O(n^c)*/
  public void initializeData() throws IOException, ParseException {
    BulkRequest createUserBulkRequest = new BulkRequest();

    // Delete data in existing user index and creates an empty user index
    if (!elasticsearchClient
        .indices()
        .exists(new GetIndexRequest("user"), RequestOptions.DEFAULT)) {
      String properties =
          "{\n"
              + "\"properties\" : {\n"
              + "        \"currentlyBorrowedBooks\" : {\n"
              + "          \"properties\" : {\n"
              + "            \"bookId\" : {\n"
              + "              \"type\" : \"text\",\n"
              + "              \"fields\" : {\n"
              + "                \"keyword\" : {\n"
              + "                  \"type\" : \"keyword\",\n"
              + "                  \"ignore_above\" : 256\n"
              + "                }\n"
              + "              }\n"
              + "            },\n"
              + "            \"returnDate\" : {\n"
              + "              \"type\" : \"date\"\n"
              + "            }\n"
              + "          }\n"
              + "        },\n"
              + "        \"currentlyReservedBooks\" : {\n"
              + "          \"properties\" : {\n"
              + "            \"bookId\" : {\n"
              + "              \"type\" : \"text\",\n"
              + "              \"fields\" : {\n"
              + "                \"keyword\" : {\n"
              + "                  \"type\" : \"keyword\",\n"
              + "                  \"ignore_above\" : 256\n"
              + "                }\n"
              + "              }\n"
              + "            },\n"
              + "            \"returnDate\" : {\n"
              + "              \"type\" : \"date\"\n"
              + "            }\n"
              + "          }\n"
              + "        },\n"
              + "        \"firstName\" : {\n"
              + "          \"type\" : \"text\",\n"
              + "          \"fields\" : {\n"
              + "            \"keyword\" : {\n"
              + "              \"type\" : \"keyword\",\n"
              + "              \"ignore_above\" : 256\n"
              + "            }\n"
              + "          }\n"
              + "        },\n"
              + "        \"lastName\" : {\n"
              + "          \"type\" : \"text\",\n"
              + "          \"fields\" : {\n"
              + "            \"keyword\" : {\n"
              + "              \"type\" : \"keyword\",\n"
              + "              \"ignore_above\" : 256\n"
              + "            }\n"
              + "          }\n"
              + "        }\n"
              + "      }"
              + "}";

      elasticsearchClient
          .indices()
          .create(
              new CreateIndexRequest("user").mapping(properties, XContentType.JSON),
              RequestOptions.DEFAULT);
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

    // Delete data in existing activity index and creates an empty user index
    if (!elasticsearchClient
        .indices()
        .exists(new GetIndexRequest("activity"), RequestOptions.DEFAULT)) {
      elasticsearchClient
          .indices()
          .create(new CreateIndexRequest("activity"), RequestOptions.DEFAULT);
    } else {
      elasticsearchClient.deleteByQuery(
          new DeleteByQueryRequest("activity").setQuery(QueryBuilders.matchAllQuery()),
          RequestOptions.DEFAULT);
    }

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
            new SearchRequest("library").source(getLibrarySearchSourceBuilder),
            RequestOptions.DEFAULT);

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
        int numBooks = new Random().nextInt(100) + 1;
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
      elasticsearchClient.indices().refresh(new RefreshRequest(), RequestOptions.DEFAULT);
      System.out.println("Library is opening for the day............");
      System.out.println(dateToday.toString());

      Date dateTomorrow =
          Date.from(
              localDate.plusDays(1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

      BulkRequest bookAndActivityBulkRequest = new BulkRequest();

      HashMap<String, User> usersToday = new HashMap<>();
      SearchRequest userSearchRequest = new SearchRequest("user");
      SearchSourceBuilder userSearchSourceBuilder = new SearchSourceBuilder();
      userSearchSourceBuilder.query(QueryBuilders.matchAllQuery());
      userSearchSourceBuilder.size(1000);
      userSearchRequest.source(userSearchSourceBuilder);

      SearchResponse searchResponse =
          elasticsearchClient.search(userSearchRequest, RequestOptions.DEFAULT);

      // Get users
      List<User> userList = new ArrayList<>();
      for (SearchHit userHit : searchResponse.getHits().getHits()) {
        Map<String, Object> userHitSourceAsMap = userHit.getSourceAsMap();
        User user = new User();
        user.setId(userHit.getId());
        user.setFirstName((String) userHitSourceAsMap.get("firstName"));
        user.setLastName((String) userHitSourceAsMap.get("lastName"));
        List<BookRecord> currentlyBorrowedBooks =
            userService.toBookRecord(
                (List<HashMap<String, Object>>) userHitSourceAsMap.get("currentlyBorrowedBooks"));
        user.setCurrentlyBorrowedBooks(currentlyBorrowedBooks);
        List<BookRecord> currentlyReservedBooks =
            userService.toBookRecord(
                (List<HashMap<String, Object>>) userHitSourceAsMap.get("currentlyReservedBooks"));
        user.setCurrentlyReservedBooks(currentlyReservedBooks);
        userList.add(user);
      }

      // Randomize userList. I know Collection.shuffle would be a better implementation.
      for (int currentUserIndex = 0; currentUserIndex < userList.size(); currentUserIndex++) {
        int swapLocation = new Random().nextInt(userList.size());
        User currentUser = userList.get(currentUserIndex);
        User swappedUser = userList.get(swapLocation);
        userList.set(currentUserIndex, swappedUser);
        userList.set(swapLocation, currentUser);
      }

      System.out.println("Randomizing User List Complete");

      // Loop through each user. And check if anyone has to return a book
      for (User user : userList) {
        List<String> idsOfReturnedBooks = new ArrayList<>();
        List<BookRecord> currentlyBorrowedBooks =
            new ArrayList<BookRecord>(user.getCurrentlyBorrowedBooks());
        for (BookRecord currentlyBorrowedBook : currentlyBorrowedBooks) {
          if (new Date(currentlyBorrowedBook.getReturnDate()).equals(dateToday)) {
            bookAndActivityBulkRequest.add(
                this.createActivityRequest(
                    Action.CHECKEDIN.toString(),
                    dateToday,
                    user.getId(),
                    currentlyBorrowedBook.getBookId(),
                    library.getId()));
            bookAndActivityBulkRequest.add(
                this.updateBookRequest(currentlyBorrowedBook.getBookId(), dateTomorrow, true));
            idsOfReturnedBooks.add(currentlyBorrowedBook.getBookId());
          }
        }

        if (idsOfReturnedBooks.size() > 0) {
          List<BookRecord> bookRecords =
              user.getCurrentlyBorrowedBooks().stream()
                  .filter(br -> !idsOfReturnedBooks.contains(br.getBookId()))
                  .collect(Collectors.toList());
          user.setCurrentlyBorrowedBooks(bookRecords);
        } else {
          continue;
        }

        // If there are room to borrow more books then borrow it.
        List<String> removeThisFromReservedBooks = new ArrayList<>();
        if (user.getCurrentlyBorrowedBooks().size() < 10) {
          for (BookRecord bookRecord : user.getCurrentlyReservedBooks()) {
            if (user.getCurrentlyBorrowedBooks().size() >= 5) {
              break;
            }

            SearchSourceBuilder getBookByIdSearchSourceBuilder =
                new SearchSourceBuilder()
                    .query(
                        QueryBuilders.boolQuery()
                            .must(QueryBuilders.matchQuery("_id", bookRecord.getBookId()))
                            .must(QueryBuilders.matchQuery("isAvailableToBorrow", true)));

            SearchResponse bookResponse =
                elasticsearchClient.search(
                    new SearchRequest("book").source(getBookByIdSearchSourceBuilder),
                    RequestOptions.DEFAULT);

            if (bookResponse.getHits().getHits().length > 0
                && user.getCurrentlyReservedBooks().size() < 5) {

              bookAndActivityBulkRequest.add(
                  this.createActivityRequest(
                      Action.CHECKEDOUT.toString(),
                      dateToday,
                      user.getId(),
                      bookRecord.getBookId(),
                      library.getId()));

              bookAndActivityBulkRequest.add(
                  this.updateBookRequest(
                      bookRecord.getBookId(),
                      Date.from(
                          localDate
                              .plusDays(5)
                              .atStartOfDay()
                              .atZone(ZoneId.systemDefault())
                              .toInstant()),
                      false));

              List<BookRecord> bookRecords = user.getCurrentlyBorrowedBooks();
              bookRecords.add(
                  new BookRecord(
                      bookRecord.getBookId(),
                      Date.from(
                              localDate
                                  .plusDays(5)
                                  .atStartOfDay()
                                  .atZone(ZoneId.systemDefault())
                                  .toInstant())
                          .getTime()));

              user.setCurrentlyBorrowedBooks(bookRecords);

              removeThisFromReservedBooks.add(bookRecord.getBookId());
            }
          }
        }

        if (removeThisFromReservedBooks.size() > 0) {
          List<BookRecord> bookRecords =
              user.getCurrentlyBorrowedBooks().stream()
                  .filter(br -> !removeThisFromReservedBooks.contains(br.getBookId()))
                  .collect(Collectors.toList());
          user.setCurrentlyReservedBooks(bookRecords);
        }

        usersToday.put(user.getId(), user);
      } // END OF CHECK USER FOR MUST RETURN BOOKS

      System.out.println("Returning books complete!");

      int sizeOfUserToday = usersToday.size();
      int numBerOfTries = 0;
      if (usersToday.size() < 30) {
        while (sizeOfUserToday < 30 && numBerOfTries <= 59) {
          Random random = new Random();
          User randomUser = null;
          int randomIndex = random.nextInt(userList.size());
          randomUser = userList.get(randomIndex);
          if (!usersToday.containsKey(randomUser.getId())) {
            if (randomUser.getCurrentlyBorrowedBooks().size() < 10
                || randomUser.getCurrentlyReservedBooks().size() < 5) {
              boolean toBorrow = randomUser.getCurrentlyBorrowedBooks().size() < 10;
              boolean toReserve = randomUser.getCurrentlyReservedBooks().size() < 5;
              if (toBorrow) {
                SearchSourceBuilder searchSourceBuilder =
                    new SearchSourceBuilder()
                        .query(
                            QueryBuilders.boolQuery()
                                .must(QueryBuilders.matchQuery("isAvailableToBorrow", true)))
                        .size(10000);
                SearchResponse bookResponse =
                    elasticsearchClient.search(
                        new SearchRequest("book").source(searchSourceBuilder),
                        RequestOptions.DEFAULT);
                List<SearchHit> searchHits = Arrays.asList(bookResponse.getHits().getHits());
                Collections.shuffle(searchHits);
                BookRecord bookRecord =
                    new BookRecord(
                        searchHits.get(0).getId(),
                        Date.from(
                                localDate
                                    .plusDays(5)
                                    .atStartOfDay()
                                    .atZone(ZoneId.systemDefault())
                                    .toInstant())
                            .getTime());
                bookAndActivityBulkRequest.add(
                    this.createActivityRequest(
                        Action.CHECKEDOUT.toString(),
                        dateToday,
                        randomUser.getId(),
                        bookRecord.getBookId(),
                        library.getId()));
                bookAndActivityBulkRequest.add(
                    this.updateBookRequest(
                        bookRecord.getBookId(),
                        Date.from(
                            localDate
                                .plusDays(5)
                                .atStartOfDay()
                                .atZone(ZoneId.systemDefault())
                                .toInstant()),
                        false));
                List<BookRecord> currentlyBorrowedBooks = randomUser.getCurrentlyBorrowedBooks();
                currentlyBorrowedBooks.add(bookRecord);
                randomUser.setCurrentlyBorrowedBooks(currentlyBorrowedBooks);
                usersToday.put(randomUser.getId(), randomUser);
                sizeOfUserToday++;
              } else if (toReserve) {
                SearchSourceBuilder searchSourceBuilder =
                    new SearchSourceBuilder()
                        .query(
                            QueryBuilders.boolQuery()
                                .must(QueryBuilders.matchQuery("isAvailableToBorrow", false)))
                        .size(10000);
                SearchResponse bookResponse =
                    elasticsearchClient.search(
                        new SearchRequest("book").source(searchSourceBuilder),
                        RequestOptions.DEFAULT);
                List<SearchHit> searchHits = Arrays.asList(bookResponse.getHits().getHits());
                Collections.shuffle(searchHits);
                BookRecord bookRecord = new BookRecord(searchHits.get(0).getId(), null);
                bookAndActivityBulkRequest.add(
                    this.createActivityRequest(
                        Action.RESERVED.toString(),
                        dateToday,
                        randomUser.getId(),
                        bookRecord.getBookId(),
                        library.getId()));
                List<BookRecord> currentlyReservedBooks = randomUser.getCurrentlyReservedBooks();
                currentlyReservedBooks.add(bookRecord);
                randomUser.setCurrentlyReservedBooks(currentlyReservedBooks);
                usersToday.put(randomUser.getId(), randomUser);
                sizeOfUserToday++;
              }
            }
          }
          numBerOfTries++;
        }
      }

      System.out.println("Completed User setup!");

      BulkRequest userTodaysBulkRequest = new BulkRequest();
      for (Entry<String, User> entry : usersToday.entrySet()) {
        User user = entry.getValue();
        List<BookRecord> currentlyBorrowedBooks =
            user.getCurrentlyBorrowedBooks() == null
                ? null
                : new ArrayList<BookRecord>(user.getCurrentlyBorrowedBooks());
        List<BookRecord> currentlyReservedBooks =
            user.getCurrentlyReservedBooks() == null
                ? null
                : new ArrayList<BookRecord>(user.getCurrentlyReservedBooks());
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("firstName", user.getFirstName());

        jsonMap.put("currentlyBorrowedBooks", currentlyBorrowedBooks);
        jsonMap.put("currentlyReservedBooks", currentlyReservedBooks);

        userTodaysBulkRequest.add(
            new UpdateRequest("user", user.getId())
                .doc(new ObjectMapper().writeValueAsString(user), XContentType.JSON));
      }

      System.out.println("Completed UserBulkRequest!");

      if (!userTodaysBulkRequest.requests().isEmpty()) {
        elasticsearchClient.bulk(userTodaysBulkRequest, RequestOptions.DEFAULT);
      }

      if (!bookAndActivityBulkRequest.requests().isEmpty()) {
        elasticsearchClient.bulk(bookAndActivityBulkRequest, RequestOptions.DEFAULT);
      }

      localDate = localDate.plusDays(1);
      dateToday = dateTomorrow;

      System.out.println("Library is closed for the day!............\n");
    }

    System.out.println("Data Creation Complete!");
  }

  private IndexRequest createUserRequest(String firstName, String lastName)
      throws JsonProcessingException {
    User user = new User();
    user.setFirstName(firstName);
    user.setLastName(lastName);
    user.setCurrentlyBorrowedBooks(new ArrayList<BookRecord>());
    user.setCurrentlyReservedBooks(new ArrayList<BookRecord>());

    return new IndexRequest("user")
        .source(new ObjectMapper().writeValueAsString(user), XContentType.JSON);
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

  private IndexRequest createActivityRequest(
      String activityType, Date activityDate, String userId, String bookId, String libraryId) {
    return new IndexRequest("activity")
        .source(
            XContentType.JSON,
            "activityType",
            activityType,
            "activityDate",
            activityDate,
            "userId",
            userId,
            "bookId",
            bookId,
            "libraryId",
            libraryId);
  }

  private UpdateRequest updateBookRequest(
      String bookId, Date nextAvailability, Boolean isAvailableToBorrow) {
    UpdateRequest request = new UpdateRequest("book", bookId);

    Map<String, Object> jsonMap = new HashMap<>();
    jsonMap.put("nextAvailability", nextAvailability);
    jsonMap.put("isAvailableToBorrow", isAvailableToBorrow);

    return request.doc(jsonMap);
  }
}
