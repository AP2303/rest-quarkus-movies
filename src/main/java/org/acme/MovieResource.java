package org.acme;


import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/movies")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MovieResource {

    @Inject
    MovieRepository movieRepository;

    @GET
    @RolesAllowed("admin")
    public List<Movie> getMovies() {
        return movieRepository.listAll();
    }

    @GET
    @Path("/size")
    @Produces(MediaType.TEXT_PLAIN)

    public long countMovies() {
        return movieRepository.count();
    }

    @POST
    @Transactional

    public Response createMovie(Movie newMovie) {
        movieRepository.persist(newMovie);
        return Response.status(Response.Status.CREATED).entity(newMovie).build();
    }

    @PUT
    @Path("/{id}/{title}")
    @Transactional

    public Response updateMovie(@PathParam("id") Long id,
                                @PathParam("title") String title) {
        Movie movie = movieRepository.findById(id);
        if (movie == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        movie.title = title;
        return Response.ok(movie).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional

    public Response deleteMovie(@PathParam("id") Long id) {
        boolean deleted = movieRepository.deleteById(id);
        if (deleted) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }


}
