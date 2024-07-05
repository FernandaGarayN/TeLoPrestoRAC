package cl.duoc.telopresto.web.apiclients.authboot;

import cl.duoc.telopresto.web.apiclients.common.BaseResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class AuthbootAuthErrorDecoder implements ErrorDecoder {
  private final ErrorDecoder defaultErrorDecoder = new Default();
  private final ObjectMapper objectMapper;

  @Override
  public Exception decode(String methodKey, feign.Response response) {
    String body = ""; // Inicializa el cuerpo como un string vacío
    if (response.body() != null) {
      try (InputStream inputStream = response.body().asInputStream();
           BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
        body = bufferedReader.lines().collect(Collectors.joining("\n"));
      } catch (IOException e) {
        log.error("Error al leer el cuerpo de la respuesta", e);
      }
    }

    log.debug("BODY ERROR: {}", body);
    log.error(String.format("Error to call method [%s]: [Status=%d] | [Body=%s]", methodKey, response.status(), response.body()));
    var failureMessage = "";
    try {
      var error = objectMapper.readValue(body, BaseResponse.class);
      failureMessage = translate(error.getFailure().message());
    } catch (IOException e) {
      log.error("Error al leer el cuerpo de la respuesta", e);
    }

    return switch (response.status()) {
      case 400 -> new AuthenticationServiceException("Solicitud incorrecta, " + failureMessage);
      case 401 -> new AuthenticationServiceException("Acceso no autorizado, " + failureMessage);
      case 403 -> new AuthenticationServiceException("Acceso prohibido, " + failureMessage);
      default -> defaultErrorDecoder.decode(methodKey, response);
    };
  }

  private static String translate(String originText) {
    return switch (originText) {
      case "Disabled user" -> "Usuario deshabilitado";
      case "Bad credentials" -> "Usuario y/o contraseña inválidos";
      default -> originText;
    };
  }
}
