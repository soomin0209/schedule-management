# 🗓️ 일정 관리 앱 만들기
## 📘 API 명세서
🔗 [API 명세서 보기](https://documenter.getpostman.com/view/50332844/2sBXVbJZui)

## 🗂️ ERD

<img width="695" height="376" alt="ERD" src="https://github.com/user-attachments/assets/3d1a4f67-409f-483f-ae8e-dd4be6201410" />

## ⚙️ 기술 스택
- **Language**: Java 17  
- **Framework**: Spring Boot 4.0.1, Spring Web  
- **ORM**: JPA (Hibernate)  
- **Database**: MySQL 8.4.7
- **Build Tool**: Gradle  
- **Tools**: Postman, dbdiagram.io  
- **Version Control**: Git, GitHub

## 🚀 구현 기능
<details>
<summary><strong> 필수 기능 </strong></summary>
<br>
  
- **일정 생성**
  - `일정 제목`, `일정 내용`, `작성자명`, `비밀번호`, `작성일`, `수정일`을 저장
- **선택 일정 조회**
  - 일정의 고유 식별자(ID)를 사용하여 조회
- **전체 일정 조회**
  - `작성자명`을 기준으로 등록된 일정 목록을 전부 조회
  - `수정일` 기준 내림차순으로 정렬
- **일정 수정**
  - 선택한 일정 내용 중 `일정 제목`, `작성자명`만 수정 가능
  - 서버에 일정 수정을 요청할 때 `비밀번호`를 함께 전달
  - `작성일`은 변경할 수 없으며, `수정일`은 수정 완료 시, 수정한 시점으로 변경
- **일정 삭제**
  - 서버에 일정 삭제을 요청할 때 `비밀번호`를 함께 전달

</details>

<details>
<summary><strong> 도전 기능 </strong></summary>
<br>
  
- **댓글 생성**
  - `댓글 내용`, `작성자명`, `비밀번호`, `작성일`, `수정일`, `일정 고유식별자(ID)`를 저장
  - 하나의 일정에는 댓글을 10개까지만 작성 가능
- **일정 단건 조회 업그레이드**
  - 해당 일정의 댓글 포함
- **유저의 입력에 대한 검증 수행**
  - `일정 제목`은 최대 30자 이내로 제한, 필수값 처리
  - `일정 내용`은 최대 200자 이내로 제한, 필수값 처리
  - `댓글 내용`은 최대 100자 이내로 제한, 필수값 처리
  - `비밀번호`, `작성자명`은 필수값 처리

</details>

<details>
<summary><strong> 공통 </strong></summary>
<br>
  
- `3 Layer Architecture`에 따라 각 Layer의 목적에 맞게 개발
- CRUD 필수 기능은 모두 데이터베이스 연결 및 JPA를 사용해서 개발해야 함
- 모든 API 응답에 `비밀번호`는 제외
- 일정/댓글 생성 시,
  - `작성/수정일`은 날짜와 시간을 모두 포함한 형태
  - 각 일정/댓글의 고유 식별자(ID)를 자동으로 생성하여 관리
  - 최초 생성 시, `수정일`은 `작성일`과 동일
  - `작성일`, `수정일` 필드는 `JPA Auditing`을 활용하여 적용
- 일정 수정/삭제 시, 선택한 일정의 `비밀번호`와 요청할 때 함께 보낸 `비밀번호`가 일치할 경우에만 가능

</details>
