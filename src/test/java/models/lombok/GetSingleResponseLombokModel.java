package models.lombok;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetSingleResponseLombokModel {
    private UserData data;
    private UserSupport support;
}
