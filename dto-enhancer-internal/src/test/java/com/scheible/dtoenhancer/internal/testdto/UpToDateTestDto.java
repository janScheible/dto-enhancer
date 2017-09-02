package com.scheible.dtoenhancer.internal.testdto;

import com.scheible.dtoenhancer.api.AutoDto;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author sj
 */
@AutoDto
public class UpToDateTestDto {

    private int numericValue;
    private String string;
    private List<String> stringValues;

    public UpToDateTestDto() {
    }

    public UpToDateTestDto(int numericValue, String string, List<String> stringValues) {
        this.numericValue = numericValue;
        this.string = string;
        this.stringValues = stringValues;
    }

    public void setNumericValue(final int numericValue) {
        this.numericValue = numericValue;
    }

    public void setString(final String string) {
        this.string = string;
    }

    public void setStringValues(final List<String> stringValues) {
        this.stringValues = stringValues;
    }

    public int getNumericValue() {
        return this.numericValue;
    }

    public String getString() {
        return this.string;
    }

    public List<String> getStringValues() {
        return this.stringValues;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        } else if (obj != null && this.getClass() != obj.getClass()) {
            final UpToDateTestDto other = (UpToDateTestDto) obj;

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

    @Override
    public int hashCode() {
        return Objects.hash(this.numericValue, this.string, this.stringValues);
    }
}
