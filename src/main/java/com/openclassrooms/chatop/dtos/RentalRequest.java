package com.openclassrooms.chatop.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RentalRequest {
    @Size(max = 255, message = "Le nom ne doit pas dépasser 255 caractères.")
    private String name;

    @Min(value = 9, message = "La superficie du bien doit être au minimum de 9m2.")
    private Integer surface;

    @Min(value = 1, message = "Le prix doit au moins être d'un euro symbolique.")
    private Integer price;

    @Size(max = 255, message = "L'url de l'image ne doit pas dépasser 255 caractères.")
    private String picture;

    @Size(max = 2000, message = "la description ne doit pas dépasser 2000 caractères.")
    private String description;

    @NotNull(message = "Le propriétaire doit figurer.")
    private Integer owner_id;
}
