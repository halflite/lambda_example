package net.halflite.sqslambda;

import javax.enterprise.context.ApplicationScoped;
import com.amazonaws.services.lambda.runtime.events.SQSEvent.SQSMessage;

@ApplicationScoped
public class ProcessingService {

  public void recieveMessage(SQSMessage message) {
    // NONE
  }
}
