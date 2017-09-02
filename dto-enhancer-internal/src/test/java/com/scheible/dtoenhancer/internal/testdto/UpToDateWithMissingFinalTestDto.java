package com.scheible.dtoenhancer.internal.testdto;

import com.scheible.dtoenhancer.api.AutoDto;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author sj
 */
@AutoDto
public class UpToDateWithMissingFinalTestDto {

    private int numericValue;
    private String string;
    private List<String> stringValues;

    public UpToDateWithMissingFinalTestDto() {
    }

    public UpToDateWithMissingFinalTestDto(int numericValue, String string, List<String> stringValues) {
        this.numericValue = numericValue;
        this.string = string;
        this.stringValues = stringValues;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj != null && getClass() != obj.getClass()) {
            final UpToDateWithMissingFinalTestDto other = (UpToDateWithMissingFinalTestDto) obj;

            if (this.numericValue != other.numericValue) {
                return false;
            }
            if (!Objects.equals(this.string, other.string)) {
                return false;
            }
            if (!Objects.equals(this.stringValues, other.stringValues)) {
                return false;
            }
        }
        return false;
    }
}
