package net.halflite.sqslambda;

import javax.inject.Inject;
import javax.inject.Named;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;

@Named("sqs.receive")
public class SqsLambda implements RequestHandler<SQSEvent, Void> {

  private final ProcessingService service;

  @Override
  public Void handleRequest(SQSEvent event, Context context) {
    event.getRecords().forEach(service::recieveMessage);
    return null;
  }

  @Inject
  public SqsLambda(ProcessingService service) {
    this.service = service;
  }
}
