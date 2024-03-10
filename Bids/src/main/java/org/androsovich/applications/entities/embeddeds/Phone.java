package org.androsovich.applications.entities.embeddeds;

import com.google.gson.annotations.SerializedName;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Phone {
    @SerializedName(value = "country_code")
    private String countryCode;

    @SerializedName(value = "city_code")
    private String cityCode;

    @SerializedName(value = "phone")
    private String phone;

    @Transient
    @SerializedName(value = "type")
    private String type;
}
