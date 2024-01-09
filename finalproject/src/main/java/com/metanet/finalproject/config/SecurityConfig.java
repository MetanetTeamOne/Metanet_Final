package com.metanet.finalproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.metanet.finalproject.jwt.JwtAuthenticationFilter;
import com.metanet.finalproject.jwt.JwtTokenProvider;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

   @Bean
   PasswordEncoder passwordEncoder() {
      return PasswordEncoderFactories.createDelegatingPasswordEncoder();
   }

   @Bean
   JwtTokenProvider jwtTokenProvider() {
      return new JwtTokenProvider();
   }

   @Bean
   JwtAuthenticationFilter authenticationFilter() {
      return new JwtAuthenticationFilter(jwtTokenProvider());
   }


   /*@Bean
   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
      http.csrf((csrfConfig) -> csrfConfig.disable());
      // 로그인 폼에 대한 설정
      http.formLogin((formLogin) -> formLogin
            // 로그인 뷰 페이지에 대해서 명시를 합니다.
            .loginPage("/login")
            // Spring security는 기본적으로 세션을 사용하여 로그인을 구현한다.
            // 이전에서 다른 로그인 방식을 구현했을 때는 DB와 데이터를 직접적으로 비교하는
            // 코드를 작성했었는데,
            // password를 DB pwd와 비교하는 코드가 따로 나와있지 않은데,
            // UserDetailsService -> loadUserByUsername메서드를 통해서
            // 객체를 생성하여 반환할 때 Spring security 내부적으로
            // username에 대하여 사용자 정보, password를 비교하게 된다.
            // 이때 사용하는 userid에 대해서 사용할 파라미터, 매개변수를 명시한는 코드이
            .usernameParameter("studentId")
            // 로그인 성공했을 때 이동할 페이지 명시
            .defaultSuccessUrl("/loginSuccess"))

            // 로그아웃에 대해서 설정하는 곳.
            .logout(logout -> logout
                  // 로그아웃 뷰 페이지에 대해서 명시를 합니다.
                  .logoutUrl("/logout")
                  // 로그아웃에 성공했을 때 이동할 페이지에 대해서 명시
                  .logoutSuccessUrl("/logoutSuccess")
                  // 세션 인증 정보에 대해서 로그아웃시 비활성화합니다.
                  .invalidateHttpSession(true));

      http.authorizeHttpRequests()
      .requestMatchers("/config/**").hasRole("ADMIN")
      .requestMatchers("/mypage/**", "/lecturecomment/**").hasAnyRole("USER")
      .requestMatchers("/**", "/css/**", "/js/**", "/image/**").permitAll()
      .requestMatchers("/signin", "/login").permitAll();
      // 빌터 패턴을 통해서 http 객체를 빌드하고 반환합니다.
      return http.build();
   }*/

   @Bean
   SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
      http.csrf((csrf)->csrf.disable());

      // 토큰을 사용하는 경우 인가를 적용한 URI 설정
      http.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
//              .requestMatchers("/file/**").hasRole("ADMIN")
              .requestMatchers("/member/insert", "/member/emailCheck", "/member/signup_ok").permitAll()
              .requestMatchers("/member/**", "/orders/**").hasAnyRole("USER", "ADMIN")
              .requestMatchers("/**", "/css/**", "/js/**", "/images/**", "/kakao/**").permitAll()
              .requestMatchers("/v3/api-docs/**","/swagger-ui/**").permitAll()
              .requestMatchers("/").permitAll());

      // Session 기반의 인증기반을 사용하지 않고 추후 JWT를 이용하여서 인증 예정
      http.sessionManagement((session) -> session
              .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

      // Spring Security JWT 필터 로드
      http.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider()),
              UsernamePasswordAuthenticationFilter.class);

      http.formLogin(form ->form
              .loginPage("/logins")
              .permitAll()
      );

      return http.build();
   }

//   @Bean
//   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
//      http.csrf((csrfConfig) -> csrfConfig.disable());
//      // 로그인 폼에 대한 설정
//      http.formLogin((formLogin) -> formLogin
//            // 로그인 뷰 페이지에 대해서 명시를 합니다.
//            .loginPage("/login")
//            // Spring security는 기본적으로 세션을 사용하여 로그인을 구현한다.
//            // 이전에서 다른 로그인 방식을 구현했을 때는 DB와 데이터를 직접적으로 비교하는
//            // 코드를 작성했었는데,
//            // password를 DB pwd와 비교하는 코드가 따로 나와있지 않은데,
//            // UserDetailsService -> loadUserByUsername메서드를 통해서
//            // 객체를 생성하여 반환할 때 Spring security 내부적으로
//            // username에 대하여 사용자 정보, password를 비교하게 된다.
//            // 이때 사용하는 userid에 대해서 사용할 파라미터, 매개변수를 명시한는 코드이
//            .usernameParameter("studentId")
//            // 로그인 성공했을 때 이동할 페이지 명시
//            .defaultSuccessUrl("/loginSuccess"))
//
//            // 로그아웃에 대해서 설정하는 곳.
//            .logout(logout -> logout
//                  // 로그아웃 뷰 페이지에 대해서 명시를 합니다.
//                  .logoutUrl("/logout")
//                  // 로그아웃에 성공했을 때 이동할 페이지에 대해서 명시
//                  .logoutSuccessUrl("/logoutSuccess")
//                  // 세션 인증 정보에 대해서 로그아웃시 비활성화합니다.
//                  .invalidateHttpSession(true));
//
//      http.authorizeHttpRequests()
//      .requestMatchers("/config/**").hasRole("ADMIN")
//      .requestMatchers("/mypage/**", "/lecturecomment/**").hasAnyRole("USER")
//      .requestMatchers("/**", "/css/**", "/js/**", "/image/**").permitAll()
//      .requestMatchers("/signin", "/login").permitAll();
//      // 빌터 패턴을 통해서 http 객체를 빌드하고 반환합니다.
//      return http.build();
//   }
//
////   @Bean
////   @ConditionalOnMissingBean(UserDetailsService.class)
////   public InMemoryUserDetailsManager userDetailsService() {
////      List<UserDetails> userDetailsList = new ArrayList<>();
////      userDetailsList.add(User.withUsername("foo")
////            .password("{noop}demo")
////            .roles("ADMIN").build());
////      userDetailsList.add(User.withUsername("bar")
////            .password("{noop}demo")
////            .roles("USER").build());
////      userDetailsList.add(User.withUsername("ted")
////            .password("{noop}demo")
////            .roles("ADMIN","USER").build());
////      InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager(userDetailsList);
////      return manager;
////   }
//
//   // 자동으로 로그인 폼에서 사용자가 입력한 비밀번호를 암호화하는 메서드를 @Bean 애너테이션을 통해 빈 컨테이너에 등록한 것.
//   @Bean
//   PasswordEncoder passwordEncoder() {
//      return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//   }
//}

   // 자동으로 로그인 폼에서 사용자가 입력한 비밀번호를 암호화하는 메서드를 @Bean 애너테이션을 통해 빈 컨테이너에 등록한 것.
}