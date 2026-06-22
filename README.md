Artlog Backend

Artlog는 전시 관람 경험, 작품 기록, 감상문을 개인적으로 아카이빙하고 공개 감상을 공유할 수 있는 문화예술 기록 서비스입니다.

사용자는 관람한 전시를 기록하고, 전시 안에서 인상 깊었던 작품을 저장하며, 전시 또는 작품에 대한 감상을 작성할 수 있습니다. 또한 공개로 설정된 감상 기록은 다른 사용자도 조회할 수 있으며, 좋아요, 북마크, 댓글 기능을 통해 다른 사용자의 감상에 반응할 수 있습니다.

⸻

프로젝트 목적

Artlog는 단순한 전시 관람 메모장이 아니라, 사용자가 자신의 문화예술 경험을 체계적으로 축적하고 되돌아볼 수 있도록 하는 것을 목표로 합니다.

주요 목적은 다음과 같습니다.

* 전시 관람 기록 관리
* 작품별 감상 기록 관리
* 개인 감상 아카이브 구축
* 공개 감상 기록 조회
* 좋아요, 북마크, 댓글 기반 사용자 반응 기능 제공
* 월별 관람 횟수, 자주 방문한 미술관, 감정 태그 등 통계 제공
* Docker 기반 배포 환경 구성
* AWS EC2, RDS를 활용한 실제 서버 배포 경험 확보
* GitHub Actions 기반 CI/CD 자동 배포 파이프라인 구축

⸻

기술 스택

Backend

* Java 17
* Spring Boot
* Spring Security
* Spring Data JPA
* JWT
* Gradle
* Swagger / Springdoc OpenAPI
* Spring Boot Actuator

Database

* MySQL
* AWS RDS MySQL

Infrastructure

* AWS EC2
* AWS RDS
* Docker
* Docker Compose
* Nginx 기반 Frontend 컨테이너 연동

CI/CD

* GitHub Actions
* SSH 기반 EC2 자동 배포
* Docker Compose 기반 컨테이너 재빌드 및 재시작

⸻

주요 기능

회원 기능

* 회원가입
* 로그인
* JWT 발급
* 내 회원 정보 조회
* 닉네임 수정
* 비밀번호 변경

전시 기록 기능

* 전시 기록 등록
* 전시 기록 목록 조회
* 전시 기록 상세 조회
* 전시 기록 수정
* 전시 기록 삭제
* 전시 기록 검색 및 필터링
* 전시 기록 페이징 조회

작품 기록 기능

* 특정 전시에 작품 기록 등록
* 작품 기록 목록 조회
* 작품 기록 상세 조회
* 작품 기록 수정
* 작품 기록 삭제
* 작품 기록 검색 및 필터링
* 작품 기록 페이징 조회

감상 기록 기능

* 전시 감상 기록 등록
* 작품 감상 기록 등록
* 감상 기록 목록 조회
* 감상 기록 상세 조회
* 감상 기록 수정
* 감상 기록 삭제
* 감상 기록 검색 및 필터링
* 공개 / 비공개 설정
* 다중 이미지 등록

공개 감상 기능

* 공개 감상 기록 목록 조회
* 공개 감상 기록 상세 조회
* 공개 감상 좋아요 수 조회
* 공개 감상 북마크 수 조회
* 공개 감상 댓글 조회

좋아요 / 북마크 / 댓글 기능

* 공개 감상 기록 좋아요
* 좋아요 취소
* 공개 감상 기록 북마크
* 북마크 취소
* 내 북마크 목록 조회
* 공개 감상 댓글 작성
* 공개 감상 댓글 삭제

통계 기능

* 전체 전시 기록 수 조회
* 전체 작품 기록 수 조회
* 전체 감상 기록 수 조회
* 평균 평점 조회
* 월별 관람 횟수 조회
* 자주 방문한 미술관 조회
* 평점 분포 조회
* 감정 태그 통계 조회

이미지 업로드 기능

* 전시 포스터 이미지 업로드
* 작품 이미지 업로드
* 감상 이미지 업로드
* 다중 이미지 업로드
* 업로드 이미지 URL 반환
* 서버 내 업로드 디렉터리 볼륨 마운트
* 정적 리소스 URL 접근 지원

⸻

배포 구조

현재 Artlog는 AWS EC2 서버에서 Docker Compose 기반으로 배포되어 있습니다.

사용자
  ↓
EC2 Public IP
  ↓
Frontend Container - Nginx + React
  ↓
Backend Container - Spring Boot
  ↓
AWS RDS MySQL

배포 환경

* Frontend: React 빌드 파일을 Nginx 컨테이너에서 서빙
* Backend: Spring Boot 애플리케이션을 Docker 컨테이너로 실행
* Database: AWS RDS MySQL 사용
* File Upload: EC2 내부 디렉터리를 Docker Volume으로 마운트하여 업로드 이미지 유지
* API 통신: Frontend에서 Backend API 서버로 HTTP 요청
* CORS: 배포된 Frontend Origin 허용

⸻

CI/CD

GitHub Actions를 사용하여 main 브랜치에 코드가 반영되면 EC2 서버에 자동 배포되도록 구성했습니다.

Backend 배포 흐름

main 브랜치 push 또는 merge
  ↓
GitHub Actions 실행
  ↓
EC2 서버 SSH 접속
  ↓
Backend Repository 최신 코드 pull
  ↓
Docker Compose로 Backend 이미지 재빌드
  ↓
Backend 컨테이너 재시작
  ↓
Actuator Health Check 수행

GitHub Actions 주요 작업

* EC2 SSH 접속
* git pull origin main
* docker compose build backend
* docker compose up -d backend
* curl http://localhost:8080/actuator/health 기반 배포 확인
* 사용하지 않는 Docker 이미지 정리

⸻

API 문서

로컬 실행 후 아래 주소에서 Swagger API 문서를 확인할 수 있습니다.

http://localhost:8080/swagger-ui.html

배포 서버에서는 아래 주소에서 확인할 수 있습니다.

http://13.125.206.86:8080/swagger-ui.html

⸻

Health Check

Spring Boot Actuator를 통해 서버 상태를 확인할 수 있습니다.

http://localhost:8080/actuator/health

배포 서버에서는 아래 주소를 사용합니다.

http://13.125.206.86:8080/actuator/health

정상 응답 예시는 다음과 같습니다.

{
  "status": "UP"
}

⸻

로컬 실행 방법

1. 환경변수 설정

.env 또는 .env.docker 파일을 생성하고 다음 값을 설정합니다.

DB_URL=jdbc:mysql://localhost:3306/artlog
DB_USER=root
DB_PASS=비밀번호
JWT_SECRET=JWT_SECRET_KEY
JWT_EXPIRATION=3600000
JPA_DDL_AUTO=update
SERVER_PORT=8080

2. Gradle 실행

./gradlew bootRun

3. Docker Compose 실행

docker compose up --build

⸻

배포 관련 트러블슈팅 경험

프로젝트를 AWS EC2와 RDS 환경에 배포하는 과정에서 다음 문제를 직접 해결했습니다.

* RDS 보안 그룹 설정
* EC2에서 RDS MySQL 접속 설정
* RDS 데이터베이스 미생성 문제 해결
* Docker Compose 기반 Backend / Frontend 컨테이너 실행
* EC2 메모리 부족으로 인한 빌드 지연 문제 해결
* Swap 메모리 추가
* CORS Origin 설정 문제 해결
* Frontend API Base URL 배포 환경 적용
* 이미지 업로드 파일 유지를 위한 Docker Volume 설정
* GitHub Actions SSH 배포 키 설정
* GitHub Actions에서 EC2 접근을 위한 보안 그룹 설정

⸻

프로젝트 의의

Artlog Backend는 단순 API 서버 구현을 넘어, 실제 배포 환경을 고려한 백엔드 애플리케이션입니다.

Spring Security와 JWT를 활용한 인증/인가, JPA 기반 데이터 관리, 이미지 업로드, 공개 감상 반응 기능, 통계 기능을 구현했습니다. 또한 AWS EC2, RDS, Docker Compose, GitHub Actions를 사용하여 개발 환경을 실제 운영 환경에 가깝게 확장했습니다.

이를 통해 백엔드 API 개발뿐만 아니라 배포, 데이터베이스 연동, 서버 운영, CI/CD 자동화까지 전체 서비스 흐름을 경험했습니다.
