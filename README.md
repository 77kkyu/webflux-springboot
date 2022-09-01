# Description
- auth : 77kkyu
- development environment : spring-boot
- technology : webflux
---

``` text
회사에서 webflux를 사용하게 되어 간단하게 혼자 사용해 보았습니다 
처음에는 stream API랑 비슷하다고 생각했었는데 많아 달랐습니다 
특히 대량의 데이터를 병렬 처리할 때 스레드 관리, 디버깅이 조금 힘들었습니다 
이슈 중에는 스레드 3개를 사용해서 딜레이를 주어가며 병렬로 처리하는 기능을 만들었는데
처음에는 기능이 잘 동작하다가도 어느 순간 되면 딜레이를 무시하는 경우도 있었고
아직 미숙한 게 많아 더 깊은 공부가 필요할 것 같습니다
```
### WebFlux
Spring MVC에서는 Thread에서 요청을 처리하게 된다(Thread 개수는 200개)
요청이 들어오면 Thread하나를 잡고 처리하게 되고 해당 Thread는 다른 호출이 왔을때 그동안 block이 되고
처리가 끝나면 block을 풀고 진행합니다

WebFlux는 EventLoop 방식으로 처리를 합니다(Thread 개수는 Core의 개수 * 2)
작업은 Event로 처리가 되어 API호출될 때 block되지 않고 다른 Thread에서 받아서 이어서 작업을 한다

webflux가 느린 이유
- log()함수 제거
    - 로그를 남기는 역할이지만 실제로는 blocking I/O를 일으키기 때문에 성능 저하가 일어난다
- map() 사용 지향
    - map() 메서드는 연산마다 객체를 생성하고 GC의 대상이 많아진다   , 동기식 함수
    - flatMap() 메서드 사용, 비동기식 함수
- lettuce 설정 (lettuce는 네티 기반으로 NIO의 비동기로 동작)
    - Connection validation 시, synchronous 동작
    - command 실행 마다 ping command를 synchronous로 실행

개선사항 .parallel()
- Reactor Meltdown
    - Event Loop의 Thread들이 Blocking API때문에 Reactor 시스템이 Hang 걸리는 현상
    - Bolocking API를 위한 별도의 Thread Pool로 격리시키는 방법
    - subcribeOn(), publishOn()
---
      ex)
      .publishOn(*) // blocking 코드를 위한 별도의 Thread Pool을 전달
      .map(x -> x)
      .flatMap(*)
---
    .map(x -> x)
    .subscribeOn(Schedulers.parallel())
    .flatMap(*)


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

