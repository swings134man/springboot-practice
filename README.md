# springboot-practice

## About Repository 
> Spring Boot 환경에 익숙해지기 위해, 활용 할 수 있는 다양한 Library, 방법들을
>  
> 공부, 적용 해보기 위한 Repositroy 입니다. 


## Skill Set
- Spring Boot
- JPA
- Spring Security 
- Spring Batch
- JWT OATUH 2.0
- AWS EC2
- AWS RDS
- Docker 
- Github Actions

## Repository 
- Spring Boot의 여러가지 활용 및 security, batch 적용. 
- Docker Image를 사용한 AWS EC2 자동 빌드 + 테스트, 배포
- CI/CD 파이프라인은 docker 및 github Actions로 구성.


## Flow
1. GitHub Master Branch push, PR 감지.
2. GitHUb Actions flow에 따라서 실행. 
3. Spring Boot 빌드 -> Docker Image 생성 -> 
4. AWS EC2 배포 (Docker) -> EC2내부 기존 컨테이너 중지/삭제 ->
5. 새로운 Docker Image pull/run 

## Currently Test List
1. Spring Boot Excel 
2. Spring File Server 
3. Spring JWT (Server, Client Side)
4. Spring - Docker - AWS CI/CD 파이프라인 구축
5. Spring Boot Log 추적기 


### TIPS
1. jasypt option : --jasypt.encryptor.password=testkey