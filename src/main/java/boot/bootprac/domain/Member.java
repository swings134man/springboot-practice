package boot.bootprac.domain;

/************
* @info : DTO 역할의 Domain class
* @name : Member
* @date : 2022/08/05 2:05 AM
* @author : SeokJun Kang(swings134@gmail.com)
* @version : 1.0.0
************/
public class Member {

    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
