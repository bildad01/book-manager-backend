# 📚 Book-manager
KT AIVLE School 4차 미니 프로젝트 :  **Spring Boot + React 기반 도서 관리 및 AI 표지 생성 시스템**

# Backend 
- 서연우, 육정민
- 김민아 (PM + support)

---
# JDBC
jdbc URL : jdbc:h2:~/librarydata <br/>
username : sa <br/>
password : 1234 <br/>

---

## Book 엔티티 클래스 정의
	BookId (PK) //아이디 
	Title //제목
	Content //내용
	CreatedAt //생성일자
	UpdatedAt //수정일자
	coverImageUrl //이미지url
 ---
 ### 주요 디렉토리  구조  
 ```
├── config
│   └── WebConfig.java
├── controller        
│   └── BookController.java
├── dto              
│   └── BookDto.java
├── domain          
│   └── Book.java
├── repository       
│   └── BookRepository.java
├── exception             
│   └── ResourceNotFoundException.java
├── service             
│   └── BookService.java
│   └── BookServiceImpl.java
└── BookManagementApplication.java  
```

 ---

### BookController.java

//도서(Book)에 대한 CRUD 기능을 제공 <br/>
//도서 관리 기능을 제공하는 REST API 컨트롤러로, <br/>
//클라이언트는 이를 통해 책을 추가, 조회, 수정, 삭제할 수 있음.<br/>

``` 
// 도서를 신규 등록  
@PostMapping
    public Book createBook(@RequestBody Book book){
        book.setCreatedAt(LocalDateTime.now());
        return bookService.insertBook(book);
    }

// 특정 ID의 도서 조회
    @GetMapping("/{id}")
    public Book findBook(@PathVariable Long id){
        return bookService.findBook(id);
    }

//특정 도서 수정
    @PutMapping("/{id}")
    public Book updateBook(@PathVariable Long id , @RequestBody Book book){
        book.setUpdatedAt(LocalDateTime.now());
        return bookService.updateBook(id, book);
    }

// 특정 ID의 도서 삭제
    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable Long id) {
        return "도서 삭제 성공!";
    }

// 모든 도서의 목록 조회
    @GetMapping
    public List<Book> findAllBook(){
        return bookService.findAllBook();
    }
```

---

### Book.java
//도서 정보를 표현 <br/>
// JPA 가 클래스를 기반으로 book 테이블을 자동 생성 <br/>

``` @Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Book {

//테이블의 기본 키, DB에서 자동으로 증가되는 값으로 설정,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

//DB 컬럼, null을 허용하지 않음, 최대 길이 100자
    @Column(nullable = false, length = 100)
    private String title;

//DB칼럼, DB에 저장될때 Text타입으로 명시, 값을 비워둘 수 없음.
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;


//도서 표지 이미지 URL을 저장, 길이는 255자
    @Column(length = 255)
    private String coverImageUrl;

//생성 시간,
    @Column
    private LocalDateTime createdAt;

//수정시간
    @Column(name = "updated_at", nullable = true)
    private LocalDateTime updatedAt;

// 작가, null값 허용하지 않음.
   @Column(nullable = false)
    private String author;

}
```

---

### BookDTO.java
//클라이언트와의 통신에 사용이 됨. <br/>
//API목적(등록, 수정, 조회)등에 따라 정리된 내부 정적 클래스로 구성 <br/>

``` 
public class BookDTO {

    // [POST] 도서 등록 요청 
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Post {
        @NotBlank(message = "제목은 필수 입력 값입니다.")
        @Size(max = 100, message = "제목은 100자 이하여야 합니다.")
        private String title;

        @NotBlank(message = "내용은 필수 입력 값입니다.")
        private String content;

        private String coverImageUrl;

        @NotBlank(message = "저자 이름은 필수 입력 값입니다")
        private String author;
    }

    // 🛠 [PUT] 도서 수정 요청
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Put {
        @NotBlank(message = "제목은 필수 입력 값입니다.")
        @Size(max = 100, message = "제목은 100자 이하여야 합니다.")
        private String title;

        @NotBlank(message = "내용은 필수 입력 값입니다.")
        private String content;

        @NotBlank(message = "저자 이름은 필수 입력 값입니다")
        private String author;
    }

    // [GET] 단일 도서 조회 응답
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long id;
        private String title;
        private String content;
        private String coverImageUrl;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private String author;
    }

    // [GET] 도서 목록 요약 응답
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Summary {
        private Long id;
        private String title;
        private String coverImageUrl;
        private LocalDateTime createdAt;
        private String author;
    }

    // [POST] AI 표지 생성 응답
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CoverResponse {
        private String coverImageUrl;
    }
``` 
---

### BookService
// 비지니스 로직 처리 <br/>
``` 
public interface BookService {
    //도서등록
    Book insertBook(Book book);

    //도서상세조회
    Book findBook(Long id);

    //도서수정
    Book updateBook(Long id, Book book);

    //도서삭제
    void deleteBook(Long id);

    //도서전체목록조회 - 리스트 형태로 조회
    List<Book> findAllBook();

}
``` 

---

###  BookServiceImpl.java
//BookService 인터페이스를 구현한 클래스 <br/>
// 실제 비즈니스 로직을 수행하는 서비스 구현체 <br/>

``` 
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override //도서등록 
    public Book insertBook(Book book) {
        return bookRepository.save(book);
    }

    @Override //도서상세조회 - id를 기준으로 도서를 조회, 없을 경우 EntityNotFoundException 발생
    public Book findBook(Long id) {
        return bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("책을 찾을 수 없습니다")
        );
    }

    @Override //도서수정 
    public Book updateBook(Long id, Book book) {
        Book b = bookRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("책을 찾을 수 없습니다")
        );
        b.setTitle(book.getTitle());
        b.setContent(book.getContent());
        b.setCreatedAt(book.getCreatedAt());
        b.setUpdatedAt(book.getUpdatedAt());
        b.setCoverImageUrl(book.getCoverImageUrl());
        return bookRepository.save(b);
    }

    @Override //도서삭제
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    @Override //도서전체목록조회
    public List<Book> findAllBook() {
        return bookRepository.findAll();
    }
}
``` 
