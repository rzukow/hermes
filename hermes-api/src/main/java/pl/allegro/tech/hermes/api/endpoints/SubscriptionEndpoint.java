package pl.allegro.tech.hermes.api.endpoints;

import pl.allegro.tech.hermes.api.Subscription;
import pl.allegro.tech.hermes.api.SubscriptionMetrics;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("topics/{topicName}/subscriptions")
public interface SubscriptionEndpoint {
    @GET
    @Produces(APPLICATION_JSON)
    List<String> list(@PathParam("topicName") String qualifiedTopicName);

    @POST
    @Consumes(APPLICATION_JSON)
    Response create(@PathParam("topicName") String qualifiedTopicName,
                    Subscription subscription);

    @GET
    @Produces(APPLICATION_JSON)
    @Path("/{subscriptionName}")
    Subscription get(@PathParam("topicName") String qualifiedTopicName,
                     @PathParam("subscriptionName") String subscriptionName);

    @GET
    @Produces(APPLICATION_JSON)
    @Path("/{subscriptionName}/state")
    Subscription.State getState(@PathParam("topicName") String qualifiedTopicName,
                                @PathParam("subscriptionName") String subscriptionName);

    @GET
    @Produces(APPLICATION_JSON)
    @Path("/{subscriptionName}/undelivered/last")
    Response getLatestUndeliveredMessage(@PathParam("topicName") String qualifiedTopicName,
                                @PathParam("subscriptionName") String subscriptionName);
    @GET
    @Produces(APPLICATION_JSON)
    @Path("/{subscriptionName}/undelivered")
    Response getLatestUndeliveredMessages(@PathParam("topicName") String qualifiedTopicName,
                                          @PathParam("subscriptionName") String subscriptionName);

    @GET
    @Produces(APPLICATION_JSON)
    @Path("/{subscriptionName}/metrics")
    SubscriptionMetrics getMetrics(@PathParam("topicName") String qualifiedTopicName,
                                   @PathParam("subscriptionName") String subscriptionName);

    @PUT
    @Consumes(APPLICATION_JSON)
    @Path("/{subscriptionName}/state")
    Response updateState(@PathParam("topicName") String qualifiedTopicName,
                         @PathParam("subscriptionName") String subscriptionName,
                         Subscription.State state);

    @DELETE
    @Path("/{subscriptionName}")
    Response remove(@PathParam("topicName") String qualifiedTopicName,
                    @PathParam("subscriptionName") String subscriptionName);

    @PUT
    @Consumes(APPLICATION_JSON)
    @Path("/{subscriptionName}")
    Response update(@PathParam("topicName") String qualifiedTopicName,
                    @PathParam("subscriptionName") String subscriptionName,
                    Subscription subscription);

    @PUT
    @Consumes(APPLICATION_JSON)
    @Path("/{subscriptionName}/retransmission")
    Response retransmit(@PathParam("topicName") String qualifiedTopicName,
                        @PathParam("subscriptionName") String subscriptionName,
                        Long timestamp);

    @GET
    @Produces(APPLICATION_JSON)
    @Path("/{subscriptionName}/events/{messageId}/trace")
    Response getMessageTrace(@PathParam("topicName") String qualifiedTopicName,
                             @PathParam("subscriptionName") String subscriptionName,
                             @PathParam("messageId") String messageId);
}
