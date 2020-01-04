package net.halflite.sqslambda.service;

import java.io.IOException;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.lambda.runtime.events.SQSEvent.SQSMessage;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.DeleteMessageResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.halflite.sqslambda.dto.MessageDto;

@ApplicationScoped
public class ProcessingService {
  /** logger */
  private static final Logger LOG = LoggerFactory.getLogger(ProcessingService.class);

  private final ObjectMapper objectMapper;
  private final AWSCredentialsProvider credentialsProvider;

  public void execute(SQSEvent event) {
    event.getRecords().forEach(this::executeMessage);
  }

  protected void executeMessage(SQSMessage message) {
    LOG.info("execute start:{}", message);
    this.recieveMessage(message);
    this.deleteMessage(message);
    LOG.info("execute complete, message ID:{}", message.getMessageId());
  }

  /**
   * メッセージを受信してログに出力します
   * 
   * @param message
   */
  protected void recieveMessage(SQSMessage message) {
    try {
      MessageDto dto = this.objectMapper.readValue(message.getBody(), MessageDto.class);
      LOG.info("{}", dto);
    } catch (IOException e) {
      LOG.warn(e.getMessage(), e);
    }
  }

  /**
   * メッセージを削除します
   * 
   * @param message
   */
  protected void deleteMessage(SQSMessage message) {
    AmazonSQS sqs = this.createFromSQSMessage(message);
    String queueName = message.getEventSourceArn().split(":")[5];
    String queueUrl = sqs.getQueueUrl(queueName).getQueueUrl();
    String receiptHandle = message.getReceiptHandle();
    DeleteMessageResult result = sqs.deleteMessage(queueUrl, receiptHandle);

    LOG.info("deteted. result:{}", result);
  }

  protected AmazonSQS createFromSQSMessage(SQSMessage message) {
    String region = message.getAwsRegion();
    return AmazonSQSClientBuilder.standard()
        .withCredentials(credentialsProvider)
        .withRegion(region)
        .build();
  }

  @Inject
  public ProcessingService(ObjectMapper objectMapper, AWSCredentialsProvider credentialsProvider) {
    this.objectMapper = objectMapper;
    this.credentialsProvider = credentialsProvider;
  }
}
