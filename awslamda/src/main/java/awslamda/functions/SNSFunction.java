package awslamda.functions;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;

public class SNSFunction{

    public String handleRequest(SNSEvent event, Context context) {
        context.getLogger().log("Received event: " + event);
        String message = event.getRecords().get(0).getSNS().getMessage();
        context.getLogger().log("From SNS: " + message);
        return message;
    }
}
