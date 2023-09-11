package org.gs.Transacao;

import java.net.URI;
import java.net.URISyntaxException;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.bson.types.ObjectId;

@Path("/transacoes")
public class TransacaoResource {

    @Inject
    TransacaoRepository repository;

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") String id) {
        Transacao transacao = (Transacao) repository.findById(new ObjectId(id));
        return Response.ok(transacao).build();
    }

    @GET
    public Response get() {
        return Response.ok(repository.listAll()).build();
    }

    @GET
    @Path("/search/{name}")
    public Response search(@PathParam("name") String name) {
        Transacao transacao = (Transacao) repository.findByName(name);
        return transacao != null ? Response.ok(transacao).build()
                : Response.status(Status.NOT_FOUND).build();
    }

    @POST
    public Response create(Transacao transacao) throws URISyntaxException {
        repository.persist(transacao);
        return Response.created(new URI("/" + transacao.id)).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") String id, Transacao transacao) {
        transacao.id = new ObjectId(id);
        repository.update(transacao);
        return Response.ok(transacao).build();

    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") String id) {
        Transacao transacao = (Transacao) repository.findById(new ObjectId(id));
        repository.delete(transacao);
        return Response.noContent().build();
    }
}
