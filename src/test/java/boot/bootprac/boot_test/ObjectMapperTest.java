package boot.bootprac.boot_test;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

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


    @DisplayName("Json To Java Obj")
    @Test
    void jsonToObj() throws JsonProcessingException {
        System.out.println("Json To Java Obj TEST START!!");
        System.out.println("-----------------------------");

        // given
        String json = "{\"name\":\"lucas\",\"age\":29}";
        this.objectMapper = new ObjectMapper();

        // when
        Member member = objectMapper.readValue(json, Member.class);

        // then
        System.out.println("member = " + member);
        System.out.println("-----------------------------");

        System.out.println("member.name = " + member.getName());
        System.out.println("member.age = " + member.getAge());
    }

    @DisplayName("Json Array To Java List")
    @Test
    void jsonArrayToList() throws JsonProcessingException {
        System.out.println("JsonArray To List TEST START!!");
        System.out.println("-----------------------------");

        // given
        String json = "[{\"name\":\"hong\",\"age\":29},{\"name\":\"kim\",\"age\":30}]";
        this.objectMapper = new ObjectMapper();

        // when
        List<Member> members = objectMapper.readValue(json, new TypeReference<>() {});

        // then
        System.out.println("members = " + members);
        System.out.println("-----------------------------");

        System.out.println("members index 0 = " + members.get(0));
        Assertions.assertThat(members.get(0).getName()).isEqualTo("hong");
    }

    @DisplayName("Json To Java Map")
    @Test
    void jsonToMap() throws JsonProcessingException {
        System.out.println("Json To Java Map TEST START!!");
        System.out.println("-----------------------------");

        // given
        String json = "{\"name\":\"lucas\",\"age\":29}";
        this.objectMapper = new ObjectMapper();

        // when
        Map<String, Object> map = objectMapper.readValue(json, new TypeReference<>() {});

        // then
        Assertions.assertThat(map.get("name")).isEqualTo("lucas");
        System.out.println("map = " + map);
    }

}
