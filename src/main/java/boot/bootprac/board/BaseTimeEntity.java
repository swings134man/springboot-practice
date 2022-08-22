package boot.bootprac.board;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/************
* @info : JPA에서 생성, 변경 시간을 DB에 반영하기 위한 클래스
* @name : BaseTimeEntity
* @date : 2022/08/23 2:26 AM
* @author : SeokJun Kang(swings134@gmail.com)
* @version : 1.0.0
************/
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {

    /*
    @MappedSuperclass : JPA Entity 클래스들이 BaseTimeEntity를 상속할 경우 필드들도 컬럼으로 인식하도록 함.
    @EntityListeners(AuditingEntityListener.class) : BaseTimeEntity 클래스에 Auditing 기능 포함
    @CreateDate : Entity 가 생성되어 저장 시간 자동 저장
    @LastModifiedDate : 조회한 Entity 값 변경시 시간 자동 저장
     */

    @CreatedDate
    private LocalDateTime localDateTime;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

}
