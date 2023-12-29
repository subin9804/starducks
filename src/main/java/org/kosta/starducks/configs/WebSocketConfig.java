package org.kosta.starducks.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration //설정 클래스 어노테이션
@EnableWebSocketMessageBroker // 웹소켓 메시지 브로커 활성화
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  /**
   * STOMP 엔드포인트를 등록하는
   * @param registry
   */
  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    // 웹소켓 연결을 위한 엔드포인트. 이것을 통해서 웹소켓 연결이 시작된다. 예시 : https://github.com/chat
    // 주로 쓰이는 경로 /ws, /websocket, /connect
    registry.addEndpoint("/chat")
        //배포해서 서버에서도 사용할 생각이면 http들도 추가해야한다.
        .setAllowedOriginPatterns("http://*", "https://*")
        //웹소켓이 지원되지 않는 브라우저에서도 사용할 수 있도록 해준다.
        .withSockJS();
  }

  /**
   * 메시지 브로커를 구성하는 메소드. 위처럼 github.com/chat처럼 사용되는 주소들이 아니고
   * STOMP를 통해 서버 내에서만 사용이 가능한 주소가 따로 생성된다.
   * 네트워크를 열어보면 subscribe 등등으로 따로 생성이 된 것을 볼 수 있다.
   * topic, app은 내부에서 따로 쓰이는 경로여서 주소창에서 쓰이지 않는다.
   * @param registry
   */
  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
    // 클라이언트가 이 주소를 구독하고 있게 된다. 서버가 여기에 새로운 정보를 추가하면 실시간으로 받게 됨
    // 주로 쓰이는 경로 /broadcast, /pub, /public
    registry.enableSimpleBroker("/topic");
    // 클라이언트가 서버로 메시지를 보내는 주소
    // 주로 쓰이는 경로 /send, /private, /user
    registry.setApplicationDestinationPrefixes("/app");
  }
}