package org.acme;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Entity
@Schema(name = "Movie", description = "Movie Representation")
public class Movie extends PanacheEntity {

    @Schema(hidden = true)  // Fsheh id në OpenAPI
    @JsonProperty(access = JsonProperty.Access.READ_ONLY) // id vetëm në output, jo në input
    public Long id;

    @Schema(required = true)
    public String title;
}
