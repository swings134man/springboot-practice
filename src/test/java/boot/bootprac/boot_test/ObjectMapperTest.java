package boot.bootprac.boot_test;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

public class ObjectMapperTest {

    private ObjectMapper objectMapper = null;

    static class Member {
        private String name;
        private int age;

        public Member() {
        }

        public Member(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "Member{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }


    @Test
    void jsonToObj() throws JsonProcessingException {
        System.out.println("Json To Java Obj TEST START!!");
        System.out.println("-----------------------------");

        // given
        String json = "{\"name\":\"seokjun\",\"age\":29}";
        this.objectMapper = new ObjectMapper();

        // when
        Member member = objectMapper.readValue(json, Member.class);

        // then
        System.out.println("member = " + member);
        System.out.println("-----------------------------");

        System.out.println("member.name = " + member.getName());
        System.out.println("member.age = " + member.getAge());

    }

}
