package net.halflite.sqslambda;

import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import net.halflite.sqslambda.service.ProcessingService;

@Named("sqs-receive")
public class AppHandler implements RequestHandler<SQSEvent, Void> {
  /** logger */
  private static final Logger LOG = LoggerFactory.getLogger(AppHandler.class);

  @Inject
  public ProcessingService service;

  @Override
  public Void handleRequest(SQSEvent event, Context context) {
    LOG.info("start.");
    service.execute(event);
    LOG.info("complete.");
    return null;
  }
}
