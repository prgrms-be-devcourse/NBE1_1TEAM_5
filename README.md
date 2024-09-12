# NBE1_1TEAM_5

---

# ☕️ 커피 주문 서비스

> Spring Boot 기반의 커피 주문 서비스입니다.

---

## 📋 프로젝트 소개

Spring Boot를 이용해 만든 커피 주문 서비스입니다. REST API 기반으로 관리자는 상품을 관리하고, 사용자는 상품을 주문할 수 있습니다. JWT 인증 방식을 적용해 사용자 로그인 및 권한을 관리하고, AOP를 이용한 성능 측정 기능을 제공합니다.

---

## 🌐 GitHub

- [GitHub - prgrms-be-devcourse/NBE1_1TEAM_5](https://github.com/prgrms-be-devcourse/NBE1_1TEAM_5)

---

## 🛠 개발 환경

| **구성 요소**        | **설명**                |
|--------------------|------------------------|
| **JDK**            | Java 17                |
| **프레임워크**     | Spring Boot 2.x.x      |
| **DB**             | MySQL                  |
| **빌드 도구**      | Maven                  |
| **IDE**            | IntelliJ IDEA          |
| **기술 및 라이브러리** | JDBC Template, Lombok, AOP, JWT, Thymeleaf, Spring Security |
| **협업 도구**      | Notion, GitHub, Slack  |

---

## 🗂 ERD

### **ERD URL**

- [ERD Cloud]![스크린샷 2024-09-06 오전 11.16.00.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/bd96b46c-359f-495d-8a5b-33df5e09796a/9e95668c-d92d-45d7-841a-b1d22535d436/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2024-09-06_%E1%84%8B%E1%85%A9%E1%84%8C%E1%85%A5%E1%86%AB_11.16.00.png)

### **1차 ERD**
![1차 ERD](https://prod-files-secure.s3.us-west-2.amazonaws.com/bd96b46c-359f-495d-8a5b-33df5e09796a/9e95668c-d92d-45d7-841a-b1d22535d436/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2024-09-06_%E1%84%8B%E1%85%A9%E1%84%8C%E1%85%A5%E1%86%AB_11.16.00.png)

### **2차 ERD**
![2차 ERD](https://prod-files-secure.s3.us-west-2.amazonaws.com/bd96b46c-359f-495d-8a5b-33df5e09796a/c1d592f3-4ef9-4ce3-8d73-01a5c747363b/Image_9-12-24_at_14.42.jpeg)

---

## 👥 사용자 시나리오

### **관리자**
- 상품 목록 조회
- 상품 카테고리별 조회
- 상품 등록
- 상품 수정
- 상품 삭제
- 주문 내역 삭제 (배송 완료 후 가능)

### **사용자**
- 회원 가입
- 회원 정보 조회
- 회원 정보 수정
- 회원 삭제
- 로그인 (JWT 토큰 사용)
- 주문 생성 (로그인한 사용자만 가능)
- 주문 조회 (로그인한 사용자만 가능)
- 주문 취소 (배송 시작 전까지 가능)
- 주문 내역 삭제 (주문 상태가 '취소' 또는 '완료'일 때만 가능)

---

## 🛠 기능 설명

### **회원 관리**
- 회원 가입
- 회원 정보 조회 (`@AuthenticatedPrincipal` 사용)
- 회원 정보 수정
- 회원 삭제
- 로그인 기능 (JWT 토큰 사용)
- 회원 리스트 조회

### **상품 관리 (관리자)**
- 상품 목록 조회
- 상품 카테고리별 조회
- 상품 등록
- 상품 수정
- 상품 삭제 (주문에 포함되지 않은 상품만 삭제 가능)

### **주문 관리**
- 주문 생성 (로그인한 사용자만 가능)
- 주문 조회 (로그인한 사용자만 가능)
- 주문 취소 (배송 시작 전까지 가능)
- 주문 내역 삭제 (주문 상태가 '취소' 또는 '완료'일 때만 가능)

---

## 🧾 API 명세서

관리자는 HTTP API를 통해 관리 기능에 접근하며, 일반 사용자는 REST API를 통해 데이터를 처리할 수 있습니다.

---

## 🚀 추가 기능 설명

- **회원 관리**: 회원 가입, 로그인, 회원 정보 수정, 회원 삭제 등.
- **일반 사용자/관리자 로그인**: 사용자 권한에 따른 기능 제어.
- **AOP를 이용한 메서드 실행 시간 측정**: 성능 이슈 발견 및 최적화.
- **주문 상태에 따른 제한**: 주문 생성, 취소, 삭제 기능의 조건부 제어.
- **응답 통일 및 예외처리**: 일관된 API 응답 형식 및 예외 처리.

---

## 📝 커밋 메시지 컨벤션

커밋 메시지는 **영어 대문자**로 작성합니다.

| 커밋 유형           | 의미                                             |
|---------------------|--------------------------------------------------|
| `Feat`              | 새로운 기능 추가                                 |
| `Fix`               | 버그 수정                                        |
| `Docs`              | 문서 수정                                        |
| `Style`             | 코드 포맷팅, 세미콜론 누락 등                    |
| `Refactor`          | 코드 리팩토링                                    |
| `Test`              | 테스트 코드 추가                                 |
| `Chore`             | 기타 변경사항 (예: .gitignore 수정)              |
| `Design`            | CSS 등 UI 디자인 변경                            |
| `Comment`           | 주석 추가 및 변경                                |
| `Rename`            | 파일 또는 폴더 이름 수정                         |
| `Remove`            | 파일 삭제                                        |
| `!BREAKING CHANGE`  | 커다란 API 변경 사항                              |
| `!HOTFIX`           | 치명적인 버그 긴급 수정                          |

### **브랜치 이름 컨벤션**
이슈 번호를 기반으로 브랜치를 나누어 작업합니다.

- 예시: `feat/#13` (이슈 번호 13번, 새로운 기능 추가)

---

## 🤝 협업 도구

- **Notion**: 프로젝트 관리 및 문서화
- **GitHub**: 버전 관리 및 코드 협업
- **Slack**: 팀원 간 소통 및 실시간 협업


