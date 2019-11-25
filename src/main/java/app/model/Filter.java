package app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Filter {

    private String id;
    private String category;
    private String priority;
    private String body;

    public static Filter getNonEmptyFilter(String id, String category, String priority, String body) {
        return new Filter(
                Optional.ofNullable(id).orElse(StringUtils.EMPTY),
                Optional.ofNullable(category).orElse(StringUtils.EMPTY),
                Optional.ofNullable(priority).orElse(StringUtils.EMPTY),
                Optional.ofNullable(body).orElse(StringUtils.EMPTY));
    }

}


