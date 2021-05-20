# 코르크 (CorC)
`자율프로젝트` `기업연계`

## 프로젝트 소개

블록체인 기반의 경비 관리 시스템 구축 프로젝트입니다. 정합성 검증을 위해 블록체인에 데이터를 추가 작성합니다. 기존의 법인카드 사용, 영수증 제출, 검증 프로세스를 개선하여 결제 시 실시간 검증되는 시스템 구축을 목표로 합니다.

## 구현 기능
- 경비관리 시스템(Web)
    - 사용자, 가맹점 등 관리 기능
    - 로그 및 통계 조회 기능
- 모바일 지갑 서비스(App)
    - QR 결제 기능
    - 한도 및 결제 내역 조회 기능
- 가맹점 전용 관리 서비스(App)
    - 가맹점 정보 등록 및 신청 기능
    - 정산계좌 등록 및 변경 기능
    - 판매 품목 등록 기능
    - 이용내역 조회 기능
    - 결제 기능


## 작업 룰

Commit Rule
```bash
type(타입) : title(제목)

body(본문, 생략 가능)

See also : #issue, ...(참고 이슈, 생략 가능)
##################################################
    types = {
      feat : 새로운 기능에 대한 커밋
      fix : 버그 수정에 대한 커밋
      build : 빌드 관련 파일 수정에 대한 커밋
      chore : 그 외 자잘한 수정에 대한 커밋
      ci : CI관련 설정 수정에 대한 커밋
      docs : 문서 수정에 대한 커밋
      style : 코드 스타일 혹은 포맷 등에 관한 커밋
      refactor :  코드 리팩토링에 대한 커밋
      test : 테스트 코드 수정에 대한 커밋
    }

ex)
feat: 로그인 기능 구현 / fix: 회원가입 수정
```

브랜치 관리
```bash
(master) -> (develop) -> (feature/frontend(backend)/feature명)
```
- master : 배포 가능한 상태로 유지
- develop : 개발용 최상위 branch
- feature : 기능별 branch

## 팀원 소개

- **이혜진** - *PM, Frontend*
- **곽지원** - *Backend*
- **윤현수** - *Frontend, Backend*
- **정지환** - *Blockchain*
- **주정훈** - *Backend, Server*
 
## Backend

<img alt="Spring" src="https://img.shields.io/badge/spring%20-%236DB33F.svg?&style=for-the-badge&logo=spring&logoColor=white"/>

<img alt="Maven" src="https://img.shields.io/badge/maven%20-%2335495e.svg?&style=for-the-badge&logo=maven&logoColor=%234FC08D"/>

<img alt="JPA" src="https://img.shields.io/badge/jpa%20-%23009639.svg?&style=for-the-badge&logo=jpa&logoColor=white"/>


```shell
cd backend
./mvnw clean package
cd target
java -jar (생성된 jar 파일)
```

