package beyond.board.author.repository;

import beyond.board.author.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
//    findBy 컬럼명의 규칙으로 자동으로 where 조건문을 사용한 쿼리 생성
//    그외: findByNameAndEmail(where name and email), findByNameOREmail(where name or email)
//    findByAgeBetween(int start, int end) start에서 end사이의 숫자 조회
//    findByLessThan(int age), findByAgeGreaterThan(int age), findByAgeGreaterThanEqual(int age)포함관계
//    findByAgeIsNull, findByAgeIsNotNull
//    List<Author> findByNameIsNull();
//    findAllOrderByEmail();
    Optional<Author> findByEmail(String email);
}
