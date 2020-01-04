package net.halflite.sqslambda.config;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

@ApplicationScoped
public class AppConfig {
  /** logger */
  private static final Logger LOG = LoggerFactory.getLogger(AppConfig.class);

  @Produces
  @ApplicationScoped
  public ObjectMapper objectMapper() {
    // snake_case JSON Mapper
    return new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
  }

  @Produces
  @ApplicationScoped
  public AWSCredentialsProvider credentialsProvider(
      @ConfigProperty(name = "aws.access.key.id") String accessKey,
      @ConfigProperty(name = "aws.secret.access.key") String secretKey) {
    LOG.debug("aws.access.key:{}, aws.secret.key:{}", accessKey, secretKey);
    AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
    return new AWSStaticCredentialsProvider(credentials);
  }
}
