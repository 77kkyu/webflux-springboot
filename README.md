# Description
- auth : 77kkyu
- development environment : spring-boot
- technology : webflux
---

### Hot, Cold
Hot, Cold의 개념은 RxJava에도 있습니다


Cold는 각 Flux, Mono를 subscribe 할 때마다 매번 독립적으로 새로 데이터를 생성해서 동작합니다
subscribe호출 전까지 아무런 동작도 하지 않고 subscribe를 호출하면 새로운 데이터를 생성합니다
기본적으로 Hot을 취급하는 연산자가 아닌 이상 Flux, Mono는 Cold로 동작합니다

Hot은 구독하기 전부터 데이터의 스트림이 동작할 수 있습니다,
예를 들어 마우스 클릭이나 키보드 입력 같은 이벤트 성은 구독여부와 상관없이 발생하고 있다가
이 이벤트를 구독하는 여러 구독자가 붙으면 해당 이벤트가 발생할 때 모두 동일한 값을 전달받을 수 있습니다
, 즉 Hot에 해당하는 스트림을 여러 곳에서 구독을 하면 현재 스트림에서 나오는 값을 구독하는 구독자들에게
동일하게 받을 수 있습니다

또 Cold를 Hot으로 바꿀 수 있는 연산자가 있습니다

