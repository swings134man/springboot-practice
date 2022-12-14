package boot.bootprac.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/************
* @info : DTO 역할의 Domain class
* @name : Member
* @date : 2022/08/05 2:05 AM
* @author : SeokJun Kang(swings134@gmail.com)
* @version : 1.0.0
 * 1. JPA 와 DB를 mapping 하는 클래스
 * 2. @Entity 어노테이션 사용하면 JPA 사용가능.
 * 3. @Id라는 것으로 PK 를 명시해줘야 함.
 * 4. @GeneratedValue -> PK즉 자동으로 ID를 자동으로 생성해줌 (Identity 전략) -> 오라클DB = 시퀀스
************/

@Entity
@Getter
@Setter
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String passWord;
    private String name;

    @Column(name = "activated")
    private boolean activated;

}
