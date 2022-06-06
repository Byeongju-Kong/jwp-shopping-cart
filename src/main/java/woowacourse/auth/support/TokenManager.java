package woowacourse.auth.support;

public interface TokenManager {
    String createToken(Long payload);

    String getPayload(String token);

    boolean validateToken(String token);
}
