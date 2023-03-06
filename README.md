# springboot-practice

## Skill Set
- Spring Boot
- Spring Security 
- Spring Batch
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
3. Spring JWT 
4. Spring - Docker - AWS CI/CD 파이프라인 구축
5. Spring Boot Log 추적기 
