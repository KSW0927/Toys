<h1 align="center">👋3년차 Java 개발자 김석원입니다.</h1>
<div align= "center">
    <h2 align="center" style="border-bottom: 1px solid #d8dee4; color: #282d33;">🛠️Tech Stacks</h2><br> 
    <div style="margin: 0 auto; align="center"> <img src="https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=Git&logoColor=white">
          <img src="https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=HTML5&logoColor=white">
          <img src="https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=Java&logoColor=white">
          <img src="https://img.shields.io/badge/Javascript-F7DF1E?style=for-the-badge&logo=Javascript&logoColor=white">
          <br/><img src="https://img.shields.io/badge/MariaDB-003545?style=for-the-badge&logo=MariaDB&logoColor=white">
          <img src="https://img.shields.io/badge/React-61DAFB?style=for-the-badge&logo=React&logoColor=white">
          <img src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=Spring&logoColor=white">
    </div>
</div>

<h3 align="center">공공데이터 포털에 전국의 명소 데이터를 기반으로 Spring Boot + React + JPA를 활용한 토이프로젝트입니다.</h3>

<br><br>

<h3 align="left">프로젝트 목표 및 개발 중점 사항</h3><br>
<p align="left">
    1. Back-end로는 서비스 기업의 표준인 <strong>Spring Boot</strong> 와 <strong>JPA</strong>를 활용하여 <strong>RESTful API</strong> 설계 및 ORM 기반의 개발 방식을 숙련하는 데 집중.<br>
    2. Front-end로는 React를 통해 컴포넌트 기반 UI 개발과 SPA 환경에서의 비동기 통신 경험.
</p>

<br><br>

<h3 align="left">추후 개선 사항</h3><br>
<p align="left">
    1. 기존 코드의 리팩토링 및 보완(상시)<br>
    2. Swagger 연동(2025.10.25 완료)<br>
    3. 회원가입 및 Docker + Redis + JWT 를 활용한 로그인 및 로그 아웃 기능 구현(10.26 ~ 11.02 완료)<br>
    4. 캐싱도입 
</p>

<br><br>

<h3 align="left">1. 전역 예외 처리 구현</h3><br>
이 프로젝트의 전역 예외 처리 구조는 저의 공공 SI 프로젝트 구축 및 운영 경험에서 비롯된 필요성에 의해 구현하였습니다.
<br><br>
다수의 개발자가 참여하는 SI 환경에서 개별적인 try-catch 블록 사용은 예외 처리 방식의 일관성을 크게 저하시켰습니다.
<br><br>
이로 인해 개발 및 유지보수 과정에서 오류 디버깅 시간이 지연되고, 코드의 응집성이 저하되어 시스템 파악 및 수정에 어려움이 있어
<br><br>
'예외 처리도 공통화 했으면 좋겠다' 라는 생각에서 시작해 설계하였으며 유지보수성과 확장성을 최우선으로 고려하여
<br><br>
예외 처리의 공통화를 토이 프로젝트의 핵심 설계 목표 중 하나로 설정했습니다.

<br><br>

<h3 align="left">2. API 응답 구조</h3>
전역 예외 처리 도입 후, 성공 응답에 대한 일관성을 확보하는 것이 다음 목표였습니다.<br><br>
SI 프로젝트에서 API를 개발할 때, 응답 데이터 필드가 API마다 제각각이었습니다. 예를 들어, 어떤 API는 핵심 데이터를 response.result로 반환했지만,<br><br>
다른 API는 response.data로 반환하는 등 속성명 자체가 통일되지 않았습니다.<br><br>
이로 인해 클라이언트 개발 시 API를 호출할 때마다 응답 구조를 확인하고 다른 파싱 로직을 적용해야 하는 비효율성이 발생했습니다.<br><br>
그래서 전역 예외 처리 이후 API 응답 구조 또한 통일되도록 설계하였습니다.

<br><br>

<h3 align="center">4. CORS 처리</h3><br>
<img style="width: 100%;" height="356" alt="image" src="https://github.com/user-attachments/assets/8ae6c16d-2eb4-426a-ba81-f944f863df1b" />

<br><br>

<h3 align="center">5. CSRF 처리</h3><br>
<img style="width: 100%;" height="173" alt="image" src="https://github.com/user-attachments/assets/bd74a18b-555c-4156-bd0f-5c39199645aa" />





    
    
    
