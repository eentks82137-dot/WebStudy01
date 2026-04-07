# Exception

## THROWABLE이란?

Throwable : 모든 exception과 error의 최상위 타입

### 종류

- Exception : 일반적으로 개발자가 처리할 수 있는 예외 상황을 나타냄
- Error : 시스템 수준의 문제를 나타내며, 일반적으로 개발자가 처리할 수
  없는 상황을 나타냄 (예: OutOfMemoryError)

## Exceptions

### 1. Checked Exception

발생 가능한 상황이 있으면 반드시 예외 처리를 강제하는 예외 타입

- IOException
- SQLException ...

### 2. Unchecked Exception

발생 가능한 상황이 있더라도 예외 처리를 강제하지 않는 예외 타입 (기본적으로 회피할 수 있는 예외 상황)

- NullPointerException
- IllegalArgumentException ...

## 예외 처리 방법

1. 예외 회피 : throws로 메소드 호출자에게 예외를 떠넘기는 방식
2. 예외 전환 : try-catch 블록에서 예외의 종류를 변환하여 던지는 구조
   ex) checked exception을 unchecked exception으로 전환하여 던지거나, 발생한 예외를 custom exception으로 전환하여 던지는 방식

## Custom Exception

### 1. 예외의 종류 결정

- checked exception : extends Exception
- unchecked exception : extends RuntimeException

### 2. 상황에 맞는 커스텀 예외를 생성하고 throw

- AuthenticationException : 인증 실패 시 발생하는 예외
- UsernameNotFoundException : 사용자를 찾을 수 없는 경우 extends AuthenticationException
- BadCredentialsException : 비밀번호가 일치하지 않는 경우 extends AuthenticationException
